package com.sifasystems.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sifasystems.R;
import com.sifasystems.model.Global;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
public class SelectActivity extends AppCompatActivity {

    private Button btn_next;
    private RadioRealButton radio_coordinator, radio_invigilator;
    private RadioRealButtonGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radio_coordinator = (RadioRealButton) findViewById(R.id.radio_coordinator);
        radio_invigilator = (RadioRealButton) findViewById(R.id.radio_invigilator);
        group = (RadioRealButtonGroup) findViewById(R.id.group);

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_coordinator.isChecked()) {
                    Global.user_type = false;
                    startActivity(new Intent(SelectActivity.this, CoordinatorConfirmActivity.class));
                    overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                }
                else if (radio_invigilator.isChecked()) {
                    Global.user_type = true;
                    startActivity(new Intent(SelectActivity.this, InvigilatorConfirmActivity.class));
                    overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                }
                else {
                    Toast.makeText(SelectActivity.this, "Please select profile. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        group.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                if (button == radio_coordinator) {
                    radio_coordinator.setDrawable(getResources().getDrawable(R.drawable.radio_check));
                    radio_invigilator.setDrawable(getResources().getDrawable(R.drawable.radio_uncheck));
                }
                else {
                    radio_coordinator.setDrawable(getResources().getDrawable(R.drawable.radio_uncheck));
                    radio_invigilator.setDrawable(getResources().getDrawable(R.drawable.radio_check));
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
