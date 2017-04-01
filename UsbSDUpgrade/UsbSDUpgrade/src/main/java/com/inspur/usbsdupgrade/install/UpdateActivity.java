
package com.inspur.usbsdupgrade.install;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.inspur.usbsdupgrade.MyApp;
import com.inspur.usbsdupgrade.R;

public class UpdateActivity extends Activity {
	private static String TAG = UpdateActivity.class.getSimpleName();
	private static Dialog dlg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_REMOVED);
		filter.addDataScheme("file");

		registerReceiver(myReceiver, filter);
		String file = MyApp.UPDATE_FILE_PATH;
		if (file != null) {
			dlg = new Dialog(this, android.R.style.Theme_Holo_Light_Dialog);
			dlg.setTitle(R.string.confirm_update);
			LayoutInflater inflater = LayoutInflater.from(this);
			InstallPackage dlgView = (InstallPackage) inflater.inflate(R.layout.install_ota, null, false);
			dlgView.setPackagePath(file);
			dlg.setContentView(dlgView);
			dlg.show();
			((Button) dlg.findViewById(R.id.confirm_update)).setVisibility(View.INVISIBLE);
			((Button) dlg.findViewById(R.id.confirm_cancel)).setVisibility(View.INVISIBLE);
			dlgView.upgradeFromOta();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "onReceive() action = " + action);
			if (action.equals(Intent.ACTION_MEDIA_REMOVED)) {
				if (dlg != null) {
					dlg.dismiss();
				}
				finish();
			}
		}

	};
}
