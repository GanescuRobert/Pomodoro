package com.example.pomodoro;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final long START_TIME_IN_MILLIS = 4000;

    private static final int MIN_DISTANCE = 150;
    private float x1, x2;
    private GestureDetector gestureDetector;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;

    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private final Pomodoro selected_pomodoro = new Pomodoro();
    private final ArrayList<Pomodoro> pomodoros = new ArrayList<>(Arrays.asList(selected_pomodoro));

    private ToneGenerator toneGen;

    private View mBottomSheetActivity;
    private ViewPager viewPager;
    private ArrayList<Pomodoro> modelArrayList;
    private SwipeAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toneGen = new ToneGenerator(AudioManager.STREAM_ALARM, 50);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStart = findViewById(R.id.start_button);
        mButtonPause = findViewById(R.id.pause_button);
        mButtonReset = findViewById(R.id.reset_button);

        updateCountDownText();

        //pick pomo
        viewPager = findViewById(R.id.viewPager);
        loadCards();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //String title = modelArrayList.get(position).getTitle();

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void loadCards(){
        modelArrayList = new ArrayList<>();

        modelArrayList.add(new Pomodoro());
        modelArrayList.add(new Pomodoro());
        modelArrayList.add(new Pomodoro());

        myAdapter = new SwipeAdapter(this, modelArrayList);

        viewPager.setAdapter(myAdapter);

        //viewPager.setPadding(100, 0, 100,0);

    }

    public void openStatisticsActivity(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void openCreatePomodoroActivity(View view) {
        Intent intent = new Intent(this, CreatePomodoroActivity.class);
        startActivity(intent);
    }

    private void updateCountDownText() {
        int minutes = (int) mTimeLeftInMillis / 1000 / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    public void startTimer(View view) {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                if (mTimeLeftInMillis < 1000) {
                    toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                } else if (mTimeLeftInMillis < 4000) {
                    toneGen.startTone(ToneGenerator.TONE_SUP_ERROR, 200);
                }
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                resetTimer(view);
            }
        }.start();

        mButtonStart.setVisibility(View.GONE);
        mButtonPause.setVisibility(View.VISIBLE);
        mButtonReset.setVisibility(View.GONE);
    }


    public void pauseTimer(View view) {
        mCountDownTimer.cancel();

        mButtonStart.setVisibility(View.VISIBLE);
        mButtonPause.setVisibility(View.GONE);
        mButtonReset.setVisibility(View.VISIBLE);
    }

    public void resetTimer(View view) {
        mButtonStart.setVisibility(View.VISIBLE);
        mButtonPause.setVisibility(View.GONE);
        mButtonReset.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                        Intent intent = new Intent(this, StatisticsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.swipe_left_start, R.anim.swipe_left_end);

                    }
                    //Swipe Left
                    else {
                        Intent intent = new Intent(this, CreatePomodoroActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.swipe_right_start, R.anim.swipe_right_end);

                    }
                }

                break;
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