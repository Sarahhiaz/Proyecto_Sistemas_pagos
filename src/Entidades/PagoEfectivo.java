/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Entidades.Pago;

/**
 *
 * @author Admin
 */
public class PagoEfectivo extends Pago {

    private double montoRecibido;
    private double cambio;

    public PagoEfectivo(double monto, double montoRecibido) {
        super(monto);
        this.montoRecibido = montoRecibido;
        this.cambio = 0;
    }

    @Override
    public boolean validarPago() {

        return montoRecibido >= monto;
    }

    @Override
    public boolean procesarPago() {

        if (validarPago()) {

            cambio = montoRecibido - monto;
            aprobarPago();

            System.out.println("\nPago en efectivo aprobado.");
            System.out.println("Cambio: $" + String.format("%.2f", cambio));

            return true;

        } else {

            rechazarPago();

            System.out.println("\nPago rechazado.");
            System.out.println("El efectivo recibido es insuficiente.");

            return false;
        }
    }

    @Override
    public String obtenerDetalles() {

        return """
                ===== PAGO EN EFECTIVO =====
                """ +
                super.obtenerDetalles()
                + "\nEfectivo recibido: $"
                + String.format("%.2f", montoRecibido)
                + "\nCambio: $"
                + String.format("%.2f", cambio);
    }
}

