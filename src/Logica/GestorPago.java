package Logica;

import Entidades.EstadoPago;
import Entidades.Pago;
import Entidades.ResumenPago;
import Entidades.Transaccion;
import Entidades.Venta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorPago {

    private List<Venta> ventas;

    private List<Transaccion> transacciones;

    public GestorPago() {

        ventas =
                new ArrayList<>();

        transacciones =
                new ArrayList<>();
    }

    // registro de ventas 

    public void registrarVenta(
            Venta venta) {

        if (venta == null) {

            throw new IllegalArgumentException(
                    "La venta no puede ser nula."
            );
        }

        if (existeCodigoVenta(
                venta.getCodigo())) {

            throw new IllegalArgumentException(
                    "Ya existe una venta con el código "
                    + venta.getCodigo()
                    + "."
            );
        }

        ventas.add(
                venta
        );
    }

    public boolean existeCodigoVenta(
            String codigo) {

        if (codigo == null
                ||
            codigo.trim().isEmpty()) {

            return false;
        }

        for (Venta venta : ventas) {

            if (venta.getCodigo()
                    .equalsIgnoreCase(
                            codigo.trim())) {

                return true;
            }
        }

        return false;
    }

    public Venta buscarVenta(
            String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Venta venta : ventas) {

            if (venta.getCodigo()
                    .equalsIgnoreCase(
                            codigo.trim())) {

                return venta;
            }
        }

        return null;
    }

    // procesamiento de datos

    public boolean procesarVenta(
            Venta venta,
            Pago metodoPago) {

        if (venta == null) {

            throw new IllegalArgumentException(
                    "Debe seleccionar una venta existente."
            );
        }

        if (metodoPago == null) {

            throw new IllegalArgumentException(
                    "Debe seleccionar un método de pago."
            );
        }

        if (venta.estaPagada()) {

            throw new IllegalStateException(
                    "La venta ya fue pagada."
            );
        }

        if (Math.abs(
                venta.getImporte()
                -
                metodoPago.getMonto()
        ) > 0.01) {

            throw new IllegalArgumentException(
                    "El monto del pago debe coincidir con el importe de la venta."
            );
        }

        boolean resultado =
                metodoPago.procesarPago();

        Transaccion transaccion =
                new Transaccion(
                        venta,
                        metodoPago
                );

        transacciones.add(
                transaccion
        );

        if (resultado) {

            venta.marcarComoPagada();
        }

        return resultado;
    }

    // consultas de transacciones

    public List<Transaccion>
            getTransacciones() {

        return new ArrayList<>(
                transacciones
        );
    }

    public Transaccion buscarPorId(
            String id) {

        if (id == null
                ||
            id.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Debe ingresar el ID de la transacción."
            );
        }

        for (Transaccion t :
                transacciones) {

            if (t.getIdTransaccion()
                    .equalsIgnoreCase(
                            id.trim())) {

                return t;
            }
        }

        return null;
    }

    public List<Transaccion>
            buscarPorMetodo(
                    String metodo) {

        List<Transaccion> resultado =
                new ArrayList<>();

        if (metodo == null
                ||
            metodo.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Debe indicar el método de pago."
            );
        }

        for (Transaccion t :
                transacciones) {

            if (t.getNombreMetodoPago()
                    .equalsIgnoreCase(
                            metodo.trim())) {

                resultado.add(t);
            }
        }

        return resultado;
    }

    public List<Transaccion>
            buscarPorEstado(
                    EstadoPago estado) {

        List<Transaccion> resultado =
                new ArrayList<>();

        if (estado == null) {

            throw new IllegalArgumentException(
                    "Debe seleccionar un estado."
            );
        }

        for (Transaccion t :
                transacciones) {

            if (t.getEstado()
                    == estado) {

                resultado.add(t);
            }
        }

        return resultado;
    }

    public List<Transaccion>
            buscarPorVenta(
                    String codigoVenta) {

        List<Transaccion> resultado =
                new ArrayList<>();

        if (codigoVenta == null
                ||
            codigoVenta
                    .trim()
                    .isEmpty()) {

            throw new IllegalArgumentException(
                    "Debe ingresar el código de la venta."
            );
        }

        for (Transaccion t :
                transacciones) {

            if (t.getVenta()
                    .getCodigo()
                    .equalsIgnoreCase(
                            codigoVenta.trim())) {

                resultado.add(t);
            }
        }

        return resultado;
    }

    public List<Transaccion>
            buscarPorFecha(
                    LocalDate fecha) {

        List<Transaccion> resultado =
                new ArrayList<>();

        if (fecha == null) {

            throw new IllegalArgumentException(
                    "Debe ingresar una fecha válida."
            );
        }

        for (Transaccion t :
                transacciones) {

            if (t.getFecha()
                    .toLocalDate()
                    .equals(fecha)) {

                resultado.add(t);
            }
        }

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

        for (Transaccion t :
                transacciones) {

            t.mostrar();
        }
    }

    // Resumen de pagos

    public ResumenPago generarResumenPago() {

        ResumenPago resumen =
                new ResumenPago();

        for (Transaccion t :
                transacciones) {

            resumen.agregarTransaccion(
                    t
            );
        }

        return resumen;
    }

    public void generarResumen() {

        ResumenPago resumen =
                generarResumenPago();

        System.out.println(
                "\n========== RESUMEN DE PAGOS =========="
        );

        System.out.println(
                "Total de transacciones: "
                + resumen.getTotal()
        );

        System.out.println(
                "Aprobadas: "
                + resumen.getAprobadas()
        );

        System.out.println(
                "Rechazadas: "
                + resumen.getRechazadas()
        );

        System.out.println(
                "\n--- POR MÉTODO ---"
        );

        System.out.println(
                "Efectivo: "
                + resumen.getEfectivo()
        );

        System.out.println(
                "Tarjeta: "
                + resumen.getTarjeta()
        );

        System.out.println(
                "Transferencia: "
                + resumen.getTransferencia()
        );

        System.out.println(
                "Pago electrónico: "
                + resumen.getElectronico()
        );

        System.out.println(
                "\nTotal aprobado: $"
                + String.format(
                        "%.2f",
                        resumen.getTotalAprobado()
                )
        );
    }
}
