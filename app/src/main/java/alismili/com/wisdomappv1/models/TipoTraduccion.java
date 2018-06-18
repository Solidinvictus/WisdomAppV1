package alismili.com.wisdomappv1.models;

public class TipoTraduccion {

    private int id_tipo_traduccion;
    private String tipo_traduccion;

    public int getId_tipo_traduccion() {
        return id_tipo_traduccion;
    }

    public void setId_tipo_traduccion(int id_tipo_traduccion) {
        this.id_tipo_traduccion = id_tipo_traduccion;
    }

    public String getTipo_traduccion() {
        return tipo_traduccion;
    }

    public void setTipo_traduccion(String tipo_traduccion) {
        this.tipo_traduccion = tipo_traduccion;
    }

    @Override
    public String toString() {
        return "TipoTraduccion{" +
                "id_tipo_traduccion=" + id_tipo_traduccion +
                ", tipo_traduccion='" + tipo_traduccion + '\'' +
                '}';
    }
}
