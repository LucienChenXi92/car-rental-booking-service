package com.lucien.carrental.repository;

import com.lucien.carrental.entity.RentalOrderDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalOrderRepository extends JpaRepository<RentalOrderDO, Long> {
}
