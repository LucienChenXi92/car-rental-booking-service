package com.lucien.carrentalbookingservice.repository;

import com.lucien.carrentalbookingservice.entity.RentalOrderDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalOrderRepository extends JpaRepository<RentalOrderDO, Long> {
}
