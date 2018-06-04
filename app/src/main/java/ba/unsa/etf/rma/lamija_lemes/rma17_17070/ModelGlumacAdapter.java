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
 * Created by Lamija on 30.3.2017..
 */

public class ModelGlumacAdapter extends BaseAdapter{

    public static ArrayList<ModelGlumac> glumci = new ArrayList<>();
    public Context context;

    public static void setList(ArrayList<ModelGlumac> l) {

        glumci.addAll(l);

    }

    public ModelGlumacAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return glumci.size();
    }

    @Override
    public Object getItem(int i) {
        return glumci.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = inflater.inflate(R.layout.element_liste_glumaca,viewGroup,false);

        TextView ImeIPrezime = (TextView) view.findViewById(R.id.ImeIPrezime);
        TextView GodinaRodjenja = (TextView) view.findViewById(R.id.GodinaRodjenja);
        TextView MjestoRodjenja = (TextView) view.findViewById(R.id.MjestoRodjenja);
        TextView Rating = (TextView) view.findViewById(R.id.Rating);
        ImageView Slika = (ImageView) view.findViewById(R.id.slika);

        ModelGlumac temp= glumci.get(i);

        ImeIPrezime.setText(temp.GetImePrezime());
        GodinaRodjenja.setText(temp.GetGodinuRodjenja());
        MjestoRodjenja.setText(temp.GetMjestoRodjenja());
        Rating.setText(temp.GetRating());
        Picasso.with(context).load(temp.GetSlikaLink()).into(Slika);

        return view;
    }
}