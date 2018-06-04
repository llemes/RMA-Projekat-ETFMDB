package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lamija on 6/11/17.
 */

public class ModelFilmAdapter extends BaseAdapter {

    public Context context;
    public static ArrayList<ModelFilm> filmovi = new ArrayList<>();

    public static void setList(ArrayList<ModelFilm> l) {

        filmovi.addAll(l);

    }

    public ModelFilmAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return filmovi.size();
    }

    @Override
    public Object getItem(int i) {
        return filmovi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null) {
            view = inflater.inflate(R.layout.element_liste_filmova, viewGroup, false);
        }

        TextView naziv = (TextView) view.findViewById(R.id.nazivFilm);
        naziv.setText(filmovi.get(i).GetNaziv());

        ImageView slika = (ImageView) view.findViewById(R.id.slikaFilm);
        Picasso.with(context).load(filmovi.get(i).GetSlikaLink()).into(slika);

        return view;

    }

}
