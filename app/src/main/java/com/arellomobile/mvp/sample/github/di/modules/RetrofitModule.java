package com.arellomobile.mvp.sample.github.di.modules;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import com.arellomobile.mvp.sample.github.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date: 8/26/2016
 * Time: 12:28
 *
 * @author Artur Artikov
 */
@Module
public final class RetrofitModule {

	private static final String BASE_URL = "https://api.github.com";
    private static final int HTTP_READ_TIMEOUT = 60;
    private static final int HTTP_CONNECT_TIMEOUT = 60;

    @Provides
	@Singleton
    @NonNull
	Retrofit provideRetrofit(Retrofit.Builder builder) {
		return builder.baseUrl(RetrofitModule.BASE_URL)
				.build();
	}

    @Provides
    @Singleton
    @NonNull
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    @NonNull
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }


    @Provides
	@Singleton
    @NonNull
	Retrofit.Builder provideRetrofitBuilder(OkHttpClient client, Converter.Factory converterFactory) {
		return new Retrofit.Builder()
                .client(client)
				.addCallAdapterFactory(RxJava2CallAdapterFactory
						.createWithScheduler(Schedulers.io()))
				.addConverterFactory(converterFactory);
	}

	@Provides
	@Singleton
    @NonNull
	Converter.Factory provideConverterFactory(Gson gson) {
		return GsonConverterFactory.create(gson);
	}

	@Provides
	@Singleton
    @NonNull
	Gson provideGson() {
		return new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setFieldNamingStrategy(new CustomFieldNamingPolicy())
				.setPrettyPrinting()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				.serializeNulls()
				.create();
	}

	private static class CustomFieldNamingPolicy implements FieldNamingStrategy {
		@Override
		public String translateName(Field field) {
			String name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
			name = name.substring(2, name.length()).toLowerCase();
			return name;
		}
	}
}
