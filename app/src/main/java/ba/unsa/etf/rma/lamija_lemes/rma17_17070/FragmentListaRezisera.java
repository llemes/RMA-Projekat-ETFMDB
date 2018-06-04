package ba.unsa.etf.rma.lamija_lemes.rma17_17070;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Lamija on 13.04.2017..
 */

public class FragmentListaRezisera extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_rezisera, container, false);

        final ListView lista = (ListView) view.findViewById(R.id.listaRez);
        lista.setAdapter(new ModelReziserAdapter(getActivity()));

        return view;

    }

}
