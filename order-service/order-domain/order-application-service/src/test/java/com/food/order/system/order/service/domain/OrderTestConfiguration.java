package com.food.order.system.order.service.domain;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.food.ordering.system.order.service.domain.OrderDomainService;
import com.food.ordering.system.order.service.domain.OrderDomainServiceImpl;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restrauntapproval.OrderPaidRestrauntRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestrauntRepository;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {
	
	@Bean
	public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher()	{
		return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
	}
	
	@Bean
	public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher()	{
		return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
	}
	
	@Bean
	public OrderPaidRestrauntRequestMessagePublisher orderPaidRestrauntRequestMessagePublisher()	{
		return Mockito.mock(OrderPaidRestrauntRequestMessagePublisher.class); 
	}
	
	@Bean
	public OrderRepository orderRepository() {
		return Mockito.mock(OrderRepository.class);
	}
	
	@Bean
	public RestrauntRepository restrauntRepository()	{
		return Mockito.mock(RestrauntRepository.class);
	}
	
	@Bean
	public CustomerRepository customerRepository()	{
		return Mockito.mock(CustomerRepository.class);
	}
	
	@Bean
	public OrderDomainService orderDomainService()	{
		return new OrderDomainServiceImpl();
	}
}
