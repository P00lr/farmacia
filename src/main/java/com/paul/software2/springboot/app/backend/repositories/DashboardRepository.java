package com.paul.software2.springboot.app.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacenId;

public interface DashboardRepository extends CrudRepository<DetalleAlmacen, DetalleAlmacenId> {
    @Query("SELECT SUM(da.stock) FROM DetalleAlmacen da")
    Integer getStockTotal();

    @Query("SELECT SUM(da.stock) FROM DetalleAlmacen da WHERE da.almacen.id = :almacenId")
    Integer getStockTotalByAlmacen(@Param("almacenId") Long almacenId);

}
