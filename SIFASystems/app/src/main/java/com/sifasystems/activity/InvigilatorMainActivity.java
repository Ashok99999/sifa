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
import com.sifasystems.helper.SQLiteHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InvigilatorMainActivity extends AppCompatActivity {

    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Button btn_start_exam, btn_verify_candidate, btn_malpractice, btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invigilator_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_start_exam = (Button) findViewById(R.id.btn_start_exam);
        btn_start_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvigilatorMainActivity.this, ChoosePaperActivity.class));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });
        btn_verify_candidate = (Button) findViewById(R.id.btn_verify_candidate);
        btn_verify_candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                launchBarcodeScanner();
                startActivity(new Intent(InvigilatorMainActivity.this, NFCActivity.class).putExtra("STUDENT",true));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });
        btn_malpractice = (Button) findViewById(R.id.btn_malpractice);
        btn_malpractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvigilatorMainActivity.this, MalpracticeActivity.class));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });
        btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void launchBarcodeScanner() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            startActivity(new Intent(InvigilatorMainActivity.this, BarcodeScanActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(InvigilatorMainActivity.this, BarcodeScanActivity.class));
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the Scanner.", Toast.LENGTH_SHORT).show();
                }
                return;
        }
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
