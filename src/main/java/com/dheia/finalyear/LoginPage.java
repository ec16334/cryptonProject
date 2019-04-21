package com.dheia.finalyear;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

public class LoginPage extends AppCompatActivity {
    private PatternLockView mPatternLockView;
    private String attemptedPassword="";
    private String savedpassword="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String restoredText = prefs.getString("Patternlock", null);
        Toast.makeText(this, restoredText, Toast.LENGTH_SHORT).show();

        if (restoredText != null) {
            savedpassword=restoredText;
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_lock_screen_pattern);
            setTitle("Pattern Lock");

            mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);

            Toast.makeText(this, "Please Select a Pattern", Toast.LENGTH_LONG).show();

            mPatternLockView.setDotCount(3);
            mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
            mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
            mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
            mPatternLockView.setAspectRatioEnabled(true);
            mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
            mPatternLockView.setDotAnimationDuration(150);
            mPatternLockView.setPathEndAnimationDuration(100);
            mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
            mPatternLockView.setInStealthMode(false);
            mPatternLockView.setTactileFeedbackEnabled(true);
            mPatternLockView.setInputEnabled(true);
            mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        }
        else{
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }
        
        
    }

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            attemptedPassword=PatternLockUtils.patternToString(mPatternLockView, pattern);
            if(attemptedPassword.equals(savedpassword)){
                Toast.makeText(LoginPage.this, "CORRECT", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LoginPage.this, "TRY AGAIN", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };
}
