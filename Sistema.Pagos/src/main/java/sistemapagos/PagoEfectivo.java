package sistemapagos;

public class PagoEfectivo extends PagoAbstracto {

    public PagoEfectivo(String titular) {
        super(titular);
    }

    @Override
    public boolean validarPago(double monto) {
        return monto > 0;
    }

    @Override
    public boolean procesarPago(double monto) {
        if (!validarPago(monto)) {
            return false;
        }

        System.out.println("Pago en efectivo procesado correctamente.");
        return true;
    }

    @Override
    public String obtenerTipoPago() {
        return "Efectivo";
    }
}
