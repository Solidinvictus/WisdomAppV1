package alismili.com.wisdomappv1.controllers;

import android.os.Parcel;
import android.os.Parcelable;

import alismili.com.wisdomappv1.models.Usuario;

public class Controlador implements Parcelable {

    //  ATTRIBUTES
    private String currentCategory;     //  Categoría seleccionada. (Colores, Hogar...)
    private String currentType;  //  Tipo seleccionado. (Palabra, Frase...)
    private String currentTranslation;  //ES-EN, EN-ES, etc...
    private ControladorListas controladorListas;
    private Usuario usuarioLogueado;

    public Controlador() {
        this.currentCategory = currentCategory;
        this.currentType = currentType;
        this.currentTranslation = currentTranslation;
        this.controladorListas = new ControladorListas();
        this.usuarioLogueado = new Usuario();


    }

    public Controlador(Parcel in) {
        currentCategory = in.readString();
        currentType = in.readString();
        currentTranslation = in.readString();
        //usuarioLogueado = in.readParcelable(Usuario.class.getClassLoader());
    }

    public static final Creator<Controlador> CREATOR = new Creator<Controlador>() {
        @Override
        public Controlador createFromParcel(Parcel in) {
            return new Controlador(in);
        }

        @Override
        public Controlador[] newArray(int size) {
            return new Controlador[size];
        }
    };


    public String getCurrentCategory() {
        return currentCategory;
    }

    public int recogeTipoContenido(){
        if(getCurrentType().equals("baulPersonal")){
            return 3;
        }else if(getCurrentType().equals("frases")){
            return 2;

        }else if(getCurrentType().equals("palabras")){
            return 1;
        }else{
            return 0;
        }
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public String getCurrentTranslation() {
        return currentTranslation;
    }

    public void setCurrentTranslation(String currentTraslation) {
        this.currentTranslation = currentTraslation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(currentCategory);
        parcel.writeString(currentType);
        parcel.writeString(currentTranslation);
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    @Override
    public String toString() {
        return "Controlador{" +
                "currentCategory='" + currentCategory + '\'' +
                ", currentType='" + currentType + '\'' +
                ", currentTranslation='" + currentTranslation + '\'' +
                ", controladorListas=" + controladorListas +
                ", usuarioLogueado=" + usuarioLogueado +
                '}';
    }
}
