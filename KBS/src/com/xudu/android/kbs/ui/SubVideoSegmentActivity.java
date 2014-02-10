package com.xudu.android.kbs.ui;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.VideoView;

import com.xudu.android.kbs.Commons;
import com.xudu.android.kbs.R;

/**
 * 
 * @author admin
 * 
 */
public class SubVideoSegmentActivity extends Activity implements
		OnCompletionListener, OnPreparedListener {

	private static final String TAG = "tag";
	private VideoView videoPlayer;
	private File videofile;
	private String path;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.videosegment);

		String subPath = getIntent().getExtras().getString("path");
		if (TextUtils.isEmpty(subPath)) {
			finish();
			return;
		}

		videofile = new File(subPath);
		if (!videofile.exists()) {
			Commons.showToastAlert(getApplicationContext(),
					getString(R.string.video_not_found), false);
			Log.d(TAG, "Missing : " + videofile.getAbsolutePath());
			finish();
		} else {
			if (videofile.isDirectory()) {
				File[] files = videofile.listFiles();

				if (files != null && files.length > 0) {
					for (int i = 0; i < files.length; i++) {
						File file = files[i];
						if (!file.isHidden())
							path = file.getAbsolutePath();
					}

				} else {
					finish();
				}
			}
		}

		videoPlayer = (VideoView) findViewById(R.id.videoPlayerVideoSegment);
		videoPlayer.setOnPreparedListener(this);
		videoPlayer.setOnCompletionListener(this);
		videoPlayer.setKeepScreenOn(true);
		videoPlayer.setVideoPath(path);
	}

	/** This callback will be invoked when the file is ready to play */
	@Override
	public void onPrepared(MediaPlayer vp) {

	//	if (videoPlayer.canSeekForward())
		//	videoPlayer.seekTo(videoPlayer.getDuration() / 5);
		videoPlayer.start();
	}

	/** This callback will be invoked when the file is finished playing */
	@Override
	public void onCompletion(MediaPlayer mp) {
		this.finish();
	}

}