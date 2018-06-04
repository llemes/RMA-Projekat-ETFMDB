package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lamija on 30.03.2017..
 */

public class ModelGlumac implements Parcelable {

    private String imePrezime;
    private String godinaRodjenja;
    private String mjestoRodjenja;
    private String rating;
    private String godinaSmrti;
    private String biografija;
    private String spol;
    private String imdbLink;
    private int id;
    private String slikaLink;
    private boolean izBaze;

    // konstruktor za aktivnost
    public ModelGlumac(String ip, String gr, String mr, double r, String gs, String bio, int sp, String imdb, int i, String sl) {

        imePrezime = ip;
        godinaRodjenja = gr;
        mjestoRodjenja = mr;
        rating = Double.toString(r).substring(0, 4);
        godinaSmrti = gs;
        biografija = bio;

        if(sp == 0) {
            spol = "M";
        }
        else if(sp == 1) {
            spol = "F";
        }
        else {
            spol = "?";
        }

        imdbLink = imdb;
        id = i;
        slikaLink = sl;
        izBaze = false;

    }

    // konstruktor za bazu
    public ModelGlumac(String ip, String gr, String mr, String r, String gs, String bio, String sp, String imdb, int i, String sl) {

        imePrezime = ip;
        godinaRodjenja = gr;
        mjestoRodjenja = mr;
        rating = r;
        godinaSmrti = gs;
        biografija = bio;
        spol = sp;
        imdbLink = imdb;
        id = i;
        slikaLink = sl;
        izBaze = true;

    }

    protected ModelGlumac(Parcel in) {

        imePrezime = in.readString();
        godinaRodjenja = in.readString();
        mjestoRodjenja = in.readString();
        rating = in.readString();
        godinaSmrti = in.readString();
        biografija = in.readString();
        spol = in.readString();
        imdbLink = in.readString();
        id = in.readInt();
        slikaLink = in.readString();

    }

    public static final Creator<ModelGlumac> CREATOR = new Creator<ModelGlumac>() {
        @Override
        public ModelGlumac createFromParcel(Parcel source) {
            return new ModelGlumac(source);
        }

        @Override
        public ModelGlumac[] newArray(int size) {
            return new ModelGlumac[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(imePrezime);
        dest.writeString(godinaRodjenja);
        dest.writeString(mjestoRodjenja);
        dest.writeString(rating);
        dest.writeString(godinaSmrti);
        dest.writeString(biografija);
        dest.writeString(spol);
        dest.writeString(imdbLink);
        dest.writeInt(id);
        dest.writeString(slikaLink);

    }

    public String GetImePrezime() { return imePrezime; }
    public String GetGodinuRodjenja() { return godinaRodjenja; }
    public String GetMjestoRodjenja() { return mjestoRodjenja; }
    public String GetRating() { return rating; }
    public String GetGodinuSmrti() { return godinaSmrti; }
    public String GetBiografija() { return biografija; }
    public String GetSpol() { return spol; }
    public String GetImdbLink() { return imdbLink; }
    public int GetId() { return id; }
    public String GetSlikaLink() { return slikaLink; }
    public boolean GetIzBaze() { return izBaze; }

}
