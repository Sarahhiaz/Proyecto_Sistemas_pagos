package sistemapagos;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class SistemaPagos {

    private final List<Venta> ventas = new ArrayList<>();
    private final List<Transaccion> transacciones = new ArrayList<>();

    public void registrarVenta(Venta venta) {
        ventas.add(venta);
        System.out.println("Venta registrada correctamente.");
    }

    public Venta buscarVenta(int id) {
        for (Venta venta : ventas) {
            if (venta.getId() == id) {
                return venta;
            }
        }
        return null;
    }

    public boolean realizarPago(Venta venta, MetodoPago metodoPago) {
        if (venta == null) {
            System.out.println("La venta no existe.");
            return false;
        }

        boolean valido = metodoPago.validarPago(venta.getMonto());
        boolean procesado = false;

        if (valido) {
            procesado = metodoPago.procesarPago(venta.getMonto());
        } else {
            System.out.println("El pago no pasó la validación.");
        }

        // Guardar en la memoria local
        int nuevoId = transacciones.size() + 1;
        Transaccion nuevaTransaccion = new Transaccion(nuevoId, venta, metodoPago, procesado);
        transacciones.add(nuevaTransaccion);

        // Guardar en la base de datos MongoDB
        try {
            MongoCollection<Document> col = ConexionDB.obtenerConexion().getCollection("transacciones");
            Document doc = new Document("id_transaccion", nuevoId)
                    .append("id_venta", venta.getId())
                    .append("descripcion", venta.getDescripcion())
                    .append("monto", venta.getMonto())
                    .append("metodo_pago", metodoPago.obtenerTipoPago())
                    .append("estado", procesado ? "EXITOSA" : "RECHAZADA");
            col.insertOne(doc);
        } catch (Exception e) {
            System.out.println("Nota: Error guardando en BD: " + e.getMessage());
        }

        return procesado;
    }

    public void consultarTransacciones() {
        System.out.println("\n===== TRANSACCIONES EN BASE DE DATOS =====");
        try {
            MongoCollection<Document> col = ConexionDB.obtenerConexion().getCollection("transacciones");
            for (Document doc : col.find()) {
                System.out.printf("Transacción #%d | Venta: $%.2f | Método: %s | Estado: %s%n",
                        doc.getInteger("id_transaccion"),
                        doc.getDouble("monto"),
                        doc.getString("metodo_pago"),
                        doc.getString("estado"));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar BD: " + e.getMessage());
        }
    }

    public void generarResumen() {
        double totalExitoso = 0;
        double totalRechazado = 0;
        int efectivo = 0, tarjeta = 0, transferencia = 0, electronico = 0;

        for (Transaccion transaccion : transacciones) {
            if (transaccion.isExitosa()) {
                totalExitoso += transaccion.getVenta().getMonto();
                switch (transaccion.getMetodoPago().obtenerTipoPago()) {
                    case "Efectivo" -> efectivo++;
                    case "Tarjeta" -> tarjeta++;
                    case "Transferencia bancaria" -> transferencia++;
                    case "Pago electrónico" -> electronico++;
                }
            } else {
                totalRechazado += transaccion.getVenta().getMonto();
            }
        }

        System.out.println("\n===== RESUMEN DE PAGOS =====");
        System.out.printf("Pagos exitosos: $%.2f%n", totalExitoso);
        System.out.printf("Pagos rechazados: $%.2f%n", totalRechazado);
        System.out.println("Efectivo: " + efectivo + " | Tarjeta: " + tarjeta + " | Transferencia: " + transferencia + " | Pago electrónico: " + electronico);
    }
}