package com.arellomobile.mvp.sample.github.common;


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
	public static <T> ObservableTransformer<T, T> applySchedulers() {
		return observable -> observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
