package Logica;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import Entidades.ConexionDB;

public class UsuarioRepository {

    public static boolean registrarUsuario(String idUnico, String nombre, String correo, String password, String rol) {
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            MongoCollection<Document> colUsuarios = db.getCollection("usuarios");

            Document existente = colUsuarios.find(
                Filters.or(
                    Filters.eq("correo", correo),
                    Filters.eq("usuario", correo)
                )
            ).first();

            if (existente != null) {
                return false; // El usuario ya existe
            }

            String passwordEncriptada = SecurityUtils.encryptPassword(password);

            Document nuevoUsuario = new Document("idUnico", idUnico)
                    .append("nombre", nombre)
                    .append("correo", correo)
                    .append("usuario", correo)
                    .append("password", passwordEncriptada)
                    .append("rol", rol);

            colUsuarios.insertOne(nuevoUsuario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Document validarLogin(String correo, String password) {
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            MongoCollection<Document> colUsuarios = db.getCollection("usuarios");

            String passwordEncriptada = SecurityUtils.encryptPassword(password);

            Document usuario = colUsuarios.find(
                Filters.and(
                    Filters.or(
                        Filters.eq("correo", correo),
                        Filters.eq("usuario", correo)
                    ),
                    Filters.eq("password", passwordEncriptada)
                )
            ).first();

            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
