package com.food.ordering.system.order.service.domain.event;

import java.time.ZonedDateTime;

import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.order.service.domain.entity.Order;

public class OrderCreatedEvent implements DomainEvent<Order> {
	
	private final Order order;
	private final ZonedDateTime createdTime;
	
	public Order getOrder() {
		return order;
	}
	
	public ZonedDateTime getCreatedTime() {
		return createdTime;
	}
	
	public OrderCreatedEvent(Order order, ZonedDateTime createdTime) {
		super();
		this.order = order;
		this.createdTime = createdTime;
	}
}
