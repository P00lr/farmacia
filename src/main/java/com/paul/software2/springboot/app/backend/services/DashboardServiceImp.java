package com.paul.software2.springboot.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paul.software2.springboot.app.backend.repositories.DashboardRepository;

@Service
public class DashboardServiceImp implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public Integer getTotalStock() {
        return dashboardRepository.getStockTotal();
    }

    public Integer getStockByAlmacen(Long almacenId) {
        Integer stock = dashboardRepository.getStockTotalByAlmacen(almacenId);
        return stock != null ? stock : 0;
    }
}
