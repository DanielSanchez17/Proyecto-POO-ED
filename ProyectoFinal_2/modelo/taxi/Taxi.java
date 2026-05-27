package modelo.taxi;

public abstract class Taxi {

    protected static final double TARIFA_BASE = 5000.0;

    protected String placa;
    protected String nombreConductor;

    public Taxi(String placa, String nombreConductor) {
        this.placa = placa;
        this.nombreConductor = nombreConductor;
    }

    public abstract double calcularTarifa(int distancia);

    public abstract String getTipo();

    public String getPlaca() { return placa; }

    public String getNombreConductor() { return nombreConductor; }
}
