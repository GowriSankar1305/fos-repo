package com.food.ordering.system.order.service.domain.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestrauntId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restraunt;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;

@Component
public class OrderDataMapper {
	
	public Restraunt createOrderCommandToRestraunt(CreateOrderCommand command)	{
		return Restraunt.builder()
				.restrauntId(new RestrauntId(command.getRestrauntId()))
				.products(command.getItems().stream().map(orderItem -> 
				new Product(new ProductId(orderItem.getProductId()))).collect(Collectors.toList()))
				.build();
	}
	
	public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand)	{
		return Order.builder()
				.customerId(new CustomerId(createOrderCommand.getCustomerId()))
				.restrauntId(new RestrauntId(createOrderCommand.getRestrauntId()))
				.streetAddress(orderAddressToStreetAddress(createOrderCommand.getOrderAddress()))
				.price(new Money(createOrderCommand.getPrice()))
				.items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
				.build();
	}
	
	private List<OrderItem> orderItemsToOrderItemEntities(
			List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> items) {
		return items.stream().map(orderItem -> {
			return OrderItem.builder()
				.product(new Product(new ProductId(orderItem.getProductId())))
				.price(new Money(orderItem.getPrice()))
				.quantity(orderItem.getQuantity())
				.subTotal(new Money(orderItem.getSubTotal()))
				.build();
		}).collect(Collectors.toList());
	}

	private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress)	{
		return new StreetAddress(UUID.randomUUID(), 
				orderAddress.getStreet(),
				orderAddress.getStreet(),
				orderAddress.getCity());
	}
	
	public CreateOrderResponse orderToCreateOrderResponse(Order order,String message)	{
		return CreateOrderResponse.builder()
				.orderTrackignId(order.getTrackingId().getValue())
				.orderStatus(order.getOrderStatus())
				.message(message)
				.build();
	}
	
	public TrackOrderResponse orderToTrackOrderResponse(Order order)	{
		return TrackOrderResponse.builder()
				.orderTrackingId(order .getTrackingId().getValue())
				.orderStatus(order.getOrderStatus())
				.failureMessages(order.getFailureMessages())
				.build();
	}
}
