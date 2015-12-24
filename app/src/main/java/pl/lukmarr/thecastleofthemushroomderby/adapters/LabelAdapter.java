package pl.lukmarr.thecastleofthemushroomderby.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.lukmarr.thecastleofthemushroomderby.R;
import pl.lukmarr.thecastleofthemushroomderby.model.nextBus.ExtendedRoute;
import pl.lukmarr.thecastleofthemushroomderby.options.Config;
import pl.lukmarr.thecastleofthemushroomderby.utils.DrawerConnector;
import pl.lukmarr.thecastleofthemushroomderby.utils.LabelAdapterConnector;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ItemViewHolder> implements LabelAdapterConnector {

    Context context;
    View behindView;
    RecyclerView recyclerView;
    DrawerConnector connector;
    String currentTitle = "", currentDescription = "";

    public LabelAdapter(Context context, View behindView, RecyclerView recyclerView, DrawerConnector connector) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.behindView = behindView;
        this.connector = connector;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        return 1;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            RelativeLayout view1 = new RelativeLayout(context);
            view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Config.transparentLayoutHeight));
            final FloatingActionButton button = new FloatingActionButton(context);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connector.closeDrawer();
                }
            });
            int color = context.getResources().getColor(R.color.accent_color);
            int whenPressedColor = context.getResources().getColor(R.color.accent_color_darker);
            button.setImageResource(R.drawable.arrow_right);
            button.setBackgroundTintList(ColorStateList.valueOf(color));
            button.setRippleColor(whenPressedColor);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(70, 70);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            button.setLayoutParams(params);
            view1.addView(button);
            view = view1;

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Log.d("|", "onScrolled() : dx = [" + dx + "], dy = [" + dy + "]");
                    button.setY(button.getY() + dy * .7f);

                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Log.d("|", "onScrollStateChanged() : newState = [" + newState + "]");

                }
            });

        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.long_description_item, parent, false);
        return new ItemViewHolder(view, viewType, behindView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        if (position == 1) {
            holder.title.setText(currentTitle);
            holder.regionTitle.setText(currentDescription);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onDrawerOpened(ExtendedRoute route) {
        currentTitle = route.getTitle();
        currentDescription = route.getDirection().get(0).getName();
        notifyDataSetChanged();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public View v;
        public TextView title, regionTitle;

        public ItemViewHolder(View v, int viewType, final View behindView) {
            super(v);
            this.v = v;
            if (viewType == 1) {
                title = (TextView) v.findViewById(R.id.title);
                title.setTextColor(Color.WHITE);
                regionTitle = (TextView) v.findViewById(R.id.regionTitle);
                regionTitle.setTextColor(Color.WHITE);

            } else {
                v.setBackgroundColor(Color.TRANSPARENT);
                v.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return behindView.dispatchTouchEvent(event);
                    }
                });
            }
        }
    }
}
