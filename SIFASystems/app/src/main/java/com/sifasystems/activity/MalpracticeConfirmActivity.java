package com.sifasystems.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sifasystems.R;
import com.sifasystems.adapter.UserAdapter;
import com.sifasystems.helper.SQLiteHandler;
import com.sifasystems.model.Global;
import com.sifasystems.model.User;

import java.util.ArrayList;

public class MalpracticeConfirmActivity extends AppCompatActivity {

    private Button btn_confirm, btn_malpractice;
    private TextView txt_title;
    private EditText txt_malpractice;
    private ListView list_user;
    private ArrayList<User> arrayUsers = null;
    private UserAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malpractice_confirm);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_malpractice = (EditText) findViewById(R.id.txt_malpractice);
        list_user = (ListView) findViewById(R.id.list_user);
        list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                showAddItemDialog(MalpracticeConfirmActivity.this);
                getInvigilators();
            }
        });
        btn_malpractice = (Button) findViewById(R.id.btn_malpractice);
        btn_malpractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayUsers == null || arrayUsers.size() == 0) {
                    Toast.makeText(getApplicationContext(), "There has no any selected invigilator.", Toast.LENGTH_LONG).show();
                }
                else {
                    startActivity(new Intent(MalpracticeConfirmActivity.this, ChooseMalpracticeActivity.class));
                    overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                }
            }
        });

        if (Global.malpractice_type == 0) {
            txt_title.setText("Enter candidate Number");
        }
        else if (Global.malpractice_type == 1) {
            txt_title.setText("Enter Range of Student");
        }
        else if (Global.malpractice_type == 2) {
            txt_title.setText("Enter Center Code");
        }
    }

    private void getInvigilators() {
        String id = txt_malpractice.getText().toString();
        SQLiteHandler mydb = new SQLiteHandler(getApplicationContext());
        if (Global.malpractice_type == 0) {
            arrayUsers = mydb.getInvigilator(id);
        }
        else {
            arrayUsers = mydb.getInvigilators(id);
        }
        adapter = new UserAdapter(arrayUsers, getApplicationContext());
        list_user.setAdapter(adapter);

        if (arrayUsers != null && arrayUsers.size() == 0) {
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

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setView(taskEditText)

                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
