package edu.weber.rhilton.cs3270.sqlitetest;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertFragment extends Fragment {

    private View rootView;
    private EditText edtTitle, edtArtist, edtGenre;

    public InsertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_insert, container, false);
        edtTitle = (EditText) rootView.findViewById(R.id.edtTitle);
        edtArtist = (EditText) rootView.findViewById(R.id.edtArtist);
        edtGenre = (EditText) rootView.findViewById(R.id.edtGenre);

        Button btnInsertSong = (Button) rootView.findViewById(R.id.btnInsertSong);
        btnInsertSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "Save clicked");
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"Music",null,1);
                long rowID = dbHelper.insertSong(
                        edtTitle.getText().toString(),
                        edtArtist.getText().toString(),
                        edtGenre.getText().toString());
                MainActivity ma = (MainActivity) getActivity();
                ma.reloadBottomFragment();
            }
        });

        return rootView;
    }

    public void populateSong(long id) {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"Music", null, 1);
        Cursor cursor = dbHelper.getOneSong(id);
        cursor.moveToFirst();

        String title = cursor.getString(cursor.getColumnIndex("title"));
        String artist = cursor.getString(cursor.getColumnIndex("artist"));
        String genre = cursor.getString(cursor.getColumnIndex("genre"));

        edtTitle.setText(title);
        edtArtist.setText(artist);
        edtGenre.setText(genre);

    }

}
