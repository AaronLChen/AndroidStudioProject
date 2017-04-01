package com.inspur.usbsdupgrade;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import android.os.SystemProperties;
import android.util.Log;

public class unzipOneFile {
	public static final int BUFFER = 2048;
	private String mPostVersion = null;
	private String mPreVersion = null;
	private static String TAG = unzipOneFile.class.getSimpleName();

	public unzipOneFile() {
	}

	// 判断是否是良好的升级包
	public Boolean isGoodOta(String zipFileName) {
		System.out.println("####EC: isGoodOta in");
		String[] fileCont = zipFileName.split("\\.");

		Log.d(TAG, "isGoodOta() zipFileName = " + zipFileName + ",fileCont = " + fileCont);
		if (!fileCont[fileCont.length - 1].equals("zip")) {
			System.out.println("####EC: file no end with zip");
			return false;
		}
		try {
			if (!unzipFilesToPath(zipFileName))
				return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!versionCheck())
			return false;

		return true;
	}

	// 提取metadata数据 读取一行数据并解析
	public Boolean unzipFilesToPath(final String zipFileName) throws FileNotFoundException, IOException {
		System.out.println("####EC: unzipFilesToPath in");
		final FileInputStream fis = new FileInputStream(zipFileName);
		final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		try {
			ZipEntry entry;

			while ((entry = zis.getNextEntry()) != null) {
				int count;
				byte[] data = new byte[BUFFER];
				if (entry.getName().equals("META-INF/com/android/metadata")) {
					System.out.println("####EC: yes find the file");
//					File myFile = new File(Environment.getExternalStorageDirectory(), "metadata");
					File myFile = new File("/data/local", "metadata");
					if (!myFile.exists()) {
						myFile.createNewFile();
					}
					final FileOutputStream fos = new FileOutputStream(myFile);
					final BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
					return true;
				}
			}
			return false;
		} finally {
			fis.close();
			zis.close();
		}
	}

	// 版本判断 如果想修改成 不同版本升级只要修改这里即可
	public Boolean versionCheck() {
		System.out.println("####EC: versionCheck in");
		String osVersion = SystemProperties.get("ro.build.version.incremental");
		readMetaInfo();
		System.out.println("####EC: mPreVersion = " + mPreVersion);
		System.out.println("####EC: mPostVersion = " + mPostVersion);
		System.out.println("####EC: osVersion = " + osVersion);
		// android.os.Build.ID;
		if (mPreVersion == null) {
			if (mPostVersion.compareToIgnoreCase(osVersion) <= 0)
				return false;
		} else {

			if (mPostVersion.equals(osVersion))
				return false;
		}
		return true;
	}

	// 读取metadata数据 7200平台和8100平台不同 就是读取版本号位置不同 可以让平台组去修改
	private String readMetaInfo() {
		System.out.println("####EC: readMetaInfo in");
//		File file = new File(Environment.getExternalStorageDirectory(), "metadata");
		File file = new File("/data/local", "metadata");
		if (!file.exists()) {
			return null;
		}
		System.out.println("####EC: readMetaInfo in 2");
		String metaInfo = new String();
		FileReader in;
		try {
			System.out.println("###EC: get the file content");
			in = new FileReader(file);
			BufferedReader bufferedreader = new BufferedReader(in);

			String[] oneLine = null;

			while ((metaInfo = bufferedreader.readLine()) != null) {
				System.out.println("####EC: metaInfo " + metaInfo);
				oneLine = metaInfo.split("=");
				if (oneLine[0].equals("post-build")) {
					String post = oneLine[1].split("/")[4];
					if (post != null) {
						String[] strarray = post.split(":");
						mPostVersion = strarray[0];
					}
				} else if (oneLine[0].equals("pre-build")) {
					String per = oneLine[1].split("/")[4];
					if (per != null) {
						String[] strarray = per.split(":");
						mPreVersion = strarray[0];
					}

				}

			}
			bufferedreader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return metaInfo;
	}
}
