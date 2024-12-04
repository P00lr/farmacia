package com.paul.software2.springboot.app.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_almacenes")
public class DetalleAlmacen {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @EmbeddedId
    private DetalleAlmacenId id;

    private Integer stock;

    @ManyToOne
    @JsonIgnoreProperties({"detalleAlmacenes", "handler", "hibernateLazyInitializer"})
    @MapsId("medicamentoId")
    private Medicamento medicamento;

    @ManyToOne
    @JsonIgnoreProperties({"detalleAlmacenes", "handler", "hibernateLazyInitializer"})
    @MapsId("almacenId")
    private Almacen almacen;

    
   

    public DetalleAlmacen(DetalleAlmacenId id, Integer stock) {
        this.id = id;
        this.stock = stock;
    }

    public DetalleAlmacen() {
    }

    public DetalleAlmacenId getId() {
        return id;
    }

    public void setId(DetalleAlmacenId id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    
}
