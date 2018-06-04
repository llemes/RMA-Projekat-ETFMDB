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
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by lamija on 6/11/17.
 */

public class WebPretragaFilm extends AsyncTask<String, Integer, Void> {

    @Override
    protected Void doInBackground(String... params) {

        String query = null;

        try {

            query = URLEncoder.encode(params[0], "utf-8");
            query.replaceAll("\\s+", "-");

            String url1 = "https://api.themoviedb.org/3/search/movie?api_key=f56ec459e1f2f35d8aa65f7f1cbe1bf3&language=en-US&query=" + query;
            URL url = new URL(url1);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = null;

            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (Throwable t) {
                t.printStackTrace();
            }

            String rezultat = this.convertStreamToString(in);

            JSONObject jo = new JSONObject(rezultat);
            JSONArray filmRez = jo.getJSONArray("results");

            int brFilmova = filmRez.length();

            if(brFilmova > 10)
                brFilmova = 10;

            ArrayList<ModelFilm> temp = new ArrayList<>();

            for(int i = 0; i < brFilmova; ++i) {

                JSONObject film = filmRez.getJSONObject(i);

                String naziv = film.getString("title");
                int id = film.getInt("id");
                String slikaLink = "https://image.tmdb.org/t/p/w1280" + film.getString("poster_path");
                String opis = film.getString("overview");

                ModelFilm filmObject = new ModelFilm(id, naziv, slikaLink, opis);

                temp.add(filmObject);

            }

            ModelFilmAdapter.setList(temp);
            ActivityMain.fragmentFilmoviStatic.refresh();

        } catch(Throwable t) {
            t.printStackTrace();
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
