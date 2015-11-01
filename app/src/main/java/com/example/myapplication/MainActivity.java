package com.example.myapplication;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    private EditText ettext;
    public  static final int MSG_RCEIVER_CODE = 1;
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_RCEIVER_CODE){
                String code = (String) msg.obj;
                ettext.setText(code);
            }
        }
    };
    SomeObserve mobserver;
    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mobserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SomeObserve someObserve = new SomeObserve(MainActivity.this,mhandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri,true,someObserve);
        ettext = (EditText) findViewById(R.id.etText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
