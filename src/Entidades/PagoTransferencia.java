package Entidades;

public class PagoTransferencia
        extends Pago {

    private String banco;

    private String cuenta;

    private String referencia;

    public PagoTransferencia(
            double monto,
            String banco,
            String cuenta,
            String referencia) {

        super(monto);

        validarBanco(banco);

        validarCuenta(cuenta);

        validarReferencia(
                referencia
        );

        this.banco =
                banco.trim();

        this.cuenta =
                cuenta.trim();

        this.referencia =
                referencia.trim();
    }

    private void validarBanco(
            String banco) {

        if (banco == null
                ||
            banco.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El banco es obligatorio."
            );
        }

        if (banco.trim().length() < 2) {

            throw new IllegalArgumentException(
                    "Ingrese un nombre de banco valido."
            );
        }
    }

    private void validarCuenta(
            String cuenta) {

        if (cuenta == null
                ||
            cuenta.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El numero de cuenta es obligatorio."
            );
        }

        String valor =
                cuenta.trim();

        if (!valor.matches(
                "[A-Za-z0-9-]+")) {

            throw new IllegalArgumentException(
                    "La cuenta solamente puede contener letras, numeros o guiones."
            );
        }

        if (valor.length() < 4) {

            throw new IllegalArgumentException(
                    "El numero de cuenta debe contener al menos 4 caracteres."
            );
        }
    }

    private void validarReferencia(
            String referencia) {

        if (referencia == null
                ||
            referencia
                    .trim()
                    .isEmpty()) {

            throw new IllegalArgumentException(
                    "La referencia de la transferencia es obligatoria."
            );
        }

        if (referencia
                .trim()
                .length() < 3) {

            throw new IllegalArgumentException(
                    "La referencia debe contener al menos 3 caracteres."
            );
        }
    }

    @Override
    public boolean validarPago() {

        return !banco.isBlank()
                && !cuenta.isBlank()
                && !referencia.isBlank();
    }

    @Override
    public boolean procesarPago() {

        if (!validarPago()) {

            rechazarPago(
                    "Los datos de la transferencia son invalidos."
            );

            return false;
        }

        aprobarPago(
                "Transferencia aprobada."
        );

        return true;
    }

    @Override
    public String obtenerDetalles() {

        return super.obtenerDetalles()
                + "\nBanco: "
                + banco
                + "\nReferencia: "
                + referencia;
    }
}
