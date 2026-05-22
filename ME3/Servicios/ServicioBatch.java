package ME3.Servicios;

import ME3.Excepciones.ArchivoInvalidoException;
import ME3.Excepciones.CupoLlenoException;
import ME3.Excepciones.EstudianteNoEncontradoException;
import ME3.Excepciones.PreRequisitoNoAprobadoException;
import ME3.Modelo.SolicitudInscripcion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class ServicioBatch {
    private final Queue<SolicitudInscripcion> colaProcesamiento;
    private final ServicioMateria servicioMateria;

    public ServicioBatch(ServicioMateria servicioMateria) {
        this.colaProcesamiento = new ArrayDeque<>();
        this.servicioMateria = servicioMateria;
    }

    public void cargarArchivoCSV(String rutaArchivo) throws ArchivoInvalidoException {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new ArchivoInvalidoException("La ruta del archivo no puede estar vacia");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    throw new ArchivoInvalidoException("Formato invalido en la linea: " + linea);
                }

                String idEstudiante = partes[0].trim();
                String codigoMateria = partes[1].trim();

                if (idEstudiante.isEmpty() || codigoMateria.isEmpty()) {
                    throw new ArchivoInvalidoException("Datos vacios en la linea: " + linea);
                }

                colaProcesamiento.offer(new SolicitudInscripcion(idEstudiante, codigoMateria));
            }
        } catch (IOException e) {
            throw new ArchivoInvalidoException("No fue posible leer el archivo: " + e.getMessage());
        }
    }

    public ResultadoBatch procesarCola() throws EstudianteNoEncontradoException {
        int exitosas = 0;
        int fallidas = 0;
        StringBuilder detalle = new StringBuilder();

        while (!colaProcesamiento.isEmpty()) {
            SolicitudInscripcion solicitud = colaProcesamiento.poll();
            try {
                servicioMateria.inscribirEstudiante(
                        solicitud.getIdEstudiante(),
                        solicitud.getCodigoMateria()
                );
                exitosas++;
                detalle.append("Exitosa: ")
                        .append(solicitud.getIdEstudiante())
                        .append(" -> ")
                        .append(solicitud.getCodigoMateria())
                        .append("\n");
            } catch (PreRequisitoNoAprobadoException | CupoLlenoException e) {
                fallidas++;
                detalle.append("Fallida: ")
                        .append(solicitud.getIdEstudiante())
                        .append(" -> ")
                        .append(solicitud.getCodigoMateria())
                        .append(" (")
                        .append(e.getMessage())
                        .append(")\n");
            }
        }

        return new ResultadoBatch(exitosas, fallidas, detalle.toString());
    }

    public void encolarSolicitud(String idEstudiante, String codigoMateria) {
        colaProcesamiento.offer(new SolicitudInscripcion(idEstudiante, codigoMateria));
    }

    public int totalEnCola() {
        return colaProcesamiento.size();
    }

    public static class ResultadoBatch {
        private final int exitosas;
        private final int fallidas;
        private final String detalle;

        public ResultadoBatch(int exitosas, int fallidas, String detalle) {
            this.exitosas = exitosas;
            this.fallidas = fallidas;
            this.detalle = detalle;
        }

        public int getExitosas() {
            return exitosas;
        }

        public int getFallidas() {
            return fallidas;
        }

        public String getDetalle() {
            return detalle;
        }
    }
}