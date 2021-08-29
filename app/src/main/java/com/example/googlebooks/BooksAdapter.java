package com.example.googlebooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BooksAdapter extends ArrayAdapter<Books> {

    public BooksAdapter(Context context,List<Books> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listview = convertView;
        if(listview ==null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }
        Books booksobject = getItem(position);
        ImageView thumbnail= listview.findViewById(R.id.smallthumbnail);
        Picasso.get().load(booksobject.getThumbnail()).into(thumbnail);


        TextView bookname = listview.findViewById(R.id.booktitle);
        bookname.setText(booksobject.getBookname());

        TextView authorname = listview.findViewById(R.id.bookauthor);
        authorname.setText(booksobject.getAuthorname());

        TextView booklanguage = listview.findViewById(R.id.booklanguage);
        booklanguage.setText(booksobject.getBookLanguage());

        return listview;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
