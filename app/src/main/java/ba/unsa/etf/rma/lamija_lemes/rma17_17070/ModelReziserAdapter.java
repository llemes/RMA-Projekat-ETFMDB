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

public class ModelReziserAdapter extends BaseAdapter {

    public static ArrayList<ModelReziser> reziseri = new ArrayList<>();
    public Context context;

    public static void DodajRezisera(ModelReziser r) {

        int len = reziseri.size();
        for(int i = 0; i < len; ++i) {
            if(r.GetImePrezime().equals(reziseri.get(i).GetImePrezime())) {
                return;
            }
        }
        reziseri.add(r);

    }

    public static void OcistiListu() {

        reziseri.clear();

    }

    public ModelReziserAdapter(Context c) {

        context = c;
    }

    @Override
    public int getCount() {
        return reziseri.size();
    }

    @Override
    public Object getItem(int i) {
        return reziseri.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = inflater.inflate(R.layout.element_liste_rezisera,viewGroup,false);

        TextView ImePrezime = (TextView) view.findViewById(R.id.ReziserImePrezime);

        ModelReziser temp = reziseri.get(i);

        ImePrezime.setText(temp.GetImePrezime());

        return view;
    }

}
