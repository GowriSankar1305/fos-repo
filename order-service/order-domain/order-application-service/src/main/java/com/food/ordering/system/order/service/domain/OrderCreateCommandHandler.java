package com.food.ordering.system.order.service.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restraunt;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestrauntRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderCreateCommandHandler {
	
	private final OrderDomainService orderDomainService;
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final RestrauntRepository restrauntRepository;
	private final OrderDataMapper orderDataMapper;
	private final OrderCreatedPaymentRequestMessagePublisher createdPaymentRequestMessagePublisher;
	
	public OrderCreateCommandHandler(OrderDomainService orderDomainService, OrderRepository orderRepository,
			CustomerRepository customerRepository, RestrauntRepository restrauntRepository,
			OrderDataMapper orderDataMapper,
			OrderCreatedPaymentRequestMessagePublisher createdPaymentRequestMessagePublisher) {
		this.orderDomainService = orderDomainService;
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.restrauntRepository = restrauntRepository;
		this.orderDataMapper = orderDataMapper;
		this.createdPaymentRequestMessagePublisher = createdPaymentRequestMessagePublisher;
	}

	@Transactional
	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand)	{
		checkCustomer(createOrderCommand.getCustomerId());
		Restraunt restraunt = checkRestraunt(createOrderCommand);
		Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
		OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restraunt);
		Order savedOrder = saveOrder(order);
		createdPaymentRequestMessagePublisher.publish(orderCreatedEvent);
		return orderDataMapper.orderToCreateOrderResponse(savedOrder,"Order created successfully");
	}

	private Order saveOrder(Order order)	{
		Order orderResult = orderRepository.save(order);
		if(orderResult == null)	{
			log.error("Could not save order!");
			throw new OrderDomainException("Could not save order!");
		}
		log.info("Order is saved with id {}",orderResult.getId().getValue());
		return orderResult;
	}
	
	private Restraunt checkRestraunt(CreateOrderCommand createOrderCommand) {
		Restraunt restraunt = orderDataMapper.createOrderCommandToRestraunt(createOrderCommand);
		Optional<Restraunt> restrauntOptional = restrauntRepository.findRestrauntInformation(restraunt);
		if(restrauntOptional.isEmpty())	{
			log.warn("restraunt not found with id {}",createOrderCommand.getRestrauntId());	
			throw new OrderDomainException("Could not find restraunt with restraunt id: " + createOrderCommand.getRestrauntId());
		}
		return restrauntOptional.get();
	}

	private void checkCustomer(UUID customerId) {
		Optional<Customer> customer = customerRepository.findCustomer(customerId);
		if(customer.isEmpty())	{
			log.warn("customer not found with id :{}",customerId);
			throw new OrderDomainException("customer does not exist with id: " + customerId);
		}
	}
}
