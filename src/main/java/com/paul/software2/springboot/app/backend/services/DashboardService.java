package com.paul.software2.springboot.app.backend.services;

public interface DashboardService {
    Integer getTotalStock();
    Integer getStockByAlmacen(Long almacenId);
}
