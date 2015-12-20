package pl.lukmarr.thecastleofthemushroomderby.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.lukmarr.thecastleofthemushroomderby.R;
import pl.lukmarr.thecastleofthemushroomderby.connection.AgencyConnector;
import pl.lukmarr.thecastleofthemushroomderby.model.Agency;

public class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.ItemViewHolder> {
    private final List<Agency> mItems = new ArrayList<>();


    onclick listener;

    public AgencyAdapter(@NonNull List<Agency> dataSet, AgencyConnector connector) {
        mItems.addAll(dataSet);
        notifyDataSetChanged();
        listener = new onclick(connector);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        Agency agency = mItems.get(position);
        holder.region.setText(agency.getRegionTitle());
        holder.title.setText(agency.getTitle());
        holder.itemView.setOnClickListener(getOnclick(position));
        holder.itemView.setBackgroundColor(position % 2 == 0 ? Color.YELLOW : Color.LTGRAY);
    }

    View.OnClickListener getOnclick(int pos) {
        listener.setAgency(mItems.get(pos));
        return listener;
    }

    private static class onclick implements View.OnClickListener {
        AgencyConnector connector;
        Agency agency;

        public onclick(AgencyConnector connector) {
            this.connector = connector;
        }

        void setAgency(Agency a) {
            agency = a;
        }

        @Override
        public void onClick(View v) {
            connector.onClicked(agency);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void refresh(List<Agency> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public View v;
        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.regionTitle)
        public TextView region;

        public ItemViewHolder(View v) {
            super(v);
            this.v = v;
            ButterKnife.bind(this, v);
        }
    }
}
