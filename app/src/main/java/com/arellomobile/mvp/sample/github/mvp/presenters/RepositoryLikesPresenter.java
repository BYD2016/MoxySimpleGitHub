package com.arellomobile.mvp.sample.github.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.sample.github.common.RxUtils;
import com.arellomobile.mvp.sample.github.mvp.views.RepositoryLikesView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;



/**
 * Date: 26.01.2016
 * Time: 16:32
 *
 * @author Yuri Shmakov
 */
@InjectViewState
public class RepositoryLikesPresenter extends BasePresenter<RepositoryLikesView> {
	public static final String TAG = "RepositoryLikesPresenter";

	private List<Integer> mInProgress = new ArrayList<>();
	private List<Integer> mLikedIds = new ArrayList<>();

	public void toggleLike(int id) {
		if (mInProgress.contains(id)) {
			return;
		}

		mInProgress.add(id);

		getViewState().updateLikes(mInProgress, mLikedIds);

		final Observable<Boolean> toggleObservable = Observable.create(subscriber -> {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			subscriber.onNext(!mLikedIds.contains(id));
		});

		toggleObservable
				.compose(RxUtils.applyUIDefaults(this))
				.subscribe(isLiked -> {
					onComplete(id, isLiked);
				}, throwable -> {
					onFail(id);
				});
	}

	private void onComplete(int id, Boolean isLiked) {
		if (!mInProgress.contains(id)) {
			return;
		}

		mInProgress.remove(Integer.valueOf(id));
		if (isLiked) {
			mLikedIds.add(id);
		} else {
			mLikedIds.remove(Integer.valueOf(id));
		}

		getViewState().updateLikes(mInProgress, mLikedIds);
	}

	private void onFail(int id) {
		if (!mInProgress.contains(id)) {
			return;
		}

		mInProgress.remove(Integer.valueOf(id));
		getViewState().updateLikes(mInProgress, mLikedIds);
	}
}
