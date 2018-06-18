package alismili.com.wisdomappv1.models;

public class Categoria {

    private int id_categoria;
    private String nombre_categoria;
    private String descripcion_categoria;
    //private boolean por_defecto;

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
    }

    /*public boolean isPor_defecto() {
        return por_defecto;
    }

    public void setPor_defecto(boolean por_defecto) {

            this.por_defecto = por_defecto;

    }*/

    @Override
    public String toString() {
        return "Categoria{" +
                "id_categoria=" + id_categoria +
                ", nombre_categoria='" + nombre_categoria + '\'' +
                ", descripcion_categoria='" + descripcion_categoria + '\'' +
                '}';
    }
}
