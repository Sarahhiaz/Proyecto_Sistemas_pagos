/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

/**
 *
 * @author Admin
 */
import Logica.GestorPago;
import Entidades.EstadoPago;
import Entidades.Pago;
import Entidades.PagoEfectivo;
import Entidades.PagoElectronico;
import Entidades.PagoTarjeta;
import Entidades.Transaccion;
import Entidades.MetodoPago;
import Entidades.Venta;
import Entidades.PagoTransferencia;
import Entidades.ConexionDB; // <-- Importación para la conexión a MongoDB

import com.mongodb.client.MongoDatabase; // <-- Importación del driver de MongoDB
import org.bson.Document; // <-- Importación para documentos BSON

import java.util.Scanner;

public class main {

    private static Scanner scanner =
            new Scanner(System.in);

    private static GestorPago gestor =
            new GestorPago();

    public static void main(String[] args) {

        // Ejecutar la prueba de conexión a MongoDB al arrancar el sistema
        probarConexionMongo();

        int opcion;

        do {

            mostrarMenu();

            opcion = leerEntero(
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
                            "\nOpcion no válida."
                    );
            }

        } while (opcion != 4);

        scanner.close();
    }

    // Método para probar la conexión a MongoDB y Compass
    private static void probarConexionMongo() {
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            db.runCommand(new Document("ping", 1));
            System.out.println("\n[✓] ¡CONEXIÓN EXITOSA A MONGODB COMPASS!");
            System.out.println("[✓] Base de datos activa: " + db.getName());
        } catch (Exception e) {
            System.err.println("\n[✗] ADVERTENCIA: No se pudo conectar a MongoDB: " + e.getMessage());
        }
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

        System.out.println(
                "\n========== NUEVA VENTA =========="
        );

        System.out.print(
                "Descripcion de la venta: "
        );

        String descripcion =
                scanner.nextLine();

        double total =
                leerDouble(
                        "Total de la venta: $"
                );

        if (total <= 0) {

            System.out.println(
                    "El total debe ser mayor que cero."
            );

            return;
        }

        Venta venta =
                new Venta(
                        descripcion,
                        total
                );

        venta.mostrarVenta();

        Pago metodoPago =
                seleccionarMetodoPago(total);

        if (metodoPago == null) {

            System.out.println(
                    "No se selecciono un metodo de pago."
            );

            return;
        }

        gestor.procesarVenta(
                venta,
                metodoPago
        );

        System.out.println(
                "\n" +
                metodoPago.obtenerDetalles()
        );
    }

    private static Pago seleccionarMetodoPago(
            double monto) {

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

        int opcion =
                leerEntero(
                        "Seleccione: "
                );

        switch (opcion) {

            case 1:
                return crearPagoEfectivo(monto);

            case 2:
                return crearPagoTarjeta(monto);

            case 3:
                return crearTransferencia(monto);

            case 4:
                return crearPagoElectronico(monto);

            default:

                System.out.println(
                        "Metodo de pago no valido."
                );

                return null;
        }
    }

    private static Pago crearPagoEfectivo(
            double monto) {

        double recibido =
                leerDouble(
                        "Monto recibido: $"
                );

        return new PagoEfectivo(
                monto,
                recibido
        );
    }

    private static Pago crearPagoTarjeta(
            double monto) {

        System.out.print(
                "Numero de tarjeta (16 digitos): "
        );

        String tarjeta =
                scanner.nextLine();

        System.out.print(
                "Nombre del titular: "
        );

        String titular =
                scanner.nextLine();

        return new PagoTarjeta(
                monto,
                tarjeta,
                titular
        );
    }

    private static Pago crearTransferencia(
            double monto) {

        System.out.print(
                "Banco: "
        );

        String banco =
                scanner.nextLine();

        System.out.print(
                "Número de cuenta: "
        );

        String cuenta =
                scanner.nextLine();

        System.out.print(
                "Referencia: "
        );

        String referencia =
                scanner.nextLine();

        return new PagoTransferencia(
                monto,
                banco,
                cuenta,
                referencia
        );
    }

    private static Pago crearPagoElectronico(
            double monto) {

        System.out.print(
                "Proveedor (PayPal, Stripe, etc.): "
        );

        String proveedor =
                scanner.nextLine();

        System.out.print(
                "Correo electrónico: "
        );

        String correo =
                scanner.nextLine();

        System.out.print(
                "Token: "
        );

        String token =
                scanner.nextLine();

        return new PagoElectronico(
                monto,
                proveedor,
                correo,
                token
        );
    }

    private static int leerEntero(
            String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                int valor =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                return valor;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese un numero entero valido."
                );
            }
        }
    }

    private static double leerDouble(
            String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                double valor =
                        Double.parseDouble(
                                scanner.nextLine()
                        );

                return valor;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese un numero valido."
                );
            }
        }
    }
}
