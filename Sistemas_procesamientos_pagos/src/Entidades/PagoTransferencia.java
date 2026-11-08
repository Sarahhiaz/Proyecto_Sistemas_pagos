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
public class PagoTransferencia extends Pago {

    private String banco;
    private String numeroCuenta;
    private String referencia;

    public PagoTransferencia(double monto,
                             String banco,
                             String numeroCuenta,
                             String referencia) {

        super(monto);

        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.referencia = referencia;
    }

    @Override
    public boolean validarPago() {

        return banco != null
                && !banco.trim().isEmpty()
                && numeroCuenta != null
                && !numeroCuenta.trim().isEmpty()
                && referencia != null
                && !referencia.trim().isEmpty();
    }

    @Override
    public boolean procesarPago() {

        if (validarPago()) {

            aprobarPago();

            System.out.println(
                    "\nTransferencia bancaria aprobada."
            );

            return true;

        } else {

            rechazarPago();

            System.out.println(
                    "\nTransferencia rechazada."
            );

            return false;
        }
    }

    @Override
    public String obtenerDetalles() {

        return """
                ===== TRANSFERENCIA BANCARIA =====
                """ +
                super.obtenerDetalles()
                + "\nBanco: " + banco
                + "\nCuenta: " + numeroCuenta
                + "\nReferencia: " + referencia;
    }
}
