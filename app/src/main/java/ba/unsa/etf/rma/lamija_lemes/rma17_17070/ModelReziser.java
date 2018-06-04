package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

/**
 * Created by Lamija on 30.03.2017..
 */

public class ModelReziser {

    private String imePrezime;
    private int id;

    public ModelReziser(String ip, int i) {
        imePrezime = ip;
        id = i;
    }

    public String GetImePrezime() { return imePrezime; }
    public int GetID() { return id; }

}
