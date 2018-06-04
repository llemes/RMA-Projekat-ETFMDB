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
 * Created by Lamija on 26.05.2017..
 */

public class WebPretragaGlumac extends AsyncTask<String, Integer, Void> {

    @Override
    protected Void doInBackground(String... params) {

        String query = null;

        try {

            // pretraga po stringu

            query = URLEncoder.encode(params[0], "utf-8");
            query.replaceAll("\\s+", "-");

            String url1 = "https://api.themoviedb.org/3/search/person?api_key=f56ec459e1f2f35d8aa65f7f1cbe1bf3&query=" + query;
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
            JSONArray glumacRez = jo.getJSONArray("results");

            int brGlumaca = glumacRez.length();

            if(brGlumaca > 10)
                brGlumaca = 10;

            ArrayList<ModelGlumac> temp = new ArrayList<>();

            for(int i = 0; i < brGlumaca; ++i) {

                JSONObject glumacId = glumacRez.getJSONObject(i);
                int id = glumacId.getInt("id");

                // pretraga po dobijenom id

                url1 = "https://api.themoviedb.org/3/person/" + Integer.toString(id) + "?api_key=f56ec459e1f2f35d8aa65f7f1cbe1bf3&language=en-US";
                url = new URL(url1);

                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());

                rezultat = convertStreamToString(in);

                jo = new JSONObject(rezultat);

                String ime = jo.getString("name");
                String godinaRodjenja = jo.getString("birthday");
                String mjestoRodjenja = jo.getString("place_of_birth");
                double rating = jo.getDouble("popularity");
                String godinaSmrti = jo.getString("deathday");

                if(godinaSmrti.equals("")) {
                    godinaSmrti = "/";
                }

                String bio = jo.getString("biography");
                int spol = jo.getInt("gender");
                String imdbLink = "http://www.imdb.com/name/" + jo.getString("imdb_id") + "/";
                String slikaLink = "https://image.tmdb.org/t/p/w1280/" + jo.getString("profile_path");

                ModelGlumac g = new ModelGlumac(ime, godinaRodjenja, mjestoRodjenja, rating, godinaSmrti, bio, spol, imdbLink, id, slikaLink);

                temp.add(g);

            }

            ModelGlumacAdapter.setList(temp);
            ActivityMain.fragmentGlumciStatic.refresh();

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
