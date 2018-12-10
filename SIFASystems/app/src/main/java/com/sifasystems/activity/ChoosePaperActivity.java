package com.sifasystems.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.sifasystems.R;
import com.sifasystems.model.Global;

import javax.microedition.khronos.opengles.GL;

public class ChoosePaperActivity extends AppCompatActivity {

    private Button btn_confirm;
    private Spinner spinner_paper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_paper);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_paper = (Spinner) findViewById(R.id.spinner_paper);
        spinner_paper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (spinner_paper.getSelectedItemId() == 0) {
                Toast.makeText(getApplicationContext(), "Please choose a paper.", Toast.LENGTH_LONG).show();
            }
            else {
                Global.selectedPaper = (int) spinner_paper.getSelectedItemId();
                startActivity(new Intent(ChoosePaperActivity.this, ExamStartActivity.class));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
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
