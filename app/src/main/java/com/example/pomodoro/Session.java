package com.example.pomodoro;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Session {

    private Pomodoro pomodoro;
    private TextView mTextViewCountDown;

    public Session(Pomodoro pomodoro, TextView textView) {
        this.pomodoro = pomodoro;
        this.mTextViewCountDown = textView;
        totalTime = get_total_time();
    }


    private CountDownTimer mCountDownTimer;
    private ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_ALARM, 50);

    private LocalTime created_at;
    private LocalTime stoped_at;
    private long elapsedMinutes;
    private Integer totalTime;

    private void updateCountDownText(long timeLeftInMillis) {
        int seconds = (int) (timeLeftInMillis / 1000) % 60 ;
        int minutes = (int) ((timeLeftInMillis / (1000*60)) % 60);
        int hours   = (int) ((timeLeftInMillis / (1000*60*60)) % 24);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void cleanCountDownText() {
        mTextViewCountDown.setText("");
    }

    public void start() {

        this.created_at = LocalTime.now();

        mCountDownTimer = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long l) {
                updateCountDownText(l);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    public void finish() {
        cleanCountDownText();
        this.stoped_at = LocalTime.now();
        elapsedMinutes = Duration.between(created_at,stoped_at).toSeconds();
        mCountDownTimer.cancel();
    }

    public Integer get_total_time() {
        Integer ans = (pomodoro.getFocus() + pomodoro.getShort_break()) * pomodoro.getSets() + pomodoro.getLong_break() * (pomodoro.getSets() / pomodoro.getSets_until_long_break() - 1);
        return ans;
    }
}
