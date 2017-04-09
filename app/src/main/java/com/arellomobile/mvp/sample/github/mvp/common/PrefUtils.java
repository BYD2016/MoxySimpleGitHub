package com.arellomobile.mvp.sample.github.mvp.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.arellomobile.mvp.sample.github.app.GithubApp;

/**
 * Date: 18.01.2016
 * Time: 15:01
 *
 * @author Yuri Shmakov
 */
final class PrefUtils {
	private static final String PREF_NAME = "github";

	static SharedPreferences getPrefs() {
		return GithubApp.getAppComponent()
				.getContext()
				.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	static SharedPreferences.Editor getEditor() {
		return PrefUtils.getPrefs().edit();
	}
}
