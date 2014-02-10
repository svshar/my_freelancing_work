package com.xudu.android.kbs.ui;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

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
import android.view.View;
import android.view.View.OnClickListener;

import com.xudu.android.kbs.Commons;
import com.xudu.android.kbs.Constants;
import com.xudu.android.kbs.R;

/**
 * 
 * @author admin
 * 
 */
public class IntroMainMovieActivity extends Activity implements
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
	private String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmovie);

		getWindow().setFlags(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mPreview = (SurfaceView) findViewById(R.id.surface);

		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		if (Constants.currentMovie >= Constants.paths.Main_Movie.length) {
			Intent intent = new Intent();
			intent.setClass(IntroMainMovieActivity.this, WelcomeActivity.class);
			startActivity(intent);
			finish();

		}

		String Main_moview_Intro = Constants.KBS_ROOT
				+ Constants.paths.Main_Movie[Constants.currentMovie]
				+ Constants.paths.intro;

		if (Constants.currentMovie == 3 || Constants.currentMovie == 7
				|| Constants.currentMovie == 11 || Constants.currentMovie == 15
				|| Constants.currentMovie == 18) {
			Intent intent = new Intent();
			intent.putExtra("bonus", true);
			intent.setClass(IntroMainMovieActivity.this,
					CorrectMainMovieActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		File videofile = new File(Main_moview_Intro);
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
			finish();
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
			// Commons.showToastAlert(getApplicationContext(), "path"+path,
			// false);
			try {
				File file = new File(path);
				FileInputStream fis = new FileInputStream(file);
				FileDescriptor fd = fis.getFD();
				// Create a new media player and set the listeners
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(fd);
				// Create a new media player and set the listeners
				mMediaPlayer.setScreenOnWhilePlaying(true);
				mMediaPlayer.setDisplay(holder);
				mMediaPlayer.setOnPreparedListener(this);

				mMediaPlayer.setOnBufferingUpdateListener(this);
				mMediaPlayer.setOnCompletionListener(this);

				mMediaPlayer.setOnVideoSizeChangedListener(this);
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mMediaPlayer.prepare();
			} catch (Exception e) {
				Commons.showToastAlert(getBaseContext(), e.getMessage() + " ",
						true);
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
		Intent intent = new Intent();
		intent.setClass(IntroMainMovieActivity.this,
				SelectionMainMovieActivity.class);
		startActivity(intent);
		finish();
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
		// playVideo(extras.getInt(MEDIA));

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
		case R.id.buttonCartoon:
			Commons.showToastAlert(getBaseContext(), "Cartoon", true);
			break;
		case R.id.buttonFlashCards:
			Commons.showToastAlert(getBaseContext(), "FlashCards", true);
			break;

		case R.id.buttonMainMoview:
			Commons.showToastAlert(getBaseContext(), "MainMoview", true);
			break;

		case R.id.buttonSongs:
			Commons.showToastAlert(getBaseContext(), "Songs", true);
			break;

		case R.id.buttonVideo:
			Commons.showToastAlert(getBaseContext(), "Video", true);
			break;

		default:
			break;
		}

	}
}
