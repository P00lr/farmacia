package com.paul.software2.springboot.app.backend.dto;

public class CrearDetalleVentaDto {
    private Long medicamentoId;
    private Integer cantidadTipo;

    
    public CrearDetalleVentaDto() {
    }
    public Long getMedicamentoId() {
        return medicamentoId;
    }
    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }
    public Integer getCantidadTipo() {
        return cantidadTipo;
    }
    public void setCantidadTipo(Integer cantidadTipo) {
        this.cantidadTipo = cantidadTipo;
    }

    
}
