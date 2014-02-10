package com.xudu.android.kbs.ui;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;

import com.xudu.android.kbs.Commons;
import com.xudu.android.kbs.Constants;
import com.xudu.android.kbs.R;

/**
 * 
 * @author admin
 * 
 */
public class SelectionMainMovieActivity extends Activity implements
		OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		OnVideoSizeChangedListener, SurfaceHolder.Callback, OnClickListener {

	private static final String TAG = "MediaPlayer";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;

	private static final int LOCAL_VIDEO = 4;

	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;
	private LayoutInflater inflator;
	private String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmovie);

		getWindow().setFlags(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

		inflator = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View controlView = inflator.inflate(
				R.layout.controls_main_intro_selection, null);

		mPreview = (SurfaceView) findViewById(R.id.surface);
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		this.addContentView(controlView, param);

		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		String Main_Movie_Selection = Constants.KBS_ROOT
				+ Constants.paths.Main_Movie[Constants.currentMovie]
				+ Constants.paths.selection;
		File videofile = new File(Main_Movie_Selection);
		if (videofile.exists()) {
			if (videofile.isDirectory()) {
				File[] files = videofile.listFiles();

				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (!file.isHidden())
						path = file.getAbsolutePath();
				}
			}
		}
		if (!videofile.exists()) {
			Commons.showToastAlert(getApplicationContext(), "Missing File!",
					false);
			Log.d(TAG, "Missing : " + Constants.paths.Main_Intro);
			finish();
		}

	}

	private void playVideo(Integer Media) {
		doCleanUp();

		switch (Media) {
		case LOCAL_VIDEO:
			try {
				// Create a new media player and set the listeners
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setScreenOnWhilePlaying(true);
				mMediaPlayer.setDataSource(path);
				mMediaPlayer.setDisplay(holder);

				mMediaPlayer.prepare();
				mMediaPlayer.setLooping(true);

				mMediaPlayer.setOnBufferingUpdateListener(this);
				mMediaPlayer.setOnCompletionListener(this);
				mMediaPlayer.setOnPreparedListener(this);
				mMediaPlayer.setOnVideoSizeChangedListener(this);
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			} catch (Exception e) {
				Commons.showToastAlert(getBaseContext(), e.getMessage(), true);
			}
			break;
		default:
			break;

		}

	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height
					+ ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");

		playVideo(LOCAL_VIDEO);

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.buttonSelection1:
			switch (Constants.currentMovie) {
			case 1:// Tigre
			case 4:// Cow
			case 5:// Cow
			case 12:// Tigre
			case 14: // Pig
			case 17:// Cow
				Intent intent = new Intent();
				intent.setClass(SelectionMainMovieActivity.this,
						CorrectMainMovieActivity.class);
				intent.putExtra("bonus", false);
				startActivity(intent);
				finish();
				break;

			default:
				break;
			}
			break;
		case R.id.buttonSelection2:
			switch (Constants.currentMovie) {
			case 6:// Bird
			case 8:// Goat
			case 13:// Goat
			case 16:// Bird
				Intent intent = new Intent();
				intent.setClass(SelectionMainMovieActivity.this,
						CorrectMainMovieActivity.class);
				intent.putExtra("bonus", false);
				startActivity(intent);
				finish();
				break;

			default:
				break;
			}
			break;

		case R.id.buttonSelection3:
			switch (Constants.currentMovie) {
			case 0:// Bear
			case 2:// Girafe
			case 9:// Bear M5
			case 10:// Girafe M6
				Intent intent = new Intent();
				intent.setClass(SelectionMainMovieActivity.this,
						CorrectMainMovieActivity.class);
				startActivity(intent);
				finish();
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}

	}
}
