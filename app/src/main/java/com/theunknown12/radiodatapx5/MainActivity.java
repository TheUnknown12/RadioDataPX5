package com.theunknown12.radiodatapx5;

import android.app.Activity;
import android.microntek.CarManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity {
    Handler carManagerHandler;
    CarManager carManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carManagerHandler = new Handler() {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                if ("Radio".equals(message.obj)) {
                 handleData(message.getData());
                }
            }
        };
        carManager = new CarManager();
        carManager.attach(carManagerHandler,"Radio");
        setContentView(R.layout.activity_main);
    }

    public void handleData(Bundle data) {
        //uncomment below to log ALL bundles MCU sends for Radio
        //Log.d("radioDataBundle",bundle2string(data));

        String dataType = data.getString("type");
        if (null == dataType) { dataType = "";}
        if (dataType.equalsIgnoreCase("psn")) {
            final byte[] psnByteArray = data.getByteArray("value");
            if (null != psnByteArray) {
                try {
                    final String psnString = new String(psnByteArray, "iso8859-1").trim();
                    Log.d("radioDataPSN",psnString);
                    TextView hello = (TextView) findViewById(R.id.hello);
                    hello.setText(hello.getText() + "\r\n" + "PSN: " + psnString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        } else if (dataType.equalsIgnoreCase("rt")) {
            final byte[] psnByteArray = data.getByteArray("value");

            if (null != psnByteArray) {
                try {
                    final String psnString = new String(psnByteArray, "iso8859-1").trim();
                    Log.d("radioDataRT", psnString);
                    TextView hello = (TextView) findViewById(R.id.hello);
                    hello.setText(hello.getText() + "\r\n" + "RT: " + psnString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }
}
