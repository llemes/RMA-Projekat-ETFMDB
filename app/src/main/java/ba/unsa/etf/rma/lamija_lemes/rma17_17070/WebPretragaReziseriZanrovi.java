package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lamija on 27.05.2017..
 */

public class WebPretragaReziseriZanrovi extends AsyncTask<String, Integer, Void> {

    @Override
    protected Void doInBackground(String... params) {

        try {

            if(FragmentListaGlumaca.kliknutiGlumac == null)
                return null;

            // pretraga filmova u kojima je glumio glumac koji je zadnji pretražen

            ModelGlumac odabrani = FragmentListaGlumaca.kliknutiGlumac;
            String url1 = "https://api.themoviedb.org/3/person/" + odabrani.GetId() + "?api_key=f56ec459e1f2f35d8aa65f7f1cbe1bf3&append_to_response=credits";
            URL url = new URL(url1);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = null;

            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            }
            catch(Throwable e) {
                e.printStackTrace();
            }

            String rezultat = this.convertStreamToString(in);

            JSONObject jo = new JSONObject(rezultat);
            JSONObject filmovi1 = jo.getJSONObject("credits");
            JSONArray filmovi = filmovi1.getJSONArray("cast");

            // dobaviti ID svih filmova jer su poredani od prvog ka zadnjem
            // u nizu su hronološki

            int length = filmovi.length();
            int[] filmoviIDs = new int[length];
            for(int i = 0; i < length; ++i) {
                if(filmovi.getJSONObject(i).getString("release_date") != null)
                    filmoviIDs[length - 1 - i] = filmovi.getJSONObject(i).getInt("id");
            }

            // pretraga filma po ID dok se ne napuni lista režisera

            int brojac = 0;
            while((ModelReziserAdapter.reziseri.size() != 7 && ModelZanrAdapter.zanrovi.size() != 7) || brojac != length) {

                url1 = "https://api.themoviedb.org/3/movie/" + filmoviIDs[brojac] + "?api_key=f56ec459e1f2f35d8aa65f7f1cbe1bf3&language=en-US&append_to_response=credits";
                url = new URL(url1);

                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                rezultat = this.convertStreamToString(in);

                jo = new JSONObject(rezultat);

                // režiseri

                if(ModelReziserAdapter.reziseri.size() != 7) {

                    JSONObject credits = jo.getJSONObject("credits");
                    JSONArray crew = credits.getJSONArray("crew");

                    int lengthCrew = crew.length();

                    Boolean dodan = false;
                    for(int i = 0; i < lengthCrew; ++i) {
                        JSONObject temp = crew.getJSONObject(i);
                        if(temp.getString("job").equals("Director") && !dodan) {
                            ModelReziserAdapter.DodajRezisera(new ModelReziser(temp.getString("name"), temp.getInt("id")));
                            dodan = true;
                        }
                    }
                }

                // žanrovi

                if(ModelZanrAdapter.zanrovi.size() != 7) {

                    JSONArray zanrovi = jo.getJSONArray("genres");
                    int lengthGenres = zanrovi.length();

                    if(lengthGenres != 0) {

                        JSONObject temp = zanrovi.getJSONObject(0);
                        ModelZanrAdapter.DodajZanr(new ModelZanr(temp.getString("name"), temp.getInt("id")));

                    }

                }

                ++brojac;

            }

        }

        catch(Throwable e) {
            e.printStackTrace();
        }

        return null;

    }

    public String convertStreamToString(InputStream is) {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();

        }

        catch(Throwable e) {
            e.printStackTrace();
        }

        finally {

            try {
                is.close();
            }

            catch(Throwable e) {
                e.printStackTrace();
            }

        }

        return null;

    }

}
