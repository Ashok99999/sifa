package com.sifasystems.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import java.io.IOException;

public class NFCActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_NFC = 200;
    private static final String TAG = "NFC ACTIVITY";
    PendingIntent mPendingIntent;
    private String[][] mTechLists;
    private TextView txt_info;
    private GifImageView gifImageView;
    private MediaPlayer mediaPlayer;
    private NfcAdapter nfcAdapter;
    private TextView tvSchool,
            tvFirstName,
            tvLastName,
            tvDOB,
            tvSex,
            tvExamNo,
            tvDistrict,
            tvSubject1,
            tvSubject2,
            tvSubject3,
            tvSubject4,
            tvSubject5,
            tvSubject6,
            tvSubject7,
            tvSubject8,
            tvSubject9,
            tvRegion;
    private ImageView imgAvator;
    private LinearLayout llScanItem, llScanedDetails, llButtons, llStudentSubjects, llConfirmButton;
    private IntentFilter mNdef;
    private IntentFilter[] mFilters;
    private Button btnAttendance, btnConfirm;
    private Tag tag;
    private boolean authenticated = false;
    private boolean student= false;
    private NfcManager mManager;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // TODO Auto-generated method stub
            mediaPlayer.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        mTechLists = new String[][]{new String[]{NfcA.class.getName()}};

        findviews();
        askPermissions();
        hideDetails();
        intializeMediaPlayer();
        wrapPendingIntent();

        CheckNFCEnabled();

        btnClickListeners();

    }

    private void differentiateInvigilatorStudents() {

        Intent intent= getIntent();
        if (intent!=null){
            student=intent.getBooleanExtra("STUDENT",false);
            if (student) showStudentDetails();
            else showInvigilatorDetails();
        }else showInvigilatorDetails();


    }

    private void showInvigilatorDetails() {
        llStudentSubjects.setVisibility(View.GONE);
        llButtons.setVisibility(View.GONE);
        llConfirmButton.setVisibility(View.VISIBLE);
    }

    private void showStudentDetails() {
        llStudentSubjects.setVisibility(View.VISIBLE);
        llButtons.setVisibility(View.VISIBLE);
        llConfirmButton.setVisibility(View.GONE);
    }

    private void btnClickListeners() {
        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        NFCActivity.this).create();

                // Setting Dialog Title
//                alertDialog.setTitle("Alert Dialog");

                // Setting Dialog Message
                alertDialog.setMessage("Attendance taken successfully!");

                // Setting Icon to Dialog
//                alertDialog.setIcon(R.drawable.tick);

                // Setting OK Button

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Scan Next Card", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hideDetails();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Back", new DialogInterface.OnClickListener() {
                       @Override
                public void onClick(DialogInterface dialog, int which) {
                           startActivity(new Intent(NFCActivity.this,InvigilatorMainActivity.class));
                           overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                    dialog.dismiss();
                }
            });

                // Showing Alert Message
                alertDialog.show();
            }
        });

//        btnMalprctice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog dialog = new AlertDialog.Builder(NFCActivity.this).create();
//
//                dialog.setMessage("Are you sure you want to submit the request?");
//
//                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(NFCActivity.this, "Request submitted succesfully", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        NFCActivity.this).create();

                // Setting Dialog Message
                alertDialog.setMessage("Invigilator confirmed successfully!");

                // Setting OK Button
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hideDetails();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });


    }

    private void CheckNFCEnabled() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            finish();
        }
    }

    private void wrapPendingIntent() {

        // PendingIntent object containing details of the scanned tag
        mPendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        try {
            // Accept all MIME types
            mNdef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            mNdef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Toast.makeText(this, String.format("MalformedMimeTypeException: %s", e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
            e.printStackTrace(); // to logcat
        }

        mFilters = new IntentFilter[]{mNdef, new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)};

    }

    private void askPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.NFC)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.NFC},
                    MY_PERMISSIONS_REQUEST_NFC);
        }
    }

    private void intializeMediaPlayer() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(this, R.raw.sound);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(onCompletionListener);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void findviews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_info = (TextView) findViewById(R.id.txt_info);
        gifImageView = (GifImageView) findViewById(R.id.GifImageView);

        mManager = (NfcManager) getSystemService(NFC_SERVICE);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        tvSchool = (TextView) findViewById(R.id.school);
        tvFirstName = (TextView) findViewById(R.id.first_name);
        tvLastName = (TextView) findViewById(R.id.last_name);
//        tvDOB= (TextView) findViewById(R.id.d_o_b);
        tvSex = (TextView) findViewById(R.id.sex);
        tvExamNo = (TextView) findViewById(R.id.exam_no);
        tvDistrict = (TextView) findViewById(R.id.district);
        tvSubject1 = (TextView) findViewById(R.id.subject1);
        tvSubject2 = (TextView) findViewById(R.id.subject2);
        tvSubject3 = (TextView) findViewById(R.id.subject3);
        tvSubject4 = (TextView) findViewById(R.id.subject4);
        tvSubject5 = (TextView) findViewById(R.id.subject5);
        tvSubject6 = (TextView) findViewById(R.id.subject6);
        tvSubject7 = (TextView) findViewById(R.id.subject7);
        tvSubject8 = (TextView) findViewById(R.id.subject8);
        tvSubject9 = (TextView) findViewById(R.id.subject9);
        tvRegion = (TextView) findViewById(R.id.region);

        imgAvator = (ImageView) findViewById(R.id.img_avator);

        llScanItem = findViewById(R.id.ll_scan_item);
        llScanedDetails = findViewById(R.id.ll_scaned_details);
        llButtons = findViewById(R.id.ll_butotns);
        llStudentSubjects = findViewById(R.id.ll_student_subjects);
        llConfirmButton = findViewById(R.id.ll_confirm_button);


        btnAttendance = findViewById(R.id.btn_attendance);
//        btnMalprctice = findViewById(R.id.btn_malpractice);
        btnConfirm = findViewById(R.id.btn_confirm);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_NFC: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    NfcManager mManager = (NfcManager) getSystemService(NFC_SERVICE);
                    nfcAdapter = NfcAdapter.getDefaultAdapter(this);

                    if (nfcAdapter == null) {
                        Toast.makeText(this,
                                "NFC NOT supported on this devices!",
                                Toast.LENGTH_LONG).show();
                        finish();
                    } else if (!nfcAdapter.isEnabled()) {
                        Toast.makeText(this,
                                "NFC NOT Enabled!",
                                Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this,
                                "Place Tag on Phone!",
                                Toast.LENGTH_LONG).show();
                    }

                    // PendingIntent object containing details of the scanned tag
                    mPendingIntent = PendingIntent.getActivity(
                            this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

                    try {
                        // Accept all MIME types
                        mNdef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
                        mNdef.addDataType("*/*");
                    } catch (IntentFilter.MalformedMimeTypeException e) {
                        Toast.makeText(this, String.format("MalformedMimeTypeException: %s", e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                        e.printStackTrace(); // to logcat
                    }

                    mFilters = new IntentFilter[]{mNdef, new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)};
                } else
                    Toast.makeText(this,
                            "Permission not granted :(",
                            Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if (nfcAdapter != null)
            nfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Make sure we have an adapter, otherwise this fails
        if (nfcAdapter != null)
            nfcAdapter.disableForegroundDispatch(this);
    }

    protected void onNewIntent(Intent intent) {
        // Make sure intent is TECH DISCOVERED; ignore the rest
        if (!NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()))
            return;

        // Retrieve extended data from the intent
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) return;

        Toast.makeText(this, "New Tag Found", Toast.LENGTH_SHORT).show();

        showDetails();

        new TagInfo().execute();
        new TagData().execute();
    }

    private void showDetails() {
        differentiateInvigilatorStudents();
        llScanedDetails.setVisibility(View.VISIBLE);
        llScanItem.setVisibility(View.INVISIBLE);
    }

    private void hideDetails() {
        llButtons.setVisibility(View.INVISIBLE);
        llConfirmButton.setVisibility(View.INVISIBLE);
        llScanedDetails.setVisibility(View.INVISIBLE);
        llScanItem.setVisibility(View.VISIBLE);

    }


    public String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        System.out.println("Decimal : " + temp.toString());

        return sb.toString().trim();
    }

    private String ByteArrayToHexString(byte[] inarray) { // converts byte
        // arrays to string
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
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

    private class TagInfo extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        MifareClassic mMfc = MifareClassic.get(tag);


        @Override
        protected void onPreExecute() {
            try {
                mMfc.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog = new ProgressDialog(NFCActivity.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String tagInfo = "";

//            tagInfo += "\nTag Id: ";
            byte[] tagId = tag.getId();
//                tagInfo += "length = " + tagId.length +"\n";
            for (int i = 0; i < tagId.length; i++) {
                tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
            }
            tagInfo += "\n";

            String[] techList = tag.getTechList();
//            tagInfo += "\nTech List\n";
//                tagInfo += "length = " + techList.length +"\n";
//            for (int i = 0; i < techList.length; i++) {
//                tagInfo += techList[i] + "\n ";
//            }
            return tagInfo.trim();
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
//            ArrayList<UserImage> userImages = new ArrayList<>();
//            SQLiteHandler mydb = new SQLiteHandler(getApplicationContext());
//            userImages = mydb.getImages(s);
//            Glide.with(MainActivity.this)
//                    .load(userImages.get(0).imageURL)
//                    .into(imgAvator);
//            textViewInfo.setText(s);
        }
    }

    private class TagData extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;
        MifareClassic mMfc = MifareClassic.get(tag);
        String rawData = "";
        String School,
                FirstName,
                LastName,
                ExamNo,
                District,
                Subject1,
                Subject2,
                Subject3,
                Subject4,
                Subject5,
                Subject6,
                Subject7,
                Subject8,
                Subject9,
                Region,
                sex;
        Bitmap avator;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(NFCActivity.this);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Loop through all sectors
//            for (int sector = 0; sector < (mMfc.getSectorCount()); ++sector) {
            for (int sector = 0; sector < 16; ++sector) {

                rawData += ("\n Sector " + sector + " : ");
                authenticated = autheticateSector(sector);

                // Authentication to sector failed, invalid key(s)
                if (!authenticated) {
                    Log.w(TAG, "Authentication to sector failed, invalid key(s)");
                    rawData += ("Authentication Failed XXX \n");
//                    continue;
                } else {

                    readSectorData(sector);
                }
            }
            return rawData;
        }

        private void readSectorData(int sector) {
            switch (sector) {
                case 0:
                    School = convertHexToString(readBlockData(sector));
                    break;
                case 1:
                    FirstName = convertHexToString(readBlockData(sector));
                    break;
                case 2:
                    LastName = convertHexToString(readBlockData(sector));
                    break;
                case 3:
                    sex = convertHexToString(readBlockData(sector));
                    break;
                case 4:
                    ExamNo = convertHexToString(readBlockData(sector));
                    break;
                case 5:
                    District = convertHexToString(readBlockData(sector));
                    break;
                case 6:
                    Subject1 = convertHexToString(readBlockData(sector));
                    break;
                case 7:
                    Subject2 = convertHexToString(readBlockData(sector));
                    break;
                case 8:
                    Subject3 = convertHexToString(readBlockData(sector));
                    break;
                case 9:
                    Subject4 = convertHexToString(readBlockData(sector));
                    break;
                case 10:
                    Subject5 = convertHexToString(readBlockData(sector));
                    break;
                case 11:
                    Subject6 = convertHexToString(readBlockData(sector));
                    break;
                case 12:
                    Subject7 = convertHexToString(readBlockData(sector));
                    break;
                case 13:
                    Subject8 = convertHexToString(readBlockData(sector));
                    break;
                case 14:
                    Subject9 = convertHexToString(readBlockData(sector));
                    break;
                case 15:
                    Region = convertHexToString(readBlockData(sector));
                    break;
                case 32:
//                    avator = readImage(sector);

            }
        }

//        private Bitmap readImage(int sector) {
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//            for (int block = 0; (block < mMfc.getBlockCountInSector(sector)); ++block) {
//                // Get block number for sector + block
//                int blockIndex = (mMfc.sectorToBlock(sector) + block);
//
//
//                try {
//                    if (sector == 32 && block < 15) {
//                        byte[] data = mMfc.readBlock(blockIndex);
//                        outputStream.write(data);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            return convertByteToImage(outputStream.toByteArray());
//        }

        private Bitmap convertByteToImage(byte[] data) {

            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }

        private String readBlockData(int sector) {
            String blockvalues = "";


            // Read all blocks in sector
            for (int block = 0; (block < mMfc.getBlockCountInSector(sector)); ++block) {
                // Get block number for sector + block
                int blockIndex = (mMfc.sectorToBlock(sector) + block);

                try {

                    // Create a string of bits from block data and fix endianness
                    // http://en.wikipedia.org/wiki/Endianness


                    if (sector <= 15 && block < 3) {
                        // Read block data from block index
                        byte[] data = mMfc.readBlock(blockIndex);
                        if (!(sector == 0 && block == 0)) {
                            String temp = ByteArrayToHexString(data);
                            blockvalues += temp;
                            Log.i(TAG, "Block " + blockIndex + " : " + temp);
                            rawData += ("Block " + blockIndex + " : " + temp + "\n");
                        }

                    }

//                    temp=convertHexToString(temp);


//                        for (int x = 0; x < temp.length(); x += 8)
//                            bits.append(new StringBuilder(temp.substring(x, x + 8)).reverse().toString());

//                        Log.w(TAG, "New byte string is: " + bits.toString());


                } catch (IOException e) {
                    Log.e(TAG, "Exception occurred  " + e.getLocalizedMessage());
                }
            }
            return blockvalues.trim();
        }

        private boolean autheticateSector(int sector) {
            authenticated = false;
            Log.i(TAG, "Authenticating Sector: " + sector + " It contains Blocks: " + mMfc.getBlockCountInSector(sector));


            try {
                if (mMfc.authenticateSectorWithKeyA(sector, MifareClassic.KEY_DEFAULT)) {
                    authenticated = true;
                    Log.w(TAG, "Authenticated!!! ");
                    rawData += ("Authenticated!!! \n");
                    //                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!authenticated) {

                autheticateSector(sector);
            }
//                if (mMfc.authenticateSectorWithKeyB(sector,MifareClassic.KEY_DEFAULT)){
//                    authenticated = true;
//                    Log.w(TAG, "Authenticated for read and write!!!");
//                }
            return authenticated;
        }

        @Override
        protected void onPostExecute(String s) {
//            s += "\n Card read successfully \n";
//            tvRawData.setText(s);
            progressDialog.dismiss();
            setDataInTV();

            try {
                mMfc.close();
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (v != null) v.vibrate(100);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void setDataInTV() {
//            ExamNo="010101013";
            setImage(ExamNo);
            tvFirstName.setText(FirstName);
            tvLastName.setText(LastName);
            tvSex.setText(sex);
            tvExamNo.setText(ExamNo);

            tvSchool.setText(School);
            tvDistrict.setText(District);
            tvRegion.setText(Region);

            tvSubject1.setText(Subject1);
            tvSubject2.setText(Subject2);
            tvSubject3.setText(Subject3);
            tvSubject4.setText(Subject4);
            tvSubject5.setText(Subject5);
            tvSubject6.setText(Subject6);
            tvSubject7.setText(Subject7);
            tvSubject8.setText(Subject8);
            tvSubject9.setText(Subject9);


        }

        public void setImage(String examNo) {

            switch (examNo) {
                case "010101001":
                    imgAvator.setImageResource(R.drawable.one1);
                    break;
                case "010101002":
                    imgAvator.setImageResource(R.drawable.two2);
                    break;
                case "010101003":
                    imgAvator.setImageResource(R.drawable.three3);
                    break;
                case "010101004":
                    imgAvator.setImageResource(R.drawable.four4);
                    break;
                case "010101005":
                    imgAvator.setImageResource(R.drawable.five5);
                    break;
                case "010101012":
                    imgAvator.setImageResource(R.drawable.six6);
                    break;
                case "010101013":
                    imgAvator.setImageResource(R.drawable.seven7);
                    break;
                case "010101014":
                    imgAvator.setImageResource(R.drawable.eight8);
                    break;
                case "010101015":
                    imgAvator.setImageResource(R.drawable.nine9);
                    break;
                case "010101016":
                    imgAvator.setImageResource(R.drawable.ten10);
                    break;
                default:
                    imgAvator.setImageResource(R.drawable.dummyavatar);
                    break;
            }
        }
    }
}
