package com.lucien.carrentalbookingservice.constant;

public class ResourcePath {

    // rental order resources
    public static final String GET_ALL_ORDER = "/api/orders";
    public static final String GET_ORDER = "/api/order/{id}";
    public static final String CREATE_ORDER = "/api/order";
    public static final String UPDATE_ORDER = "/api/order/{id}";
    public static final String DELETE_ORDER = "/api/order/{id}";

    // stock resources
    public static final String GET_ALL_AVAILABLE_STOCK = "/api/available-stocks";
    public static final String GET_ALL_STOCK = "api/stocks";
    public static final String GET_STOCK = "/api/stock/{id}";
}
