package sistemapagos;

public class Venta {

    private final int id;
    private final String descripcion;
    private final double monto;

    public Venta(int id, String descripcion, double monto) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return String.format(
                "Venta #%d | %s | $%.2f",
                id, descripcion, monto
        );
    }
}
