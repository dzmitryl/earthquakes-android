package org.dzlebedzeu.earthquakelite.mvp.presenter;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.dzlebedzeu.earthquakelite.mvp.model.ApiProperties;
import org.dzlebedzeu.earthquakelite.mvp.model.EarthquakesApiClient;
import org.dzlebedzeu.earthquakelite.mvp.model.entities.EarthquakesList;
import org.dzlebedzeu.earthquakelite.mvp.view.EarthquakesListView;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;

@SuppressWarnings("unchecked")
public class EarthquakesListScreenPresenter extends MvpBasePresenter<EarthquakesListView> {

    private Subscription mSubscription;
    private Scheduler mScheduler;

    //Normally i would inject proper scheduler with Dagger2 dependency injection, but this will
    // make the project overcomplicated
    public EarthquakesListScreenPresenter(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    public void getEarthquakesListByCoordinates(float north, float south, float east, float west,
                                                boolean cacheObservable, boolean useCache) {


        getEarthquakesListByCoordinates(getObserver(), north, south, east, west, cacheObservable, useCache);
    }

    public void getEarthquakesListByCoordinates(Observer<EarthquakesList> observer, float north, float south, float east, float west,
                                                boolean cacheObservable, boolean useCache) {

        // username hardcoded for simplicity
        Observable<EarthquakesList> observable = (Observable<EarthquakesList>) EarthquakesApiClient.getInstance()
                .getPreparedObservable(mScheduler, EarthquakesApiClient.getInstance()
                        .getEarthquakesListByCoordinates(north, south, east, west, true,
                                ApiProperties.USERNAME), EarthquakesList.class, cacheObservable, useCache);

        mSubscription = observable.subscribe(observer);
    }

    @NonNull
    private Observer<EarthquakesList> getObserver() {
        return new Observer<EarthquakesList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().onError();
                }
            }

            @Override
            public void onNext(EarthquakesList earthquakesList) {
                if (earthquakesList != null && earthquakesList.getEarthquakesList() != null
                        && earthquakesList.getEarthquakesList().size() != 0) {
                    if (isViewAttached()) {
                        getView().onEarthquakesListLoaded(earthquakesList.getEarthquakesList());
                    }
                } else {
                    if (isViewAttached()) {
                        getView().onNoResults();
                    }
                }
            }
        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.detachView(retainInstance);
    }
}
