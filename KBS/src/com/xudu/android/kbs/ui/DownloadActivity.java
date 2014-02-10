package com.xudu.android.kbs.ui;

import java.io.File;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xudu.android.kbs.PreferencesManager;
import com.xudu.android.kbs.R;
import com.xudu.android.kbs.services.SyncService;

public class DownloadActivity extends Activity {

	static Vector<String> vector = new Vector<String>();
	private int progress = 0;
	private ProgressBar mProgressBar;
	private TextView textViewProgressPercent;
	private Thread background;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		textViewProgressPercent = (TextView) findViewById(R.id.textViewProgressPercent);
		if (!PreferencesManager.getDownloadStatus(getBaseContext())) {
			if (!isServiceRunning())
				showDialog();
			else
				Toast.makeText(getBaseContext(), "Downloading in progress",
						Toast.LENGTH_SHORT).show();
		} else {
			startActivity(new Intent().setClass(DownloadActivity.this,
					WelcomeActivity.class));
			finish();
		}

	}

	@Override
	protected void onResume() {
		updateProgressBar();
		super.onResume();
	}

	private void updateProgressBar() {
		Runnable runnable = new updateProgress();
		background = new Thread(runnable);
		background.start();
	}

	public class updateProgress implements Runnable {
		public void run() {
			while (Thread.currentThread() == background)
				// while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					progress = SyncService.progress;
					msg.what = progress;
					handler.sendMessage(msg);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
				}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressBar.setProgress(msg.what);
			textViewProgressPercent.setText(progress + "%");
			//
		}
	};

	// Don't forget to Destroy the thread when ur activity is not visible.

	private void destroyRunningThreads() {
		if (background != null) {
			background.interrupt();
			background = null;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (!PreferencesManager.getDownloadStatus(getBaseContext())) {
			if (!isServiceRunning())
				showDialog();
			else
				Toast.makeText(getBaseContext(), "Downloading in progress",
						Toast.LENGTH_SHORT).show();
		} else {
			startActivity(new Intent().setClass(DownloadActivity.this,
					WelcomeActivity.class));
			finish();
		}

		super.onNewIntent(intent);
	}

	public boolean isServiceRunning() {
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		String serviceClassName = "com.xudu.android.kbs.services.SyncService";
		for (RunningServiceInfo runningServiceInfo : services) {
			if (runningServiceInfo.service.getClassName().equals(
					serviceClassName)) {
				return true;
			}
		}
		return false;
	}

	void showDialog() {
		new AlertDialog.Builder(DownloadActivity.this)
				.setTitle("Download Videos")
				.setMessage(
						"Please BEE patient! This application requires videos to be downloaded and may take several minutes.")
				.setPositiveButton("Download", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// new AsyncDownload().execute();
						// Thread downloadThread = new Thread(new Runnable() {

						// @Override
						// public void run() {
						// progress.setV
						// startDownload();
						startDownloadService();

						// }

						// });
						// downloadThread.start();
					}
				}).setCancelable(false).show();
	}

	// private class DownloadReceiver extends ResultReceiver {
	// public DownloadReceiver(Handler handler) {
	// super(handler);
	// }
	//
	// @Override
	// protected void onReceiveResult(int resultCode, Bundle resultData) {
	// super.onReceiveResult(resultCode, resultData);
	// if (resultCode == SyncService.UPDATE_PROGRESS) {
	// int progress = resultData.getInt("progress");
	// mProgressBar.setProgress(progress);
	// textViewProgressPercent.setText(progress + "%");
	// // if (progress == 100) {
	// // mProgressDialog.dismiss();
	// // }
	// }
	// }
	// }

	private void startDownloadService() {
		Intent intent = new Intent(DownloadActivity.this, SyncService.class);

		// String path =
		// Environment.getExternalStorageDirectory()+"/IFL_RFP_SPA.pdf";
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/KBS/";
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		intent.putExtra("BasePath", path);

		String[] arrDownloadPaths = new String[1];
		arrDownloadPaths[0] = "/";

		intent.putExtra("File_Download", arrDownloadPaths);
		// intent.putExtra("receiver", new DownloadReceiver(new Handler()));

		startService(intent);

	}

}
