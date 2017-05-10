package org.dzlebedzeu.earthquakelite.ui.viewwholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.dzlebedzeu.earthquakelite.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.warning_icon)
    public ImageView mWarningIcon;

    @BindView(R.id.date_time_value)
    public TextView mDateTime;

    @BindView(R.id.latitude)
    public TextView mLatitude;

    @BindView(R.id.longitude)
    public TextView mLongitude;

    @BindView(R.id.depth)
    public TextView mDepth;

    @BindView(R.id.magnitude)
    public TextView mMagnitude;

    @BindView(R.id.data_container)
    RelativeLayout mDataContainer;

    public EarthquakeViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
