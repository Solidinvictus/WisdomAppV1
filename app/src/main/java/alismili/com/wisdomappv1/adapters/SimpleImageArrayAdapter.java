package alismili.com.wisdomappv1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import alismili.com.wisdomappv1.R;


/**
 * Esta clase que hereda de ArrayAdapter nos creara un menu ocultable
 * al pulsar en el spinner de opciones
 */
public class SimpleImageArrayAdapter extends ArrayAdapter<Integer>{

    private Integer[] images;
    private String[] text;
    private Context context;

    /**
     * Constructor que tiene como argumentos el contexto y los arrays de texto y de imagenes
     *
     * @param context
     * @param images
     * @param text
     */
    public SimpleImageArrayAdapter(Context context, Integer[] images, String[] text) {
        super(context, android.R.layout.simple_spinner_item, images);
        this.images = images;
        this.text = text;
        this.context = context;
    }

    /**
     * Para poder mostrarnos la lista de items del menu
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    /**
     * Es necesario este metodo para poder adaptar el item al convertview
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    /**
     * Uste metodo usa el inflater para poder inflar el spinner_value_layout.xml
     * previamente creado y devolver una vista (view) que sera precisamente el menu
     * desplegable
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    private View getImageForPosition(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_value_layout, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.spinnerTextView);
        textView.setText(text[position]);
        ImageView imageView = (ImageView) row.findViewById(R.id.spinnerImages);
        imageView.setImageResource(images[position]);
        return row;
    }

}

