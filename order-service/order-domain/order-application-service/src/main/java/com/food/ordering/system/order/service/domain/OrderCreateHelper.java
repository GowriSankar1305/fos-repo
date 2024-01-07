package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestrauntRepository;

public class OrderCreateHelper {
	
	private final OrderDomainService orderDomainService;
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final RestrauntRepository restrauntRepository;
	private final OrderDataMapper orderDataMapper;
	
	public OrderCreateHelper(OrderDomainService orderDomainService, OrderRepository orderRepository,
			CustomerRepository customerRepository, RestrauntRepository restrauntRepository,
			OrderDataMapper orderDataMapper) {
		this.orderDomainService = orderDomainService;
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.restrauntRepository = restrauntRepository;
		this.orderDataMapper = orderDataMapper;
	}
	
	public OrderCreatedEvent 
}
