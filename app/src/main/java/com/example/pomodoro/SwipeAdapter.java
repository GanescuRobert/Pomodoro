package com.example.pomodoro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

public class SwipeAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Pomodoro> modelArrayList;

    public SwipeAdapter(Context context, ArrayList<Pomodoro> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        try {
            return modelArrayList.size();
        }
        catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager, container, false);

        TextView cardTitle= view.findViewById(R.id.cardTitle);
        View banner = view.findViewById(R.id.banner);

        //get data
        Pomodoro model = modelArrayList.get(position);
        String name = model.getName();
        Integer focus = model.getFocus();
        Integer short_break = model.getShort_break();
        Integer long_break = model.getLong_break();
        Integer sets = model.getSets();
        Integer sets_until_long_break = model.getSets_until_long_break();
        Integer color = model.getColor();
        //set data
        cardTitle.setText(name);
        banner.setBackgroundColor(color);

        // handle click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // To do
            }
        });
        // add to container
        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
