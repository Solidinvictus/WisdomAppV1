package alismili.com.wisdomappv1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText aliasEt;
    private EditText passwordEt;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

    /**
     * Inicializa y configura lo necesario para crear la actividad.
     *
     * @param savedInstanceState no se utiliza.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        setTheme(preferencias.getInt("tema", R.style.AppTheme));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }*/

        aliasEt = findViewById(R.id.editTextUsuario);
        passwordEt = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        //crearBotonEntrar();
        //crearBotonRegistro();
    }

    private void onLogin(){
        final String alias = aliasEt.getText().toString().trim();
        final String password = passwordEt.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Aqui dfinimos el objeto JSON
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("error")){
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogin(
                                        jsonObject.getInt("id_usuario"),
                                        jsonObject.getString("alias"),
                                        jsonObject.getString("email")
                                );
                        //Toast.makeText(getApplicationContext(),"User login successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        //Para que no se pueda volver al login
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("alias",alias );
                params.put("password",password );
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }



    public void goRegister(View view){
        startActivity(new Intent(this,RegisterActivity.class));

    }

    public void onClick(View v){
        onLogin();
    }




    /**
     * Configura el botón de entrar.
     * Lanza un mensaje emergente si el usuario y password son válidos o no.
     */
    /*private void crearBotonEntrar() {
        Button btn_entrar = findViewById(R.id.btn_entrar);

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usuario = findViewById(R.id.editTextUsuario);
                EditText password = findViewById(R.id.editTextPassword);

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                // 2. Chain together various setter methods to set the dialog characteristics

                if ((usuario.getText().toString().equals("admin") &&
                        password.getText().toString().equals("admin"))) {
                    builder.setMessage("Login correcto");
                } else {
                    builder.setMessage("Login Incorrecto");
                }

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }*/

    /**
     * Configura el botón de registro, que lanza RegistroActivity al pulsarlo.
     */
    /*private void crearBotonRegistro() {
        Button btn_registro = findViewById(R.id.btn_registro);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }*/

}
