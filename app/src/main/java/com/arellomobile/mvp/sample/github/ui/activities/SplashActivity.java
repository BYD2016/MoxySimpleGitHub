package com.arellomobile.mvp.sample.github.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.sample.github.mvp.presenters.SplashPresenter;
import com.arellomobile.mvp.sample.github.mvp.views.SplashView;

public final class SplashActivity extends MvpAppCompatActivity implements SplashView {

	@InjectPresenter
	SplashPresenter mSplashPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// By default view attaches to presenter in onResume() method,
		// but we attach it manually for earlier check authorization state.
		getMvpDelegate().onAttach();
	}

	/**
	 * 依据认证状态进行路由
	 *
	 * @param isAuthorized 是否通过认证
	 */
	@Override
	public void setAuthorized(boolean isAuthorized) {
		startActivity(new Intent(this, isAuthorized ? HomeActivity.class : SignInActivity.class));
	}
}
