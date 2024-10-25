package com.paul.software2.springboot.proyecto.farmacia.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "categorias")
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "categoria") // Relación uno a mucho
    @JsonBackReference
    private List<SubCategoria> subCategorias;

    @OneToMany(mappedBy = "categoria") // Relación uno a muchos
    @JsonBackReference
    private List<Medicamento> medicamentos;

    public Categoria() {
    }

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Categoria(Long id, String nombre, List<SubCategoria> subCategorias) {
        this.id = id;
        this.nombre = nombre;
        this.subCategorias = subCategorias;
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

    public List<SubCategoria> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(List<SubCategoria> subCategorias) {
        this.subCategorias = subCategorias;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", nombre=" + nombre + "}";
    }

    // En la clase Categoria, agrega este método:
    public void addSubCategoria(SubCategoria subCategoria) {
        if (this.subCategorias == null) {
            this.subCategorias = new ArrayList<>();
        }
        this.subCategorias.add(subCategoria);
        subCategoria.setCategoria(this); // Establecer la relación inversa
    }

    
   

}
