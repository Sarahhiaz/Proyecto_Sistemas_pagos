package sistemapagos;

public class PagoTransferencia extends PagoAbstracto {

    private final String cuentaBancaria;

    public PagoTransferencia(String titular, String cuentaBancaria) {
        super(titular);
        this.cuentaBancaria = cuentaBancaria;
    }

    @Override
    public boolean validarPago(double monto) {
        return monto > 0
                && cuentaBancaria != null
                && !cuentaBancaria.isBlank();
    }

    @Override
    public boolean procesarPago(double monto) {
        if (!validarPago(monto)) {
            System.out.println("No se pudo procesar la transferencia.");
            return false;
        }

        System.out.println("Transferencia bancaria procesada correctamente.");
        return true;
    }

    @Override
    public String obtenerTipoPago() {
        return "Transferencia bancaria";
    }
}
