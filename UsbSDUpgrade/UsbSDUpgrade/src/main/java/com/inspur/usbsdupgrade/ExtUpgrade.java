
package com.inspur.usbsdupgrade;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.io.File;

import com.inspur.usbsdupgrade.install.InstallPackage;

public class ExtUpgrade extends BroadcastReceiver {
	private final String TAG = ExtUpgrade.class.getSimpleName();
	private static File RECOVERY_DIR = new File("/cache/recovery");
	private static File COMMAND_FILE = new File(RECOVERY_DIR, "command");
	Context mContext = null;
	AlertDialog.Builder builder = null;
	private unzipOneFile mOneFile = null;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		mContext = arg0;
		mOneFile = new unzipOneFile();

		Log.d(TAG, "dddd onReceive   onReceive");
		String action = arg1.getAction();

		Log.d(TAG, "dddd onReceive   onReceive  action" + action);
		final String filePath = arg1.getData().getPath() + "/upgrade";
		Log.d(TAG, "dddd onReceive   onReceive  action" + action + "  filePath  " + filePath);
		if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					File dirs = new File(filePath);
					Log.d(TAG, "dddd onReceive   onReceive  dirs " + dirs.exists());
					if (!dirs.exists()) {
						return;
					}

					String[] fileNames = dirs.list();
					Log.d(TAG, "dddd onReceive   onReceive  fileNames   " + fileNames + "  dd   d fileNames.length"
							+ fileNames.length);
					if (fileNames == null) {
						return;
					}

					String mFilename = null;

					for (int i = 0; i < fileNames.length; i++) {

						File file = new File(filePath + "/" + fileNames[i]);
						Log.d(TAG,
								"dddd onReceive   onReceive  file.isFile()   " + file.isFile()
										+ "  mOneFile.isGoodOta(filePath  + fileNames[i])"
										+ mOneFile.isGoodOta(filePath + "/" + fileNames[i]));
						
						if (file.isFile() && mOneFile.isGoodOta(filePath + "/" + fileNames[i])) {
							MyApp.UPDATE_FILE_PATH = filePath + "/" + file.getName();
							Intent intent = new Intent();
							intent.setClass(mContext, UpgradeDialogActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
							mContext.startActivity(intent);
							Log.d(TAG, " dddd onReceive  MyApp.UPDATE_FILE_PATH    " + MyApp.UPDATE_FILE_PATH);
							break;
						}
					}
				}
			}).start();
		}
	}
}
