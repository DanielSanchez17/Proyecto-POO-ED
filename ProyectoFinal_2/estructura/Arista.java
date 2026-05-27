package estructura;

public class Arista {

    String destino;       
    int distancia;        
    boolean habilitada;   

    public Arista(String destino, int distancia) {
        this.destino = destino;
        this.distancia = distancia;
        this.habilitada = true;
    }
}
