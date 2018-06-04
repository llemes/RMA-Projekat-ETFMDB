package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lamija on 6/7/17.
 */

public class BazaHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bazaGlumci.db";
    public static final int DATABASE_VERSION = 12;

    // tabela glumci

    public static final String DATABASE_TABLE_GLUMAC = "glumci";

    public static final String GLUMAC_ID = "_id";
    public static final String GLUMAC_IME = "ime";
    public static final String GLUMAC_GODINA_RODJENJA = "godRodjenja";
    public static final String GLUMAC_MJESTO_RODJENJA = "mjestoRodjenja";
    public static final String GLUMAC_RATING = "rating";
    public static final String GLUMAC_GODINA_SMRTI = "godSmrti";
    public static final String GLUMAC_BIOGRAFIJA = "biografija";
    public static final String GLUMAC_SPOL = "spol";
    public static final String GLUMAC_LINK = "imdbLink";
    public static final String GLUMAC_SLIKA_LINK = "slikaLink";

    // tabela reziseri

    public static final String DATABASE_TABLE_REZISER = "reziseri";

    public static final String REZISER_ID = "_id";
    public static final String REZISER_IME = "ime";

    // tabela zanrovi

    public static final String DATABASE_TABLE_ZANR = "zanrovi";

    public static final String ZANR_ID = "_id";
    public static final String ZANR_IME = "ime";

    // tabele veze

    public static final String DATABASE_GZVEZA = "gzveza";

    public static final String GZVEZA_GLUMAC = "glumac";
    public static final String GZVEZA_ZANR = "zanr";

    public static final String DATABASE_GRVEZA = "grveza";

    public static final String GRVEZA_GLUMAC = "glumac";
    public static final String GRVEZA_REZISER = "reziser";

    private static final String CREATE_GLUMAC = "create table " + DATABASE_TABLE_GLUMAC + " (" +
            GLUMAC_ID + " integer unique, " +
            GLUMAC_IME + " text not null, " +
            GLUMAC_GODINA_RODJENJA + " text not null, " +
            GLUMAC_MJESTO_RODJENJA + " text not null, " +
            GLUMAC_RATING + " text not null, " +
            GLUMAC_GODINA_SMRTI + " text not null, " +
            GLUMAC_BIOGRAFIJA + " text not null, " +
            GLUMAC_SPOL + " text not null, " +
            GLUMAC_LINK + " text not null, " +
            GLUMAC_SLIKA_LINK + " text not null);";

    private static final String CREATE_REZISER = "create table " + DATABASE_TABLE_REZISER + " (" +
            REZISER_ID + " integer unique, " +
            REZISER_IME + " text not null);";

    private static final String CREATE_ZANR = "create table " + DATABASE_TABLE_ZANR + " (" +
            ZANR_ID + " integer unique, " +
            ZANR_IME + " text not null);";

    private static final String CREATE_GRVEZA = "create table " + DATABASE_GRVEZA + " (" +
            GRVEZA_GLUMAC + " integer references " + DATABASE_TABLE_GLUMAC + " (" + GLUMAC_ID + ") on delete cascade, " +
            GRVEZA_REZISER + " integer references " + DATABASE_TABLE_REZISER + " (" + REZISER_ID + ") on delete cascade, " +
            "unique( " + GRVEZA_GLUMAC + ", " + GRVEZA_REZISER + ") on conflict replace); ";

    private static final String CREATE_GZVEZA = "create table " + DATABASE_GZVEZA + " (" +
            GZVEZA_GLUMAC + " integer references " + DATABASE_TABLE_GLUMAC + "(" + GLUMAC_ID + ") on delete cascade, " +
            GZVEZA_ZANR + " integer references " + DATABASE_TABLE_ZANR + " (" + ZANR_ID + ") on delete cascade, " +
            "unique( " + GZVEZA_GLUMAC + ", " + GZVEZA_ZANR + ") on conflict replace);";

    public BazaHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_GLUMAC);
        db.execSQL(CREATE_REZISER);
        db.execSQL(CREATE_ZANR);
        db.execSQL(CREATE_GRVEZA);
        db.execSQL(CREATE_GZVEZA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_GZVEZA);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_GRVEZA);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_GLUMAC);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_REZISER);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ZANR);

        db.execSQL(CREATE_GLUMAC);
        db.execSQL(CREATE_REZISER);
        db.execSQL(CREATE_ZANR);
        db.execSQL(CREATE_GRVEZA);
        db.execSQL(CREATE_GZVEZA);

    }


}
