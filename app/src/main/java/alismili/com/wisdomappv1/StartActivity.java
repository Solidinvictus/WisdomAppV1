package alismili.com.wisdomappv1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    /**
     * Configura y crea la activity. Se asigna el contentView al layout activity_start.xml
     * @param savedInstanceState no se utiliza
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    /**
     * Llama a crearTimer cada vez que la Activity aparezca
     */
    @Override
    protected void onResume() {
        super.onResume();
        final int SEGUNDOS = 5;
        crearTimer(SEGUNDOS);
    }
    /**
     * Crea un timer que hará que StartActivity lance automáticamente la siguiente Activity
     * (CategoriesActivity) después de n segundos.
     * @param SEGUNDOS los segundos que tardará en pasar a la siguiente pantalla
     */
    private void crearTimer(int SEGUNDOS) {

        final Intent i = new Intent(this, LoginActivity.class);

        // Crea el handler (otro hilo, para no dejar "freeze" el hilo main
        final Handler handler = new Handler();
        final Timer timer = new Timer(false);

        // Lanza el handler, con la tarea de lanzar la actividad
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                    }
                });
            }
        };
        // Se programa la tarea para realizarla después de x segundos
        timer.schedule(timerTask, SEGUNDOS * 1000);
    }

}

