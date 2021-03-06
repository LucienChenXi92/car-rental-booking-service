package com.lucien.carrentalbookingservice.controller;

import com.lucien.carrentalbookingservice.constant.ResourcePath;
import com.lucien.carrentalbookingservice.model.StockVO;
import com.lucien.carrentalbookingservice.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
@CrossOrigin(value = "*")
@Api(tags = "Stock Controller", description = "Provide stock related APIs")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = notNull(stockService);
    }

    @ApiOperation("Find all available stocks")
    @GetMapping(value = ResourcePath.GET_ALL_AVAILABLE_STOCK, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StockVO> getAllAvailable() {
        return stockService.findAllAvailable();
    }

    @ApiOperation("Find all stocks")
    @GetMapping(value = ResourcePath.GET_ALL_STOCK, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StockVO> getAll() {
        return stockService.findAll();
    }

    @ApiOperation("Find stock by id")
    @GetMapping(value = ResourcePath.GET_STOCK)
    public StockVO getStockById(@PathVariable long id) {
        return stockService.findById(id);
    }
}
