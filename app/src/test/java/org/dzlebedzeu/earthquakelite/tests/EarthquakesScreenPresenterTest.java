package org.dzlebedzeu.earthquakelite.tests;

import org.dzlebedzeu.earthquakelite.BuildConfig;
import org.dzlebedzeu.earthquakelite.mvp.model.entities.EarthquakesList;
import org.dzlebedzeu.earthquakelite.mvp.presenter.EarthquakesListScreenPresenter;
import org.dzlebedzeu.earthquakelite.mvp.view.EarthquakesListView;
import org.dzlebedzeu.earthquakelite.utils.TestDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Observer;
import rx.schedulers.Schedulers;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class EarthquakesScreenPresenterTest {

    private EarthquakesListScreenPresenter mPresenter;
    private EarthquakesListView mView;

    @Captor
    ArgumentCaptor<Observer<EarthquakesList>> mObserverArgumentCaptor;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks(this);

        mPresenter = Mockito.spy(new EarthquakesListScreenPresenter(Schedulers.immediate()));

        mView = Mockito.mock(EarthquakesListView.class);

        mPresenter.attachView(mView);
    }

    @Test
    public void loadEarthquakes_UnitTest_Positive() {
        mPresenter.getEarthquakesListByCoordinates(22.3f, -11.0f, 50.5f, -89.9f, false, false);

        Mockito.verify(mPresenter).getEarthquakesListByCoordinates(mObserverArgumentCaptor.capture(),
                Mockito.eq(22.3f), Mockito.eq(-11.0f), Mockito.eq(50.5f), Mockito.eq(-89.9f),
                Mockito.eq(false), Mockito.eq(false));

        EarthquakesList list = TestDataProvider.provideEarthquakesResponseData();

        mObserverArgumentCaptor.getValue().onNext(list);

        Mockito.verify(mView).onEarthquakesListLoaded(list.getEarthquakesList());

        Mockito.reset(mPresenter);
    }
}
