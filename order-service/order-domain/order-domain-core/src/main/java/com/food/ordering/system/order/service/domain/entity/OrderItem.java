package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId>{
	
	private OrderId orderId;
	private final Product product;
	private final Integer quantity;
	private final Money price;
	private final Money subTotal;
	
	private OrderItem(OrderItemBuilder builder)	{
		super.setId(builder.orderItemId);
		this.product = builder.product;
		this.quantity = builder.quantity;
		this.price = builder.price;
		this.subTotal = builder.subTotal;
	}
	
	public static OrderItemBuilder builder()	{
		return new OrderItemBuilder();
	}
	
	public OrderId getOrderId() {
		return orderId;
	}
	public void setOrderId(OrderId orderId) {
		this.orderId = orderId;
	}
	public Product getProduct() {
		return product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public Money getPrice() {
		return price;
	}
	public Money getSubTotal() {
		return subTotal;
	}
	
	public static final class OrderItemBuilder	{
		
		private OrderItemId orderItemId;
		private Product product;
		private Integer quantity;
		private Money price;
		private Money subTotal;
		
		private OrderItemBuilder()	{
			
		}
		
		public OrderItemBuilder orderItemId(OrderItemId itemId)	{
			this.orderItemId = itemId;
			return this;
		}
		
		public OrderItemBuilder product(Product product)	{
			this.product = product;
			return this;
		}
		
		public OrderItemBuilder quantity(Integer quantity)	{
			this.quantity = quantity;
			return this;
		}
		
		public OrderItemBuilder price(Money price)	{
			this.price = price;
			return this;
		}
		
		public OrderItemBuilder subTotal(Money subTotal) {
			this.subTotal = subTotal;
			return this;
		}
		
		public OrderItem build()	{
			return new OrderItem(this);
		}
	}

	public void initializeOrderItem(OrderId orderId,OrderItemId orderItemId) {
		super.setId(orderItemId);
		this.orderId = orderId;
	}
	
	boolean isPriceValid()	{
		return price.isGreaterThanZero() && 
			   price.equals(product.getPrice()) && 
			   price.multiply(quantity).equals(subTotal);
	}
}
