package com.den.SearchApp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;

/**
 * UI Activity to interact with a user
 *
 * Created by Denis Lobur
 */
public class MainActivity extends FragmentActivity implements RequestHolderFragment.IEmptyListReturned {

    private EditText urlField;
    private RequestHolderFragment requestList;
    private NoDataFragment noDataFragment;
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        urlField = (EditText) findViewById(R.id.search_label);
        requestList = new RequestHolderFragment(this);
        noDataFragment = new NoDataFragment();
        fm.beginTransaction().add(R.id.container_request, noDataFragment).commit();
    }

    public void download(View view) {
        String url = urlField.getText().toString();
        requestList.setUrl(url);
        requestList.startRequest();
        fm.beginTransaction().replace(R.id.container_request, requestList).commit();
    }

    //If no data found this call back will be fired, and list set to "No Data" option

    @Override
    public void emptyListReturned() {
        fm.beginTransaction().add(R.id.container_request, noDataFragment).commit();
    }
}
