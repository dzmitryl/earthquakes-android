package org.dzlebedzeu.earthquakelite.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import org.dzlebedzeu.earthquakelite.R;
import org.dzlebedzeu.earthquakelite.adapter.EarthquakesAdapter;
import org.dzlebedzeu.earthquakelite.mvp.model.entities.Earthquake;
import org.dzlebedzeu.earthquakelite.mvp.presenter.EarthquakesListScreenPresenter;
import org.dzlebedzeu.earthquakelite.mvp.view.EarthquakesListView;
import org.dzlebedzeu.earthquakelite.ui.ActivityBaseUiController;
import org.dzlebedzeu.earthquakelite.util.KeyboardHelper;
import org.dzlebedzeu.earthquakelite.util.ResourcesHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.schedulers.Schedulers;

public class EarthquakesListFragment extends MvpFragment<EarthquakesListView, EarthquakesListScreenPresenter>
        implements EarthquakesListView {

    private static final int MEDIAN_COORDINATE = 0;
    private static final int TOP_NORTH = 90;
    private static final int BOTTOM_SOUTH = -90;
    private static final int RIGHT_EAST = 180;
    private static final int LEFT_WEST = -180;

    private static final String PARAM_CALL_IN_PROGRESS = "callInProgress";
    private static final String PARAM_NORTH = "north";
    private static final String PARAM_SOUTH = "south";
    private static final String PARAM_EAST = "east";
    private static final String PARAM_WEST = "west";

    private OnEarthquakesListFragmentInteractionListener mListener;

    private ActivityBaseUiController mActivityUiController;

    private Unbinder mUnbinder;

    @BindView(R.id.north_input)
    EditText mNorthInput;

    @BindView(R.id.south_input)
    EditText mSouthInput;

    @BindView(R.id.west_input)
    EditText mWestInput;

    @BindView(R.id.east_input)
    EditText mEestInput;

    @BindView(R.id.show_button)
    Button mShowButton;

    @BindView(R.id.earthquakes_list)
    RecyclerView mRecyclerView;

    private EarthquakesAdapter mAdapter;

    private boolean mCallInProgress = false;
    private String mNorth;
    private String mSouth;
    private String mEast;
    private String mWest;

    public EarthquakesListFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public EarthquakesListScreenPresenter createPresenter() {
        return new EarthquakesListScreenPresenter(Schedulers.io());
    }

    public static EarthquakesListFragment newInstance() {
        return new EarthquakesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(PARAM_CALL_IN_PROGRESS)) {
                mCallInProgress = savedInstanceState.getBoolean(PARAM_CALL_IN_PROGRESS);
            }

            if (savedInstanceState.containsKey(PARAM_NORTH)) {
                mNorth = savedInstanceState.getString(PARAM_NORTH);
            }

            if (savedInstanceState.containsKey(PARAM_SOUTH)) {
                mSouth = savedInstanceState.getString(PARAM_SOUTH);
            }

            if (savedInstanceState.containsKey(PARAM_EAST)) {
                mEast = savedInstanceState.getString(PARAM_EAST);
            }

            if (savedInstanceState.containsKey(PARAM_WEST)) {
                mWest = savedInstanceState.getString(PARAM_WEST);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCallInProgress && !TextUtils.isEmpty(mNorth) && !TextUtils.isEmpty(mSouth)
                && !TextUtils.isEmpty(mEast) && !TextUtils.isEmpty(mWest)) {

            loadEarthquakes(mNorth, mSouth, mEast, mWest, true, true);
        } else if (!mCallInProgress && !TextUtils.isEmpty(mNorth) && !TextUtils.isEmpty(mSouth)
                && !TextUtils.isEmpty(mEast) && !TextUtils.isEmpty(mWest)) {
            loadEarthquakes(mNorth, mSouth, mEast, mWest, true, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_earthquakes_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(PARAM_NORTH, mNorthInput.getText().toString());
        outState.putString(PARAM_SOUTH, mSouthInput.getText().toString());
        outState.putString(PARAM_EAST, mEestInput.getText().toString());
        outState.putString(PARAM_WEST, mWestInput.getText().toString());

        outState.putBoolean(PARAM_CALL_IN_PROGRESS, mCallInProgress);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnEarthquakesListFragmentInteractionListener) {
            mListener = (OnEarthquakesListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEarthquakesListFragmentInteractionListener");
        }

        if (context instanceof ActivityBaseUiController) {
            mActivityUiController = (ActivityBaseUiController) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ActivityBaseUiController");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mActivityUiController = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mUnbinder.unbind();
    }

    @OnClick(R.id.show_button)
    public void onShowEarthquakesButtonClick(View view) {

        KeyboardHelper.hideKeyboard(getActivity(), view);

        mCallInProgress = true;

        String northStringValue = mNorthInput.getText().toString();
        String southStringValue = mSouthInput.getText().toString();
        String eastStringValue = mEestInput.getText().toString();
        String westStringValue = mWestInput.getText().toString();

        loadEarthquakes(northStringValue, southStringValue, eastStringValue,
                westStringValue, true, false);
    }

    private void loadEarthquakes(String northStringValue, String southStringValue,
                                 String eastStringValue, String westStringValue, boolean cacheObservable, boolean useCache) {
        if (TextUtils.isEmpty(northStringValue) || TextUtils.isEmpty(southStringValue)
                || TextUtils.isEmpty(eastStringValue) || TextUtils.isEmpty(westStringValue)) {

            showErrorNotification(R.string.error_fields_can_not_be_empty);

            return;
        }

        float north;
        float south;
        float east;
        float west;

        try {
            north = Float.valueOf(northStringValue);
            south = Float.valueOf(southStringValue);
            east = Float.valueOf(eastStringValue);
            west = Float.valueOf(westStringValue);
        } catch (NumberFormatException e) {
            Log.d(EarthquakesListFragment.class.getName(), "This exception is handled");
            e.printStackTrace();

            showErrorNotification(R.string.error_incorrect_format_of_input);

            return;
        }

        if (!validateCoordinates(north, south, east, west)) {
            return;
        }

        mActivityUiController.showSpinner();

        getPresenter().getEarthquakesListByCoordinates(north, south, east, west, cacheObservable, useCache);
    }

    private void showErrorNotification(int errorMessageResourceId) {
        Snackbar snackbar = Snackbar
                .make(mActivityUiController.getCoordinatorLayout(),
                        errorMessageResourceId, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ResourcesHelper.getColorForColorId(getActivity(), R.color.rose));

        snackbar.show();
    }

    private boolean validateCoordinates(float north, float south, float east, float west) {
        if (north > TOP_NORTH || north < MEDIAN_COORDINATE) {
            showErrorNotification(R.string.error_north_coordinate_invalid);
            return false;
        } else if (south < BOTTOM_SOUTH || south > MEDIAN_COORDINATE) {
            showErrorNotification(R.string.error_south_coordinate_invalid);
            return false;
        } else if (east > RIGHT_EAST || east < MEDIAN_COORDINATE) {
            showErrorNotification(R.string.error_east_coordinate_invalid);
            return false;
        } else if (west < LEFT_WEST || west > MEDIAN_COORDINATE) {
            showErrorNotification(R.string.error_west_coordinate_invalid);
            return false;
        }

        return true;
    }

    @Override
    public void onEarthquakesListLoaded(List<Earthquake> earthquakesList) {
        mCallInProgress = false;

        mActivityUiController.hideSpinner();

        if (mAdapter == null) {
            mAdapter = new EarthquakesAdapter();

            mRecyclerView.setAdapter(mAdapter);
        }

        mAdapter.setEarthquakeList(earthquakesList);
    }

    @Override
    public void onNoResults() {
        mActivityUiController.hideSpinner();

        showErrorNotification(R.string.no_data_available);
    }

    @Override
    public void onError() {
        mActivityUiController.hideSpinner();

        showErrorNotification(R.string.error_occurred);
    }

    public interface OnEarthquakesListFragmentInteractionListener {
        void onEarthquakeSelected(Earthquake earthquake);
    }
}
