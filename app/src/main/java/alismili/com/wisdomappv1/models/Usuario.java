package alismili.com.wisdomappv1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

    private int id_usuario;
    private String alias;
    private String email;
    private String password;

    //Constructores

    public Usuario() {
        this.id_usuario = id_usuario;
        this.alias = alias;
        this.email = email;
        this.password = password;
    }

    public Usuario(int id_usuario, String alias, String email, String password) {
        this.id_usuario = id_usuario;
        this.alias = alias;
        this.email = email;
        this.password = password;
    }

    protected Usuario(Parcel in) {
        id_usuario = in.readInt();
        alias = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", alias='" + alias + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id_usuario);
        parcel.writeString(alias);
        parcel.writeString(email);

    }
}
