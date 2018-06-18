package alismili.com.wisdomappv1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import alismili.com.wisdomappv1.controllers.Controlador;
import alismili.com.wisdomappv1.fromphp.PhpConstants;
import alismili.com.wisdomappv1.models.Categoria;
import alismili.com.wisdomappv1.models.Nucleo;
import alismili.com.wisdomappv1.models.TipoTraduccion;
import alismili.com.wisdomappv1.models.Usuario;

public class ResultsActivity extends AppCompatActivity {
    HashMap<String, String> contenido_trad = new HashMap<>();
    private String currentCodeTranslate;
    private String currentCategory;
    private String currentType;
    private Usuario userLogued;

    //Variables relacionadas con el parseo
    private List<Usuario> usuariosArrayList;
    private List<Categoria> categoriasArrayList;
    private List<TipoTraduccion> tipoTraduccionesArrayList;
    private List<Nucleo> nucleosArrayList;



    public ControladorListas controladorListas;

    private Controlador controlador;

    //***
    private JsonArrayRequest arrayRequest ;
    private RequestQueue requestQueue ;
    private static List<Usuario> lstUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Aqui empiezo a trabajar el parseo
        usuariosArrayList = new ArrayList<>();
        categoriasArrayList = new ArrayList<>();
        tipoTraduccionesArrayList = new ArrayList<>();
        nucleosArrayList = new ArrayList<>();
        controladorListas = new ControladorListas();
        //******************
        getJsonUsuarios(PhpConstants.ServiceType.URL_GET_USERS);
        getJsonCategorias(PhpConstants.ServiceType.URL_GET_CATEGORIES);
        getJsonTipoTraducciones(PhpConstants.ServiceType.URL_GET_TRASLATETYPES);
        getJsonNucleos(PhpConstants.ServiceType.URL_GET_CORES);

        inicializeControler();

        //Testing Controller
        System.out.println("Probando controlador");
        System.out.println(controlador.toString());

    }


    public void inicializeControler(){
        this.controlador =  getIntent().getExtras().getParcelable("controller");
        this.userLogued = getIntent().getExtras().getParcelable("userLogued");  //No va
        controlador.setUsuarioLogueado(userLogued);

        System.out.println("Probando controlador desde el inicialize");
        System.out.println(controlador.toString());
        this.currentType = controlador.getCurrentType();
        this.currentCategory = controlador.getCurrentCategory();
        this.currentCodeTranslate = controlador.getCurrentTranslation();

    }


    //1- USUARIOS
    //this method is actually fetching the json string
    private void getJsonUsuarios(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {
                    loadIntoListUsers(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        //sb.append(json + "\n");
                        sb.append(json);
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void loadIntoListUsers(String json) throws JSONException {
        JSONObject Jasonobject = new JSONObject(json);
        JSONArray jsonArray = Jasonobject.getJSONArray("users");
        //JSONArray jsonArray = new JSONArray(json);
        //JSONArray dataArray = jsonObject.getJSONArray("users");

        for (int i = 0; i < jsonArray.length(); i++) {
            Usuario usuario = new Usuario();
            JSONObject dataobj = jsonArray.getJSONObject(i);
            usuario.setId_usuario(dataobj.getInt(PhpConstants.Params.ID_USUARIO));
            usuario.setAlias(dataobj.getString(PhpConstants.Params.ALIAS));
            usuario.setEmail(dataobj.getString(PhpConstants.Params.EMAIL));
            usuario.setPassword(dataobj.getString(PhpConstants.Params.PASSWORD));
            usuariosArrayList.add(usuario);
            if(usuario.getId_usuario()==1){     //Temporal
                userLogued = usuario;
            }



        }
        System.out.println("Imprimiendo los usuarios");
        System.out.println(usuariosArrayList);

    }

    //2-CATEGORIAS
    //this method is actually fetching the json string
    private void getJsonCategorias(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {
                    loadIntoListCategories(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        //sb.append(json + "\n");
                        sb.append(json);
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void loadIntoListCategories(String json) throws JSONException {
        JSONObject Jasonobject = new JSONObject(json);
        JSONArray jsonArray = Jasonobject.getJSONArray("categories");
        //JSONArray jsonArray = new JSONArray(json);
        //JSONArray dataArray = jsonObject.getJSONArray("users");

        for (int i = 0; i < jsonArray.length(); i++) {
            Categoria categoria = new Categoria();
            JSONObject dataobj = jsonArray.getJSONObject(i);
            categoria.setId_categoria(dataobj.getInt(PhpConstants.Params.ID_CATEGORIA));
            categoria.setNombre_categoria(dataobj.getString(PhpConstants.Params.NOMBRE_CATEGORIA));
            categoria.setDescripcion_categoria(dataobj.getString(PhpConstants.Params.DESCRIPCION_CATEGORIA));
            //categoria.setPor_defecto(dataobj.getBoolean(PhpConstants.Params.POR_DEFECTO));
            categoriasArrayList.add(categoria);

        }
        System.out.println("Imprimiendo las categorias");
        System.out.println(categoriasArrayList);

    }

    //3-TIPOS TRADUCCIONES
    //this method is actually fetching the json string
    private void getJsonTipoTraducciones(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {
                    loadIntoListTraslateTypes(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        //sb.append(json + "\n");
                        sb.append(json);
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void loadIntoListTraslateTypes(String json) throws JSONException {
        JSONObject Jasonobject = new JSONObject(json);
        JSONArray jsonArray = Jasonobject.getJSONArray("traslatetypes");
        //JSONArray jsonArray = new JSONArray(json);
        //JSONArray dataArray = jsonObject.getJSONArray("users");

        for (int i = 0; i < jsonArray.length(); i++) {
            TipoTraduccion tipoTraduccion = new TipoTraduccion();
            JSONObject dataobj = jsonArray.getJSONObject(i);
            tipoTraduccion.setId_tipo_traduccion(dataobj.getInt(PhpConstants.Params.ID_TIPO_TRADUCCION));
            tipoTraduccion.setTipo_traduccion(dataobj.getString(PhpConstants.Params.ORIGEN_DESTINO));
            tipoTraduccionesArrayList.add(tipoTraduccion);

        }
        System.out.println("Imprimiendo los tipos de traducciones");
        System.out.println(tipoTraduccionesArrayList);

    }

    //4- NUCLEOS
    //this method is actually fetching the json string
    private void getJsonNucleos(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {
                    loadIntoListCores(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        //sb.append(json + "\n");
                        sb.append(json);
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void loadIntoListCores(String json) throws JSONException {
        JSONObject Jasonobject = new JSONObject(json);
        JSONArray jsonArray = Jasonobject.getJSONArray("cores");
        //JSONArray jsonArray = new JSONArray(json);
        //JSONArray dataArray = jsonObject.getJSONArray("users");

        for (int i = 0; i < jsonArray.length(); i++) {
            Nucleo nucleo = new Nucleo();
            JSONObject dataobj = jsonArray.getJSONObject(i);
            nucleo.setIdNucleo(dataobj.getInt(PhpConstants.Params.ID_NUCLEO));
            nucleo.setClave(dataobj.getString(PhpConstants.Params.CLAVE));
            nucleo.setValor(dataobj.getString(PhpConstants.Params.VALOR));
            TipoTraduccion tpTrad = new TipoTraduccion();   //COMO SE TRATA DE OBJETOS*
            tpTrad.setId_tipo_traduccion(dataobj.getInt(PhpConstants.Params.ID_TIPO_TRADUCCION_FK));//*
            nucleo.setTipoTraduccion(tpTrad);//*

            Categoria categoria = new Categoria();
            Usuario usuario = new Usuario();
            categoria.setId_categoria(dataobj.getInt(PhpConstants.Params.ID_CATEGORIA_FK));
            usuario.setId_usuario(dataobj.getInt(PhpConstants.Params.ID_USUARIO_FK));
            nucleo.setCategoria(categoria);
            nucleo.setUsuario(usuario);
            nucleo.setTipo(dataobj.getInt(PhpConstants.Params.TIPO));


            nucleosArrayList.add(nucleo);

        }
        System.out.println("Imprimiendo los nucleos");
        System.out.println(nucleosArrayList);
        setNucleosArrayList(nucleosArrayList);

        for (Categoria cat: categoriasArrayList){
            if(controlador.getCurrentCategory().equals(cat.getNombre_categoria())){

            }
        }


        //Empieza la chicha:


        //METODO FILTRADOR SELECTIVO DESDE LA BBDD
        System.out.println("Probando antes la chicha el controlador");
        System.out.println(controlador);

        for (Nucleo nucleo : nucleosArrayList) {
            if((nucleo.getCategoria().getId_categoria()== recogeTipoCategoria())
            && (nucleo.getTipo()== controlador.recogeTipoContenido())){

                contenido_trad.put(nucleo.getClave(),nucleo.getValor());
            }

            //contenido_trad.put(nucleo.getClave(),nucleo.getValor());
        }



        //METODO ORIGINAL QUE TRAIA TODO DESDE LA BBDD
        /*for (Nucleo nucleo : nucleosArrayList) {
            contenido_trad.put(nucleo.getClave(),nucleo.getValor());
        }*/
        List<HashMap<String,String>> listItems = new ArrayList<>();

        ListView resultsListView = findViewById(R.id.default_results_listView);
        SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.item_row_result,
                new String[]{"Contenido","Traduccion"},
                new int[]{R.id.itemOriginal,R.id.itemTraduccion});
        //Aqui esta la chicha
        Iterator it = contenido_trad.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String,String> resultMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultMap.put("Contenido",pair.getKey().toString());
            resultMap.put("Traduccion",pair.getValue().toString());

            listItems.add(resultMap);
        }

        resultsListView.setAdapter(adapter);

    }

    public List<Nucleo> getNucleosArrayList() {
        return nucleosArrayList;
    }

    public void setNucleosArrayList(List<Nucleo> nucleosArrayList) {
        this.nucleosArrayList = nucleosArrayList;
    }


    public int recogeTipoCategoria(){
        int result=0;

        switch (controlador.getCurrentCategory().toUpperCase()) {

            case "COLORES":
                result = 1;
                break;

            case "NATURALEZA":
                result = 2;
                break;
            case "LUGARES":
                result = 3;
                break;

            case "TIEMPO":
                result = 4;
                break;

            case "COMIDA":
                result = 5;
                break;

            case "TRANSPORTE":
                result = 6;
                break;

            case "ANIMALES":
                result = 7;
                break;

            case "MATERIALES":
                result = 8;
                break;

            case "SALUD":
                result = 9;
                break;
            case "BAÚL*":
                result = 10;
                break;
        }

        return result;
    }
}
