package com.food.order.system.order.service.domain;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestrauntRepository;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {
	
	@Autowired
	private OrderApplicationService orderApplicationService;
	@Autowired
	private OrderDataMapper orderDataMapper;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RestrauntRepository restrauntRepository;
	
	private CreateOrderCommand createOrderCommand;
	private CreateOrderCommand createOrderCommandWrongPrice;
	private CreateOrderCommand createOrderCommandWrongProductPrice;
	private final UUID CUSTOMER_ID = UUID.randomUUID();
	private final UUID RESTRAUNT_ID = UUID.randomUUID();
	private final UUID PRODUCT_ID = UUID.randomUUID();
	private final UUID ORDER_ID = UUID.randomUUID();
	private final BigDecimal PRICE = new BigDecimal("200.00");
}
