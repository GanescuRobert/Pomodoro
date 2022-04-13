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
    private GestureDetector gestureDetector;
    private static final int MIN_DISTANCE = 150;

    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonFinish;
    private TextView mTextViewCountDown;

    private boolean isRunning = false;

    private final Pomodoro selected_pomodoro = new Pomodoro();
    private ArrayList<Pomodoro> pomodoros = new ArrayList<>(Arrays.asList(selected_pomodoro));

    private Session session;
    private ArrayList<Session> sessions = new ArrayList<>();

    private ViewPager viewPager;
    private SwipeAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gestureDetector = new GestureDetector(MainActivity.this, this);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStart = findViewById(R.id.start_button);
        mButtonPause = findViewById(R.id.pause_button);
        mButtonFinish = findViewById(R.id.finish_button);

        //pick pomo
        viewPager = findViewById(R.id.viewPager);
        loadCards();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String title = pomodoros.get(position).getName();
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

        if(getIntent().hasExtra("key")) {
            Pomodoro pmd = (Pomodoro) getIntent().getSerializableExtra("key");
            pomodoros.add(pmd);
        }

        myAdapter = new SwipeAdapter(this, pomodoros);
        viewPager.setAdapter(myAdapter);
    }

    public void openStatisticsActivity(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.swipe_left_start, R.anim.swipe_left_end);
    }

    public void openCreatePomodoroActivity(View view) {
        Intent intent = new Intent(this, CreatePomodoroActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.swipe_right_start, R.anim.swipe_right_end);
    }

    public void startTimer(View view) {
        if (isRunning == false) {
            isRunning = true;
            session = new Session(selected_pomodoro, mTextViewCountDown, mButtonFinish);
        }
        session.start();


        mButtonStart.setVisibility(View.GONE);
        mButtonPause.setVisibility(View.VISIBLE);
        mButtonFinish.setVisibility(View.GONE);
    }
    public void pauseTimer(View view) {
        session.pause();

        mButtonStart.setVisibility(View.VISIBLE);
        mButtonPause.setVisibility(View.GONE);
        mButtonFinish.setVisibility(View.VISIBLE);
    }
    public void finishTimer(View view) {
        isRunning = false;
        session.resetCountDownText();
        sessions.add(session);

        mButtonStart.setVisibility(View.VISIBLE);
        mButtonPause.setVisibility(View.GONE);
        mButtonFinish.setVisibility(View.GONE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1=0, x2=0;
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