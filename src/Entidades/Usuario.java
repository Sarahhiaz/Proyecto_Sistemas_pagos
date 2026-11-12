package Entidades;

public class Usuario {
    
    // Atributos privados según el diagrama de clases
    private String id;
    private String nombre;
    private String correo;
    private String contraseña;
    private String rol;

    // Constructor vacio
    public Usuario() {
    }

    // Constructor con todos los atributos
    public Usuario(String id, String nombre, String correo, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    /**
     * Método especificado en el diagrama para validar las credenciales.
     * @return true si los campos obligatorios de acceso son válidos.
     */
    public boolean validarCredenciales() {
        return this.correo != null && !this.correo.trim().isEmpty() &&
               this.contraseña != null && !this.contraseña.trim().isEmpty();
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}