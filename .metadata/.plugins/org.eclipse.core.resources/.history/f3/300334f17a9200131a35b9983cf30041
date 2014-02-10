package com.xudu.android.kbs.ui;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.VideoView;

import com.xudu.android.kbs.Commons;
import com.xudu.android.kbs.Constants;
import com.xudu.android.kbs.R;

/**
 * 
 * @author admin
 * 
 */
public class AnimeSegmentActivity extends Activity implements
		OnCompletionListener, OnPreparedListener, OnClickListener,
		OnErrorListener {

	private static final String TAG = "tag";
	private VideoView videoPlayer;
	private File videofile;
	private String path;

	private LayoutInflater inflator;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.anima_segment);

		videofile = new File(Constants.paths.anime_segment_main_screen);
		if (!videofile.exists()) {
			Commons.showToastAlert(
					getApplicationContext(),
					getString(R.string.video_not_found)
							+ videofile.getAbsolutePath(), false);
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
		inflator = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View controlView = inflator.inflate(R.layout.controls_anime_segment,
				null);

		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.addContentView(controlView, param);

		videoPlayer = (VideoView) findViewById(R.id.videoPlayerAnimeSegment);
		videoPlayer.setOnPreparedListener(this);
		videoPlayer.setOnCompletionListener(this);
		videoPlayer.setOnErrorListener(this);
		videoPlayer.setKeepScreenOn(true);
		videoPlayer.setVideoPath(path);
		// Commons.showToastAlert(getApplicationContext(), path, false);
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
		// Statements to be executed when the video finishes.
		this.finish();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.buttonAnimeBear:
			String Main_moview_Intro = Constants.KBS_ROOT
					+ Constants.paths.Main_Movie[0] + Constants.paths.intro;

			intent = new Intent();
			intent.setClass(AnimeSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Main_moview_Intro);
			startActivity(intent);
			break;
		case R.id.buttonAnimeCow:
			Main_moview_Intro = Constants.KBS_ROOT
					+ Constants.paths.Main_Movie[4] + Constants.paths.intro;

			intent = new Intent();
			intent.setClass(AnimeSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Main_moview_Intro);
			startActivity(intent);
			break;

		case R.id.buttonAnimeGoat:

			Main_moview_Intro = Constants.KBS_ROOT
					+ Constants.paths.Main_Movie[8] + Constants.paths.intro;

			intent = new Intent();
			intent.setClass(AnimeSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Main_moview_Intro);
			startActivity(intent);
			break;

		case R.id.buttonAnimePig:
			Main_moview_Intro = Constants.KBS_ROOT
					+ Constants.paths.Main_Movie[5] + Constants.paths.intro;

			intent = new Intent();
			intent.setClass(AnimeSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Main_moview_Intro);
			startActivity(intent);
			break;

		case R.id.buttonAnimeTiger:
			Main_moview_Intro = Constants.KBS_ROOT
					+ Constants.paths.Main_Movie[1] + Constants.paths.intro;

			intent = new Intent();
			intent.setClass(AnimeSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Main_moview_Intro);
			startActivity(intent);
			break;
		case R.id.buttonAnimeMainMenu:
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Commons.showToastAlert(getBaseContext(), what + " " + extra, false);// TODO
																			// Auto-generated
																			// method
																			// stub
		return false;
	}
}