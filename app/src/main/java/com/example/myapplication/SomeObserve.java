package com.example.myapplication;

import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 007 on 2015/10/31.
 */
public class SomeObserve extends ContentObserver {
    private Context mContext;
    private Handler mHandler;
    public SomeObserve(Context context,Handler handler){
        super(handler);
       mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange,Uri uri) {
        super.onChange(selfChange, uri);

        Log.e("debug", "SMS has changed");
        Log.e("debug",uri.toString());
        String code = "";
        if (uri.toString().equals("content://dms/raw")){
            return;
        }
        Uri inbox = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(inbox,null,null,null,"data desc");
        if (c != null){
            if (c.moveToFirst()){
                String adress = c.getString(c.getColumnIndex("adress"));
                String body = c.getString(c.getColumnIndex("body"));
                
                Log.e("debug", "地址为" + adress + "内容为" + body);
                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()){
                    code = matcher.group(0);
                    mHandler.obtainMessage(MainActivity.MSG_RCEIVER_CODE,code).sendToTarget();

                }
            }
            c.close();
        }
    }
}
