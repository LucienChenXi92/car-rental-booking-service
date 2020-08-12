package com.lucien.carrentalbookingservice.controller;

import com.lucien.carrentalbookingservice.constant.ResourcePath;
import com.lucien.carrentalbookingservice.model.CreateRentalOrderVO;
import com.lucien.carrentalbookingservice.model.RentalOrderVO;
import com.lucien.carrentalbookingservice.model.UpdateRentalOrderVO;
import com.lucien.carrentalbookingservice.service.RentalOrderService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
@Api(tags = "Rental Order Controller", description = "Provide Rental Order related APIs")
public class RentalOrderController {

    private final RentalOrderService rentalOrderService;

    public RentalOrderController(RentalOrderService rentalOrderService) {
        this.rentalOrderService = notNull(rentalOrderService);
    }

    @GetMapping(value = ResourcePath.GET_ALL_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RentalOrderVO> getAllRentalOrder() {
        return rentalOrderService.findAll();
    }

    @GetMapping(value = ResourcePath.GET_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalOrderVO getRentalOrderById(@PathVariable long id) {
        return rentalOrderService.findById(id);
    }

    @PostMapping(value = ResourcePath.CREATE_ORDER, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalOrderVO createRentalOrder(@RequestBody CreateRentalOrderVO rentalOrderVO) {
        return rentalOrderService.create(rentalOrderVO);
    }

    @PutMapping(value = ResourcePath.UPDATE_ORDER, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalOrderVO updateRentalOrder(@RequestBody UpdateRentalOrderVO rentalOrderVO, @PathVariable long id) {
        rentalOrderVO.setRentalOrderId(id);
        return rentalOrderService.update(rentalOrderVO);
    }

    @DeleteMapping(value = ResourcePath.DELETE_ORDER)
    public void deleteRentalOrder(@PathVariable long id) {
        rentalOrderService.delete(id);
    }

}
