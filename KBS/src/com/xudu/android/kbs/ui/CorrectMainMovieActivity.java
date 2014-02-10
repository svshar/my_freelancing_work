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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.xudu.android.kbs.Commons;
import com.xudu.android.kbs.Constants;
import com.xudu.android.kbs.R;

/**
 * 
 * @author admin
 * 
 */
public class CorrectMainMovieActivity extends Activity implements
		OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		OnVideoSizeChangedListener, SurfaceHolder.Callback {

	private static final String TAG = "MediaPlayer";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;

	private static final int LOCAL_VIDEO = 4;

	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;

	boolean isBonus = false;
	private String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmovie);

		getWindow().setFlags(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

		isBonus = getIntent().getBooleanExtra("bonus", false);

		mPreview = (SurfaceView) findViewById(R.id.surface);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		String Main_Movie_Correct;
		if (isBonus)
			Main_Movie_Correct = Constants.KBS_ROOT
					+ Constants.paths.Main_Movie[Constants.currentMovie];
		else
			Main_Movie_Correct = Constants.KBS_ROOT
					+ Constants.paths.Main_Movie[Constants.currentMovie]
					+ Constants.paths.correct;
		File videofile = new File(Main_Movie_Correct);
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
			Commons.showToastAlert(getApplicationContext(),
					getString(R.string.video_not_found), false);
			Log.d(TAG, "Missing : " + Constants.paths.Main_Intro);
			finish();
		}

	}

	private void playVideo(Integer Media) {
		doCleanUp();

		switch (Media) {
		case LOCAL_VIDEO:
			try {
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setScreenOnWhilePlaying(true);
				mMediaPlayer.setDataSource(path);
				mMediaPlayer.setDisplay(holder);

				mMediaPlayer.prepare();

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
		if (Constants.currentMovie >= Constants.paths.Main_Movie.length - 1) {
			//Intent intent = new Intent();
			//intent.setClass(CorrectMainMovieActivity.this, WelcomeActivity.class);
			//startActivity(intent);
			finish();

		} else {

			++Constants.currentMovie;
			Intent intent = new Intent();
			intent.setClass(CorrectMainMovieActivity.this,
					IntroMainMovieActivity.class);
			startActivity(intent);
			finish();
		}
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
}
