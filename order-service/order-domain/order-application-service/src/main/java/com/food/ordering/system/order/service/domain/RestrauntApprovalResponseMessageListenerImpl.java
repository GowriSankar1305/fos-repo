package com.food.ordering.system.order.service.domain;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.food.ordering.system.order.service.domain.dto.message.RestrauntApprovalResponse;
import com.food.ordering.system.order.service.domain.ports.input.message.listener.restrauntapproval.RestrauntApprovalResponseMessageListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Service
public class RestrauntApprovalResponseMessageListenerImpl implements RestrauntApprovalResponseMessageListener {

	@Override
	public void orderApproved(RestrauntApprovalResponse restrauntApprovalResponse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void orderRejected(RestrauntApprovalResponse restrauntApprovalResponse) {
		// TODO Auto-generated method stub

	}

}
