package com.den.SearchApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadWebPage extends AsyncTask<String, String, String> {

    private TextView dataField;
    private Context context;
    private ProgressDialog mProgress;

    final static String apiKey = "AIzaSyAGxxLBeUY2z3wek75rncgChUMbl1VERXw";
    final static String customSearchEngineKey = "009991385833239908374:upp0zxbugva";
    final static String searchURL = "https://www.googleapis.com/customsearch/v1?";
    private static final String TAG = DownloadWebPage.class.getSimpleName();

    public DownloadWebPage(Context context, TextView dataField) {
        this.context = context;
        this.dataField = dataField;
    }


    //check Internet conenction.
    private void checkInternetConenction() {
        ConnectivityManager check = (ConnectivityManager) this.context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (check != null) {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Toast.makeText(context, "Internet is connected",
                                Toast.LENGTH_SHORT).show();
                    }

        } else {
            Toast.makeText(context, "not conencted to internet",
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPreExecute() {
        //checkInternetConenction();
        mProgress = new ProgressDialog(context);
        //mProgress.setTitle("Searching...");
        mProgress.setIndeterminate(false);
        mProgress.setMessage("Loading");
        mProgress.show();
    }

    private static String makeSearchString(String qSearch, int start, int numOfResults) {
        String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey + "&q=";

        //replace spaces in the search query with +
        String keys[] = qSearch.split("[ ]+");
        for (String key : keys) {
            toSearch += key + "+"; //append the keywords to the url
        }

        //specify response format as json
        toSearch += "&alt=json";

        //specify starting result number
        toSearch += "&start=" + start;

        //specify the number of results you need from the starting position
        toSearch += "&num=" + numOfResults;

        return toSearch;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String pUrl = makeSearchString(arg0[0], 1, 1);
        try {
            URL url = new URL(pUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            Log.d(TAG, buffer.toString());
            ArrayList<Item> it = request(buffer.toString());
            return buffer.toString();
        } catch (Exception e) {
            Log.d(TAG, "exception", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        this.dataField.setText(result);
        mProgress.dismiss();
    }

    private ArrayList<Item> request(String s) {
        ArrayList<Item> list = new ArrayList<Item>();
        //BufferedReader reader = new BufferedReader(new InputStreamReader());
        InputStream stream = new ByteArrayInputStream(s.getBytes());
        BufferedReader in = new BufferedReader(
                new InputStreamReader(stream));
        /*String inputLine;
        while ((inputLine = in.readLine()) != null) {
            jsonString = inputLine;
        }
        in.close();*/
        Gson gson = new GsonBuilder().create();

        //Get the root object for the response
        Example ex = gson.fromJson(in, Example.class);
        String kind = ex.getKind();
        String type = Example.Url.getType();
        String template = Example.Url.getTemplate();
        Log.d(TAG, "linka" + kind);
        Log.d(TAG, "type" + type);
        Log.d(TAG, "template" + template);
        return list;
    }
}
