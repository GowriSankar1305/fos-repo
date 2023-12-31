package com.food.ordering.system.order.service.domain.dto.message;

import java.time.Instant;
import java.util.List;

import com.food.ordering.system.domain.valueobject.OrderApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RestrauntApprovalResponse {
	private String id;
	private String sagaId;
	private String orderId;
	private String restrauntId;
	private Instant createdTime;
	private OrderApprovalStatus orderApprovalStatus;
	private List<String> failureMessages;
}
