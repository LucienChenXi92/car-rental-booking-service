package com.lucien.carrental.service;

import com.lucien.carrental.model.StockVO;

import java.util.List;

public interface StockService {

    List<StockVO> findAllAvailable();

    List<StockVO> findAll();

    StockVO findById(long id);
}
