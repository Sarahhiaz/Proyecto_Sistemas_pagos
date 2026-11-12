/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Admin
 */

import Entidades.Venta;
import Entidades.Pago;
import java.time.LocalDateTime;

public class Transaccion {

    private static int contador = 1;

    private int idTransaccion;
    private int idVenta;
    private LocalDateTime fecha;
    private double monto;
    private String metodoPago;
    private EstadoPago estado;

    public Transaccion(
            Venta venta,
            Pago pago) {

        this.idTransaccion = contador++;
        this.idVenta = venta.getIdVenta();
        this.fecha = LocalDateTime.now();
        this.monto = pago.getMonto();
        this.metodoPago =
                pago.getClass().getSimpleName();
        this.estado = pago.getEstado();
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public double getMonto() {
        return monto;
    }

    public void mostrar() {

        System.out.println(
                "\n-----------------------------"
        );

        System.out.println(
                "Transacción #" + idTransaccion
        );

        System.out.println(
                "Venta #" + idVenta
        );

        System.out.println(
                "Fecha: " + fecha
        );

        System.out.println(
                "Método: " + metodoPago
        );

        System.out.println(
                "Monto: $"
                + String.format("%.2f", monto)
        );

        System.out.println(
                "Estado: " + estado
        );
    }
}
