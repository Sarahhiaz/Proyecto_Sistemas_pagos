package sistemapagos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public LoginFrame() {
        setTitle("Inicio de Sesión - Sistema de Pagos");
        setSize(380, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar pantalla
        setResizable(false);
        
        // Panel Principal
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("ACCESO AL SISTEMA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelUser = new JPanel(new BorderLayout(5, 5));
        panelUser.add(new JLabel("Usuario: "), BorderLayout.WEST);
        txtUsuario = new JTextField();
        panelUser.add(txtUsuario, BorderLayout.CENTER);

        JPanel panelPass = new JPanel(new BorderLayout(5, 5));
        panelPass.add(new JLabel("Contraseña: "), BorderLayout.WEST);
        txtClave = new JPasswordField();
        panelPass.add(txtClave, BorderLayout.CENTER);

        btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(lblTitulo);
        panel.add(panelUser);
        panel.add(panelPass);
        panel.add(btnIngresar);

        add(panel);

        // Evento del botón
        btnIngresar.addActionListener(e -> validarLogin());
    }

    private void validarLogin() {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtClave.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            MongoDatabase db = ConexionDB.obtenerConexion();
            MongoCollection<Document> usuarios = db.getCollection("usuarios");

            Document filtro = new Document("usuario", user).append("clave", pass);
            Document encontrado = usuarios.find(filtro).first();

            if (encontrado != null) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido, " + user + "!", "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Cierra el Login
                new MenuFrame().setVisible(true); // Abre el Menú Principal
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión a MongoDB: " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}