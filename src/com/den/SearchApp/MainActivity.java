package com.den.SearchApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText urlField;
    private TextView data;
    private ListView requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        urlField = (EditText) findViewById(R.id.editText1);
        requestList = (ListView)findViewById(R.id.request_list);
        //data = (TextView) findViewById(R.id.textView2);
    }

    public void download(View view) {

        String url = urlField.getText().toString();
        new DownloadWebPage(this, data, requestList).execute(url);
    }
}
