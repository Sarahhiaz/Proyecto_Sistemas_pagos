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

public class PagoBitcoin extends Pago {

    private String wallet;

    public PagoBitcoin(
            double monto,
            String wallet) {

        super(monto);
        this.wallet = wallet;
    }

    @Override
    public boolean validarPago() {

        return wallet != null
                && !wallet.trim().isEmpty();
    }

    @Override
    public boolean procesarPago() {

        if (validarPago()) {

            aprobarPago();

            System.out.println(
                    "Pago con Bitcoin aprobado."
            );

            return true;
        }

        rechazarPago();

        return false;
    }

    @Override
    public String obtenerDetalles() {

        return """
                ===== PAGO BITCOIN =====
                """ +
                super.obtenerDetalles()
                + "\nWallet: " + wallet;
    }

    private void aprobarPago() {
        throw new UnsupportedOperationException("Not supported yet."); // monitorea el rendimiento y fuerzan la ejecucion del recolector  nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void rechazarPago() {
        throw new UnsupportedOperationException("Not supported yet."); //  nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

