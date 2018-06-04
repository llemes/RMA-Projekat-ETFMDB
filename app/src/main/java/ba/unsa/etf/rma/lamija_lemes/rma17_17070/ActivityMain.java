package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.facebook.stetho.Stetho;

import java.util.Locale;

public class ActivityMain extends AppCompatActivity {

    public static FragmentListaGlumaca fragmentGlumciStatic;
    public static FragmentListaFilmova fragmentFilmoviStatic;

    public static int pozicija = 0;
    public static int pozicijaFilm = 0;
    public static Boolean veliki;
    private static String jezik = Locale.getDefault().toString();
    private String BOSANSKI = "bs";
    private String ENGLESKI = "en";

    private static final int PERMISSION_CALENDAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.READ_CALENDAR}, PERMISSION_CALENDAR);

        }

        // za prikazivanje sadržaja baze u chrome://inspect
        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.activity_main);
        new BazaFunkcionalnosti(this);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float dpWidth  = outMetrics.widthPixels / getResources().getDisplayMetrics().density;

        final FragmentListaGlumaca fragmentGlumci = fragmentGlumciStatic = new FragmentListaGlumaca() ;
        final FragmentListaRezisera fragmentReziseri = new FragmentListaRezisera();
        final FragmentListaZanrova fragmentZanrovi = new FragmentListaZanrova();
        final FragmentDetaljiGlumca fragmentGlumac = new FragmentDetaljiGlumca();
        final FragmentListaFilmova fragmentListaFilmova = fragmentFilmoviStatic = new FragmentListaFilmova();
        final FragmentDetaljiFilma fragmentDetaljiFilma = new FragmentDetaljiFilma();

        jezik = Locale.getDefault().toString();
        if(jezik.contains("en")) {
            jezik = ENGLESKI;
        }

        if(dpWidth < 500) {

            veliki = false;

            if(jezik.equals(ENGLESKI)) {
                findViewById(R.id.buttonJezik).setBackgroundResource(R.drawable.en);
            }

            else {
                findViewById(R.id.buttonJezik).setBackgroundResource(R.drawable.bs);
            }

            if(findViewById(R.id.aktivniFragment) != null && getSupportFragmentManager().findFragmentById(R.id.aktivniFragment) == null) {

                getFragmentManager().beginTransaction().add(R.id.aktivniFragment, fragmentGlumci).addToBackStack("").commit();

            }

            findViewById(R.id.buttonReziseri).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.aktivniFragment, fragmentReziseri).addToBackStack("").commit();
                }

            });

            findViewById(R.id.buttonZanrovi).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.aktivniFragment, fragmentZanrovi).addToBackStack("").commit();
                }

            });

            findViewById(R.id.buttonGlumci).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.aktivniFragment, fragmentGlumci).addToBackStack("").commit();
                }

            });

            findViewById(R.id.buttonFilmovi).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.aktivniFragment, fragmentListaFilmova).addToBackStack("").commit();
                }
            });

            findViewById(R.id.buttonJezik).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String langToLoad;

                    if(jezik.equals(ENGLESKI)) {
                        langToLoad = jezik = BOSANSKI;
                    }

                    else {
                        langToLoad = jezik = ENGLESKI;
                    }

                    Locale locale = new Locale(langToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
            });

        }

        else {

            veliki = true;

            if(jezik.equals(BOSANSKI)) {
                findViewById(R.id.buttonJezik500).setBackgroundResource(R.drawable.bs);
            }

            else {
                findViewById(R.id.buttonJezik500).setBackgroundResource(R.drawable.en);
            }

            if(findViewById(R.id.lijevi) != null && getFragmentManager().findFragmentById(R.id.lijevi) == null) {

                getFragmentManager().beginTransaction().add(R.id.lijevi, fragmentGlumci).addToBackStack("").commit();

            }

            if(findViewById(R.id.desni) != null && getSupportFragmentManager().findFragmentById(R.id.desni) == null) {

                getFragmentManager().beginTransaction().add(R.id.desni, fragmentGlumac).addToBackStack("").commit();

            }

            findViewById(R.id.buttonJezik500).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String langToLoad;

                    if(jezik.equals(ENGLESKI)) {
                        langToLoad = jezik = BOSANSKI;
                    }

                    else {
                        langToLoad = jezik = ENGLESKI;
                    }

                    Locale locale = new Locale(langToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
            });

            findViewById(R.id.buttonOstalo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.lijevi, fragmentZanrovi).addToBackStack("").commit();
                    getFragmentManager().beginTransaction().replace(R.id.desni, fragmentReziseri).addToBackStack("").commit();
                }
            });

            findViewById(R.id.buttonGlumci500).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.lijevi, fragmentGlumci).addToBackStack("").commit();
                    getFragmentManager().beginTransaction().replace(R.id.desni, fragmentGlumac).addToBackStack("").commit();
                }
            });

            findViewById(R.id.buttonFilmovi500).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.lijevi, fragmentListaFilmova).addToBackStack("").commit();
                    getFragmentManager().beginTransaction().replace(R.id.desni, fragmentDetaljiFilma).addToBackStack("").commit();
                }
            });

        }

    }
}
