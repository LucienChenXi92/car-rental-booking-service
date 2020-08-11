package com.lucien.carrental.service;

import com.lucien.carrental.model.CreateRentalOrderVO;
import com.lucien.carrental.model.RentalOrderVO;
import com.lucien.carrental.model.UpdateRentalOrderVO;

import java.util.List;

public interface RentalOrderService {

    List<RentalOrderVO> findAll();

    RentalOrderVO findById(long id);

    RentalOrderVO create(CreateRentalOrderVO rentalOrderVO);

    RentalOrderVO update(UpdateRentalOrderVO rentalOrderVO);

    void delete(long id);
}
