package com.example.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePomodoroActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final int MIN_DISTANCE = 150;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pomodoro);

        getProgress(R.id.seekBar3, R.id.textView15);
        getProgress(R.id.seekBar5, R.id.textView16);
        getProgress(R.id.seekBar7, R.id.textView17);
        getProgress(R.id.seekBar4, R.id.textView19);

    }

    public void getProgress(int seekBar, int textV) {

        SeekBar simpleSeekBar = (SeekBar) findViewById(seekBar);
        TextView seekBarValue = (TextView) findViewById(textV);

        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(String.valueOf(progress));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void applyToPomodoro(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.gestureDetector = new GestureDetector(CreatePomodoroActivity.this, this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipe_left_start,
                R.anim.swipe_left_end);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1 = 0, x2 = 0;
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();

                float valueX = x2 - x1;

                if (Math.abs(valueX) > MIN_DISTANCE) {
                    // Swipe Right
                    if (valueX > 0) {
                        onBackPressed();
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event.getAction());
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}