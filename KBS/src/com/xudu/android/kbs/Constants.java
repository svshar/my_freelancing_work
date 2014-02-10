package com.xudu.android.kbs;

import java.io.File;

import android.os.Environment;

/**
 * 
 * @author admin
 * 
 */
public class Constants {

	public static int currentMovie = 0;

	public static String KBS_ROOT = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
			.getParent()
			+ File.separator + "KBS" + File.separator;
	// + File.separator + "KBS" + File.separator;
	public static final String MAIN_MENU = "MAIN INTRO" + File.separator;

	public static interface paths {
		public static final String flashCards = KBS_ROOT + "/20_FLASHCARDS/";
		public static final String intro = "/INTRO/";
		public static final String correct = "/CORRECT/";
		public static final String selection = "/SELECTION/";
		public static final String Main_Intro = KBS_ROOT + MAIN_MENU;
		public static final String[] Main_Movie = { "/1_BEAR_M2/",
				"/2_TIGER_M4/", "/3_GIRAFFE_M2/", "/4_ITSY_BITSY_SPIDER/",
				"/5_COW_M3/", "/6_PIG_M6/", "/7_BIRD_M1/", "/8_SHAPES/",
				"/9_GOAT_M2/", "/10_BEAR_M5/", "/11_GIRAFFE_M5/", "/12_HSKF/",
				"/13_TIGER_M1/", "/14_GOAT_M5/", "/15_PIG_M3/", "/16_RGB/",
				"/17_BIRD_M4/", "/18_COW_M6/", "/19_TTLS/" };
		// public static final String Main_Movie_Intro = KBS_ROOT
		// + Main_Movie[currentMovie] + intro;
		// public static final String Main_Movie_Selection = KBS_ROOT
		// + Main_Movie[currentMovie] + selection;
		// public static final String Main_Movie_Correct = KBS_ROOT
		// / + Main_Movie[currentMovie] + correct;
		public static final String video_segment = KBS_ROOT
				+ "/21_VIDEO_SEGMENTS/";
		public static final String video_segment_main_screen = video_segment
				+ "/MAIN_SCREEN/";
		public static final String video_segment_bear = video_segment
				+ "/BEAR/";
		public static final String video_segment_bird = video_segment
				+ "/BIRD/";
		public static final String video_segment_cow = video_segment + "/COW/";
		public static final String video_segment_giraffe = video_segment
				+ "/GIRAFFE/";
		public static final String video_segment_pig = video_segment + "/PIG/";
		public static final String video_segment_goat = video_segment
				+ "/GOAT/";
		public static final String video_segment_tiger = video_segment
				+ "/TIGER/";
		public static final String anime_segment = KBS_ROOT
				+ "/22_ANIME_SEGMENTS/" + "/MAIN_SCREEN/";
		public static final String songs = KBS_ROOT + "/23_SONGS_MENU/";
		public static final String anime_segment_main_screen = KBS_ROOT
				+ "/22_ANIME_SEGMENTS/MAIN_SCREEN/";

	}

}
