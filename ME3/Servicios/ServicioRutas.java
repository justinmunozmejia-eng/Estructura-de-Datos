package ME3.Servicios;

import ME3.Modelo.Edificio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServicioRutas {
    private final List<Edificio> edificios;
    private final int[][] distancias;

    public ServicioRutas(int numeroEdificios) {
        if (numeroEdificios < 5) {
            throw new IllegalArgumentException("La universidad debe tener al menos 5 edificios");
        }
        this.edificios = new ArrayList<>();
        this.distancias = new int[numeroEdificios][numeroEdificios];
        inicializarMatriz();
    }

    private void inicializarMatriz() {
        for (int i = 0; i < distancias.length; i++) {
            Arrays.fill(distancias[i], Integer.MAX_VALUE);
            distancias[i][i] = 0;
        }
    }

    public void agregarEdificio(Edificio edificio) {
        if (edificio == null) {
            throw new IllegalArgumentException("El edificio no puede ser nulo");
        }
        if (edificios.size() >= distancias.length) {
            throw new IllegalStateException("Ya se alcanzo el numero maximo de edificios");
        }
        edificios.add(edificio);
    }

    public void agregarConexion(int indiceOrigen, int indiceDestino, int distancia) {
        validarIndices(indiceOrigen, indiceDestino);
        if (distancia <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor que 0");
        }
        distancias[indiceOrigen][indiceDestino] = distancia;
        distancias[indiceDestino][indiceOrigen] = distancia;
    }

    public RutaResultado calcularRutaMasCorta(int origen, int destino) {
        validarIndices(origen, destino);

        int n = edificios.size();
        int[] distancia = new int[n];
        int[] padre = new int[n];
        boolean[] visitado = new boolean[n];

        Arrays.fill(distancia, Integer.MAX_VALUE);
        Arrays.fill(padre, -1);
        distancia[origen] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = seleccionarMinimo(distancia, visitado, n);
            if (u == -1) {
                break;
            }
            visitado[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visitado[v] && distancias[u][v] != Integer.MAX_VALUE && distancia[u] != Integer.MAX_VALUE) {
                    int nuevaDistancia = distancia[u] + distancias[u][v];
                    if (nuevaDistancia < distancia[v]) {
                        distancia[v] = nuevaDistancia;
                        padre[v] = u;
                    }
                }
            }
        }

        List<Integer> camino = reconstruirCamino(padre, destino);
        return new RutaResultado(camino, distancia[destino]);
    }

    private int seleccionarMinimo(int[] distancia, boolean[] visitado, int n) {
        int min = Integer.MAX_VALUE;
        int indice = -1;
        for (int i = 0; i < n; i++) {
            if (!visitado[i] && distancia[i] <= min) {
                min = distancia[i];
                indice = i;
            }
        }
        return indice;
    }

    private List<Integer> reconstruirCamino(int[] padre, int destino) {
        List<Integer> camino = new ArrayList<>();
        int actual = destino;
        while (actual != -1) {
            camino.add(actual);
            actual = padre[actual];
        }
        Collections.reverse(camino);
        return camino;
    }

    private void validarIndices(int a, int b) {
        if (a < 0 || b < 0 || a >= edificios.size() || b >= edificios.size()) {
            throw new IllegalArgumentException("Indices de edificio invalidos");
        }
    }

    public List<Edificio> getEdificios() {
        return edificios;
    }

    public int[][] getDistancias() {
        return distancias;
    }

    public static class RutaResultado {
        private final List<Integer> camino;
        private final int distanciaTotal;

        public RutaResultado(List<Integer> camino, int distanciaTotal) {
            this.camino = camino;
            this.distanciaTotal = distanciaTotal;
        }

        public List<Integer> getCamino() {
            return camino;
        }

        public int getDistanciaTotal() {
            return distanciaTotal;
        }
    }
}