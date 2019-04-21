package com.dheia.finalyear;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

public class LockScreenPattern extends AppCompatActivity {

    private PatternLockView mPatternLockView;

    private String savedPassword="";
    private boolean settinglock = false;






    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            savedPassword = PatternLockUtils.patternToString(mPatternLockView, pattern);

            if(settinglock) {
                Log.d(getClass().getName(), "Pattern complete: " +
                        PatternLockUtils.patternToString(mPatternLockView, pattern));
                Toast.makeText(LockScreenPattern.this, "Pattern Saved", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LockScreenPattern.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Patternlock", savedPassword);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("loggedin",true);
                startActivity(intent);
            }
            else if(!settinglock){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LockScreenPattern.this);
                SharedPreferences.Editor editor = preferences.edit();
                String savedPin = preferences.getString("Patternlock", null);

                Log.d("Pattern complete: here ",savedPin);

                if(savedPin!=null){
                    Toast.makeText(LockScreenPattern.this, savedPassword, Toast.LENGTH_SHORT).show();

                    if(savedPin.equals(savedPassword)){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("loggedin",true);
                        startActivity(intent);
                    }
                    else{
                        //Toast.makeText(LockScreenPattern.this, "Incorrect Entry, Please Try Again", Toast.LENGTH_SHORT).show();

                    }
                }


//                editor.apply();

            }

        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lock_screen_pattern);
        setTitle("Pattern Lock");

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);

        Toast.makeText(this, "Please Select a Pattern", Toast.LENGTH_LONG).show();

        //boolean check = getIntent().getBooleanExtra("settingLock",false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LockScreenPattern.this);
        SharedPreferences.Editor editor = preferences.edit();
        String check = preferences.getString("Patternlock", null);

        if(check==null){
            settinglock=true;
        }

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

    @Override
    public void onBackPressed() {
        return;
    }
}
