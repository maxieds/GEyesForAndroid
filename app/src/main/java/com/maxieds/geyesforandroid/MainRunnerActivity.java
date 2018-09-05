package com.maxieds.geyesforandroid;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainRunnerActivity extends AppCompatActivity {

    private static MainRunnerActivity localInst;
    private SourceTrackerDrawable sourceTrackerImage;
    private ImageView sourceTrackerImageView;
    private ArrayList<EyeLayout> activeEyes;

    public static MainRunnerActivity getRunningInstance() {
        return localInst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        localInst = this;

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbarActionBar);
        actionBar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryText));
        actionBar.setTitle("GEyes for Android v" + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");
        actionBar.setSubtitleTextColor(getResources().getColor(R.color.colorPrimaryText));
        actionBar.setSubtitle("Eyes that intentionally follow you around online...");
        actionBar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Drawable toolbarLogo = getResources().getDrawable(R.drawable.main_eye_pointer_logo);
        actionBar.setLogo(toolbarLogo);
        actionBar.setBackground(getResources().getDrawable(R.drawable.action_bar_gradient));
        actionBar.setContentInsetsAbsolute(15, 75);

        // initialize source tracker drawable:
        sourceTrackerImage = new SourceTrackerDrawable(this);
        sourceTrackerImageView = (ImageView) findViewById(R.id.positionTrackerDrawable);
        //sourceTrackerImageView.setImageResource(0);
        sourceTrackerImageView.setBackground(sourceTrackerImage.getUntrackedDrawable());
        sourceTrackerImageView.setVisibility(View.VISIBLE);

        // TODO: start tracking positions;
        activeEyes = new ArrayList<>();






    }

    public void addNewEyes(String themeName) {
        EyeLayout nextEyes = EyeLayout.createEyeDisplayLayout(themeName);
        if(nextEyes == null) {
            throw new NullPointerException("Invalid theme name \"" + themeName + "\"");
        }
        activeEyes.add(nextEyes);
        ((GridLayout) findViewById(R.id.eyeGridLayout)).addView(nextEyes.getDisplayLayout());
    }

    public void createNewEyeThemeLauncher(View imageButton) {

        String[] themeList = EyeLayout.getAvailableThemeNames();
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        for(int t = 0; t < themeList.length; t++) {
            CheckBox cbTheme = new CheckBox(this);
            cbTheme.setTag(themeList[t]);
            cbTheme.setText(themeList[t]);
            cbTheme.setChecked(true);
            mainLayout.addView(cbTheme);
        }
        final LinearLayout mainLayoutFinal = mainLayout;

        AlertDialog adNewEyes = new AlertDialog.Builder(this)
                .setIcon(R.drawable.eye_theme_icon)
                .setTitle("Create New Eyes to Display...")
                .setMessage("Select a new eye theme from the dropdown menu below to proceed.")
                .setView(mainLayoutFinal)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Add Eyes!", new DialogInterface.OnClickListener() {
                    LinearLayout mainLayout2 = mainLayoutFinal;
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int c = 0; c < mainLayout2.getChildCount(); c++) {
                            CheckBox cbTheme = (CheckBox) mainLayout2.getChildAt(c);
                            if(cbTheme.isChecked()) {
                                MainRunnerActivity.getRunningInstance().addNewEyes(cbTheme.getTag().toString());
                            }
                        }
                        dialogInterface.dismiss();
                    }
                })
                .create();
        adNewEyes.show();

    }

    public void clearAllEyesLauncher(View imageButton) {
        activeEyes.clear();
        ((GridLayout) findViewById(R.id.eyeGridLayout)).removeAllViews();
    }

    public void changeTrackPathLauncher(View imageButton) {
        // TODO
    }

    public void aboutApplicationLauncher(View imageButton) {
        // TODO
    }

    public void reportBugLauncher(View imageButton) {
        // TODO
    }


}
