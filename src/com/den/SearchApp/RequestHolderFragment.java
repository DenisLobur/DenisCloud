package com.den.SearchApp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fragment that does all the hard work of requesting and posting results to the list
 *
 * Created by Denis Lobur
 */
public class RequestHolderFragment extends ListFragment {

    private Context context;
    private WeakReference<AsyncDownload> asyncTaskWeakRef;
    private String receivedUrl;
    private IEmptyListReturned emptyListReturned;

    public RequestHolderFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            emptyListReturned = (IEmptyListReturned) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + "must implement IEmptyListReturned");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setUrl(String url) {
        receivedUrl = url;
    }

    public void startRequest() {
        startAsyncDownload();
    }

    private void startAsyncDownload() {
        AsyncDownload asyncTask = new AsyncDownload(this);
        this.asyncTaskWeakRef = new WeakReference<AsyncDownload>(asyncTask);
        asyncTask.execute(receivedUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class AsyncDownload extends AsyncTask<String, String, String> {

        private WeakReference<RequestHolderFragment> fragmentWeakRef;
        private JSONArray items = null;
        private static final String ITEMS = "items";
        private static final String LINK = "link";
        private static final String TITLE = "title";
        private static final String SNIPPED = "snippet";
        private final String TAG = AsyncDownload.class.getSimpleName();
        private ArrayList<HashMap<String, String>> requests = new ArrayList<HashMap<String, String>>();

        final static String apiKey = "AIzaSyAGxxLBeUY2z3wek75rncgChUMbl1VERXw";
        final static String customSearchEngineKey = "009991385833239908374:upp0zxbugva";
        final static String searchURL = "https://www.googleapis.com/customsearch/v1?";

        private AsyncDownload(RequestHolderFragment fragment) {
            this.fragmentWeakRef = new WeakReference<RequestHolderFragment>(fragment);
        }

        /**
         * Assemble the request into a string with Search API and Google Engine Keys
         * in order to follow Custom Search policy
         *
         * @param qSearch
         * @param start
         * @param numOfResults
         * @return
         */

        private String makeSearchString(String qSearch, int start, int numOfResults) {
            String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey + "&q=";

            //replace spaces in the search query with "+"
            String keys[] = qSearch.split("[ ]+");
            for (String key : keys) {
                toSearch += key + "+";
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
        protected String doInBackground(String... params) {
            String pUrl = makeSearchString(params[0], 1, 10);
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
                String jsonStr = buffer.toString();
                //*************************************************
                JSONObject jsonObj = new JSONObject(jsonStr);
                items = jsonObj.getJSONArray(ITEMS);
                for (int i = 0; i < items.length(); i++) {
                    JSONObject c = items.getJSONObject(i);
                    String link = c.getString(LINK);
                    String title = c.getString(TITLE);
                    String snipped = c.getString(SNIPPED);
                    HashMap<String, String> oneRequest = new HashMap<String, String>();
                    oneRequest.put(LINK, link);
                    oneRequest.put(TITLE, title);
                    oneRequest.put(SNIPPED, snipped);

                    requests.add(oneRequest);
                }
                //*************************************************
                return jsonStr;
            } catch (IOException e) {
                Log.d(TAG, "from http request: ", e);
            } catch (JSONException e) {
                Log.d(TAG, "from JSON parsing: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (this.fragmentWeakRef.get() != null) {
                ListAdapter adapter = new SimpleAdapter(
                        context, requests,
                        R.layout.list_item, new String[]{TITLE, LINK, SNIPPED}, new int[]{R.id.item_name,
                        R.id.item_url, R.id.item_description});

                setListAdapter(adapter);
            }
            if (requests.isEmpty()) {
                emptyListReturned.emptyListReturned();
            }
        }
    }

    public interface IEmptyListReturned {
        /**
         *  If no data found this callback will be fired and set "No Data" option
         */
        public void emptyListReturned();
    }
}
