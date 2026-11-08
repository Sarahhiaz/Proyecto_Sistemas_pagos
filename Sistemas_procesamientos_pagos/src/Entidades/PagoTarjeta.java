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
public class PagoTarjeta extends Pago {

    private String numeroTarjeta;
    private String titular;
    private String autorizacion;

    public PagoTarjeta(double monto,
                       String numeroTarjeta,
                       String titular) {

        super(monto);

        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
    }

    @Override
    public boolean validarPago() {

        return numeroTarjeta != null
                && numeroTarjeta.length() == 16
                && numeroTarjeta.matches("\\d+")
                && titular != null
                && !titular.trim().isEmpty();
    }

    @Override
    public boolean procesarPago() {

        if (validarPago()) {

            autorizacion =
                    "AUT-" + System.currentTimeMillis();

            aprobarPago();

            System.out.println("\nPago con tarjeta aprobado.");

            return true;

        } else {

            rechazarPago();

            System.out.println("\nPago con tarjeta rechazado.");
            System.out.println(
                    "Los datos de la tarjeta no son validos."
            );

            return false;
        }
    }

    private String ocultarNumeroTarjeta() {

        if (numeroTarjeta == null ||
                numeroTarjeta.length() < 4) {

            return "****";
        }

        return "************"
                + numeroTarjeta.substring(
                        numeroTarjeta.length() - 4
                );
    }

    @Override
    public String obtenerDetalles() {

        return """
                ===== PAGO CON TARJETA =====
                """ +
                super.obtenerDetalles()
                + "\nTarjeta: "
                + ocultarNumeroTarjeta()
                + "\nTitular: "
                + titular
                + "\nAutorizacion: "
                + autorizacion;
    }
}