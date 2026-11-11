package Entidades;

public class PagoElectronico
        extends Pago {

    private String proveedor;

    private String correo;

    private String token;

    public PagoElectronico(
            double monto,
            String proveedor,
            String correo,
            String token) {

        super(monto);
// Validaciones 
        validarProveedor(
                proveedor
        );

        validarCorreo(
                correo
        );

        validarToken(
                token
        );

        this.proveedor =
                proveedor.trim();

        this.correo =
                correo.trim();

        this.token =
                token.trim();
    }

    private void validarProveedor(
            String proveedor) {

        if (proveedor == null
                ||
            proveedor
                    .trim()
                    .isEmpty()) {

            throw new IllegalArgumentException(
                    "El proveedor del pago electrónico es obligatorio."
            );
        }
    }

    private void validarCorreo(
            String correo) {

        if (correo == null
                ||
            correo.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El correo electrónico es obligatorio."
            );
        }

        String expresion =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!correo
                .trim()
                .matches(
                        expresion
                )) {

            throw new IllegalArgumentException(
                    "Ingrese un correo válido. "
                    + "Ejemplo: usuario@correo.com"
            );
        }
    }

    private void validarToken(
            String token) {

        if (token == null
                ||
            token.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El token es obligatorio."
            );
        }

        if (token.trim().length() < 4) {

            throw new IllegalArgumentException(
                    "El token debe contener al menos 4 caracteres."
            );
        }
    }

    @Override
    public boolean validarPago() {

        return !proveedor.isBlank()
                && !correo.isBlank()
                && !token.isBlank();
    }

    @Override
    public boolean procesarPago() {

        if (!validarPago()) {

            rechazarPago(
                    "Los datos del pago electrónico son inválidos."
            );

            return false;
        }

        aprobarPago(
                "Pago electrónico aprobado."
        );

        return true;
    }

    @Override
    public String obtenerDetalles() {

        return super.obtenerDetalles()
                + "\nProveedor: "
                + proveedor
                + "\nCorreo: "
                + correo;
    }
}
