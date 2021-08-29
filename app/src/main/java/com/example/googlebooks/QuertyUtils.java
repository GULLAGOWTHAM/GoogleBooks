package com.example.googlebooks;


import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QuertyUtils {
    private static final String LOG_TAG = QuertyUtils.class.getSimpleName();

    private QuertyUtils() {

    }

    public static List<Books> FetchBooksData(String requestUrl) {
        URL url = createURL(requestUrl);

        String JsonRespnse = null;
        try {
            JsonRespnse = MakeHTTPRequest(url);
        } catch (IOException exception) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", exception);
        }
        List<Books> list = FetchVolumeInfo(JsonRespnse);
        return list;
    }

    public static URL createURL(String urls) {
        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static String MakeHTTPRequest(URL url) throws IOException {
        String JsonRespse = "";

        if (url == null) {
            return JsonRespse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                JsonRespse = readFromStram(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException exception) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", exception);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return JsonRespse;
    }

    public static String readFromStram(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    public static List<Books> FetchVolumeInfo(String Jsonrespnse) {
        if (TextUtils.isEmpty(Jsonrespnse)) {
            return null;
        }
        List<Books> list = new ArrayList<>();

        try {
            JSONObject jsoonBaseobject = new JSONObject(Jsonrespnse);
            JSONArray itemsarray = jsoonBaseobject.getJSONArray("items");
            for (int i = 0; i < itemsarray.length(); i++) {
                JSONObject individualobject = itemsarray.getJSONObject(i);
                JSONObject volumeobject = individualobject.getJSONObject("volumeInfo");
                String booktitle = volumeobject.getString("title");
                JSONArray authors = volumeobject.getJSONArray("authors");
                String firstauthor = authors.getString(0);
                JSONObject imagelinks = volumeobject.getJSONObject("imageLinks");
                String thumbnail = imagelinks.getString("thumbnail");
                String originalthumbnail =httpremoval(thumbnail);
                String language = volumeobject.getString("language");
                String infolinks = volumeobject.getString("infoLink");
                String bookurl =httpremoval(infolinks);

                Books books = new Books(booktitle, firstauthor, language, originalthumbnail, bookurl);
                list.add(books);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Book JSON results", e);
        }

        return list;
    }
    public static String httpremoval(String httpurl){
        String[] httpsplit = httpurl.split("//");
        String originallink ="https://"+httpsplit[1];
        return originallink;
    }
}
