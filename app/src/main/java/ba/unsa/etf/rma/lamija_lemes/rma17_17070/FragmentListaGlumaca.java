package ba.unsa.etf.rma.lamija_lemes.rma17_17070;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.ActivityMain.veliki;

/**
 * Created by Lamija on 13.04.2017..
 */

public class FragmentListaGlumaca extends Fragment {

    public static ListView listView;
    public static ModelGlumac kliknutiGlumac;
    private ModelGlumacAdapter adapter;
    public void refresh(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_glumaca, container, false);

        final ListView lista = (ListView) view.findViewById(R.id.lista);
        listView = lista;
        adapter = new ModelGlumacAdapter(getActivity());
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ActivityMain.pozicija = position;
                ModelZanrAdapter.zanrovi.clear();
                ModelReziserAdapter.reziseri.clear();
                kliknutiGlumac = ModelGlumacAdapter.glumci.get(position);

                if(kliknutiGlumac.GetIzBaze()) {
                    try {
                        BazaFunkcionalnosti.pokupiOstalo(kliknutiGlumac.GetId());
                    }
                    catch(Throwable t) {
                        t.printStackTrace();
                    }
                }

                else {
                    new WebPretragaReziseriZanrovi().execute("dummy");
                }

                FragmentDetaljiGlumca gd = new FragmentDetaljiGlumca();

                if(veliki) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.desni, gd).addToBackStack("").commit();
                }

                else {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.aktivniFragment, gd).addToBackStack("").commit();
                }

            }
        });

        final Button pretraga = (Button) view.findViewById(R.id.buttonPretraga);
        final EditText unosTeksta = (EditText) view.findViewById(R.id.queryText);

        pretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = unosTeksta.getText().toString();

                try {

                    if(query.contains("actor:")) {

                        String queryIme = query.split(":")[1];
                        ArrayList<ModelGlumac> rez =  BazaFunkcionalnosti.PretragaGlumaca(queryIme);
                        ModelGlumacAdapter.glumci = rez;
                        ActivityMain.fragmentGlumciStatic.refresh();

                    }

                    else if(query.contains("director:")) {

                        String queryIme = query.split(":")[1];
                        ArrayList<ModelGlumac> rez = BazaFunkcionalnosti.PretragaGlumacaPoReziseru(queryIme);
                        ModelGlumacAdapter.glumci = rez;
                        ActivityMain.fragmentGlumciStatic.refresh();

                    }

                    else {
                        new WebPretragaGlumac().execute(query);
                    }

                } catch(Throwable t) {

                    t.printStackTrace();
                    Toast toast = Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT);
                    toast.show();

                }

                unosTeksta.getText().clear();

            }
        });

        return view;
    }
}
