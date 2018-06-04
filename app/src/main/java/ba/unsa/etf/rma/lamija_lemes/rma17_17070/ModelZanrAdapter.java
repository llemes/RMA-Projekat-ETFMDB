package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lamija on 30.03.2017..
 */

public class ModelZanrAdapter extends BaseAdapter {

    public static ArrayList<ModelZanr> zanrovi = new ArrayList<>();
    private Context context;

    public static void DodajZanr(ModelZanr z) {
        int len = zanrovi.size();
        for(int i = 0; i < len; ++i) {
            if(zanrovi.get(i).GetNaziv().equals(z.GetNaziv())) {
                return;
            }
        }
        zanrovi.add(z);
    }

    public static void OcistiListu() {

        zanrovi.clear();

    }

    public ModelZanrAdapter(Context c) {

        context = c;

    }

    @Override
    public int getCount() {
        return zanrovi.size();
    }

    @Override
    public Object getItem(int i) {
        return zanrovi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = inflater.inflate(R.layout.element_liste_zanrova, viewGroup, false);

        TextView Naziv = (TextView) view.findViewById(R.id.ZanrNaziv);

        ModelZanr temp = zanrovi.get(i);

        Naziv.setText(temp.GetNaziv());

        return view;

    }
}
