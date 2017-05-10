package org.dzlebedzeu.earthquakelite.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dzlebedzeu.earthquakelite.R;
import org.dzlebedzeu.earthquakelite.mvp.model.entities.Earthquake;
import org.dzlebedzeu.earthquakelite.ui.viewwholder.EarthquakeViewHolder;
import org.dzlebedzeu.earthquakelite.util.ResourcesHelper;

import java.util.List;

public class EarthquakesAdapter extends RecyclerView.Adapter<EarthquakeViewHolder> {

    private List<Earthquake> mEarthquakeList;

    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_eitem_earthquake, parent, false);
        return new EarthquakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EarthquakeViewHolder holder, int position) {
        if (mEarthquakeList == null || mEarthquakeList.size() < position) {
            return;
        }

        Earthquake earthquake = mEarthquakeList.get(position);

        Float magnitude = earthquake.getMagnitude();

        holder.mDateTime.setText(earthquake.getDateTime());
        holder.mLatitude.setText(String.valueOf(earthquake.getLatitude()));
        holder.mLongitude.setText(String.valueOf(earthquake.getLongitude()));
        holder.mDepth.setText(String.valueOf(earthquake.getDepth()));
        holder.mMagnitude.setText(String.valueOf(magnitude));

        if (magnitude >= 8) {
            holder.mWarningIcon.setColorFilter(ResourcesHelper.getColorForColorId(holder.mWarningIcon.getContext(), R.color.red));
        } else if (magnitude < 8 && magnitude >= 3) {
            holder.mWarningIcon.setColorFilter(ResourcesHelper.getColorForColorId(holder.mWarningIcon.getContext(), R.color.orange));
        } else if (magnitude < 3) {
            holder.mWarningIcon.setColorFilter(ResourcesHelper.getColorForColorId(holder.mWarningIcon.getContext(), R.color.yellow));
        }
    }

    @Override
    public int getItemCount() {
        if (mEarthquakeList == null) {
            return 0;
        } else {
            return mEarthquakeList.size();
        }
    }

    public void setEarthquakeList(List<Earthquake> earthquakeList) {
        mEarthquakeList = earthquakeList;

        notifyDataSetChanged();
    }
}
