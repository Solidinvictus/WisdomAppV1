package alismili.com.wisdomappv1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText aliasEt;
    private EditText emailEt;
    private EditText passwordEt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }*/

        aliasEt = findViewById(R.id.editTextUser);
        emailEt = findViewById(R.id.editTextMail);
        passwordEt = findViewById(R.id.editTextPassword);

        progressDialog = new ProgressDialog(this);


    }

    public void onRegister(View view){
        final String str_alias = aliasEt.getText().toString().trim();
        final String str_email = emailEt.getText().toString().trim();
        final String str_password = passwordEt.getText().toString().trim();
        //String type = "register";
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //Aqui dfinimos el objeto JSON
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("alias",str_alias );
                params.put("email",str_email );
                params.put("password",str_password );
                return params;
            }
        };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        /**
         * El singleton que hemos implementado para manejar la peticion
         */
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        /*BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_alias, str_email,str_password );*/

    }

    public void entrarLogin(View view){
        Button btn_registro = findViewById(R.id.buttonEntrar);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creacion del Intent
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                //  iniciar la actividad
                startActivity(intent);
            }
        });
    }
}
