package alismili.com.wisdomappv1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import alismili.com.wisdomappv1.controllers.Controlador;
import alismili.com.wisdomappv1.models.Usuario;


public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUserName, textViewUserEmail;
    public static final String ALIAS_PROFILE="USER_LOGGED_ALIAS";
    public static final String EMAIL_PROFILE="USER_LOGGED_EMAIL";


    //private CustomAdapter customeAdapter;

    private Controlador controlador;
    Usuario userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userLogged = new Usuario();
        controlador = new Controlador();
        //Seteamos el usuario logueado en nuestra controladora


        if(!SharedPrefManager.getInstance(this).isLoggedIn()){

            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            //Seteamos el usuario logueado en nuestra controladora

            userLogged.setEmail(SharedPrefManager.getInstance(this).getUserEmail());
            userLogged.setAlias(SharedPrefManager.getInstance(this).getUserName());
        }

        textViewUserName = findViewById(R.id.textViewUserName);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);

        textViewUserName.setText(SharedPrefManager.getInstance(this).getUserName());
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuLogout:
               SharedPrefManager.getInstance(this).logOut();
               finish();
               startActivity(new Intent(this,LoginActivity.class));

                break;
            case R.id.menuSettings:
                Toast.makeText(this,"Settings on implementation...",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void logOut(View view){
        SharedPrefManager.getInstance(this).logOut();
        finish();
        startActivity(new Intent(this,LoginActivity.class));

    }

    public void irCategorias(View view){
        Intent intent = new Intent(this, CategoriesActivity.class);

        intent.putExtra( ALIAS_PROFILE,userLogged.getAlias());
        intent.putExtra(EMAIL_PROFILE,userLogged.getEmail());

        //Testing UserLogged
        System.out.println("PROBANDO INTENT DENTRO DE PROFILEACTIVITY...");
        System.out.println(userLogged.getAlias());
        System.out.println(userLogged.getEmail());

        startActivity(intent);

    }


}
