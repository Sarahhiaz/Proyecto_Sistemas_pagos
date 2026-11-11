package sistemapagos;

public class Transaccion {

    private final int id;
    private final Venta venta;
    private final MetodoPago metodoPago;
    private final boolean exitosa;

    public Transaccion(
            int id,
            Venta venta,
            MetodoPago metodoPago,
            boolean exitosa) {

        this.id = id;
        this.venta = venta;
        this.metodoPago = metodoPago;
        this.exitosa = exitosa;
    }

    public int getId() {
        return id;
    }

    public Venta getVenta() {
        return venta;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public boolean isExitosa() {
        return exitosa;
    }

    @Override
    public String toString() {
        return String.format(
                "Transacción #%d | Venta: $%.2f | Método: %s | Estado: %s",
                id,
                venta.getMonto(),
                metodoPago.obtenerTipoPago(),
                exitosa ? "EXITOSA" : "RECHAZADA"
        );
    }
}
