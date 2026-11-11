/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Admin
 */

import Entidades.EstadoPago;
import java.util.ArrayList;
import java.util.List;
import Entidades.Pago;
import Entidades.Transaccion;
import Entidades.Venta;

public class GestorPago {

    private List<Transaccion> transacciones;

    public GestorPago() {

        transacciones =
                new ArrayList<>();
    }

    public boolean procesarVenta(
            Venta venta,
            Pago metodoPago) {

        venta.setMetodoPago(metodoPago);

        System.out.println(
                "\nProcesando pago..."
        );

        boolean resultado =
                metodoPago.procesarPago();

        Transaccion transaccion =
                new Transaccion(
                        venta,
                        metodoPago
                );

        transacciones.add(transaccion);

        return resultado;
    }

    public void consultarTransacciones() {

        System.out.println(
                "\n========== TRANSACCIONES =========="
        );

        if (transacciones.isEmpty()) {

            System.out.println(
                    "No existen transacciones registradas."
            );

            return;
        }

        for (Transaccion transaccion
                : transacciones) {

            transaccion.mostrar();
        }
    }

    public void generarResumen() {

        int aprobadas = 0;
        int rechazadas = 0;

        double totalAprobado = 0;

        for (Transaccion transaccion
                : transacciones) {

            if (transaccion.getEstado()
                    == EstadoPago.APROBADO) {

                aprobadas++;

                totalAprobado +=
                        transaccion.getMonto();

            } else if (
                    transaccion.getEstado()
                            == EstadoPago.RECHAZADO) {

                rechazadas++;
            }
        }

        System.out.println(
                "\n========== RESUMEN DE PAGOS =========="
        );

        System.out.println(
                "Transacciones totales: "
                        + transacciones.size()
        );

        System.out.println(
                "Aprobadas: " + aprobadas
        );

        System.out.println(
                "Rechazadas: " + rechazadas
        );

        System.out.println(
                "Total aprobado: $"
                        + String.format(
                        "%.2f",
                        totalAprobado
                )
        );
    }
}

