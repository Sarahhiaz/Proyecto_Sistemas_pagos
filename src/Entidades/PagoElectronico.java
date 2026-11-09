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
public class PagoElectronico extends Pago {

    private String proveedor;
    private String correo;
    private String token;

    public PagoElectronico(double monto,
                           String proveedor,
                           String correo,
                           String token) {

        super(monto);

        this.proveedor = proveedor;
        this.correo = correo;
        this.token = token;
    }

    @Override
    public boolean validarPago() {

        return proveedor != null
                && !proveedor.trim().isEmpty()
                && correo != null
                && correo.contains("@")
                && token != null
                && !token.trim().isEmpty();
    }

    @Override
    public boolean procesarPago() {

        if (validarPago()) {

            aprobarPago();

            System.out.println(
                    "\nPago electronico aprobado."
            );

            return true;

        } else {

            rechazarPago();

            System.out.println(
                    "\nPago electronico rechazado."
            );

            return false;
        }
    }

    @Override
    public String obtenerDetalles() {

        return """
                ===== PAGO ELECTRONICO =====
                """ +
                super.obtenerDetalles()
                + "\nProveedor: " + proveedor
                + "\nCorreo: " + correo;
    }
}
