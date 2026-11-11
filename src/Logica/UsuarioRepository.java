package Logica;

import Entidades.ConexionDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class UsuarioRepository {

    public static boolean registrarUsuario(String id, String nombre, String correo, String contraseña, String rol) {
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            MongoCollection<Document> collection = db.getCollection("usuarios");

            // Verificar si el correo ya existe
            Document existente = collection.find(Filters.eq("correo", correo)).first();
            if (existente != null) {
                return false; 
            }

            // Encriptar la contraseña antes de guardarla
            String passwordEncriptada = SecurityUtils.encryptPassword(contraseña);

            Document doc = new Document("id", id)
                    .append("nombre", nombre)
                    .append("correo", correo)
                    .append("contraseña", passwordEncriptada)
                    .append("rol", rol);

            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public static Document validarLogin(String correo, String contraseña) {
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            MongoCollection<Document> collection = db.getCollection("usuarios");

            // Encriptar la contraseña ingresada para buscar el hash coincidente
            String passwordEncriptada = SecurityUtils.encryptPassword(contraseña);

            Document usuario = collection.find(
                    Filters.and(
                            Filters.eq("correo", correo),
                            Filters.eq("contraseña", passwordEncriptada)
                    )
            ).first();

            return usuario;
        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            return null;
        }
    }
}