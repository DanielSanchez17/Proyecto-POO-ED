package estructura;

import excepcion.SinConectividadException;
import excepcion.ZonaInexistenteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GrafoCiudad {

    private Map<String, List<Arista>> adyacencia;

    public GrafoCiudad() {
        adyacencia = new HashMap<>();
    }

    public void agregarZona(String nombre) {
        adyacencia.putIfAbsent(nombre, new ArrayList<>());
    }

    public void agregarConexion(String origen, String destino, int distancia)
            throws ZonaInexistenteException {

        if (!adyacencia.containsKey(origen)) {
            throw new ZonaInexistenteException(origen);
        }
        if (!adyacencia.containsKey(destino)) {
            throw new ZonaInexistenteException(destino);
        }

        // conexion bidireccional: se agrega en ambas listas
        adyacencia.get(origen).add(new Arista(destino, distancia));
        adyacencia.get(destino).add(new Arista(origen, distancia));
    }

    public boolean hayConectividad(String origen, String destino)
            throws ZonaInexistenteException {

        if (!adyacencia.containsKey(origen)) throw new ZonaInexistenteException(origen);
        if (!adyacencia.containsKey(destino)) throw new ZonaInexistenteException(destino);

        if (origen.equals(destino)) return true;

        Queue<String> cola = new LinkedList<>();
        List<String> visitados = new ArrayList<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();

            for (Arista arista : adyacencia.get(actual)) {
                if (!arista.habilitada) continue; 

                if (arista.destino.equals(destino)) {
                    return true; 
                }

                if (!visitados.contains(arista.destino)) {
                    visitados.add(arista.destino);
                    cola.add(arista.destino);
                }
            }
        }

        return false; // no hay ruta habilitada
    }

    public int calcularDistancia(String origen, String destino)
            throws ZonaInexistenteException, SinConectividadException {

        if (!adyacencia.containsKey(origen)) throw new ZonaInexistenteException(origen);
        if (!adyacencia.containsKey(destino)) throw new ZonaInexistenteException(destino);

        if (origen.equals(destino)) return 0;

        Map<String, Integer> distancias = new HashMap<>();
        Queue<String> cola = new LinkedList<>();

        distancias.put(origen, 0);
        cola.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            int distActual = distancias.get(actual);

            for (Arista arista : adyacencia.get(actual)) {
                if (!arista.habilitada) continue;

                if (!distancias.containsKey(arista.destino)) {
                    distancias.put(arista.destino, distActual + arista.distancia);
                    cola.add(arista.destino);
                }
            }
        }

        if (!distancias.containsKey(destino)) {
            throw new SinConectividadException(origen, destino);
        }

        return distancias.get(destino);
    }

    public void setConexionHabilitada(String origen, String destino, boolean habilitada)
            throws ZonaInexistenteException {

        if (!adyacencia.containsKey(origen)) throw new ZonaInexistenteException(origen);
        if (!adyacencia.containsKey(destino)) throw new ZonaInexistenteException(destino);

        for (Arista a : adyacencia.get(origen)) {
            if (a.destino.equals(destino)) a.habilitada = habilitada;
        }
        for (Arista a : adyacencia.get(destino)) {
            if (a.destino.equals(origen)) a.habilitada = habilitada;
        }
    }

    public void mostrarRed() {
        System.out.println("=== RED VIAL ===");
        for (Map.Entry<String, List<Arista>> entry : adyacencia.entrySet()) {
            System.out.print("  " + entry.getKey() + " -> ");
            if (entry.getValue().isEmpty()) {
                System.out.println("(sin conexiones)");
            } else {
                for (Arista a : entry.getValue()) {
                    String estado = a.habilitada ? "" : " [CERRADA]";
                    System.out.print(a.destino + "(" + a.distancia + "m)" + estado + "  ");
                }
                System.out.println();
            }
        }
    }

    public List<String> getZonas() {
        return new ArrayList<>(adyacencia.keySet());
    }
}
