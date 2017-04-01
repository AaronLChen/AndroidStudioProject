package com.aaron.broadcasttext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;

    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
//
//    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Button button = (Button) findViewById(R.id.button);
        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);
        intentFilter.addAction("com.aaron.broadcasttext.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.aaron.broadcasttext.LOCAL_BROADCAST");
//                sendBroadcast(intent);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(localReceiver);
    }

    //    class NetworkChangeReceiver extends BroadcastReceiver {
//        public void onReceive(Context context, Intent intent) {
////            Toast.makeText(context, "network changes", Toast.LENGTH_SHORT).show();
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
//                    Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//
//            if (networkInfo != null && networkInfo.isAvailable()) {
//                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
    class LocalReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "recevied local brodcast", Toast.LENGTH_SHORT).show();
        }
    }
}
