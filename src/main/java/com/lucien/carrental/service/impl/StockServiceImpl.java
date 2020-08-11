package com.lucien.carrental.service.impl;

import com.lucien.carrental.entity.StockDO;
import com.lucien.carrental.entity.StockView;
import com.lucien.carrental.model.StockVO;
import com.lucien.carrental.repository.StockRepository;
import com.lucien.carrental.service.StockService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = notNull(stockRepository);
    }

    @Override
    public List<StockVO> findAllAvailable() {
        List<StockView> results = stockRepository.findAllAvailable();
        return results.stream().map(StockVO::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<StockVO> findAll() {
        List<StockView> results = stockRepository.findAllStocks();
        return results.stream().map(StockVO::convertToVO).collect(Collectors.toList());
    }

    @Override
    public StockVO findById(long id) {
        StockView result = stockRepository.findById(id);
        if (result == null) {
            throw new RuntimeException("This stock doesn't exist or already deleted.");
        }
        return StockVO.convertToVO(result);
    }

}
