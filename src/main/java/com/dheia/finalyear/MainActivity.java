package com.dheia.finalyear;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    implements NavigationView.OnNavigationItemSelectedListener
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    Switch patternlockSwitch;


    @Override
    public void onBackPressed() {
        //nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        patternlockSwitch = findViewById(R.id.patternlockmenuswitch);

        //checking SP for savedpattern and if user is logged in
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        String savedPin = preferences.getString("Patternlock", null);
        String isPatternEnabled = preferences.getString("Patternlock", null);

        if(savedPin!=null){
            boolean userLoginStatus = getIntent().getBooleanExtra("loggedin",false);
            Log.d("Pattern complete:",Boolean.toString(userLoginStatus));
            if(!userLoginStatus) {
                startActivity(new Intent(MainActivity.this, LockScreenPattern.class));
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Crypton");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mSectionsPagerAdapter.notifyDataSetChanged();





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //clicking menu
        if (id == R.id.action_settings) {

            //creating settings dialogue
            Dialog d = new Dialog(MainActivity.this);
            d.setTitle("Security Settings");
            d.setContentView(R.layout.settingdialogue);
            d.show();
            //setting pattern and button inside the dialogue
            Boolean patternNotSet = true;
            Switch patternlockSwitch = d.findViewById(R.id.patternlockmenuswitch);
            Button confirm = d.findViewById(R.id.menulockbutton);

            //checking sharedprefernces for pattern saved in device
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String name = preferences.getString("Patternlock", "");
            if(!name.equalsIgnoreCase(""))
            {
                patternlockSwitch.setChecked(true);
                patternNotSet=false;
            }


            d.setCanceledOnTouchOutside(false);


            final Boolean finalpattern = patternNotSet;

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ensure no pattern is saved in SP and pattern is checked
                    if(patternlockSwitch.isChecked()&&finalpattern) {
                        Intent intent = new Intent(MainActivity.this,LockScreenPattern.class);
                        intent.putExtra("settingLock",true);
                        startActivity(intent);
                        d.dismiss();
                    }
                    //if pattern is unchecked
                    else if(!patternlockSwitch.isChecked()){
//
                        SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = spreferences.edit();
                        editor.remove("Patternlock");
                        editor.commit();
                        d.dismiss();
                    }

                }
            });


        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //returning the current tabs
            switch (position) {
                case 0:
                    Tab1 tab1 = new Tab1();
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
                case 2:
                    Tab3 tab3 = new Tab3();
                    return tab3;

                default:
                    throw new IllegalArgumentException("unexpected position: " + position);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
