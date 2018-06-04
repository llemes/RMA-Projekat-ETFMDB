package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

/**
 * Created by Lamija on 30.03.2017..
 */

public class ModelZanr {

    private String naziv;
    private int id;

    public ModelZanr(String n, int i) {
        naziv = n;
        id = i;
    }

    public String GetNaziv() { return naziv; }
    public int GetID() { return id; }


}
