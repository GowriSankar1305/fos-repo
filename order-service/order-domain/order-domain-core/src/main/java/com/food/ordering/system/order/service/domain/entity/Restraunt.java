package com.food.ordering.system.order.service.domain.entity;

import java.util.List;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.RestrauntId;

public class Restraunt extends AggregateRoot<RestrauntId> {
	
	private final List<Product> products;
	private boolean active;
	
	private Restraunt(RestrauntBuilder builder) {
		super.setId(builder.restrauntId);
		this.products = builder.products;
		this.active = builder.active;
	}
	
	public static RestrauntBuilder builder()	{
		return new RestrauntBuilder();
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Product> getProducts() {
		return products;
	}
	
	public static final class RestrauntBuilder	{
		private RestrauntId restrauntId;
		private List<Product> products;
		private boolean active;
		
		public RestrauntBuilder restrauntId(RestrauntId restrauntId) {
			this.restrauntId = restrauntId;
			return this;
		}
		
		public RestrauntBuilder products(List<Product> products)	{
			this.products = products;
			return this;
		}
		
		public RestrauntBuilder active(boolean active)	{
			this.active = active;
			return this;
		}
		
		public Restraunt build()	{
			return new Restraunt(this);
		}
	}
}
