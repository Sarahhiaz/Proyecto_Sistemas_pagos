package formularios;

import Entidades.*;
import Logica.UsuarioRepository;
import org.bson.Document;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnLogin, btnIrRegistro;

    public LoginFrame() {
        setTitle("Iniciar Sesión - Sistema de Pagos");
        setSize(380, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN");
        lblTitulo.setBounds(130, 20, 150, 25);
        add(lblTitulo);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(40, 70, 80, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(120, 70, 190, 25);
        add(txtCorreo);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(40, 110, 80, 25);
        add(lblPass);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(120, 110, 190, 25);
        add(txtContrasena);

        btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(40, 160, 140, 30);
        add(btnLogin);

        btnIrRegistro = new JButton("Registrarse");
        btnIrRegistro.setBounds(190, 160, 120, 30);
        add(btnIrRegistro);

        // Lógica de Inicio de Sesión
        btnLogin.addActionListener((ActionEvent e) -> {
            String correo = txtCorreo.getText().trim();
            String pass = new String(txtContrasena.getPassword());

            if (correo.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese correo y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Document usuarioEncontrado = UsuarioRepository.validarLogin(correo, pass);
            if (usuarioEncontrado != null) {
                String rol = usuarioEncontrado.getString("rol");
                String nombre = usuarioEncontrado.getString("nombre");
                
                JOptionPane.showMessageDialog(null, "¡Bienvenido, " + nombre + "!\nRol asignado: " + rol, "Acceso Permitido", JOptionPane.INFORMATION_MESSAGE);
                
                // ==========================================
                // CONEXIÓN AL MENÚ PRINCIPAL
                // ==========================================
                MenuPrincipalFrame menuPrincipal = new MenuPrincipalFrame(nombre, rol);
                menuPrincipal.setVisible(true);
                
                dispose(); // Cierra la ventana de login actual
            } else {
                JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón para abrir la ventana de Registro
        btnIrRegistro.addActionListener((ActionEvent e) -> {
            dispose();
            new RegisterFrame().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}