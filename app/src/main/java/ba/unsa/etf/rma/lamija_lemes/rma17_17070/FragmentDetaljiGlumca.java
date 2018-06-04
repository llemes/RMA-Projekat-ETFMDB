package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.ActivityMain.pozicija;

/**
 * Created by Lamija on 13.04.2017..
 */

public class FragmentDetaljiGlumca extends Fragment {

    ModelGlumac g;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_detalji_glumca, container, false);

        if(ModelGlumacAdapter.glumci.size() == 0)
            return view;

        g = ModelGlumacAdapter.glumci.get(pozicija);

        final RelativeLayout r = (RelativeLayout) view.findViewById(R.id.layoutBio);
        if(g.GetSpol().equals("M")) r.setBackgroundColor(0xFF000033);
        else if(g.GetSpol().equals("F")) r.setBackgroundColor(0xFF660000);

        ImageView slika = (ImageView) view.findViewById(R.id.slikaBio);
        Picasso.with(getContext()).load(g.GetSlikaLink()).into(slika);

        TextView imePrezime = (TextView) view.findViewById(R.id.imePrezimeBio);
        imePrezime.setText(g.GetImePrezime());

        TextView Godina = (TextView) view.findViewById(R.id.godinaRodjenjaSmrti);
        String tempGodina = g.GetGodinuRodjenja() + " - " + g.GetGodinuSmrti();
        Godina.setText(tempGodina);

        TextView MjestoRodjenja = (TextView) view.findViewById(R.id.mjestoRodjenjaBio);
        MjestoRodjenja.setText(g.GetMjestoRodjenja());

        TextView Rating = (TextView) view.findViewById(R.id.ratingBio);
        Rating.setText(g.GetRating());

        TextView spol = (TextView) view.findViewById(R.id.spolBio);
        spol.setText(g.GetSpol());

        final String biografijaTemp = g.GetBiografija();

        Button dugme = (Button) view.findViewById(R.id.buttonPodijeli);
        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, biografijaTemp);
                i.setType("text/plain");
                if(i.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(Intent.createChooser(i, getString(R.string.shareTekst)));
            }

        });

        Button bookmark = (Button) view.findViewById(R.id.buttonBookmark);

        if(g.GetIzBaze()) {

            bookmark.setText("remove bookmark");

        }

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if(g.GetIzBaze()) {

                        BazaFunkcionalnosti.deleteGlumac(g);

                        String feedback = "bookmark removed";

                        Toast toast = Toast.makeText(getContext(), feedback, Toast.LENGTH_SHORT);
                        toast.show();

                    }

                    else {

                        long rez = BazaFunkcionalnosti.insertGlumac(g);

                        String feedback = "bookmark failed";

                        if(rez > 0)
                            feedback = "bookmark added";

                        Toast toast = Toast.makeText(getContext(), feedback, Toast.LENGTH_SHORT);
                        toast.show();

                    }

                } catch(Throwable e) {

                    String feedback = "error";
                    Toast toast = Toast.makeText(getContext(), feedback, Toast.LENGTH_SHORT);
                    toast.show();

                    e.printStackTrace();
                }
            }
        });

        TextView link = (TextView) view.findViewById(R.id.imdbLink);
        link.setText(g.GetImdbLink());
        link.setMovementMethod(LinkMovementMethod.getInstance());

        TextView bio = (TextView) view.findViewById(R.id.biografijaBio);
        bio.setText(g.GetBiografija());

        return view;

    }
}
