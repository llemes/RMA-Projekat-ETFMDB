package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

/**
 * Created by lamija on 6/11/17.
 */

public class ModelFilm {

    private int id;
    private String naziv;
    private String slikaLink;
    private String opis;

    public ModelFilm(int i, String n, String sl, String o) {

        id = i;
        naziv = n;
        slikaLink = sl;
        opis = o;

    }

    public int GetId() { return id; }
    public String GetNaziv() { return naziv; }
    public String GetSlikaLink() { return slikaLink; }
    public String GetOpis() { return opis; }

}
