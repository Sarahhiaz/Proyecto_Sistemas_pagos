package Entidades;

public interface MetodoPago {

    boolean validarPago();

    boolean procesarPago();

    String obtenerDetalles();

    String getMensajeResultado();
}
