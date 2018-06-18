package alismili.com.wisdomappv1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity {

    /**
     * Referencia necesaria para acceder a las preferencias guardadas en el teléfono.
     */
    private SharedPreferences preferencias;

    /**
     * Inicializa y configura lo necesario para crear la actividad, así como conseguir la referencia
     * de las preferencias actuales guardadas en el teléfono
     * @param savedInstanceState no se utiliza
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        setTheme(preferencias.getInt("tema", R.style.AppTheme));
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

        pantallaCarga();
        cambiarTema();
    }

    /**
     * Crea un listener que recoge cambios en el primer checkbox de la activity. De momento
     * sólo muestra un toast.
     */
    private void pantallaCarga() {
        Preference.OnPreferenceChangeListener chkListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(PreferencesActivity.this,
                        "Funcionalidad a añadir en versiones futuras.", Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        CheckBoxPreference chkPreference = (CheckBoxPreference) findPreference("presentacionCheckBox");
        chkPreference.setOnPreferenceChangeListener(chkListener);
    }

    /**
     * Crea un listener que recoge cambios en el listPreference de la activity. Guarda los cambios
     * en el archivo de preferencias, que luego las demás clases leerán en el onCreate.
     */
    private void cambiarTema() {

        Preference.OnPreferenceChangeListener listListener = new Preference.OnPreferenceChangeListener() {
            SharedPreferences.Editor ed = preferencias.edit();

            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {
                String tema = value.toString();
                if (tema.equals("MORADO")){
                    ed.putInt("tema", R.style.AppTheme);
                    ed.commit();
                } else if (tema.equals("BLANCO")){
                    ed.putInt("tema", R.style.AppTheme_blanco);
                    ed.commit();
                } else if (tema.equals("NEGRO")){
                    ed.putInt("tema", R.style.AppTheme_negro);
                    ed.commit();
                }
                Toast.makeText(PreferencesActivity.this, "Has elegido el tema: " + tema,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        ListPreference listPreference = (ListPreference) findPreference("listaColor");
        listPreference.setOnPreferenceChangeListener(listListener);
    }
}