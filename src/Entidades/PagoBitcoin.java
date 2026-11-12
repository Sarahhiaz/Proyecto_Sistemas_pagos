package Entidades;

public class PagoBitcoin extends Pago {

    private String wallet;

    public PagoBitcoin(double monto, String wallet) {
        super(monto);
        this.wallet = wallet;
    }

    @Override
    public boolean validarPago() {
        return wallet != null && !wallet.trim().isEmpty();
    }

    @Override
    public boolean procesarPago() {
        if (validarPago()) {
            
            this.estado = EstadoPago.APROBADO; 
            System.out.println("Pago con Bitcoin aprobado.");
            return true;
        }

        this.estado = EstadoPago.RECHAZADO;
        return false;
    }

    @Override
    public String obtenerDetalles() {
        return """
                ===== PAGO BITCOIN =====
                """ +
                super.obtenerDetalles()
                + "\nWallet: " + wallet;
    }
}
