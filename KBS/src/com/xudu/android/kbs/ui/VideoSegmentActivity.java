package com.xudu.android.kbs.ui;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
public class VideoSegmentActivity extends Activity implements
		OnCompletionListener, OnPreparedListener, OnClickListener {

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

		setContentView(R.layout.videosegment);

		videofile = new File(Constants.paths.video_segment_main_screen);
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
		inflator = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View controlView = inflator.inflate(R.layout.controls_videosegment,
				null);
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		this.addContentView(controlView, param);

		videoPlayer = (VideoView) findViewById(R.id.videoPlayerVideoSegment);
		videoPlayer.setOnPreparedListener(this);
		videoPlayer.setOnCompletionListener(this);
		videoPlayer.setKeepScreenOn(true);
		videoPlayer.setVideoPath(path);
	}

	/** This callback will be invoked when the file is ready to play */
	@Override
	public void onPrepared(MediaPlayer vp) {

		vp.setLooping(true);
		//if (videoPlayer.canSeekForward())
		//	videoPlayer.seekTo(videoPlayer.getDuration() / 5);
		videoPlayer.start();
	}

	/** This callback will be invoked when the file is finished playing */
	@Override
	public void onCompletion(MediaPlayer mp) {
		this.finish();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.buttonVideoSegmentBear:
			intent = new Intent();
			intent.setClass(VideoSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Constants.paths.video_segment_bear);
			startActivity(intent);
			break;
		case R.id.buttonVideoSegmentBird:
			intent = new Intent();
			intent.setClass(VideoSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Constants.paths.video_segment_bird);
			startActivity(intent);
			break;

		case R.id.buttonVideoSegmentGiraffe:

			intent = new Intent();
			intent.setClass(VideoSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Constants.paths.video_segment_giraffe);
			startActivity(intent);
			break;

		case R.id.buttonVideoSegmentGoat:
			intent = new Intent();
			intent.setClass(VideoSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Constants.paths.video_segment_goat);
			startActivity(intent);
			break;

		case R.id.buttonVideoSegmentMainCow:
			intent = new Intent();
			intent.setClass(VideoSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Constants.paths.video_segment_cow);
			startActivity(intent);
			break;

		case R.id.buttonVideoSegmentMainMovie:
			finish();
			break;

		case R.id.buttonVideoSegmentTiger:
			intent = new Intent();
			intent.setClass(VideoSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Constants.paths.video_segment_tiger);
			startActivity(intent);
			break;

		case R.id.buttonVideoSegmentPig:
			intent = new Intent();
			intent.setClass(VideoSegmentActivity.this,
					SubVideoSegmentActivity.class);
			intent.putExtra("path", Constants.paths.video_segment_pig);
			startActivity(intent);
			break;

		default:
			break;
		}

	}
}