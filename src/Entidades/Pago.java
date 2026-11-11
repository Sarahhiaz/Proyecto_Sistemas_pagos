package Entidades;

import java.time.LocalDateTime;

public abstract class Pago
        implements MetodoPago {

    protected double monto;

    protected LocalDateTime fecha;

    protected EstadoPago estado;

    protected String mensajeResultado;

    public Pago(double monto) {

        validarMonto(monto);

        this.monto = monto;

        this.fecha =
                LocalDateTime.now();

        this.estado =
                EstadoPago.PENDIENTE;

        this.mensajeResultado =
                "Pago pendiente.";
    }

    private void validarMonto(
            double monto) {

        if (Double.isNaN(monto)
                || Double.isInfinite(monto)) {

            throw new IllegalArgumentException(
                    "El monto ingresado no es válido."
            );
        }

        if (monto <= 0) {

            throw new IllegalArgumentException(
                    "El monto del pago debe ser mayor que cero."
            );
        }
    }

    protected void aprobarPago(
            String mensaje) {

        estado =
                EstadoPago.APROBADO;

        mensajeResultado =
                mensaje;
    }

    protected void rechazarPago(
            String mensaje) {

        estado =
                EstadoPago.RECHAZADO;

        mensajeResultado =
                mensaje;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    @Override
    public String getMensajeResultado() {
        return mensajeResultado;
    }

    @Override
    public abstract boolean validarPago();

    @Override
    public abstract boolean procesarPago();

    @Override
    public String obtenerDetalles() {

        return "Monto: $"
                + String.format(
                        "%.2f",
                        monto
                )
                + "\nFecha: "
                + fecha
                + "\nEstado: "
                + estado
                + "\nResultado: "
                + mensajeResultado;
    }
}
