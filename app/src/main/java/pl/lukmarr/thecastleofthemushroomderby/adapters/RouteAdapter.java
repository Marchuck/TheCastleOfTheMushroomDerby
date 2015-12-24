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
import pl.lukmarr.thecastleofthemushroomderby.connection.DataCallback;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.Route;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.RouteConfigBody;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import pl.lukmarr.thecastleofthemushroomderby.providers.RouteConfigProvider;
import pl.lukmarr.thecastleofthemushroomderby.utils.DrawerConnector;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ItemViewHolder> {
    private final List<Route> mItems = new ArrayList<>();

    /**
     * lel class with lowerCase?!!1
     */
    onclick listener;

    public RouteAdapter(@NonNull List<Route> dataSet, DrawerConnector drawerConnector) {
        mItems.addAll(dataSet);
        notifyDataSetChanged();
        listener = new onclick(drawerConnector);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        Route agency = mItems.get(position);
        holder.title.setText(agency.getTitle());
        holder.itemView.setOnClickListener(getOnclick(position));
        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(0xfffbbb);
        else
            holder.itemView.setBackgroundColor(0xd7acff);

    }

    View.OnClickListener getOnclick(int pos) {
        listener.setRoute(mItems.get(pos));
        return listener;
    }

    private static class onclick implements View.OnClickListener {
        public static final String TAG = onclick.class.getSimpleName();
        DrawerConnector connector;
        Route route;

        public onclick(DrawerConnector connector) {
            this.connector = connector;
        }

        void setRoute(Route a) {
            route = a;
        }

        @Override
        public void onClick(View v) {
            if (!connector.isOpened()) {
                RouteConfigProvider.provide(Config.TAG, route.getTag(), new DataCallback<RouteConfigBody>() {
                    @Override
                    public void onDataReceived(RouteConfigBody item) {
                        connector.openDrawer(item.getRoute());
                    }
                });
            } else connector.closeDrawer();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void refresh(List<Route> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public View v;
        @Bind(R.id.title)
        public TextView title;

        public ItemViewHolder(View v) {
            super(v);
            this.v = v;
            ButterKnife.bind(this, v);
            title.setTextColor(Color.WHITE);
        }
    }
}
