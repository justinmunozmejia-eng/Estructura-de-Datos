package ME3.Modelo;

public class SolicitudInscripcion { 
    private String idEstudiante; 
    private String codigoMateria;

    public SolicitudInscripcion(String idEstudiante, String codigoMateria) {
        this.idEstudiante = idEstudiante;
        this.codigoMateria = codigoMateria; 
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getCodigoMateria() { 
        return codigoMateria;
    }
}