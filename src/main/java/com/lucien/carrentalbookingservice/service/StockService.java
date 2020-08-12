package com.lucien.carrentalbookingservice.service;

import com.lucien.carrentalbookingservice.model.StockVO;

import java.util.List;

public interface StockService {

    List<StockVO> findAllAvailable();

    List<StockVO> findAll();

    StockVO findById(long id);
}
