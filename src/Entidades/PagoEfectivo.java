package Entidades;

public class PagoEfectivo
        extends Pago {

    private double montoRecibido;

    private double cambio;

    public PagoEfectivo(
            double monto,
            double montoRecibido) {

        super(monto);

        if (Double.isNaN(
                montoRecibido)
                ||
            Double.isInfinite(
                montoRecibido)) {

            throw new IllegalArgumentException(
                    "El monto recibido no es valido."
            );
        }

        if (montoRecibido <= 0) {

            throw new IllegalArgumentException(
                    "El monto recibido debe ser mayor que cero."
            );
        }

        this.montoRecibido =
                montoRecibido;
    }

    @Override
    public boolean validarPago() {

        return montoRecibido >= monto;
    }

    @Override
    public boolean procesarPago() {

        if (!validarPago()) {

            cambio = 0;

            rechazarPago(
                    "El monto recibido es insuficiente. "
                    + "Faltan $"
                    + String.format(
                            "%.2f",
                            monto - montoRecibido
                    )
            );

            return false;
        }

        cambio =
                montoRecibido - monto;

        aprobarPago(
                "Pago en efectivo aprobado."
        );

        return true;
    }

    public double getMontoRecibido() {
        return montoRecibido;
    }

    public double getCambio() {
        return cambio;
    }

    @Override
    public String obtenerDetalles() {

        return super.obtenerDetalles()
                + "\nMonto recibido: $"
                + String.format(
                        "%.2f",
                        montoRecibido
                )
                + "\nCambio: $"
                + String.format(
                        "%.2f",
                        cambio
                );
    }
}
