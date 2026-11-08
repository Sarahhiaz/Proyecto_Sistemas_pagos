/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Admin
 */

import Entidades.Pago;
import java.time.LocalDateTime;

public class Venta {

    private static int contador = 1;

    private int idVenta;
    private LocalDateTime fecha;
    private String descripcion;
    private double total;
    private Pago metodoPago;

    public Venta(String descripcion, double total) {

        this.idVenta = contador++;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getTotal() {
        return total;
    }

    public Pago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Pago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void mostrarVenta() {

        System.out.println("\n========== VENTA ==========");

        System.out.println(
                "ID: " + idVenta
        );

        System.out.println(
                "Descripción: " + descripcion
        );

        System.out.println(
                "Total: $" + String.format("%.2f", total)
        );

        System.out.println(
                "Fecha: " + fecha
        );
    }
}

