package com.paul.software2.springboot.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.software2.springboot.app.backend.services.DashboardService;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
     private DashboardService dashboardService;

    @GetMapping("/total-stock")
    public ResponseEntity<Integer> getTotalStock() {
        Integer totalStock = dashboardService.getTotalStock();
        return ResponseEntity.ok(totalStock);
    }

    @GetMapping("/stock-por-almacen/{almacenId}")
    public ResponseEntity<Integer> getStockByAlmacen(@PathVariable Long almacenId) {
        Integer stock = dashboardService.getStockByAlmacen(almacenId);
        return ResponseEntity.ok(stock);
    }
}
