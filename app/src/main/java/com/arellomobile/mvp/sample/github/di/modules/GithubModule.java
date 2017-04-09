package com.arellomobile.mvp.sample.github.di.modules;


import com.arellomobile.mvp.sample.github.app.GithubApi;
import com.arellomobile.mvp.sample.github.mvp.GithubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Date: 8/26/2016
 * Time: 11:58
 *
 * @author Artur Artikov
 */

@Module(includes = {ApiModule.class})
public final class GithubModule {
	@Provides
	@Singleton
	GithubService provideGithubService(GithubApi githubApi) {
		return new GithubService(githubApi);
	}
}