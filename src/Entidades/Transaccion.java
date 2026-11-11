package Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaccion {

    private String idTransaccion;

    private Venta venta;

    private Pago metodoPago;

    private double importe;

    private LocalDateTime fecha;

    private EstadoPago estado;

    public Transaccion(
            Venta venta,
            Pago metodoPago) {

        if (venta == null) {

            throw new IllegalArgumentException(
                    "La transaccion debe estar asociada a una venta."
            );
        }

        if (metodoPago == null) {

            throw new IllegalArgumentException(
                    "La transaccion debe tener un metodo de pago."
            );
        }

        if (Math.abs(
                venta.getImporte()
                -
                metodoPago.getMonto()
        ) > 0.01) {

            throw new IllegalArgumentException(
                    "El importe de la venta y el pago no coinciden."
            );
        }

        this.idTransaccion =
                UUID.randomUUID()
                        .toString();

        this.venta =
                venta;

        this.metodoPago =
                metodoPago;

        this.importe =
                metodoPago.getMonto();

        this.fecha =
                LocalDateTime.now();

        this.estado =
                metodoPago.getEstado();
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public Venta getVenta() {
        return venta;
    }

    public Pago getMetodoPago() {
        return metodoPago;
    }

    public double getImporte() {
        return importe;
    }

    public double getMonto() {
        return importe;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public String getNombreMetodoPago() {

        return metodoPago
                .getClass()
                .getSimpleName();
    }

    public void mostrar() {

        System.out.println(
                "\n----------------------------------"
        );

        System.out.println(
                "ID: "
                + idTransaccion
        );

        System.out.println(
                "Venta: "
                + venta.getCodigo()
        );

        System.out.println(
                "Metodo: "
                + getNombreMetodoPago()
        );

        System.out.println(
                "Importe: $"
                + String.format(
                        "%.2f",
                        importe
                )
        );

        System.out.println(
                "Estado: "
                + estado
        );

        System.out.println(
                "Fecha: "
                + fecha
        );
    }
}
