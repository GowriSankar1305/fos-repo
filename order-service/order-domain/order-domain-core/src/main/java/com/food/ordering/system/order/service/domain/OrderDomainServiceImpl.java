package com.food.ordering.system.order.service.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restraunt;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

	@Override
	public OrderCreatedEvent validateAndInitiateOrder(Order order, Restraunt restraunt) {
		validateRestraunt(restraunt);
		setOrderProductInformation(order,restraunt);
		order.validateOrder();
		order.initializeOrder();
		log.info("Order with id: {} is initiated",order.getId().getValue());
		return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	private void setOrderProductInformation(Order order,Restraunt restraunt) {
		order.getItems().forEach(orderItem -> restraunt.getProducts().forEach(restrauntProduct -> {
			Product currenProduct = orderItem.getProduct();
			if(currenProduct.equals(restrauntProduct))	{
				currenProduct.updateWithConfirmedNameAndPrice
				(restrauntProduct.getName(),restrauntProduct.getPrice());
			}
		}));
	}

	private void validateRestraunt(Restraunt restraunt) {
		if(!restraunt.isActive())	{
			throw new OrderDomainException("Restraunt with id: " + 
		restraunt.getId().getValue() + " is currently not active");
		}
	}

	@Override
	public OrderPaidEvent payOrder(Order order) {
		order.pay();
		log.info("Order with id: {} is paid!",order.getId().getValue());
		return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
		order.initCancel(failureMessages);
		log.info("order payment is cancelling for order id {}",order.getId().getValue());
		return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
	}

	@Override
	public void approveOrder(Order order) {
		order.approve();
		log.info("Order with id: {} is approved!",order.getId().getValue());
	}

	@Override
	public void cancelOrder(Order order, List<String> falureMessages) {
		order.cancel(falureMessages);
		log.info("Order with id {} is cancelled",order.getId().getValue());
	}

}
