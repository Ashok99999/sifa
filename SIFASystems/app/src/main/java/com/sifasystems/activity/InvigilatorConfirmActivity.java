package com.sifasystems.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sifasystems.R;
import com.sifasystems.adapter.CenterAdapter;
import com.sifasystems.helper.SQLiteHandler;
import com.sifasystems.model.Center;

import java.util.ArrayList;

public class InvigilatorConfirmActivity extends AppCompatActivity {

    private Button btn_confirm;
    private EditText txt_number;
    private ListView list_user;
    private ArrayList<Center> arrayCenters = null;
    private CenterAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invigilator_confirm);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_number = (EditText) findViewById(R.id.txt_number);
        list_user = (ListView) findViewById(R.id.list_user);
        list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(InvigilatorConfirmActivity.this, UserInformationActivity.class)
                        .putExtra("CENTER_NUMBER", arrayCenters.get(i).centerID)
                        .putExtra("SCHOOL_NAME", arrayCenters.get(i).schoolName));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                getCenters();
            }
        });
    }

    private void getCenters() {
        String id = txt_number.getText().toString();
        SQLiteHandler mydb = new SQLiteHandler(getApplicationContext());
        arrayCenters = mydb.getCenterDetails();
        adapter = new CenterAdapter(arrayCenters, getApplicationContext());
        list_user.setAdapter(adapter);

        if (arrayCenters != null && arrayCenters.size() == 0) {
            Toast.makeText(getApplicationContext(), "Sorry! your entry is not recognised.", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_left_to_right, R.anim.activity_right_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
