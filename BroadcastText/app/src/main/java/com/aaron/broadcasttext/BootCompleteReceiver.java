package com.aaron.broadcasttext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by chenl on 3/16/2017.
 */

public class BootCompleteReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot complete!", Toast.LENGTH_SHORT).show();
    }
}
