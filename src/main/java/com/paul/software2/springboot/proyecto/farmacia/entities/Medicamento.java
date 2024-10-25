package com.paul.software2.springboot.proyecto.farmacia.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private Date fechaVencimiento;
    private String contraIndicaciones;
    private String efectosSecundarios;
    private String tipoMedicamento;

    @ManyToOne
    private Laboratorio laboratorio;
    
    public Medicamento() {
    }
    public Medicamento(Long id, String nombre, Double precio, Date fechaVencimiento, String contraIndicaciones,
            String efectosSecundarios, String tipoMedicamento) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaVencimiento = fechaVencimiento;
        this.contraIndicaciones = contraIndicaciones;
        this.efectosSecundarios = efectosSecundarios;
        this.tipoMedicamento = tipoMedicamento;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public String getContraIndicaciones() {
        return contraIndicaciones;
    }
    public void setContraIndicaciones(String contraIndicaciones) {
        this.contraIndicaciones = contraIndicaciones;
    }
    public String getEfectosSecundarios() {
        return efectosSecundarios;
    }
    public void setEfectosSecundarios(String efectosSecundarios) {
        this.efectosSecundarios = efectosSecundarios;
    }
    public String getTipoMedicamento() {
        return tipoMedicamento;
    }
    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }
    public Laboratorio getLaboratorio() {
        return laboratorio;
    }
    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }
    @Override
    public String toString() {
        return "{id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", fechaVencimiento="
                + fechaVencimiento + ", contraIndicaciones=" + contraIndicaciones + ", efectosSecundarios="
                + efectosSecundarios + ", tipoMedicamento=" + tipoMedicamento + ", laboratorio=" + laboratorio + "}";
    }

    
}
