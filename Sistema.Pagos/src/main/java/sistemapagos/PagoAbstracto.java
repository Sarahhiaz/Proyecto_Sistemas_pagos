package sistemapagos;

/**
 * Clase abstracta que contiene elementos comunes de los métodos de pago.
 */
public abstract class PagoAbstracto implements MetodoPago {

    protected String titular;

    public PagoAbstracto(String titular) {
        this.titular = titular;
    }

    public String getTitular() {
        return titular;
    }

    // Método concreto heredado por las clases hijas.
    public void mostrarInformacion() {
        System.out.println("Titular: " + titular);
    }

    // Métodos abstractos que cada método de pago debe implementar.
    @Override
    public abstract boolean validarPago(double monto);

    @Override
    public abstract boolean procesarPago(double monto);
}
