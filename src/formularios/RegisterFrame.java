package formularios;

import Logica.UsuarioRepository;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame {
    private JTextField txtNombre, txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar, btnVolver;

    public RegisterFrame() {
        setTitle("Registro de Usuarios - Sistema de Pagos");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO USUARIO");
        lblTitulo.setBounds(90, 20, 230, 25);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 60, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 60, 180, 25);
        add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(50, 100, 80, 25);
        add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(140, 100, 180, 25);
        add(txtCorreo);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(50, 140, 80, 25);
        add(lblPass);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(140, 140, 180, 25);
        add(txtContrasena);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setBounds(50, 180, 80, 25);
        add(lblRol);

        cmbRol = new JComboBox<>(new String[]{"Cliente", "Administrador"});
        cmbRol.setBounds(140, 180, 180, 25);
        add(cmbRol);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(50, 230, 130, 30);
        add(btnRegistrar);

        btnVolver = new JButton("Volver al Login");
        btnVolver.setBounds(190, 230, 130, 30);
        add(btnVolver);

        // Acción Registrar
        btnRegistrar.addActionListener((ActionEvent e) -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String pass = new String(txtContrasena.getPassword());
            String rol = (String) cmbRol.getSelectedItem();

            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String idUnico = "USR-" + System.currentTimeMillis();
            boolean registrado = UsuarioRepository.registrarUsuario(idUnico, nombre, correo, pass, rol);

            if (registrado) {
                JOptionPane.showMessageDialog(null, "¡Usuario registrado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose(); 
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción Volver
        btnVolver.addActionListener((ActionEvent e) -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}