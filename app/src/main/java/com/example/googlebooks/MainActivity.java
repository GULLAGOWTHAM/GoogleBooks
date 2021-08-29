package com.example.googlebooks;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static String GOOGLE_BOOKS_REQUEST = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final Integer LOADER_MANAGER_ID = 1;
    private BooksAdapter booksAdapter;
    private Boolean SearchClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.List_view);

        booksAdapter = new BooksAdapter(this, new ArrayList<Books>());

        listView.setAdapter(booksAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Books booksobj = booksAdapter.getItem(position);

                Uri bookuri = Uri.parse(booksobj.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookuri);

                startActivity(websiteIntent);
            }
        });


    }
    public void SeachBarCLicked(View view){
        String searchtext =null;
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        EditText editText = findViewById(R.id.search);
        searchtext = editText.getText().toString();
        String LINK =GOOGLE_BOOKS_REQUEST+"{"+searchtext+"}";
        task.execute(LINK);

    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Books>> {


        @Override
        protected List<Books> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Books> result = QuertyUtils.FetchBooksData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Books> data) {
            booksAdapter.clear();

            if (data != null && !data.isEmpty()) {
                booksAdapter.addAll(data);
            }
            TextView textView = findViewById(R.id.EmptyView);
            textView.setText("");
        }
    }
}
//
//    @Override
//    public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
//        Log.e(LOG_TAG,"Loader is created");
//        return new BooksLoader(this,GOOGLE_BOOKS_REQUEST);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<List<Books>> loader, List<Books> data) {
//        booksAdapter.clear();
//        if (data!= null || !data.isEmpty()) {
//            booksAdapter.addAll(data);
//        }
//        if (data == null || booksAdapter.isEmpty()) {
//            TextView textView = findViewById(R.id.EmptyView);
//            textView.setText("Earth Qakes Not Found");
//            Log.e(LOG_TAG, "Load has finished");
//        }
//        ProgressBar progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<List<Books>> loader) {
//        booksAdapter.clear();
//    }
//
