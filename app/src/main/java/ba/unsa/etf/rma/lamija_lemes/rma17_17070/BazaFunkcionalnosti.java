package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;

import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.DATABASE_GRVEZA;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.DATABASE_GZVEZA;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.DATABASE_TABLE_GLUMAC;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.DATABASE_TABLE_REZISER;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.DATABASE_TABLE_ZANR;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_ID;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_BIOGRAFIJA;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_GODINA_RODJENJA;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_GODINA_SMRTI;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_IME;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_LINK;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_MJESTO_RODJENJA;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_RATING;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_SLIKA_LINK;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GLUMAC_SPOL;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GRVEZA_GLUMAC;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GRVEZA_REZISER;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GZVEZA_GLUMAC;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.GZVEZA_ZANR;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.REZISER_ID;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.REZISER_IME;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.ZANR_ID;
import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.BazaHelper.ZANR_IME;

/**
 * Created by lamija on 6/8/17.
 */

public class BazaFunkcionalnosti {

    public static BazaHelper helper;

    public static ArrayList<ModelGlumac> PretragaGlumaca(String ime) {

        ArrayList<ModelGlumac> rez = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM glumci WHERE ime LIKE ?", new String[] { "%" + ime + "%" });

        if(cursor.moveToFirst()) {

            while(!cursor.isAfterLast()) {

                ModelGlumac temp = new ModelGlumac(cursor.getString(cursor.getColumnIndex(GLUMAC_IME)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_GODINA_RODJENJA)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_MJESTO_RODJENJA)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_RATING)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_GODINA_SMRTI)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_BIOGRAFIJA)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_SPOL)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_LINK)),
                        cursor.getInt(cursor.getColumnIndex(GLUMAC_ID)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_SLIKA_LINK)));

                rez.add(temp);

                cursor.moveToNext();

            }

        }

        cursor.close();

        return rez;

    }

    public static ArrayList<ModelGlumac> PretragaGlumacaPoReziseru(String ime) {

        ArrayList<ModelGlumac> rez = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();

        String query = "SELECT " + DATABASE_TABLE_GLUMAC + ".* FROM " +
                DATABASE_TABLE_GLUMAC + ", " + DATABASE_TABLE_REZISER + ", " + DATABASE_GRVEZA + " WHERE " +
                DATABASE_TABLE_GLUMAC + "." + GLUMAC_ID + " = " + DATABASE_GRVEZA + "." + GRVEZA_GLUMAC + " AND " +
                DATABASE_TABLE_REZISER + "." + REZISER_ID + " = " + DATABASE_GRVEZA + "." + GRVEZA_REZISER + " AND " +
                DATABASE_TABLE_REZISER + "." + REZISER_IME + " LIKE ?;";

        Cursor cursor = db.rawQuery(query, new String[] { "%" + ime + "%" });

        if(cursor.moveToFirst()) {

            while(!cursor.isAfterLast()) {

                ModelGlumac temp = new ModelGlumac(cursor.getString(cursor.getColumnIndex(GLUMAC_IME)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_GODINA_RODJENJA)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_MJESTO_RODJENJA)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_RATING)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_GODINA_SMRTI)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_BIOGRAFIJA)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_SPOL)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_LINK)),
                        cursor.getInt(cursor.getColumnIndex(GLUMAC_ID)),
                        cursor.getString(cursor.getColumnIndex(GLUMAC_SLIKA_LINK)));

                rez.add(temp);
                cursor.moveToNext();

            }

        }

        cursor.close();

        return rez;

    }

    public static void pokupiOstalo(int idGlumca) {

        SQLiteDatabase db = helper.getReadableDatabase();

        // režiseri

        String query = "SELECT " + DATABASE_TABLE_REZISER + "." + REZISER_IME + ", " + DATABASE_TABLE_REZISER + "." + REZISER_ID + " FROM " +
                DATABASE_TABLE_REZISER + ", " + DATABASE_GRVEZA +
                " WHERE " + DATABASE_TABLE_REZISER + "." + REZISER_ID + " = " + DATABASE_GRVEZA + "." + GRVEZA_REZISER +
                " AND " + DATABASE_GRVEZA + "." + GRVEZA_GLUMAC + " = ?";

        Cursor cursor = db.rawQuery(query, new String[] { Integer.toString(idGlumca) });
        ModelReziserAdapter.OcistiListu();

        if(cursor.moveToFirst()) {

            while(!cursor.isAfterLast()) {

                ModelReziser temp = new ModelReziser(cursor.getString(cursor.getColumnIndex(REZISER_IME)),
                        cursor.getInt(cursor.getColumnIndex(REZISER_ID)));

                ModelReziserAdapter.DodajRezisera(temp);

                cursor.moveToNext();

            }

        }

        cursor.close();

        // žanrovi

        query = "SELECT " + DATABASE_TABLE_ZANR + "." + ZANR_IME + ", " + DATABASE_TABLE_ZANR + "." + ZANR_ID + " FROM " +
                DATABASE_TABLE_ZANR + ", " + DATABASE_GZVEZA +
                " WHERE " + DATABASE_TABLE_ZANR + "." + ZANR_ID + " = " + DATABASE_GZVEZA + "." + GZVEZA_ZANR +
                " AND " + DATABASE_GZVEZA + "." + GZVEZA_GLUMAC + " = ?";

        cursor = db.rawQuery(query, new String[] { Integer.toString(idGlumca) });
        ModelZanrAdapter.OcistiListu();

        if(cursor.moveToFirst()) {

            while(!cursor.isAfterLast()) {

                ModelZanr temp = new ModelZanr(cursor.getString(cursor.getColumnIndex(ZANR_IME)),
                        cursor.getInt(cursor.getColumnIndex(ZANR_ID)));

                ModelZanrAdapter.DodajZanr(temp);

                cursor.moveToNext();

            }

        }

        cursor.close();

    }

    public static long insertGlumac(ModelGlumac g) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues vals = new ContentValues();

        vals.put(BazaHelper.GLUMAC_ID, g.GetId());
        vals.put(BazaHelper.GLUMAC_IME, g.GetImePrezime());
        vals.put(BazaHelper.GLUMAC_GODINA_RODJENJA, g.GetGodinuRodjenja());
        vals.put(BazaHelper.GLUMAC_MJESTO_RODJENJA, g.GetMjestoRodjenja());
        vals.put(BazaHelper.GLUMAC_RATING, g.GetRating());
        vals.put(BazaHelper.GLUMAC_GODINA_SMRTI, g.GetGodinuSmrti());
        vals.put(BazaHelper.GLUMAC_BIOGRAFIJA, g.GetBiografija());
        vals.put(BazaHelper.GLUMAC_SPOL, g.GetSpol());
        vals.put(BazaHelper.GLUMAC_LINK, g.GetImdbLink());
        vals.put(BazaHelper.GLUMAC_SLIKA_LINK, g.GetSlikaLink());

        long rez = db.insertWithOnConflict(DATABASE_TABLE_GLUMAC, null, vals, SQLiteDatabase.CONFLICT_IGNORE);

        insertOstalo(g.GetId());

        return rez;

    }

    private static void insertZanr(ModelZanr z, int idGlumca) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues vals = new ContentValues();

        vals.put(BazaHelper.ZANR_ID, z.GetID());
        vals.put(BazaHelper.ZANR_IME, z.GetNaziv());

        long rez = db.insertWithOnConflict(DATABASE_TABLE_ZANR, null, vals, SQLiteDatabase.CONFLICT_IGNORE);

        vals = new ContentValues();
        vals.put(BazaHelper.GZVEZA_GLUMAC, idGlumca);
        vals.put(BazaHelper.GZVEZA_ZANR, z.GetID());

        rez = db.insert(DATABASE_GZVEZA, null, vals);

        return;

    }

    private static void insertReziser(ModelReziser r, int idGlumca) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues vals = new ContentValues();

        vals.put(BazaHelper.REZISER_ID, r.GetID());
        vals.put(BazaHelper.REZISER_IME, r.GetImePrezime());

        long rez = db.insertWithOnConflict(DATABASE_TABLE_REZISER, null, vals, SQLiteDatabase.CONFLICT_IGNORE);

        vals = new ContentValues();
        vals.put(BazaHelper.GRVEZA_GLUMAC, idGlumca);
        vals.put(BazaHelper.GRVEZA_REZISER, r.GetID());

        rez = db.insert(DATABASE_GRVEZA, null, vals);

        return;

    }

    private static void insertOstalo(int idGlumca) {

        // ono što treba ubacivati je već u fragmentima

        int zLen = ModelZanrAdapter.zanrovi.size();

        for(int i = 0; i < zLen; ++i) {

            insertZanr(ModelZanrAdapter.zanrovi.get(i), idGlumca);

        }

        int rLen = ModelReziserAdapter.reziseri.size();

        for(int i = 0; i < rLen; ++i) {

            insertReziser(ModelReziserAdapter.reziseri.get(i), idGlumca);

        }

    }

    private static void damageControl() {

        SQLiteDatabase db = helper.getWritableDatabase();

        String query = "DELETE FROM " + DATABASE_TABLE_REZISER +
                " WHERE " + REZISER_ID + " NOT IN " +
                "(SELECT " + GRVEZA_REZISER + " FROM " + DATABASE_GRVEZA + ");";

        db.execSQL(query);

        query = "DELETE FROM " + DATABASE_TABLE_ZANR +
                " WHERE " + ZANR_ID + " NOT IN " +
                "(SELECT " + GZVEZA_ZANR + " FROM " + DATABASE_GZVEZA + ");";

        db.execSQL(query);

    }

    public static void deleteGlumac(ModelGlumac g) {

        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("PRAGMA foreign_keys=ON");

        String query = "DELETE FROM " + DATABASE_TABLE_GLUMAC + " WHERE " +
                GLUMAC_ID + " = " + Integer.toString(g.GetId()) + ";";

        db.execSQL(query);
        damageControl();

    }

    public BazaFunkcionalnosti(Context c) {

        helper = new BazaHelper(c);

    }

}
