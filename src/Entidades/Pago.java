package Entidades;
import java.time.LocalDateTime;

public abstract class Pago implements MetodoPago {

    protected double monto;
    protected LocalDateTime fecha;
    protected EstadoPago estado;

    public Pago(double monto) {
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoPago.PENDIENTE;
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

    protected void aprobarPago() {
        estado = EstadoPago.APROBADO;
    }

    protected void rechazarPago() {
        estado = EstadoPago.RECHAZADO;
    }

    @Override
    public abstract boolean validarPago();

    @Override
    public abstract boolean procesarPago();

    @Override
    public String obtenerDetalles() {
        return "Monto: $" + String.format("%.2f", monto)
                + "\nFecha: " + fecha
                + "\nEstado: " + estado;
    }
}