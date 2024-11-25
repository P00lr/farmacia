package com.paul.software2.springboot.app.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.paul.software2.springboot.app.backend.entities.DetalleAlmacen;
import com.paul.software2.springboot.app.backend.entities.DetalleAlmacenId;

public interface DetalleAlmacenRepository extends CrudRepository<DetalleAlmacen, DetalleAlmacenId> {
    @Query("SELECT d FROM DetalleAlmacen d WHERE d.medicamento.id = :medicamentoId")
    List<DetalleAlmacen> findAllByMedicamentoId(@Param("medicamentoId") Long medicamentoId);
}
