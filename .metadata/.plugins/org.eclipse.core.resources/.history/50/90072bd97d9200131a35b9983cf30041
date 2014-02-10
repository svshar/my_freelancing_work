package com.xudu.android.kbs.services;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.xudu.android.kbs.FileTransferUtility;
import com.xudu.android.kbs.PreferencesManager;
import com.xudu.android.kbs.R;
import com.xudu.android.kbs.ui.DownloadActivity;

/**
 * 
 * @author SandeepS
 * 
 */
public class SyncService extends IntentService {

	public static final int UPDATE_PROGRESS = 100;

	private static Vector<String> vector = new Vector<String>();

	private final int mNotificationId = 1010;

	private FileTransferUtility fileTransferUtility;
	private String baseDirPath;
	private int maxAttemptCount = 3;
	private int attemptCount = 0;
	private int mFilestoDownload = 0;
	private int mFilesDownloaded = 0;
	public static int progress;

	private ResultReceiver receiver;

	public SyncService() {
		super("Sync Service");
	}

	public SyncService(String name) {
		super(name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {

		showNotification("");

		fileTransferUtility = new FileTransferUtility();

		baseDirPath = intent.getStringExtra("BasePath");

		String[] files_Download = intent.getStringArrayExtra("File_Download");

		receiver = (ResultReceiver) intent.getParcelableExtra("receiver");

		/*
		 * boolean flagConnect =
		 * fileTransferUtility.ftpConnect("59.144.124.103", "acceltree",
		 * "@at$$123", 21);
		 */

		downkoadVideos(files_Download);
		cancelNotification();

		// if (flagConnect) {
		//
		// if (files_Download != null)
		// try {
		// if (downloadFiles(files_Download))
		// PreferencesManager.setDownloadStatus(getBaseContext(),
		// true);
		// else {
		// fileTransferUtility.ftpDisconnect();
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// // Disconnect from the FTP server
		// fileTransferUtility.ftpDisconnect();
		//
		// cancelNotification();
		// }
	}

	void downkoadVideos(String[] files_Download) {
		boolean flagConnect = fileTransferUtility.ftpConnect("64.207.157.96",
				"ftpuser", "kidsbee@123", 21);
		if (flagConnect) {

			if (files_Download != null)
				try {
					if (downloadFiles(files_Download)) {
						PreferencesManager.setDownloadStatus(getBaseContext(),
								true);

						Intent intent = new Intent();
						intent.setClass(SyncService.this,
								DownloadActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						stopSelf();
					} else {
						if (maxAttemptCount > attemptCount) {
							attemptCount++;
							fileTransferUtility.ftpDisconnect();
							downloadFiles(files_Download);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			// Disconnect from the FTP server
			fileTransferUtility.ftpDisconnect();

		} else {
			// Handle if no internet connectivity
		}

	}

	/**
	 * 
	 * @param arrPath
	 * @throws IOException
	 */
	private boolean downloadFiles(String[] arrPath) throws IOException {
		boolean status = true;
		for (int i = 0; i < arrPath.length; i++) {

			if (!status)
				break;

			Vector<String> vectLocal = getLocalFileList(baseDirPath, "", 0);

			// Vector<String> vectLocal = getLocalFileList(baseDirPath
			// + arrPath[i]);
			// Vector<String> vectRemote = fileTransferUtility
			// .ftpPrintFilesList("/" + arrPath[i]);
			Vector<String> vectRemote = fileTransferUtility.listDirectory(
					arrPath[i], "", 0);
			mFilestoDownload = vectRemote.size();

			for (int j = 0; j < vectRemote.size(); j++) {

				if (!vectLocal.contains(vectRemote.elementAt(j))) {

					status = fileTransferUtility.ftpDownload(arrPath[i] + "/"
							+ vectRemote.elementAt(j), baseDirPath + arrPath[i]
							+ "/" + vectRemote.elementAt(j));
					if (!status) {
						break;

					}
					// fileTransferUtility.ftpDownload(arrPath[i]+"/"+vectRemote.elementAt(j),
					// baseDirPath+"/abc/"+vectRemote.elementAt(j));
				} else
					Log.e("KBS",
							"Already downloaded:" + vectRemote.elementAt(j));
				mFilesDownloaded++;
				progress = (int) (mFilesDownloaded * 100 / mFilestoDownload);
				// listener.onDownloadProgress(mFilestoDownload, progress);
				// publishing the progress....
				// Bundle resultData = new Bundle();
				// resultData.putInt("progress" ,);
				// receiver.send(UPDATE_PROGRESS, resultData);
			}
		}
		return status;
	}

	public Vector<String> getLocalFileList(String parentDir, String currentDir,
			int level) throws IOException {
		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}
		File f = new File(dirToList);
		File[] subFiles = f.listFiles();
		if (subFiles != null && subFiles.length > 0) {
			for (File aFile : subFiles) {
				String currentFileName = aFile.getName();
				if (currentFileName.equals(".") || currentFileName.equals("..")) {
					// skip parent directory and directory itself
					continue;
				}
				for (int i = 0; i < level; i++) {
					System.out.print("\t");
					// Log.e("kbs", "\t" + level);
				}
				if (aFile.isDirectory()) {
					// Log.e("kbs", "[" + currentFileName + "]");
					// System.out.println("[" + currentFileName + "]");
					getLocalFileList(dirToList, currentFileName, level + 1);
				} else {
					// System.out.println(currentFileName);
					Log.e("kbs", parentDir + "/" + currentDir + "/"
							+ currentFileName);
					String filename = parentDir + "/" + currentDir + "/"
							+ currentFileName;
					filename = filename.substring(filename.indexOf("KBS")
							+ ("KBS".length()));
					vector.add(filename);
				}
			}
		}
		return vector;
	}

	/**
	 * 
	 * @param aDirPath
	 * @return
	 */
	private Vector<String> getLocalFileList(String aDirPath) {

		Vector<String> vector = new Vector<String>();

		File f = new File(aDirPath);

		if (f.isDirectory()) {

			File[] files = f.listFiles();

			if (files != null) {

				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.isFile())
						vector.add(file.getName());
				}
			}
		} else {
			f.mkdirs();
		}

		return vector;
	}

	/**
	 * 
	 * @param authority
	 */
	private void showNotification(String authority) {
		Object service = getSystemService(NOTIFICATION_SERVICE);
		NotificationManager notificationManager = (NotificationManager) service;
		int icon = R.drawable.ic_launcher;
		String tickerText = null;
		long when = 0;

		Notification notification = new Notification(icon, tickerText, when);
		Context context = this;
		CharSequence contentTitle = "KBS"; // createNotificationTitle();
		CharSequence contentText = "Videos downloading started!"; // createNotificationText();
		PendingIntent contentIntent = createNotificationIntent();

		notification.when = System.currentTimeMillis();
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		notificationManager.notify(mNotificationId, notification);
	}

	private void cancelNotification() {
		Object service = getSystemService(NOTIFICATION_SERVICE);
		NotificationManager nm = (NotificationManager) service;
		nm.cancel(mNotificationId);
		// nm.cancel(mNotificationId);
	}

	/**
	 * Returns the notification {@link Intent}.
	 * <p>
	 * The default implementation returns an {@link Intent} that does nothing.
	 * 
	 * @see Notification#setLatestEventInfo(Context, CharSequence, CharSequence,
	 *      PendingIntent)
	 */
	protected PendingIntent createNotificationIntent() {
		Context context = this;
		int requestCode = 0;
		Intent intent = new Intent();
		int flags = 0;
		return PendingIntent.getBroadcast(context, requestCode, intent, flags);
	}

}
