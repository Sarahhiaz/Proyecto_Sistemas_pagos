package Entidades;

import java.time.LocalDateTime;

public class Venta {

    private String codigo;

    private String descripcion;

    private double importe;

    private LocalDateTime fecha;

    private boolean pagada;

    public Venta(
            String codigo,
            String descripcion,
            double importe) {

        validarCodigo(
                codigo
        );

        validarDescripcion(
                descripcion
        );

        validarImporte(
                importe
        );

        this.codigo =
                codigo.trim();

        this.descripcion =
                descripcion.trim();

        this.importe =
                importe;

        this.fecha =
                LocalDateTime.now();

        this.pagada =
                false;
    }

    private void validarCodigo(
            String codigo) {

        if (codigo == null
                ||
            codigo.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El codigo de la venta es obligatorio."
            );
        }

        if (!codigo
                .trim()
                .matches(
                        "[A-Za-z0-9-]+")) {

            throw new IllegalArgumentException(
                    "El codigo solamente puede contener letras, numeros y guiones."
            );
        }

        if (codigo
                .trim()
                .length() > 20) {

            throw new IllegalArgumentException(
                    "El codigo no puede superar 20 caracteres."
            );
        }
    }

    private void validarDescripcion(
            String descripcion) {

        if (descripcion == null
                ||
            descripcion
                    .trim()
                    .isEmpty()) {

            throw new IllegalArgumentException(
                    "La descripcion es obligatoria."
            );
        }

        if (descripcion
                .trim()
                .length() < 3) {

            throw new IllegalArgumentException(
                    "La descripcion debe contener al menos 3 caracteres."
            );
        }
    }

    private void validarImporte(
            double importe) {

        if (Double.isNaN(importe)
                ||
            Double.isInfinite(
                    importe)) {

            throw new IllegalArgumentException(
                    "El importe ingresado no es valido."
            );
        }

        if (importe <= 0) {

            throw new IllegalArgumentException(
                    "El importe debe ser mayor que cero."
            );
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getImporte() {
        return importe;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public boolean estaPagada() {
        return pagada;
    }

    public void marcarComoPagada() {

        if (pagada) {

            throw new IllegalStateException(
                    "La venta ya se encuentra pagada."
            );
        }

        pagada = true;
    }

    public void mostrarVenta() {

        System.out.println(
                "\n========== VENTA =========="
        );

        System.out.println(
                "Codigo: "
                + codigo
        );

        System.out.println(
                "Descripcion: "
                + descripcion
        );

        System.out.println(
                "Importe: $"
                + String.format(
                        "%.2f",
                        importe
                )
        );

        System.out.println(
                "Fecha: "
                + fecha
        );

        System.out.println(
                "Estado: "
                + (
                    pagada
                    ? "PAGADA"
                    : "PENDIENTE"
                )
        );
    }
}
