package alismili.com.wisdomappv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import alismili.com.wisdomappv1.adapters.SimpleImageArrayAdapter;
import alismili.com.wisdomappv1.controllers.Controlador;
import alismili.com.wisdomappv1.models.Usuario;

public class CategoriesActivity extends AppCompatActivity {

    private Spinner spinner_options;    ///< Spinner de opciones de configuracion y menu ocultable con imagenes
    private Spinner spinner_idiomas;    ///< Spinner de opciones de traducción y menu ocultable
    private Button[] botonesCategoria;
    private Controlador controlador;
    private Usuario usuarioLogueado;
    private TextView aliasTv;
    String aliasUsuarioLogueado;
    String emailUsuarioLogueado;

    /**
     * Método onCreate que instancia la actividad
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        setTheme(preferencias.getInt("tema", R.style.AppTheme));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        spinner_options = findViewById(R.id.spinnerOptions);
        spinner_idiomas = findViewById(R.id.spinnerIdiomas);
        aliasTv = findViewById(R.id.textViewTemas);

        controlador = new Controlador();
        usuarioLogueado = new Usuario();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
         aliasUsuarioLogueado = intent.getStringExtra(ProfileActivity.ALIAS_PROFILE);
         emailUsuarioLogueado = intent.getStringExtra(ProfileActivity.EMAIL_PROFILE);

         usuarioLogueado.setId_usuario(1);   //Admin
         usuarioLogueado.setAlias(aliasUsuarioLogueado);
         usuarioLogueado.setEmail(emailUsuarioLogueado);

        System.out.println("Probando alias e email desde CategoriesActivity [onCreate]");
        System.out.println(aliasUsuarioLogueado);
        System.out.println(emailUsuarioLogueado);

        controlador.setUsuarioLogueado(usuarioLogueado);

        //Colocamos nuestro Alias en el TextView
        aliasTv.setText("Bienvenido "+aliasUsuarioLogueado+" ");


        //usuarioLogueado.setAlias();

        configurarBotonesCategorias();
        configurarSpinnerOptions();
        configurarSpinnerIdiomas();
    }

    /**
     * Spinner de Opciones de la app-->Menu desplegable que muestra iconos/imagenes junto a un texto
     * Desde la carpeta drawable, se recogen las imagenes y se meten en el array de Integers
     * Los textos del menu se almacenan en el array de strings StringsOpciones
     */
    private void configurarSpinnerOptions() {
        String[] StringsOpciones = new String[]{"Cuenta", "Ajustes"};
        Integer[] IconosOpciones = new Integer[]{R.drawable.ayudaicon, R.drawable.ajustesicon};

        /**
         * Instanciamos esta clase que se definira a continuacion
         * y tiene como argumentos los arrays tanto de imagenes como los textos
         * que seran los items de este menu ocultable gracias a la herecia de ArrayAdapter
         * @param getApplicationContext()
         * @param IconosOpciones
         * @param StringsOpciones
         */
        SimpleImageArrayAdapter adapter = new
                SimpleImageArrayAdapter(getApplicationContext(), IconosOpciones, StringsOpciones);

        /**
         * Aplicamos este adaptador instanciado al spinner de opciones
         * @param adapter
         */
        spinner_options.setAdapter(adapter);

        configurarLisenersSpinnerOpciones();
    }

    /**
     * Define el comportamiento al pulsar un botón en el Spinner de Opciones
     */
    private void configurarLisenersSpinnerOpciones() {

        final int POSICION_CUENTA = 0;   ///< La posición de la opción Ayuda(tercera) dentro del menu ocultable
        final int POSICION_AJUSTES = 1; ///< La posición de la opción Ajustes(cuarta) dentro del menu ocultable

        /**
         * Método que se llama cuando se selecciona un elemento en el Spinner opciones
         * @param AdapterView.OnItemSelectedListener()
         */
        spinner_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int posicion = spinner_options.getSelectedItemPosition();

                switch (posicion) {

                    /*case POSICION_CUENTA:
                        startActivity(new Intent(CategoriesActivity.this, LoginActivity.class));
                        Toast.makeText(CategoriesActivity.this, "Aplicación creada por Ali Smili",
                                Toast.LENGTH_SHORT).show();
                        break;*/
                    case POSICION_AJUSTES:
                        mostrarAjustes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    /**
     * Método que nos lleva al activity de preferencias: PreferenciasActivity
     * gracias a un intent
     */
    private void mostrarAjustes() {
        startActivity(new Intent(this, PreferencesActivity.class));
    }

    /**
     * En este método se configura todas las cosas relacionadas con el spinner de idiomnas
     * Spinner de idiomas que usa la potencia del  ArrayAdapter
     * para mostrar un menu ocultable.
     * El inflator aqui no se usa para mostrar mas opciones de uso
     * No se usan imagenes en este spinner
     */
    private void configurarSpinnerIdiomas() {

        /**
         * Creamos un ArrayAdapter usando un array de strings y un  spinner con un layout por defecto
         */
        ArrayAdapter<CharSequence> adapterIdiomas = ArrayAdapter.createFromResource(this,
                R.array.idiomas_array, android.R.layout.simple_spinner_item);

        /**
         * Hay que especificar el layout a usar cdo la lista de opciones aparece
         */
        adapterIdiomas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /**
         *  Aplicamos el adaptador al Spinner
         */
        spinner_idiomas.setAdapter(adapterIdiomas);
        /**
         * Se configura el comportamiento de los botones
         */
        configurarLisenersSpinnerIdiomas();
    }

    /**
     * Define el comportamiento al pulsar un botón en el Spinner de idiomas.
     */
    private void configurarLisenersSpinnerIdiomas() {
        final TextView txt_idioma = (TextView) findViewById(R.id.txt_idioma_seleccionado);
        final int POSICION_ESP_ENG = 0;
        final int POSICION_ENG_ESP = 1;
        final int POSICION_ESP_FR = 2;


        spinner_idiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int posicion = spinner_idiomas.getSelectedItemPosition();

                switch (posicion) {
                    case POSICION_ESP_ENG:
                        txt_idioma.setText("ESP_ENG");
                        controlador.setCurrentTranslation("ESP_ENG");
                        break;
                    case POSICION_ENG_ESP:
                        txt_idioma.setText("ENG_ESP");
                        controlador.setCurrentTranslation("ENG_ESP");
                        break;
                    case POSICION_ESP_FR:
                        txt_idioma.setText("ESP_FR");
                        controlador.setCurrentTranslation("ESP_FR");

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    /**
     * Metodo para configurar los botones de las categorias
     * Inclusion de Fragment y su configuración
     */
    private void configurarBotonesCategorias() {

        LinearLayout ll =  findViewById(R.id.layout_botones); ///<Layout que los contiene
        botonesCategoria = new Button[ll.getChildCount()];  ///< Creando el array de botonesCategoria con el número de botones total que contiene el layout

        for (int i = 0; i < botonesCategoria.length; i++) {
            botonesCategoria[i] = (Button) ll.getChildAt(i);
            botonesCategoria[i].setOnClickListener(new View.OnClickListener() {

                // Por cada uno de los botones, se les asigna un listener que creará y llamará al fragment
                @Override
                public void onClick(View view) {

                    /**
                     * Creando el fragment, guardando en un Bundle la categoría del botón del que
                     * ha sido llamado, para luego poder acceder a ella en el fragment con .getArguments()
                     */
                    controlador.setCurrentCategory(((Button) view).getText().toString());
                    /*Usuario userLog = new Usuario();
                    userLog.setId_usuario(1);   //Admin
                    userLog.setAlias(aliasUsuarioLogueado);
                    userLog.setEmail(emailUsuarioLogueado);*/

                    //Testing controler for userLogged
                    System.out.println("Probando controlador desde el fragment en CategoriesActivity el usuario logueado");
                    System.out.println(controlador.getUsuarioLogueado());
                   // controlador.setUsuarioLogueado(userLog);

                    CategoriesFragmentFilter cff = new CategoriesFragmentFilter();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("controller", controlador);
                    bundle.putParcelable("userLogued", controlador.getUsuarioLogueado());



                    cff.setArguments(bundle);

                    /**
                     * Mostrando fragment
                     * @param getFragmentManager()
                     * @param tag:"Fragment filtrador"
                     */
                    cff.show(getFragmentManager(), "Fragment filtrador");
                }
            });
        }
    }
}
