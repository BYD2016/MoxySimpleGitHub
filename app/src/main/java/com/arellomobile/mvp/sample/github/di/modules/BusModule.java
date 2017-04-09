package com.arellomobile.mvp.sample.github.di.modules;

import com.arellomobile.mvp.sample.github.app.GithubApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Date: 20.09.2016
 * Time: 20:22
 *
 * @author Yuri Shmakov
 */
@Module
public final class BusModule {
	@Provides
	@Singleton
	Bus provideBus(GithubApi githubApi) {
		return new Bus();
	}
}
