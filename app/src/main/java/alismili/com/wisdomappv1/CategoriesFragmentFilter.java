package alismili.com.wisdomappv1;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import alismili.com.wisdomappv1.controllers.Controlador;
import alismili.com.wisdomappv1.models.Usuario;

public class CategoriesFragmentFilter extends DialogFragment implements View.OnClickListener {

    /**
     * Necesarios para crear el onClickListener
     */
    private TextView txtPalabras;
    private TextView txtFrases;
    private TextView txtBaulUsuario;
    private Controlador controlador;
    private Usuario userLogued;
    private Button b;  //Nuevo item
    String seleccion ;


    /**
     * Aquí se inicicializan los textview y se les asigna el listener, que se declara en la clase
     * para ser genérico y reusable para todos.
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true); // Permite que el usuario pueda darle atrás o cancelar

        // Configuro los escuchadores para los elementos del fragment
        txtPalabras = view.findViewById(R.id.txtPalabras);
        txtFrases =  view.findViewById(R.id.txtFrases);
        txtBaulUsuario =  view.findViewById(R.id.txtPersonales);
        txtPalabras.setOnClickListener(this);
        txtFrases.setOnClickListener(this);
        txtBaulUsuario.setOnClickListener(this);
        controlador = this.getArguments().getParcelable("controller");
        userLogued = this.getArguments().getParcelable("userLogued");
        seleccion = "";
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    /**
     * Método necesario para la creación de la vista a través de un inflater.
     *
     * @return devuelve el View inflado con los elementos definidos en el XML fragment_categoriesfilter
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categoriesfilter, container, false);
    }

    /**
     * Crea un toast (de momento) con la información que tenía en el Bundle de CATEGORIAS +
     * la opción que se haya elegido en el momento (Palabras, Frases, Baul personal)
     */
    @Override
    public void onClick(View view) {

        // Empieza construyendo el toast con el argumento pasado en el bundle al crearlo
        // (Definido en onClick de los botones Categoria)

        //controlador = new Controlador();

        if (view.equals(txtPalabras)) {
            seleccion = "palabras";
            getControlador().setCurrentType("palabras");
        } else if (view.equals(txtFrases)) {
            seleccion = "frases";
            getControlador().setCurrentType("frases");
        }else if (view.equals(txtBaulUsuario)) {
            seleccion = "baulPersonal";
            getControlador().setCurrentType("baulPersonal");
        }

        if (!seleccion.equalsIgnoreCase("")) {
            controlador.setCurrentType(seleccion);
        }

        // Cuando se ha elegido una opción, el fragment desaparece
        dismiss();

        // Y se lanza el siguiente activity**************************
        Intent lanzador = new Intent(this.getActivity().getApplicationContext(), ResultsActivity.class);
        lanzador.putExtra("controller", controlador);
        startActivity(lanzador);

        //Toast.makeText(getActivity().getApplicationContext(), ""+controlador.getLenguage()+" / "+controlador.getCategory()+" / "+controlador.getSubCategory(), Toast.LENGTH_SHORT).show();

        /**
         *  Le pasamos al intent el objeto controlador
         */
        lanzador = lanzador.putExtra("MiControl", controlador);     //Hace falta mirarlo bien


        lanzarActivityResultados(lanzador);

    }

    /**
     * Lanza la siguiente activity. Necesita pasarle el bundle con los parámentros necesarios
     */
    private void lanzarActivityResultados(Intent i) {
        startActivity(i);
    }
}

