package com.inspur.usbsdupgrade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.inspur.usbsdupgrade.install.UpdateActivity;

public class UpgradeDialogActivity extends Activity {
	private final String TAG = UpgradeDialogActivity.class.getSimpleName();
	private static AlertDialog dlg;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dlg = new AlertDialog.Builder(this).create();
		dlg.setCancelable(false);
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.upgrade_dialog_layout);

		ImageButton ok = (ImageButton) window.findViewById(R.id.dialog_button_ok);
		ok.requestFocus();

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_REMOVED);
		filter.addDataScheme("file");
		
		registerReceiver(myReceiver, filter);
		Log.d(TAG, "onCreate() registerReceiver ");

		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "start upgrade....");
				Intent intent = new Intent();
				intent.setClass(UpgradeDialogActivity.this, UpdateActivity.class);
				startActivity(intent);
				finish();
				dlg.dismiss();
			}
		});

		ImageButton cancel = (ImageButton) window.findViewById(R.id.dialog_button_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dlg.dismiss();
				finish();
			}
		});
	}

	public void dismiss() {
		Log.i(TAG, "dismiss()");
		if (dlg == null) {
			Log.i(TAG, "dismiss() dlg = null");
			return;
		}
		Log.d(TAG, "dismiss() dlg.isShowing() = " + dlg.isShowing());
		if (dlg.isShowing()) {
			dlg.dismiss();
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(myReceiver);
		super.onDestroy();
		Log.d(TAG, "finish upgrade....");
	}

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "onReceive() action = " + action);
			if (action.equals(Intent.ACTION_MEDIA_REMOVED)) {
				dismiss();
			}
		}

	};
}
