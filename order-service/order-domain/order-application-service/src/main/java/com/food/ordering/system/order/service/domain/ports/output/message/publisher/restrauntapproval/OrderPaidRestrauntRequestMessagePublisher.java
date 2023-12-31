package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restrauntapproval;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestrauntRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {

}
