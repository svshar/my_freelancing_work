package com.xudu.android.kbs.ui;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.VideoView;

import com.xudu.android.kbs.Commons;
import com.xudu.android.kbs.Constants;
import com.xudu.android.kbs.R;

/**
 * 
 * @author admin
 * 
 */
public class WelcomeActivity extends Activity implements OnCompletionListener,
		OnPreparedListener, OnClickListener {

	private static final String TAG = "tag";
	private VideoView videoPlayer;
	private LayoutInflater inflator;
	private String path;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_welcome);

		File videofile = new File(Constants.paths.Main_Intro);

		if (videofile.exists()) {
			if (videofile.isDirectory()) {
				File[] files = videofile.listFiles();

				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (!file.isHidden())
						path = file.getAbsolutePath();
				}

			}

		} else {

			Commons.showToastAlert(getApplicationContext(),
					getString(R.string.video_not_found), false);
			Log.d(TAG, "Missing : " + Constants.paths.Main_Intro);
			finish();
		}
		if (TextUtils.isEmpty(path)) {
			Commons.showToastAlert(getApplicationContext(),
					getString(R.string.video_not_found), false);
			Log.d(TAG, "Missing : " + Constants.paths.Main_Intro);
			finish();
		}else{
		inflator = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View controlView = inflator.inflate(R.layout.controls_welcome, null);
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		this.addContentView(controlView, param);

		videoPlayer = (VideoView) findViewById(R.id.videoPlayer);
		videoPlayer.setOnPreparedListener(this);
		videoPlayer.setOnCompletionListener(this);
		videoPlayer.setKeepScreenOn(true);
		videoPlayer.setVideoPath(path);
		}
	}

	/** This callback will be invoked when the file is ready to play */
	@Override
	public void onPrepared(MediaPlayer vp) {

		vp.setLooping(true);
		// if (videoPlayer.canSeekForward())
		// videoPlayer.seekTo(videoPlayer.getDuration() / 5);
		videoPlayer.start();
	}

	/** This callback will be invoked when the file is finished playing */
	@Override
	public void onCompletion(MediaPlayer mp) {
		this.finish();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		int id = v.getId();
		switch (id) {
		case R.id.buttonCartoon:
			intent = new Intent();
			intent.setClass(WelcomeActivity.this, AnimeSegmentActivity.class);
			startActivity(intent);
			break;
		case R.id.buttonFlashCards:
			intent = new Intent();
			intent.setClass(WelcomeActivity.this, FlashCardActivity.class);
			startActivity(intent);
			break;

		case R.id.buttonMainMoview:
			Constants.currentMovie = 0;
			intent = new Intent();
			intent.setClass(WelcomeActivity.this, IntroMainMovieActivity.class);
			startActivity(intent);
			break;

		case R.id.buttonSongs:
			intent = new Intent();
			intent.setClass(WelcomeActivity.this, SongsSegmentActivity.class);
			startActivity(intent);
			break;

		case R.id.buttonVideo:
			intent = new Intent();
			intent.setClass(WelcomeActivity.this, VideoSegmentActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}
}