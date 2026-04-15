package ME2.Servicios;

import ME2.Entidades.Contenedor;

public class ServicioPatio {
    private Contenedor[][] patio;

    public ServicioPatio(int filas, int columnas) {
        patio = new Contenedor[filas][columnas];
    }

    public boolean ubicarContenedor(Contenedor contenedor) {
        for (int i = 0; i < patio.length; i++) {
            for (int j = 0; j < patio[i].length; j++) {
                if (patio[i][j] == null) {
                    patio[i][j] = contenedor;
                    return true;
                }
            }
        }
        return false;
    }

    public Contenedor[][] obtenerPatio() {
        return patio;
    }

    public void mostrarPatio() {
        for (int i = 0; i < patio.length; i++) {
            for (int j = 0; j < patio[i].length; j++) {
                if (patio[i][j] == null) {
                    System.out.print("[Vacio]\t");
                } else {
                    System.out.print("[" + patio[i][j].getId() + "]\t");
                }
            }
            System.out.println();
        }
    }
}