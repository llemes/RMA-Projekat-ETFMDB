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

import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.ActivityMain.veliki;

/**
 * Created by lamija on 6/11/17.
 */

public class FragmentListaFilmova extends Fragment {

    private ModelFilmAdapter adapter;
    public static ModelFilm kliknutiFilm;
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

        View view = inflater.inflate(R.layout.fragment_lista_filmova, container, false);

        final ListView lista = (ListView) view.findViewById(R.id.listaFilm);
        adapter = new ModelFilmAdapter(getActivity());
        lista.setAdapter(adapter);

        final Button pretraga = (Button) view.findViewById(R.id.buttonPretragaFilm);
        final EditText unos = (EditText) view.findViewById(R.id.queryFilm);

        pretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = unos.getText().toString();

                try {

                    new WebPretragaFilm().execute(query);

                } catch(Throwable t) {

                    t.printStackTrace();
                    Toast toast = Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT);
                    toast.show();

                }

                unos.getText().clear();

            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ActivityMain.pozicijaFilm = position;
                kliknutiFilm = ModelFilmAdapter.filmovi.get(position);

                FragmentDetaljiFilma fd = new FragmentDetaljiFilma();

                if(veliki) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.desni, fd).addToBackStack("").commit();
                }
                else {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.aktivniFragment, fd).addToBackStack("").commit();
                }

            }
        });

        return view;

    }

}
