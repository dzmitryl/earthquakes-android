package org.dzlebedzeu.earthquakelite.mvp.model;

import android.support.v4.util.LruCache;

import org.dzlebedzeu.earthquakelite.mvp.model.entities.EarthquakesList;
import org.dzlebedzeu.earthquakelite.mvp.model.service.EarthquakesApiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

public class EarthquakesApiClient {

    private static EarthquakesApiClient sInstance;

    private static OkHttpClient.Builder sHttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder sBuilder =
            new Retrofit.Builder()
                    .baseUrl(ApiProperties.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    private EarthquakesApiService mApiService;

    private LruCache<Class<?>, Observable<?>> apiObservables;

    private EarthquakesApiClient() {
        apiObservables = new LruCache<>(5);

        Retrofit retrofit = sBuilder.client(sHttpClient.build()).build();
        mApiService = retrofit.create(EarthquakesApiService.class);
    }

    public static EarthquakesApiClient getInstance() {
        if (sInstance == null) {
            sInstance = new EarthquakesApiClient();
        }

        return sInstance;
    }

    /**
     * Method for caching observables so that then can be continued to be used after orientation changes
     *
     * @param scheduler            RxAndroid {@link Scheduler}
     * @param unPreparedObservable newly created observable
     * @param clazz                parameter class of observable
     * @param cacheObservable      if observable should be cached
     * @param useCache             if observable should be retrieved from cache
     * @return observable that is either retrieved from cache ot not
     */
    public Observable<?> getPreparedObservable(Scheduler scheduler, Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache) {

        Observable<?> preparedObservable = null;

        if (useCache) {
            preparedObservable = apiObservables.get(clazz);
        }

        if (preparedObservable != null) {
            return preparedObservable;
        }

        preparedObservable = unPreparedObservable.subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());

        if (cacheObservable) {
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }

        return preparedObservable;
    }

    public Observable<EarthquakesList> getEarthquakesListByCoordinates(float north, float south,
                                                                       float east, float west, boolean formatted, String username) {

        return mApiService.getEarthquakesListByCoordinates(formatted, north, south, east, west, username);
    }
}
