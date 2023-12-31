package com.food.ordering.system.order.service.domain.ports.output.repository;

import java.util.Optional;

import com.food.ordering.system.order.service.domain.entity.Restraunt;

public interface RestrauntRepository {
	Optional<Restraunt> findRestrauntInformation(Restraunt restraunt);
}
