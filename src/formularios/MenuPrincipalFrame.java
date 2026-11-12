package formularios;

import Entidades.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuPrincipalFrame extends JFrame {
    private String nombreUsuario, rolUsuario;
    
    // Componentes del Cliente (Procesar Venta)
    private JTextField txtDescripcionCliente, txtMontoCliente, txtDetalleExtraCliente;
    private JComboBox<String> cmbMetodoPagoCliente;
    private JLabel lblDetalleDinamico;
    
    // Componentes del Cliente (Historial / CRUD)
    private JTable tablaHistorialCliente;
    private DefaultTableModel modeloTablaCliente;

    // Componentes del Administrador (CRUD)
    private JTable tablaAdminCRUD;
    private DefaultTableModel modeloAdminCRUD;

    // Componentes del Administrador (Consulta Avanzada - Proceso 4)
    private JTextField txtFiltroBusqueda;
    private JComboBox<String> cmbTipoFiltro;
    private JTable tablaConsultas;
    private DefaultTableModel modeloConsultas;

    // Componentes del Administrador (Resumen - Proceso 5)
    private JTextArea txtAreaResumen;

    public MenuPrincipalFrame(String nombre, String rol) {
        this.nombreUsuario = nombre;
        this.rolUsuario = rol;

        setTitle("Sistema de Procesamiento de Pagos - Panel de " + rol + " (" + nombre + ")");
        setSize(850, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        if (rol.equalsIgnoreCase("Administrador")) {
            tabbedPane.addTab("Gestión de Ventas (CRUD Admin)", crearPanelAdminCRUD());
            tabbedPane.addTab("Consulta de Transacciones (Proc. 4)", crearPanelConsultasAdmin());
            tabbedPane.addTab("Resumen de Pagos (Proc. 5)", crearPanelResumenAdmin());
        } else {
            tabbedPane.addTab("Procesar Venta y Pago", crearPanelClienteProcesar());
            tabbedPane.addTab("Historial y Gestión (CRUD)", crearPanelClienteHistorial());
        }

        add(tabbedPane);
    }

    // ==========================================
    // MÓDULO CLIENTE: PROCESAR VENTA Y PAGO
    // ==========================================
    private JPanel crearPanelClienteProcesar() {
        JPanel panel = new JPanel(null);

        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setBounds(40, 35, 150, 25);
        panel.add(lblDesc);

        txtDescripcionCliente = new JTextField();
        txtDescripcionCliente.setBounds(200, 35, 500, 25);
        panel.add(txtDescripcionCliente);

        JLabel lblMonto = new JLabel("Importe ($):");
        lblMonto.setBounds(40, 75, 150, 25);
        panel.add(lblMonto);

        txtMontoCliente = new JTextField();
        txtMontoCliente.setBounds(200, 75, 500, 25);
        panel.add(txtMontoCliente);

        JLabel lblMetodo = new JLabel("Método de Pago:");
        lblMetodo.setBounds(40, 115, 150, 25);
        panel.add(lblMetodo);

        cmbMetodoPagoCliente = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia", "Electrónico", "Bitcoin"});
        cmbMetodoPagoCliente.setBounds(200, 115, 500, 25);
        panel.add(cmbMetodoPagoCliente);

        lblDetalleDinamico = new JLabel("Monto Recibido ($):");
        lblDetalleDinamico.setBounds(40, 155, 150, 25);
        panel.add(lblDetalleDinamico);

        txtDetalleExtraCliente = new JTextField();
        txtDetalleExtraCliente.setBounds(200, 155, 500, 25);
        panel.add(txtDetalleExtraCliente);

        cmbMetodoPagoCliente.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String seleccionado = (String) e.getItem();
                switch (seleccionado) {
                    case "Efectivo":
                        lblDetalleDinamico.setText("Monto Recibido ($):");
                        break;
                    case "Tarjeta":
                        lblDetalleDinamico.setText("Nº Tarjeta (16 dígitos):");
                        break;
                    case "Transferencia":
                        lblDetalleDinamico.setText("Banco / Cuenta / Ref:");
                        break;
                    case "Electrónico":
                        lblDetalleDinamico.setText("Correo / Token:");
                        break;
                    case "Bitcoin":
                        lblDetalleDinamico.setText("Dirección Wallet:");
                        break;
                }
            }
        });

        JButton btnRegistrarCobrar = new JButton("Validar, Registrar y Procesar Pago");
        btnRegistrarCobrar.setBounds(200, 210, 500, 38);
        panel.add(btnRegistrarCobrar);

        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.setBounds(580, 500, 120, 25);
        panel.add(btnSalir);

        btnSalir.addActionListener((ActionEvent e) -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        btnRegistrarCobrar.addActionListener((ActionEvent e) -> {
            ejecutarProcesosVentaYTransaccionCliente();
        });

        return panel;
    }

    // ==========================================
    // MÓDULO CLIENTE: HISTORIAL Y CRUD
    // ==========================================
    private JPanel crearPanelClienteHistorial() {
        JPanel panel = new JPanel(new BorderLayout());

        modeloTablaCliente = new DefaultTableModel(new String[]{"ID Venta", "Descripción", "Importe ($)", "Método", "Fecha", "Estado"}, 0);
        tablaHistorialCliente = new JTable(modeloTablaCliente);
        JScrollPane scrollPane = new JScrollPane(tablaHistorialCliente);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnActualizarTabla = new JButton("Consultar / Actualizar");
        JButton btnEditarVenta = new JButton("Editar / Actualizar Venta");
        JButton btnEliminarVenta = new JButton("Eliminar Venta");

        panelBotones.add(btnActualizarTabla);
        panelBotones.add(btnEditarVenta);
        panelBotones.add(btnEliminarVenta);
        panel.add(panelBotones, BorderLayout.SOUTH);

        btnActualizarTabla.addActionListener((ActionEvent e) -> cargarDatosTransaccionesCliente());
        btnEditarVenta.addActionListener((ActionEvent e) -> editarVentaSeleccionadaCliente());
        btnEliminarVenta.addActionListener((ActionEvent e) -> eliminarVentaSeleccionadaCliente());

        cargarDatosTransaccionesCliente();

        return panel;
    }

    // ==========================================
    // MÓDULO ADMINISTRADOR: GESTIÓN CRUD DE VENTAS
    // ==========================================
    private JPanel crearPanelAdminCRUD() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrarSesionAdmin = new JButton("Cerrar Sesión");
        panelSuperior.add(btnCerrarSesionAdmin);
        panel.add(panelSuperior, BorderLayout.NORTH);

        btnCerrarSesionAdmin.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });

        modeloAdminCRUD = new DefaultTableModel(new String[]{"ID Venta", "Descripción", "Importe ($)", "Método", "Fecha", "Estado"}, 0);
        tablaAdminCRUD = new JTable(modeloAdminCRUD);
        panel.add(new JScrollPane(tablaAdminCRUD), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Consultar / Actualizar Tabla");
        JButton btnEditar = new JButton("Editar Venta Seleccionada");
        JButton btnEliminar = new JButton("Eliminar Venta Seleccionada");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarTablaAdminCRUD());
        btnEditar.addActionListener(e -> editarVentaAdmin());
        btnEliminar.addActionListener(e -> eliminarVentaAdmin());

        cargarTablaAdminCRUD();
        return panel;
    }

    // ==========================================
    // MÓDULO ADMINISTRADOR: CONSULTA DE TRANSACCIONES (Proceso 4)
    // ==========================================
    private JPanel crearPanelConsultasAdmin() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelFiltros = new JPanel(new FlowLayout());
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Criterios de Consulta Avanzada (Proceso 4)"));

        cmbTipoFiltro = new JComboBox<>(new String[]{"Todos", "Por ID Venta", "Por Descripción", "Por Método", "Por Estado"});
        txtFiltroBusqueda = new JTextField(15);
        JButton btnFiltrar = new JButton("Buscar / Filtrar");

        panelFiltros.add(new JLabel("Filtro:"));
        panelFiltros.add(cmbTipoFiltro);
        panelFiltros.add(new JLabel("Valor:"));
        panelFiltros.add(txtFiltroBusqueda);
        panelFiltros.add(btnFiltrar);

        panel.add(panelFiltros, BorderLayout.NORTH);

        modeloConsultas = new DefaultTableModel(new String[]{"ID Venta", "Descripción", "Importe ($)", "Método", "Fecha", "Estado"}, 0);
        tablaConsultas = new JTable(modeloConsultas);
        panel.add(new JScrollPane(tablaConsultas), BorderLayout.CENTER);

        btnFiltrar.addActionListener(e -> ejecutarConsultaAvanzada());

        cargarConsultasAdmin(null, null);
        return panel;
    }

    // ==========================================
    // MÓDULO ADMINISTRADOR: RESUMEN DE PAGOS (Proceso 5)
    // ==========================================
    private JPanel crearPanelResumenAdmin() {
        JPanel panel = new JPanel(new BorderLayout());

        txtAreaResumen = new JTextArea();
        txtAreaResumen.setEditable(false);
        txtAreaResumen.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(new JScrollPane(txtAreaResumen), BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        JButton btnGenerarResumen = new JButton("Calcular y Generar Resumen (Proceso 5)");
        panelSur.add(btnGenerarResumen);
        panel.add(panelSur, BorderLayout.SOUTH);

        btnGenerarResumen.addActionListener(e -> calcularResumenPagos());

        return panel;
    }

    // ==========================================
    // LÓGICA DE NEGOCIO: CLIENTE
    // ==========================================
    private void ejecutarProcesosVentaYTransaccionCliente() {
        String descripcion = txtDescripcionCliente.getText().trim();
        String strMonto = txtMontoCliente.getText().trim();
        String detalleExtra = txtDetalleExtraCliente.getText().trim();
        String tipoMetodo = (String) cmbMetodoPagoCliente.getSelectedItem();

        if (descripcion.isEmpty() || strMonto.isEmpty() || detalleExtra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(strMonto);
            if (monto <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El importe debe ser un número válido mayor a cero.", "Importe Inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Venta venta = new Venta(descripcion, monto);

            Pago pago = null;
            switch (tipoMetodo) {
                case "Efectivo":
                    double recibido = Double.parseDouble(detalleExtra);
                    pago = new PagoEfectivo(monto, recibido);
                    break;
                case "Tarjeta":
                    pago = new PagoTarjeta(monto, detalleExtra, "Titular Cliente");
                    break;
                case "Transferencia":
                    pago = new PagoTransferencia(monto, "Banco General", "Cuenta-001", detalleExtra);
                    break;
                case "Electrónico":
                    pago = new PagoElectronico(monto, "PayPal", detalleExtra, "token_ok");
                    break;
                case "Bitcoin":
                    pago = new PagoBitcoin(monto, detalleExtra);
                    break;
            }

            if (pago == null) {
                JOptionPane.showMessageDialog(this, "Método de pago no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean exitoPago = pago.procesarPago();
            venta.setMetodoPago(pago);

            Transaccion transaccion = new Transaccion(venta, pago);

            MongoDatabase db = ConexionDB.getDatabase();
            MongoCollection<Document> colVentas = db.getCollection("ventas");
            MongoCollection<Document> colTransacciones = db.getCollection("transacciones");

            // ==========================================
            // LÓGICA DE ID AUTOINCREMENTABLE
            // ==========================================
            int nuevoIdVenta = 1;
            Document ultimoDocumento = colVentas.find().sort(new Document("idVenta", -1)).first();
            if (ultimoDocumento != null && ultimoDocumento.containsKey("idVenta")) {
                Object idObj = ultimoDocumento.get("idVenta");
                if (idObj instanceof Number) {
                    nuevoIdVenta = ((Number) idObj).intValue() + 1;
                }
            }

            Document docVenta = new Document("idVenta", nuevoIdVenta)
                    .append("fecha", venta.getFecha().toString())
                    .append("descripcion", venta.getDescripcion())
                    .append("total", venta.getTotal())
                    .append("estado", pago.getEstado().toString());
            colVentas.insertOne(docVenta);

            Document docTransaccion = new Document("idTransaccion", transaccion.hashCode())
                    .append("idVenta", nuevoIdVenta)
                    .append("descripcion", venta.getDescripcion())
                    .append("fecha", LocalDateTime.now().toString())
                    .append("monto", transaccion.getMonto())
                    .append("metodoPago", pago.getClass().getSimpleName())
                    .append("estado", transaccion.getEstado().toString());
            colTransacciones.insertOne(docTransaccion);

            if (exitoPago) {
                JOptionPane.showMessageDialog(this, "¡Pago Aprobado y Registrado con Éxito!\n\n" + pago.obtenerDetalles(), "Resultado Exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El pago fue rechazado por las validaciones.\n\n" + pago.obtenerDetalles(), "Pago Rechazado", JOptionPane.WARNING_MESSAGE);
            }
            
            txtDescripcionCliente.setText("");
            txtMontoCliente.setText("");
            txtDetalleExtraCliente.setText("");
            cargarDatosTransaccionesCliente();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar el pago: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosTransaccionesCliente() {
        if (modeloTablaCliente == null) return;
        modeloTablaCliente.setRowCount(0);

        try {
            MongoDatabase db = ConexionDB.getDatabase();
            for (Document doc : db.getCollection("transacciones").find()) {
                double montoVal = 0.0;
                Object objMonto = doc.get("monto");
                if (objMonto instanceof Number) {
                    montoVal = ((Number) objMonto).doubleValue();
                }

                modeloTablaCliente.addRow(new Object[]{
                    doc.get("idVenta"),
                    doc.getString("descripcion"),
                    "$" + montoVal,
                    doc.getString("metodoPago"),
                    doc.getString("fecha"),
                    doc.getString("estado")
                });
            }
        } catch (Exception e) {
            System.err.println("Error al cargar historial: " + e.getMessage());
        }
    }

    private void editarVentaSeleccionadaCliente() {
        int filaSeleccionada = tablaHistorialCliente.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una transacción de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idVentaActual = modeloTablaCliente.getValueAt(filaSeleccionada, 0);
        String descActual = (String) modeloTablaCliente.getValueAt(filaSeleccionada, 1);
        String montoActualStr = modeloTablaCliente.getValueAt(filaSeleccionada, 2).toString().replace("$", "");
        String metodoActual = (String) modeloTablaCliente.getValueAt(filaSeleccionada, 3);
        String fechaActual = (String) modeloTablaCliente.getValueAt(filaSeleccionada, 4);
        String estadoActual = (String) modeloTablaCliente.getValueAt(filaSeleccionada, 5);

        JTextField txtIdV = new JTextField(idVentaActual.toString());
        JTextField txtDesc = new JTextField(descActual);
        JTextField txtMonto = new JTextField(montoActualStr);
        JComboBox<String> cmbMetodo = new JComboBox<>(new String[]{"PagoEfectivo", "PagoTarjeta", "PagoTransferencia", "PagoElectronico", "PagoBitcoin"});
        cmbMetodo.setSelectedItem(metodoActual);
        JTextField txtFecha = new JTextField(fechaActual);
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"APROBADO", "RECHAZADO", "PENDIENTE"});
        cmbEstado.setSelectedItem(estadoActual);

        Object[] message = {
            "ID Venta:", txtIdV,
            "Descripción:", txtDesc,
            "Importe ($):", txtMonto,
            "Método:", cmbMetodo,
            "Fecha:", txtFecha,
            "Estado:", cmbEstado
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Editar Venta / Transacción", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int nuevoIdVenta = Integer.parseInt(txtIdV.getText().trim());
                String nuevaDesc = txtDesc.getText().trim();
                double nuevoMonto = Double.parseDouble(txtMonto.getText().trim());
                String nuevoMetodo = (String) cmbMetodo.getSelectedItem();
                String nuevaFecha = txtFecha.getText().trim();
                String nuevoEstado = (String) cmbEstado.getSelectedItem();

                MongoDatabase db = ConexionDB.getDatabase();
                
                db.getCollection("ventas").updateOne(Filters.eq("idVenta", idVentaActual), 
                    new Document("$set", new Document("idVenta", nuevoIdVenta).append("descripcion", nuevaDesc).append("total", nuevoMonto).append("fecha", nuevaFecha).append("estado", nuevoEstado)));
                
                db.getCollection("transacciones").updateOne(Filters.eq("idVenta", idVentaActual), 
                    new Document("$set", new Document("idVenta", nuevoIdVenta).append("descripcion", nuevaDesc).append("monto", nuevoMonto).append("metodoPago", nuevoMetodo).append("fecha", nuevaFecha).append("estado", nuevoEstado)));

                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente en MongoDB.");
                cargarDatosTransaccionesCliente();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar (verifique que los números sean válidos): " + e.getMessage());
            }
        }
    }

    private void eliminarVentaSeleccionadaCliente() {
        int filaSeleccionada = tablaHistorialCliente.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una transacción de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object idVentaObj = modeloTablaCliente.getValueAt(filaSeleccionada, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la venta ID " + idVentaObj + " y su transacción asociada?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                MongoDatabase db = ConexionDB.getDatabase();
                db.getCollection("ventas").deleteOne(Filters.eq("idVenta", idVentaObj));
                db.getCollection("transacciones").deleteOne(Filters.eq("idVenta", idVentaObj));

                JOptionPane.showMessageDialog(this, "Registro eliminado de MongoDB Compass.");
                cargarDatosTransaccionesCliente();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // MÓDULO ADMINISTRADOR: CRUD
    // ==========================================
    private void cargarTablaAdminCRUD() {
        modeloAdminCRUD.setRowCount(0);
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            for (Document doc : db.getCollection("transacciones").find()) {
                double montoVal = 0.0;
                Object objMonto = doc.get("monto");
                if (objMonto instanceof Number) {
                    montoVal = ((Number) objMonto).doubleValue();
                }

                modeloAdminCRUD.addRow(new Object[]{
                    doc.get("idVenta"),
                    doc.getString("descripcion"),
                    "$" + montoVal,
                    doc.getString("metodoPago"),
                    doc.getString("fecha"),
                    doc.getString("estado")
                });
            }
        } catch (Exception e) {
            System.err.println("Error cargando tabla admin: " + e.getMessage());
        }
    }

    private void editarVentaAdmin() {
        int fila = tablaAdminCRUD.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para editar.");
            return;
        }
        Object idVentaActual = tablaAdminCRUD.getValueAt(fila, 0);
        String descActual = (String) tablaAdminCRUD.getValueAt(fila, 1);
        String montoActualStr = tablaAdminCRUD.getValueAt(fila, 2).toString().replace("$", "");
        String metodoActual = (String) tablaAdminCRUD.getValueAt(fila, 3);
        String fechaActual = (String) tablaAdminCRUD.getValueAt(fila, 4);
        String estadoActual = (String) tablaAdminCRUD.getValueAt(fila, 5);

        JTextField txtIdV = new JTextField(idVentaActual.toString());
        JTextField txtDesc = new JTextField(descActual);
        JTextField txtMonto = new JTextField(montoActualStr);
        JComboBox<String> cmbMetodo = new JComboBox<>(new String[]{"PagoEfectivo", "PagoTarjeta", "PagoTransferencia", "PagoElectronico", "PagoBitcoin"});
        cmbMetodo.setSelectedItem(metodoActual);
        JTextField txtFecha = new JTextField(fechaActual);
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"APROBADO", "RECHAZADO", "PENDIENTE"});
        cmbEstado.setSelectedItem(estadoActual);

        Object[] message = {
            "ID Venta:", txtIdV,
            "Descripción:", txtDesc,
            "Importe ($):", txtMonto,
            "Método:", cmbMetodo,
            "Fecha:", txtFecha,
            "Estado:", cmbEstado
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Administrador - Editar Venta / Transacción", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int nuevoIdVenta = Integer.parseInt(txtIdV.getText().trim());
                String nuevaDesc = txtDesc.getText().trim();
                double nuevoMonto = Double.parseDouble(txtMonto.getText().trim());
                String nuevoMetodo = (String) cmbMetodo.getSelectedItem();
                String nuevaFecha = txtFecha.getText().trim();
                String nuevoEstado = (String) cmbEstado.getSelectedItem();

                MongoDatabase db = ConexionDB.getDatabase();
                
                db.getCollection("ventas").updateOne(Filters.eq("idVenta", idVentaActual), 
                    new Document("$set", new Document("idVenta", nuevoIdVenta).append("descripcion", nuevaDesc).append("total", nuevoMonto).append("fecha", nuevaFecha).append("estado", nuevoEstado)));
                
                db.getCollection("transacciones").updateOne(Filters.eq("idVenta", idVentaActual), 
                    new Document("$set", new Document("idVenta", nuevoIdVenta).append("descripcion", nuevaDesc).append("monto", nuevoMonto).append("metodoPago", nuevoMetodo).append("fecha", nuevaFecha).append("estado", nuevoEstado)));

                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente en ventas y transacciones (MongoDB).");
                cargarTablaAdminCRUD();
                cargarConsultasAdmin(null, null);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al editar (verifique los campos numéricos): " + e.getMessage());
            }
        }
    }

    private void eliminarVentaAdmin() {
        int fila = tablaAdminCRUD.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para eliminar.");
            return;
        }
        Object idVentaObj = tablaAdminCRUD.getValueAt(fila, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la venta ID " + idVentaObj + " y su transacción?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                MongoDatabase db = ConexionDB.getDatabase();
                db.getCollection("transacciones").deleteOne(Filters.eq("idVenta", idVentaObj));
                db.getCollection("ventas").deleteOne(Filters.eq("idVenta", idVentaObj));

                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
                cargarTablaAdminCRUD();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void ejecutarConsultaAvanzada() {
        String tipo = (String) cmbTipoFiltro.getSelectedItem();
        String valor = txtFiltroBusqueda.getText().trim();
        cargarConsultasAdmin(tipo, valor);
    }

    private void cargarConsultasAdmin(String tipoFiltro, String valor) {
        modeloConsultas.setRowCount(0);
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            var query = db.getCollection("transacciones").find();

            if (tipoFiltro != null && !valor.isEmpty()) {
                if (tipoFiltro.equals("Por ID Venta")) {
                    try {
                        int idVal = Integer.parseInt(valor);
                        query = db.getCollection("transacciones").find(Filters.eq("idVenta", idVal));
                    } catch (NumberFormatException ex) {
                        query = db.getCollection("transacciones").find(Filters.eq("idVenta", valor));
                    }
                } else if (tipoFiltro.equals("Por Descripción")) {
                    query = db.getCollection("transacciones").find(Filters.regex("descripcion", "(?i)" + valor));
                } else if (tipoFiltro.equals("Por Método")) {
                    query = db.getCollection("transacciones").find(Filters.regex("metodoPago", "(?i)" + valor));
                } else if (tipoFiltro.equals("Por Estado")) {
                    query = db.getCollection("transacciones").find(Filters.regex("estado", "(?i)" + valor));
                } else if (tipoFiltro.equals("Todos")) {
                    List<org.bson.conversions.Bson> filtrosBusqueda = new ArrayList<>();
                    
                    filtrosBusqueda.add(Filters.regex("descripcion", "(?i)" + valor));
                    filtrosBusqueda.add(Filters.regex("metodoPago", "(?i)" + valor));
                    filtrosBusqueda.add(Filters.regex("estado", "(?i)" + valor));
                    
                    try {
                        int idVal = Integer.parseInt(valor);
                        filtrosBusqueda.add(Filters.eq("idVenta", idVal));
                    } catch (NumberFormatException ignored) {}

                    query = db.getCollection("transacciones").find(Filters.or(filtrosBusqueda));
                }
            }

            for (Document doc : query) {
                double montoVal = 0.0;
                Object objMonto = doc.get("monto");
                if (objMonto instanceof Number) {
                    montoVal = ((Number) objMonto).doubleValue();
                }

                modeloConsultas.addRow(new Object[]{
                    doc.get("idVenta"),
                    doc.getString("descripcion"),
                    "$" + montoVal,
                    doc.getString("metodoPago"),
                    doc.getString("fecha"),
                    doc.getString("estado")
                });
            }
        } catch (Exception e) {
            System.err.println("Error en consulta avanzada: " + e.getMessage());
        }
    }

    private void calcularResumenPagos() {
        double totalAprobado = 0;
        int countEfectivo = 0, countTarjeta = 0, countTransferencia = 0, countElectronico = 0, countBitcoin = 0;
        int countAprobados = 0, countRechazados = 0, countPendientes = 0;

        try {
            MongoDatabase db = ConexionDB.getDatabase();
            for (Document doc : db.getCollection("transacciones").find()) {
                String estado = doc.getString("estado");
                String metodo = doc.getString("metodoPago");
                
                double importe = 0.0;
                Object objMonto = doc.get("monto");
                if (objMonto instanceof Number) {
                    importe = ((Number) objMonto).doubleValue();
                }

                if (estado != null && (estado.equalsIgnoreCase("APROBADO") || estado.equalsIgnoreCase("ACEPTADO"))) {
                    totalAprobado += importe;
                    countAprobados++;
                } else if (estado != null && estado.equalsIgnoreCase("RECHAZADO")) {
                    countRechazados++;
                } else {
                    countPendientes++;
                }

                if (metodo != null) {
                    if (metodo.equalsIgnoreCase("PagoEfectivo")) countEfectivo++;
                    else if (metodo.equalsIgnoreCase("PagoTarjeta")) countTarjeta++;
                    else if (metodo.equalsIgnoreCase("PagoTransferencia")) countTransferencia++;
                    else if (metodo.equalsIgnoreCase("PagoElectronico")) countElectronico++;
                    else if (metodo.equalsIgnoreCase("PagoBitcoin")) countBitcoin++;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("==================================================\n");
            sb.append("         REPORTE DE RESUMEN DE PAGOS\n");
            sb.append("==================================================\n\n");
            sb.append(" 1. TOTAL DE INGRESOS APROBADOS: $").append(String.format("%.2f", totalAprobado)).append("\n\n");
            sb.append(" 2. CONTEO DE OPERACIONES POR MÉTODO:\n");
            sb.append("    • PagoEfectivo: ").append(countEfectivo).append("\n");
            sb.append("    • PagoTarjeta: ").append(countTarjeta).append("\n");
            sb.append("    • PagoTransferencia: ").append(countTransferencia).append("\n");
            sb.append("    • PagoElectronico: ").append(countElectronico).append("\n");
            sb.append("    • PagoBitcoin: ").append(countBitcoin).append("\n\n");
            sb.append(" 3. ESTADÍSTICAS DE ESTADO:\n");
            sb.append("    • Aprobados: ").append(countAprobados).append("\n");
            sb.append("    • Rechazados: ").append(countRechazados).append("\n");
            sb.append("    • Pendientes: ").append(countPendientes).append("\n");
            sb.append("==================================================\n");

            txtAreaResumen.setText(sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar resumen: " + e.getMessage());
        }
    }
}