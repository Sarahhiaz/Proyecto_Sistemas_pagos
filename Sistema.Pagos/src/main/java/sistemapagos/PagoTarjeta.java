package sistemapagos;

public class PagoTarjeta extends PagoAbstracto {

    private final String numeroTarjeta;

    public PagoTarjeta(String titular, String numeroTarjeta) {
        super(titular);
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public boolean validarPago(double monto) {
        return monto > 0
                && numeroTarjeta != null
                && numeroTarjeta.length() >= 4;
    }

    @Override
    public boolean procesarPago(double monto) {
        if (!validarPago(monto)) {
            System.out.println("No se pudo procesar el pago con tarjeta.");
            return false;
        }

        System.out.println("Pago con tarjeta procesado correctamente.");
        return true;
    }

    @Override
    public String obtenerTipoPago() {
        return "Tarjeta";
    }
}
