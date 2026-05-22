package ME3.Modelo;


public class Aula {
    private String nombre;
    private int capacidad;
    private boolean[][] horario;

    public Aula(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.horario = new boolean[7][24];
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean[][] getHorario() {
        return horario;
    }

    public boolean consultarDisponibilidad(int dia, int hora) {
        validarDiaHora(dia, hora);
        return !horario[dia][hora];
    }

    public void reservar(int dia, int hora, int duracion) throws IllegalStateException {
        validarDiaHora(dia, hora);
        validarDuracion(duracion);

        if (hora + duracion > 24) {
            throw new IllegalArgumentException("La reserva supera el limite del dia");
        }

        for (int h = hora; h < hora + duracion; h++) {
            if (horario[dia][h]) {
                throw new IllegalStateException("Horario conflictivo en dia " + dia + " hora " + h);
            }
        }

        for (int h = hora; h < hora + duracion; h++) {
            horario[dia][h] = true;
        }
    }

    public void liberar(int dia, int hora, int duracion) {
        validarDiaHora(dia, hora);
        validarDuracion(duracion);

        if (hora + duracion > 24) {
            throw new IllegalArgumentException("La liberacion supera el limite del dia");
        }

        for (int h = hora; h < hora + duracion; h++) {
            horario[dia][h] = false;
        }
    }

    private void validarDiaHora(int dia, int hora) {
        if (dia < 0 || dia > 6) {
            throw new IllegalArgumentException("El dia debe estar entre 0 y 6");
        }
        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23");
        }
    }

    private void validarDuracion(int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duracion debe ser mayor que 0");
        }
    }

    @Override
    public String toString() {
        return "Aula{" +
                "nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                '}';
    }
}