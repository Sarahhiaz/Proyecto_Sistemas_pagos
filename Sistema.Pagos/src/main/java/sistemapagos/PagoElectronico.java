package sistemapagos;

public class PagoElectronico extends PagoAbstracto {

    private final String correo;

    public PagoElectronico(String titular, String correo) {
        super(titular);
        this.correo = correo;
    }

    @Override
    public boolean validarPago(double monto) {
        return monto > 0
                && correo != null
                && correo.contains("@");
    }

    @Override
    public boolean procesarPago(double monto) {
        if (!validarPago(monto)) {
            System.out.println("No se pudo procesar el pago electrónico.");
            return false;
        }

        System.out.println("Pago electrónico procesado correctamente.");
        return true;
    }

    @Override
    public String obtenerTipoPago() {
        return "Pago electrónico";
    }
}
