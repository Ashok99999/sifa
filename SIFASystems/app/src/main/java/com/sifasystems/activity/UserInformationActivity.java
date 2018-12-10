package com.sifasystems.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sifasystems.R;
import com.sifasystems.model.Global;
import com.sifasystems.model.User;

public class UserInformationActivity extends AppCompatActivity {

    private Button btn_confirm;
    private TextView txt_id, txt_name, txt_location, txt_updated_at, tv_school_name, tv_center_ID,
    tvUserInfo, tvCenterInfo ;
    private LinearLayout llUser, llCenter;
    private ImageView imgCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();

        if (Global.user_type == false) {
            showUserUI();
            User user = Global.selectedUser;
            txt_id.setText(user.key);
            txt_name.setText(String.format("%s %s", user.fname, user.sname));
            txt_location.setText(user.location);
            txt_updated_at.setText(user.updated_at);

            switch (user.key.trim()){
                case "JB000001": imgCoordinator.setImageResource(R.drawable.logo);
                    break;
                case "JB000002": imgCoordinator.setImageResource(R.drawable.logo);
                    break;
                case "JB000003": imgCoordinator.setImageResource(R.drawable.logo);
                    break;
                case "JB000004": imgCoordinator.setImageResource(R.drawable.logo);
                    break;
                case "JB000005": imgCoordinator.setImageResource(R.drawable.logo);
                    break;
                case "JB000006": imgCoordinator.setImageResource(R.drawable.pic1);
                    break;
                case "JB000007": imgCoordinator.setImageResource(R.drawable.pic2);
                    break;
                case "JB000008": imgCoordinator.setImageResource(R.drawable.pic3);
                    break;
                case "JB000009": imgCoordinator.setImageResource(R.drawable.pic4);
                    break;
                case "JB000010": imgCoordinator.setImageResource(R.drawable.pic5);

            }
        }
        else {
            Intent getIntnt=getIntent();
           showCenterUI();
           if (getIntnt!=null){

               String centerID= getIntnt.getStringExtra("CENTER_NUMBER");
               String SchoolName = getIntnt.getStringExtra("SCHOOL_NAME");

               if (centerID!=null && SchoolName!= null){
                tv_center_ID.setText(centerID);
                tv_school_name.setText(SchoolName);
                   Log.e("CenterID",centerID);
                   Log.e("School Name", SchoolName);
               }
               else
                   Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();

           }


        }



        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.user_type == false) {
                    startActivity(new Intent(UserInformationActivity.this, NFCActivity.class));
                    overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                }
                else {
                    startActivity(new Intent(UserInformationActivity.this, InvigilatorMainActivity.class));
                    overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                }
            }
        });
    }

    private void findViews() {
        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_location = (TextView) findViewById(R.id.txt_location);
        txt_updated_at = (TextView) findViewById(R.id.txt_updated_at);
        tv_school_name = (TextView) findViewById(R.id.txt_school_name);
        tv_center_ID=findViewById(R.id.txt_center_id);

        tvUserInfo=findViewById(R.id.tv_user_info);
        tvCenterInfo=findViewById(R.id.tv_center_info);

        imgCoordinator=findViewById(R.id.img_coordinator);

        llCenter=findViewById(R.id.ll_center);
        llUser=findViewById(R.id.ll_user);
    }


    private void showUserUI(){
        llUser.setVisibility(View.VISIBLE);
        llCenter.setVisibility(View.GONE);
        tvUserInfo.setVisibility(View.VISIBLE);
        tvCenterInfo.setVisibility(View.GONE);
    }

    private void showCenterUI(){
        llCenter.setVisibility(View.VISIBLE);
        llUser.setVisibility(View.GONE);
        tvCenterInfo.setVisibility(View.VISIBLE);
        tvUserInfo.setVisibility(View.GONE);
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
