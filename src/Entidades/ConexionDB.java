package Entidades;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionDB {
    private static MongoClient cliente;
    private static MongoDatabase baseDeDatos;

    // Método principal que creaste
    public static MongoDatabase obtenerConexion() {
        if (baseDeDatos == null) {
            cliente = MongoClients.create("mongodb://localhost:27017");
            baseDeDatos = cliente.getDatabase("SistemaVentas");
        }
        return baseDeDatos;
    }

    // Método auxiliar para compatibilidad (llama al mismo método para evitar el error)
    public static MongoDatabase getDatabase() {
        return obtenerConexion();
    }
}