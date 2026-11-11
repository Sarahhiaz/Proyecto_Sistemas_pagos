package Entidades;

public class ResumenPago {

    private int total;

    private int aprobadas;

    private int rechazadas;

    private int efectivo;

    private int tarjeta;

    private int transferencia;

    private int electronico;

    private double totalAprobado;

    public void agregarTransaccion(
            Transaccion transaccion) {

        if (transaccion == null) {
            return;
        }

        total++;

        if (transaccion.getEstado()
                == EstadoPago.APROBADO) {

            aprobadas++;

            totalAprobado +=
                    transaccion
                            .getImporte();

        } else if (
                transaccion.getEstado()
                    == EstadoPago.RECHAZADO) {

            rechazadas++;
        }

        String metodo =
                transaccion
                        .getNombreMetodoPago();

        switch (metodo) {

            case "PagoEfectivo":
                efectivo++;
                break;

            case "PagoTarjeta":
                tarjeta++;
                break;

            case "PagoTransferencia":
                transferencia++;
                break;

            case "PagoElectronico":
                electronico++;
                break;
        }
    }

    public int getTotal() {
        return total;
    }

    public int getAprobadas() {
        return aprobadas;
    }

    public int getRechazadas() {
        return rechazadas;
    }

    public int getEfectivo() {
        return efectivo;
    }

    public int getTarjeta() {
        return tarjeta;
    }

    public int getTransferencia() {
        return transferencia;
    }

    public int getElectronico() {
        return electronico;
    }

    public double getTotalAprobado() {
        return totalAprobado;
    }
}
