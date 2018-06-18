package alismili.com.wisdomappv1.models;

public class Nucleo {

    private int idNucleo;
    private String clave;
    private String valor;
    private TipoTraduccion tipoTraduccion;
    private Categoria categoria;
    private Usuario usuario;
    private int tipo;

    public int getIdNucleo() {
        return idNucleo;
    }

    public void setIdNucleo(int idNucleo) {
        this.idNucleo = idNucleo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public TipoTraduccion getTipoTraduccion() {
        return tipoTraduccion;
    }

    public void setTipoTraduccion(TipoTraduccion tipoTraduccion) {
        this.tipoTraduccion = tipoTraduccion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Nucleo{" +
                "idNucleo=" + idNucleo +
                ", clave='" + clave + '\'' +
                ", valor='" + valor + '\'' +
                ", tipoTraduccion=" + tipoTraduccion +
                ", categoria=" + categoria +
                ", usuario=" + usuario +
                ", tipo=" + tipo +
                '}';
    }
}
