package sistemapagos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionDB {
    private static MongoClient cliente;
    private static MongoDatabase baseDeDatos;

    public static MongoDatabase obtenerConexion() {
        if (baseDeDatos == null) {
            cliente = MongoClients.create("mongodb://localhost:27017");
            baseDeDatos = cliente.getDatabase("SistemaVentas");
        }
        return baseDeDatos;
    }
}