package com.sifasystems.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sifasystems.R;
import com.sifasystems.model.Global;

public class MalpracticeActivity extends AppCompatActivity {

    private Button btn_individual, btn_group_of_students, btn_whole_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malpractice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_individual = (Button) findViewById(R.id.btn_individual);
        btn_individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.malpractice_type = 0;
                startActivity(new Intent(MalpracticeActivity.this, MalpracticeConfirmActivity.class));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });
        btn_group_of_students = (Button) findViewById(R.id.btn_group_of_students);
        btn_group_of_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.malpractice_type = 1;
                startActivity(new Intent(MalpracticeActivity.this, MalpracticeConfirmActivity.class));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });
        btn_whole_center = (Button) findViewById(R.id.btn_whole_center);
        btn_whole_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.malpractice_type = 2;
                startActivity(new Intent(MalpracticeActivity.this, MalpracticeConfirmActivity.class));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_left_to_right, R.anim.activity_right_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
