package Entidades;

public class PagoTarjeta
        extends Pago {

    private String numeroTarjeta;

    private String titular;

    private String autorizacion;

    public PagoTarjeta(
            double monto,
            String numeroTarjeta,
            String titular) {

        super(monto);

        validarNumeroTarjeta(
                numeroTarjeta
        );

        validarTitular(
                titular
        );

        this.numeroTarjeta =
                numeroTarjeta.trim();

        this.titular =
                titular.trim();
    }

    private void validarNumeroTarjeta(
            String numeroTarjeta) {

        if (numeroTarjeta == null
                ||
            numeroTarjeta
                    .trim()
                    .isEmpty()) {

            throw new IllegalArgumentException(
                    "El numero de tarjeta es obligatorio."
            );
        }

        String tarjeta =
                numeroTarjeta.trim();

        if (!tarjeta.matches(
                "\\d+")) {

            throw new IllegalArgumentException(
                    "El numero de tarjeta debe contener solamente numeros."
            );
        }

        if (tarjeta.length() != 16) {

            throw new IllegalArgumentException(
                    "El numero de tarjeta debe contener exactamente 16 digitos."
            );
        }
    }

    private void validarTitular(
            String titular) {

        if (titular == null
                ||
            titular.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre del titular es obligatorio."
            );
        }

        if (!titular.trim().matches(
                "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {

            throw new IllegalArgumentException(
                    "El nombre del titular solamente puede contener letras."
            );
        }
    }

    @Override
    public boolean validarPago() {

        return numeroTarjeta != null
                && numeroTarjeta.matches(
                        "\\d{16}")
                && titular != null
                && !titular.isBlank();
    }

    @Override
    public boolean procesarPago() {

        if (!validarPago()) {

            rechazarPago(
                    "Los datos de la tarjeta no son validos."
            );

            return false;
        }

        autorizacion =
                "AUT-"
                + System.currentTimeMillis();

        aprobarPago(
                "Pago con tarjeta aprobado."
        );

        return true;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    @Override
    public String obtenerDetalles() {

        String ultimosDigitos =
                numeroTarjeta.substring(
                        12
                );

        return super.obtenerDetalles()
                + "\nTarjeta: **** **** **** "
                + ultimosDigitos
                + "\nTitular: "
                + titular;
    }
}
