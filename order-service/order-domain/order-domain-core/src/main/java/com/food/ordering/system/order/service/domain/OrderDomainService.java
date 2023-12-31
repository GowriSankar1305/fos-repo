package com.food.ordering.system.order.service.domain;

import java.util.List;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restraunt;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderDomainService {
	OrderCreatedEvent validateAndInitiateOrder(Order order,Restraunt restraunt);
	OrderPaidEvent payOrder(Order order);
	OrderCancelledEvent cancelOrderPayment(Order order,List<String> failureMessages);
	void approveOrder(Order order);
	void cancelOrder(Order order,List<String> falureMessages);
}
