package org.dzlebedzeu.earthquakelite.mvp.model.service;

import org.dzlebedzeu.earthquakelite.mvp.model.entities.EarthquakesList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface EarthquakesApiService {

    @GET("/earthquakesJSON")
    Observable<EarthquakesList> getEarthquakesListByCoordinates(@Query("formatted") boolean formatted,
                                                                @Query("north") float north,
                                                                @Query("south") float south,
                                                                @Query("east") float east,
                                                                @Query("west") float west,
                                                                @Query("username") String username);
}
