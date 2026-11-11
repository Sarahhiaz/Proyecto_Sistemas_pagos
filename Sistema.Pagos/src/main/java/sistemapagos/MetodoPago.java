package sistemapagos;

/**
 * Interfaz que representa el contrato para cualquier método de pago.
 */
public interface MetodoPago {
    boolean validarPago(double monto);
    boolean procesarPago(double monto);
    String obtenerTipoPago();
}
