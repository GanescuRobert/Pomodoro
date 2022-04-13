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
    private final Context context;
    private final ArrayList<Pomodoro> modelArrayList;

    public SwipeAdapter(Context context, ArrayList<Pomodoro> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        try {
            return modelArrayList.size();
        } catch (NullPointerException e) {
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

        TextView mTextViewTitle = view.findViewById(R.id.title_value);
        TextView mTextViewFocus = view.findViewById(R.id.focus_value);
        TextView mTextViewShortBreak = view.findViewById(R.id.short_break_value);
        TextView mTextViewLongBreak = view.findViewById(R.id.long_break_value);
        TextView mTextViewSets = view.findViewById(R.id.sets_value);
        TextView mTextViewSetsUntilBreak = view.findViewById(R.id.sets_until_break_value);
        TextView mTextViewColor = view.findViewById(R.id.color_value);

        //get data
        Pomodoro pomodoro = modelArrayList.get(position);

        //set data
        mTextViewTitle.setText(pomodoro.getName());
        mTextViewFocus.setText(String.valueOf(pomodoro.getFocus()));
        mTextViewShortBreak.setText(String.valueOf(pomodoro.getShort_break()));
        mTextViewLongBreak.setText(String.valueOf(pomodoro.getLong_break()));
        mTextViewSets.setText(String.valueOf(pomodoro.getSets()));
        mTextViewSetsUntilBreak.setText(String.valueOf(pomodoro.getSets_until_long_break()));
        mTextViewColor.setBackgroundColor(pomodoro.getColor());

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
