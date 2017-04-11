package com.arellomobile.mvp.sample.github.common;


import com.arellomobile.mvp.sample.github.mvp.presenters.BasePresenter;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Date: 11.01.2017
 * Time: 16:39
 *
 * @author Yuri Shmakov
 */

public class RxUtils {
	public static <T> ObservableTransformer<T, T> applyUIDefaults(final BasePresenter<?> presenter) {
		return upstream -> upstream
				.compose(RxUtils.addToCompositeDisposable(presenter))
				.compose(RxUtils.applySchedulers());
	}

	public static <T> ObservableTransformer<T, T> applySchedulers() {
		return observable -> observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	private static <T> ObservableTransformer<T, T> addToCompositeDisposable(final BasePresenter<?> presenter) {
		return upstream -> upstream.doOnSubscribe(
				disposable -> presenter.unsubscribeOnDestroy(disposable));
	}
}
