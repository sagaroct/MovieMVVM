package com.air.movieapp.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.air.movieapp.R;
import com.air.movieapp.adapter.SettingsAdapter;

/**
 * Created by sagar on 1/8/16.
 */
public class SettingsActivity extends AppCompatActivity implements SettingsAdapter.OnItemClickListener {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private RecyclerView mRecyclerViewSettings;
    private SharedPreferences mSharedPreferences;
    private SettingsAdapter mSettingsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        mRecyclerViewSettings = (RecyclerView) findViewById(R.id.rv_settings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewSettings.setLayoutManager(mLayoutManager);
        mSettingsAdapter = new SettingsAdapter(getResources().getStringArray(R.array.settings_array));
        mSettingsAdapter.setmOnItemClickListener(this);
        mRecyclerViewSettings.setAdapter(mSettingsAdapter);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException nPE) {
            nPE.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(String string) {
        if (string.equals(getString(R.string.date_format))) {
            showDialogToChangeDateFormat();
        } else {
        }
    }

    private void showDialogToChangeDateFormat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.date_format));
        CharSequence[] charSequence = new CharSequence[2];
        charSequence[0] = getString(R.string.month_day_yr);
        charSequence[1] = getString(R.string.yr_month_day);
        builder.setSingleChoiceItems(charSequence, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
