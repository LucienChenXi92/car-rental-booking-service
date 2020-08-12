package com.lucien.carrentalbookingservice.service;

import com.lucien.carrentalbookingservice.model.CreateRentalOrderVO;
import com.lucien.carrentalbookingservice.model.RentalOrderVO;
import com.lucien.carrentalbookingservice.model.UpdateRentalOrderVO;

import java.util.List;

public interface RentalOrderService {

    List<RentalOrderVO> findAll();

    RentalOrderVO findById(long id);

    RentalOrderVO create(CreateRentalOrderVO rentalOrderVO);

    RentalOrderVO update(UpdateRentalOrderVO rentalOrderVO);

    void delete(long id);
}
