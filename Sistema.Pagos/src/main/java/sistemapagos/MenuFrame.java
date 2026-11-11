package sistemapagos;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuFrame extends JFrame {

    private SistemaPagos sistema = new SistemaPagos();

    // Componentes Venta y Pago
    private JTextField txtIdVenta, txtDescripcion, txtMonto, txtTitular, txtDetalleMetodo;
    private JComboBox<String> cbMetodoPago;
    private JLabel lblDetalle;
    
    // Tabla de Historial MongoDB
    private JTable tablaTransacciones;
    private DefaultTableModel modeloTabla;

    public MenuFrame() {
        setTitle("Sistema de Gestión de Pagos - MongoDB");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel con pestañas
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Procesar Pago", crearPanelPago());
        pestañas.addTab("Historial en MongoDB", crearPanelHistorial());

        add(pestañas);
    }

    private JPanel crearPanelPago() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdVenta = new JTextField();
        txtDescripcion = new JTextField();
        txtMonto = new JTextField();
        txtTitular = new JTextField();
        txtDetalleMetodo = new JTextField();
        
        cbMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia bancaria", "Pago electrónico"});
        lblDetalle = new JLabel("Detalle (N/A):");

        cbMetodoPago.addActionListener(e -> {
            String seleccion = (String) cbMetodoPago.getSelectedItem();
            switch (seleccion) {
                case "Tarjeta" -> lblDetalle.setText("Últimos 4 dígitos:");
                case "Transferencia bancaria" -> lblDetalle.setText("Número de Cuenta:");
                case "Pago electrónico" -> lblDetalle.setText("Correo electrónico:");
                default -> lblDetalle.setText("Detalle (N/A):");
            }
        });

        JButton btnCobrar = new JButton("Registrar y Cobrar");
        btnCobrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCobrar.addActionListener(e -> procesarCobro());

        panel.add(new JLabel("ID Venta:"));
        panel.add(txtIdVenta);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("Monto ($):"));
        panel.add(txtMonto);
        panel.add(new JLabel("Titular del Pago:"));
        panel.add(txtTitular);
        panel.add(new JLabel("Método de Pago:"));
        panel.add(cbMetodoPago);
        panel.add(lblDetalle);
        panel.add(txtDetalleMetodo);
        panel.add(new JLabel(""));
        panel.add(btnCobrar);

        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        modeloTabla = new DefaultTableModel(new String[]{"# Transacción", "ID Venta", "Descripción", "Monto", "Método", "Estado"}, 0);
        tablaTransacciones = new JTable(modeloTabla);

        JButton btnCargar = new JButton("Cargar / Actualizar desde MongoDB");
        btnCargar.addActionListener(e -> cargarDatosMongoDB());

        panel.add(new JScrollPane(tablaTransacciones), BorderLayout.CENTER);
        panel.add(btnCargar, BorderLayout.SOUTH);

        return panel;
    }

    private void procesarCobro() {
        try {
            int idVenta = Integer.parseInt(txtIdVenta.getText().trim());
            String desc = txtDescripcion.getText().trim();
            double monto = Double.parseDouble(txtMonto.getText().trim());
            String titular = txtTitular.getText().trim();
            String metodoSel = (String) cbMetodoPago.getSelectedItem();
            String detalle = txtDetalleMetodo.getText().trim();

            Venta venta = new Venta(idVenta, desc, monto);
            sistema.registrarVenta(venta);

            MetodoPago metodo = switch (metodoSel) {
                case "Tarjeta" -> new PagoTarjeta(titular, detalle);
                case "Transferencia bancaria" -> new PagoTransferencia(titular, detalle);
                case "Pago electrónico" -> new PagoElectronico(titular, detalle);
                default -> new PagoEfectivo(titular);
            };

            boolean exito = sistema.realizarPago(venta, metodo);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Pago registrado y guardado exitosamente en MongoDB.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatosMongoDB(); // Refresca la tabla
            } else {
                JOptionPane.showMessageDialog(this, "El pago fue rechazado por validación.", "Error de Pago", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los valores numéricos (ID y Monto).", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosMongoDB() {
        modeloTabla.setRowCount(0); // Limpia la tabla visual
        try {
            MongoCollection<Document> col = ConexionDB.obtenerConexion().getCollection("transacciones");
            for (Document doc : col.find()) {
                modeloTabla.addRow(new Object[]{
                    doc.getInteger("id_transaccion"),
                    doc.getInteger("id_venta"),
                    doc.getString("descripcion"),
                    "$" + doc.getDouble("monto"),
                    doc.getString("metodo_pago"),
                    doc.getString("estado")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar MongoDB: " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtIdVenta.setText("");
        txtDescripcion.setText("");
        txtMonto.setText("");
        txtTitular.setText("");
        txtDetalleMetodo.setText("");
    }
}