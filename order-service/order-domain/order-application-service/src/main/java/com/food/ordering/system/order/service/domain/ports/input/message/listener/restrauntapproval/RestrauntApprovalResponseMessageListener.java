package com.food.ordering.system.order.service.domain.ports.input.message.listener.restrauntapproval;

import com.food.ordering.system.order.service.domain.dto.message.RestrauntApprovalResponse;

public interface RestrauntApprovalResponseMessageListener {
	void orderApproved(RestrauntApprovalResponse restrauntApprovalResponse);
	void orderRejected(RestrauntApprovalResponse restrauntApprovalResponse);
}
