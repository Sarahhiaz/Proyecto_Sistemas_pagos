package sistemapagos;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Iniciar la interfaz gráfica de Swing
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}