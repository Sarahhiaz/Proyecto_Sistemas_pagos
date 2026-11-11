package principal;

import Entidades.Pago;
import Entidades.PagoEfectivo;
import Entidades.PagoElectronico;
import Entidades.PagoTarjeta;
import Entidades.PagoTransferencia;
import Entidades.Transaccion;
import Entidades.Venta;

import Logica.GestorPago;

import java.util.Scanner;

public class main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final GestorPago gestor =
            new GestorPago();

    public static void main(
            String[] args) {

        int opcion;

        do {

            mostrarMenu();

            opcion =
                    leerEntero(
                            "Seleccione una opcion: "
                    );

            switch (opcion) {

                case 1:
                    registrarVenta();
                    break;

                case 2:
                    gestor.consultarTransacciones();
                    break;

                case 3:
                    gestor.generarResumen();
                    break;

                case 4:
                    System.out.println(
                            "\nGracias por utilizar el sistema."
                    );
                    break;

                default:

                    System.out.println(
                            "Seleccione una opcion del 1 al 4."
                    );
            }

        } while (opcion != 4);

        scanner.close();
    }

    private static void mostrarMenu() {

        System.out.println(
                "\n=================================="
        );

        System.out.println(
                " SISTEMA DE PROCESAMIENTO DE PAGOS"
        );

        System.out.println(
                "=================================="
        );

        System.out.println(
                "1. Registrar una venta"
        );

        System.out.println(
                "2. Consultar transacciones"
        );

        System.out.println(
                "3. Generar resumen de pagos"
        );

        System.out.println(
                "4. Salir"
        );
    }

    private static void registrarVenta() {

        try {

            System.out.println(
                    "\n========== NUEVA VENTA =========="
            );

            String codigo =
                    leerTextoObligatorio(
                            "Codigo de la venta: "
                    );

            String descripcion =
                    leerTextoObligatorio(
                            "Descripcion: "
                    );

            double total =
                    leerDoublePositivo(
                            "Total de la venta: $"
                    );

            Venta venta =
                    new Venta(
                            codigo,
                            descripcion,
                            total
                    );

            
            gestor.registrarVenta(
                    venta
            );

            venta.mostrarVenta();

            Pago metodoPago =
                    seleccionarMetodoPago(
                            total
                    );

            if (metodoPago == null) {

                System.out.println(
                        "La venta fue registrada, pero no se proceso ningún pago."
                );

                return;
            }

            boolean resultado =
                    gestor.procesarVenta(
                            venta,
                            metodoPago
                    );

            System.out.println();

            if (resultado) {

                System.out.println(
                        "PAGO APROBADO"
                );

            } else {

                System.out.println(
                        "PAGO RECHAZADO"
                );
            }

            System.out.println(
                    metodoPago
                            .obtenerDetalles()
            );

        } catch (
                IllegalArgumentException e) {

            System.out.println(
                    "\nError de validación: "
                    + e.getMessage()
            );

        } catch (
                IllegalStateException e) {

            System.out.println(
                    "\nOperacion no permitida: "
                    + e.getMessage()
            );
        }
    }

    private static Pago seleccionarMetodoPago(
            double monto) {

        while (true) {

            System.out.println(
                    "\n===== METODO DE PAGO ====="
            );

            System.out.println(
                    "1. Efectivo"
            );

            System.out.println(
                    "2. Tarjeta"
            );

            System.out.println(
                    "3. Transferencia bancaria"
            );

            System.out.println(
                    "4. Pago electronico"
            );

            System.out.println(
                    "5. Cancelar"
            );

            int opcion =
                    leerEntero(
                            "Seleccione: "
                    );

            try {

                switch (opcion) {

                    case 1:
                        return crearPagoEfectivo(
                                monto
                        );

                    case 2:
                        return crearPagoTarjeta(
                                monto
                        );

                    case 3:
                        return crearTransferencia(
                                monto
                        );

                    case 4:
                        return crearPagoElectronico(
                                monto
                        );

                    case 5:
                        return null;

                    default:

                        System.out.println(
                                "Seleccione del 1 al 5."
                        );
                }

            } catch (
                    IllegalArgumentException e) {

                System.out.println(
                        "\nDatos incorrectos: "
                        + e.getMessage()
                );

                System.out.println(
                        "Intente nuevamente."
                );
            }
        }
    }

    private static Pago crearPagoEfectivo(
            double monto) {

        double recibido =
                leerDoublePositivo(
                        "Monto recibido: $"
                );

        return new PagoEfectivo(
                monto,
                recibido
        );
    }

    private static Pago crearPagoTarjeta(
            double monto) {

        String tarjeta =
                leerTextoObligatorio(
                        "Numero de tarjeta (16 dígitos): "
                );

        String titular =
                leerTextoObligatorio(
                        "Nombre del titular: "
                );

        return new PagoTarjeta(
                monto,
                tarjeta,
                titular
        );
    }

    private static Pago crearTransferencia(
            double monto) {

        String banco =
                leerTextoObligatorio(
                        "Banco: "
                );

        String cuenta =
                leerTextoObligatorio(
                        "Numero de cuenta: "
                );

        String referencia =
                leerTextoObligatorio(
                        "Referencia: "
                );

        return new PagoTransferencia(
                monto,
                banco,
                cuenta,
                referencia
        );
    }

    private static Pago crearPagoElectronico(
            double monto) {

        String proveedor =
                leerTextoObligatorio(
                        "Proveedor: "
                );

        String correo =
                leerTextoObligatorio(
                        "Correo electronico: "
                );

        String token =
                leerTextoObligatorio(
                        "Token: "
                );

        return new PagoElectronico(
                monto,
                proveedor,
                correo,
                token
        );
    }

    // =================================
    // VALIDACIONES DE ENTRADA
    // =================================

    private static String leerTextoObligatorio(
            String mensaje) {

        while (true) {

            System.out.print(
                    mensaje
            );

            String valor =
                    scanner
                            .nextLine()
                            .trim();

            if (valor.isEmpty()) {

                System.out.println(
                        "Este campo es obligatorio."
                );

                continue;
            }

            return valor;
        }
    }

    private static int leerEntero(
            String mensaje) {

        while (true) {

            try {

                System.out.print(
                        mensaje
                );

                String texto =
                        scanner
                                .nextLine()
                                .trim();

                if (texto.isEmpty()) {

                    System.out.println(
                            "Debe ingresar una opcion."
                    );

                    continue;
                }

                return Integer.parseInt(
                        texto
                );

            } catch (
                    NumberFormatException e) {

                System.out.println(
                        "Ingrese un numero entero valido."
                );
            }
        }
    }

    private static double leerDoublePositivo(
            String mensaje) {

        while (true) {

            try {

                System.out.print(
                        mensaje
                );

                String texto =
                        scanner
                                .nextLine()
                                .trim();

                if (texto.isEmpty()) {

                    System.out.println(
                            "Este campo es obligatorio."
                    );

                    continue;
                }

                double numero =
                        Double.parseDouble(
                                texto
                        );

                if (numero <= 0) {

                    System.out.println(
                            "El valor debe ser mayor que cero."
                    );

                    continue;
                }

                return numero;

            } catch (
                    NumberFormatException e) {

                System.out.println(
                        "Ingrese un numero valido. "
                        + "Ejemplo: 25.50"
                );
            }
        }
    }
}