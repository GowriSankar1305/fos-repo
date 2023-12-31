package com.food.ordering.system.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.domain.valueobject.RestrauntId;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

public class Order extends AggregateRoot<OrderId> {
	
	private final CustomerId customerId;
	private final RestrauntId restrauntId;
	private final StreetAddress streetAddress;
	private final Money price;
	private final List<OrderItem> items;
	private TrackingId trackingId;
	private OrderStatus orderStatus;
	private List<String> failureMessages;
	
	
	public void pay()	{
		if(orderStatus != OrderStatus.PENDING)	{
			throw new OrderDomainException("Order is not in correct state for pay operation!");
		}
		orderStatus = OrderStatus.PAID;
	}
	
	public void approve()	{
		if(orderStatus != OrderStatus.PAID)	{
			throw new OrderDomainException("Order is not in correct state for approve operation!");
		}
		orderStatus = OrderStatus.APPROVED;
	}
	
	public void initCancel(List<String> failureMessages)	{
		if(orderStatus != OrderStatus.PAID)	{
			throw new OrderDomainException("Order is not in correct state for cancelling operation!");
		}
		orderStatus = OrderStatus.CANCELLING;
		updateFailureMessages(failureMessages);
	}
	
	private void updateFailureMessages(List<String> failureMessages) {
		if(this.failureMessages != null && failureMessages != null)	{
			this.failureMessages.addAll(failureMessages);
		}
		if(this.failureMessages == null)	{
			this.failureMessages = failureMessages;
		}
	}

	public void cancel(List<String> failureMessages)	{
		if(!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING))	{
			throw new OrderDomainException("Order is not in correct state for cancel operation!");
		}
		orderStatus =OrderStatus.CANCELLED;
		updateFailureMessages(failureMessages);
	}
	
	public void initializeOrder()	{
		super.setId(new OrderId(UUID.randomUUID()));
		this.trackingId = new TrackingId(UUID.randomUUID());
		this.orderStatus = OrderStatus.PENDING;
		
	}
	
	private void initializeOrderItems()	{
		long itemId = 1;
		for(OrderItem orderItem : this.items)	{
			orderItem.initializeOrderItem(super.getId(),new OrderItemId(itemId++));
		}
	}
	
	public void validateOrder()	{
		validateInitialOrder();
		validateTotalPrice();
		validateItemsPrice();
	}
	
	private void validateItemsPrice() {
		Money orderItemsTotal = items.stream().map(orderItem -> {
			validateItemPrice(orderItem);
			return orderItem.getSubTotal(); 
		}).reduce(Money.ZERO,Money::add);
		
		if(!price.equals(orderItemsTotal))	{
			throw new OrderDomainException(
					"Total price: " + price + " is nor equal to Order items toal: " + orderItemsTotal);
		}
	}

	private void validateItemPrice(OrderItem orderItem) {
		 if(!orderItem.isPriceValid()) {
			 throw new OrderDomainException("Order item price: "+ 
					 orderItem.getPrice().getAmount() + " is not valid for product: " 
					 + orderItem.getProduct().getId().getValue());
		 }
	}

	private void validateTotalPrice() {
		if(price == null || !price.isGreaterThanZero())	{
			throw new OrderDomainException("TOtal price must be grater than zero!");
		}
	}

	private void validateInitialOrder() {
		if(orderStatus != null && super.getId() != null)	{
			throw new OrderDomainException("Order is not in correct state!");
		}
	}

	private Order(OrderBuilder builder)	{
		super.setId(builder.orderId);
		this.customerId = builder.customerId;
		this.restrauntId = builder.restrauntId;
		this.streetAddress = builder.streetAddress;
		this.price = builder.price;
		this.items = builder.items;
		this.trackingId = builder.trackingId;
		this.orderStatus = builder.orderStatus;
		this.failureMessages = builder.failureMessages;
	}
	
	public static OrderBuilder builder()	{
		return new OrderBuilder();
	}
	
	public TrackingId getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(TrackingId trackingId) {
		this.trackingId = trackingId;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<String> getFailureMessages() {
		return failureMessages;
	}
	public void setFailureMessages(List<String> failureMessages) {
		this.failureMessages = failureMessages;
	}
	public CustomerId getCustomerId() {
		return customerId;
	}
	public RestrauntId getRestrauntId() {
		return restrauntId;
	}
	public StreetAddress getStreetAddress() {
		return streetAddress;
	}
	public Money getPrice() {
		return price;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	
	public static final class OrderBuilder	{
		private OrderId orderId;
		private CustomerId customerId;
		private RestrauntId restrauntId;
		private StreetAddress streetAddress;
		private Money price;
		private List<OrderItem> items;
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessages;
		
		public OrderBuilder orderId(OrderId orderId)	{
			this.orderId = orderId;
			return this;
		}
		
		public OrderBuilder customerId(CustomerId customerId)	{
			this.customerId = customerId;
			return this;
		}
		
		public OrderBuilder restrauntId(RestrauntId restrauntId)	{
			this.restrauntId = restrauntId;
			return this;
		}
		
		public OrderBuilder streetAddress(StreetAddress streetAddress)	{
			this.streetAddress = streetAddress;
			return this;
		}
		
		public OrderBuilder price(Money money)	{
			this.price = money;
			return this;
		}
		
		public OrderBuilder items(List<OrderItem> items)	{
			this.items = items;
			return this;
		}
		
		public OrderBuilder trackingId(TrackingId trackingId)	{
			this.trackingId = trackingId;
			return this;
		}
		
		public OrderBuilder orderStatus(OrderStatus orderStatus)	{
			this.orderStatus = orderStatus;
			return this;
		}
		
		public OrderBuilder failureMessages(List<String> failureMessages)	{
			this.failureMessages = failureMessages;
			return this;
		}
		
		public Order build()	{
			return new Order(this);
		}
	}
}
