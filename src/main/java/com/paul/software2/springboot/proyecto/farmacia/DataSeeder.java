package com.paul.software2.springboot.proyecto.farmacia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paul.software2.springboot.proyecto.farmacia.entities.Categoria;
import com.paul.software2.springboot.proyecto.farmacia.entities.Laboratorio;
import com.paul.software2.springboot.proyecto.farmacia.entities.Medicamento;
import com.paul.software2.springboot.proyecto.farmacia.entities.Proveedor;
import com.paul.software2.springboot.proyecto.farmacia.entities.SubCategoria;
import com.paul.software2.springboot.proyecto.farmacia.repositories.CategoriaRepository;
import com.paul.software2.springboot.proyecto.farmacia.repositories.LaboratorioRepository;
import com.paul.software2.springboot.proyecto.farmacia.repositories.MedicamentoRepository;
import com.paul.software2.springboot.proyecto.farmacia.repositories.ProveedorRepository;
import com.paul.software2.springboot.proyecto.farmacia.repositories.SubCategoriaRepository;

import java.util.Date;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner initDatabase(
            LaboratorioRepository laboratorioRepository,
            MedicamentoRepository medicamentoRepository,
            CategoriaRepository categoriaRepository,
            SubCategoriaRepository subCategoriaRepository,
            ProveedorRepository proveedorRepository) {

        return args -> {
            // Paso 1: Crear laboratorios
            Laboratorio lab1 = new Laboratorio(
                    null, "Inti",
                    "C. Lucas Jaimes #1959 Miraflores",
                    "800 17 4684", "drogueria@inti.com.bo",
                    "Bolivia",
                    "La Paz");
            Laboratorio lab2 = new Laboratorio(
                    null,
                    "Bago",
                    "Avenida Costanera #1000, esquina calle 9 de Calacoto. Edificio Costanera 1000, Torre 1, piso 4.",
                    "2770110",
                    "bolivia@bago.com.bo",
                    "Bolivia",
                    "La Paz");

            // Guardar los laboratorios
            laboratorioRepository.save(lab1);
            laboratorioRepository.save(lab2);

            // Paso 2: Crear categorías
            Categoria cat1 = new Categoria(null, "Medicamentos");
            Categoria cat2 = new Categoria(null, "Vitaminas");

            // Guardar categorías
            categoriaRepository.save(cat1);
            categoriaRepository.save(cat2);

            // Crear subcategorías y asociarlas con las categorías
            SubCategoria subCat1 = new SubCategoria(null, "Analgésicos", cat1);
            SubCategoria subCat2 = new SubCategoria(null, "Antipiréticos", cat1);
            SubCategoria subCat3 = new SubCategoria(null, "Multivitamínicos", cat2);

            // Agregar subcategorías a sus respectivas categorías
            cat1.addSubCategoria(subCat1);
            cat1.addSubCategoria(subCat2);
            cat2.addSubCategoria(subCat3);

            // Guardar subcategorías
            subCategoriaRepository.save(subCat1);
            subCategoriaRepository.save(subCat2);
            subCategoriaRepository.save(subCat3);

            // Paso 3: Crear medicamentos y asociarlos con los laboratorios y categorías
            Medicamento med1 = new Medicamento(
                null,
                 "Ibuprofeno",
                  15.75, new Date(),
                    "No usar si tiene úlceras gástricas",
                     "Náuseas, mareos",
                      "Analgésico"
            );
            med1.setLaboratorio(lab1); // Asociar con el laboratorio lab1
            med1.setCategoria(cat1);   // Asociar con la categoría cat1 (Medicamentos)

            Medicamento med2 = new Medicamento(
                null,
                 "Paracetamol",
                  10.50, new Date(),
                    "No usar si es alérgico al paracetamol",
                     "Somnolencia, dolor de cabeza",
                      "Antipirético"
            );
            med2.setLaboratorio(lab2); // Asociar con el laboratorio lab2
            med2.setCategoria(cat1);   // Asociar con la categoría cat1 (Medicamentos)

            // Guardar los medicamentos
            medicamentoRepository.save(med1);
            medicamentoRepository.save(med2);

            //--------------------------------------------------------
            // Paso 3: Crear proveedores
            Proveedor proveedor1 = new Proveedor(null, "Proveedor A", "Bolivia", "La Paz", "Calle Falsa 123", "proveedora@example.com", "123456789");
            Proveedor proveedor2 = new Proveedor(null, "Proveedor B", "Bolivia", "Santa Cruz", "Avenida Siempre Viva 456", "proveedorb@example.com", "987654321");

            // Guardar proveedores
            proveedorRepository.save(proveedor1);
            proveedorRepository.save(proveedor2);

            // Mensaje en la consola para verificar que los datos fueron creados
            System.out.println("Datos de prueba insertados en la base de datos.");
        };
    }
}
