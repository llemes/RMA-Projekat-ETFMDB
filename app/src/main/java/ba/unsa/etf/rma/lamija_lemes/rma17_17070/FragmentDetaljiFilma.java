package ba.unsa.etf.rma.lamija_lemes.rma17_17070;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static ba.unsa.etf.rma.lamija_lemes.rma17_17070.ActivityMain.pozicijaFilm;

/**
 * Created by lamija on 6/11/17.
 */

public class FragmentDetaljiFilma extends Fragment {

    private static final int PERMISSION_CALENDAR = 1;

    ModelFilm f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_detalji_filma, container, false);

        if(ModelFilmAdapter.filmovi.size() == 0)
            return view;

        f = ModelFilmAdapter.filmovi.get(pozicijaFilm);

        ImageView slika = (ImageView) view.findViewById(R.id.slikaFilmDetalji);
        Picasso.with(getActivity()).load(f.GetSlikaLink()).into(slika);

        TextView naziv = (TextView) view.findViewById(R.id.nazivFilmDetalji);
        naziv.setText(f.GetNaziv());

        TextView opis = (TextView) view.findViewById(R.id.filmOpis);
        opis.setText(f.GetOpis());

        final EditText sadrzaj = (EditText) view.findViewById(R.id.detalji);
        final DatePicker datum = (DatePicker) view.findViewById(R.id.datum);

        Button save = (Button) view.findViewById(R.id.buttonZapamti);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.READ_CALENDAR}, PERMISSION_CALENDAR);

                }

                try {

                    ContentResolver cr = getActivity().getContentResolver();
                    ContentValues vals = new ContentValues();
/*

                    provjera postojećih kalendara (for debugging)

                    Cursor c = cr.query(CalendarContract.Calendars.CONTENT_URI, new String[] { CalendarContract.CalendarEntity._ID, CalendarContract.CalendarEntity.CALENDAR_DISPLAY_NAME }, null, null, null);

                    while (c.moveToNext()) {
                        String calName = c.getString(c.getColumnIndex(CalendarContract.CalendarEntity.CALENDAR_DISPLAY_NAME));
                        int calId = c.getInt(c.getColumnIndex(CalendarContract.CalendarEntity._ID));
                        Toast.makeText(getActivity(), calName + " " + calId, Toast.LENGTH_SHORT).show();
                    }
*/

                    // defaultni kalendar ima ID 1
                    long calID = 1;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, datum.getDayOfMonth());
                    calendar.set(Calendar.MONTH, datum.getMonth());
                    calendar.set(Calendar.YEAR, datum.getYear());
                    vals.put(CalendarContract.Events.DTSTART, calendar.getTimeInMillis());

                    vals.put(CalendarContract.Events.CALENDAR_ID, calID);
                    vals.put(CalendarContract.Events.ALL_DAY, 1);

                    String napomena = sadrzaj.getText().toString();
                    vals.put(CalendarContract.Events.DESCRIPTION, napomena);

                    vals.put(CalendarContract.Events.DURATION, 1000 * 60 * 60);
                    vals.put(CalendarContract.Events.TITLE, f.GetNaziv());
                    vals.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

                    Uri rez = cr.insert(CalendarContract.Events.CONTENT_URI, vals);

                    Toast.makeText(getActivity(), "Event added", Toast.LENGTH_SHORT).show();

                } catch (Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;

    }

}
