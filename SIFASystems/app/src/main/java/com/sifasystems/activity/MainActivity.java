package com.sifasystems.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sifasystems.R;
import com.sifasystems.helper.SQLiteHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btn_begin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_begin = (Button) findViewById(R.id.btn_begin);
        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectActivity.class));
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            }
        });

        saveMeta();
    }

    private void saveMeta() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        SQLiteHandler mydb = new SQLiteHandler(getApplicationContext());
        mydb.addUser("JB000001", "Femi", "Agoro", -33.86785, 151.20732, "Ikeja", 0, currentDateandTime);
        mydb.addUser("JB000002", "Juwon", "Ladega", -33.86785, 151.20732, "Ikorodu", 0, currentDateandTime);
        mydb.addUser("JB000003", "Seyi", "Adio", -33.86785, 151.20732, "Agege", 0, currentDateandTime);
        mydb.addUser("JB000004", "Dupe", "Williams", -33.86785, 151.20732, "Abeokuta", 0, currentDateandTime);
        mydb.addUser("JB000005", "Chioma", "Arinze", -33.86785, 151.20732, "Ibadan", 0, currentDateandTime);

        mydb.addUser("JB000006", "Yemi", "Agoro", -33.86785, 151.20732, "Federal Government College Ikorodu", 1, currentDateandTime);
        mydb.addUser("JB000007", "Bomi", "Kadiri", -33.86785, 151.20732, "Federal Government College Ilorin", 1, currentDateandTime);
        mydb.addUser("JB000008", "Feyi", "Sule", -33.86785, 151.20732, "Federal Government College Akure", 1, currentDateandTime);
        mydb.addUser("JB000009", "Sope", "Shomeru", -33.86785, 151.20732, "Federal Government College Benin", 1, currentDateandTime);
        mydb.addUser("JB000010", "Florence", "Ake", -33.86785, 151.20732, "Federal Government College Lagos", 1, currentDateandTime);

//        Toast.makeText(getApplicationContext(), "Registered Successful!!", Toast.LENGTH_LONG).show();
        mydb.addUserImage(100001, "9d df 3b b7", 61110010, "Acilo", "http://1.bp.blogspot.com/_sY37ini3P4Q/Sl8Rh9oQ2wI/AAAAAAAAAAw/OTRCUgl60f4/S240/ako.jpg");
        mydb.addUserImage(100002, "8d ff 57 b7", 6111002, "Jane", "http://1.bp.blogspot.com/-nYAqdM5wJac/VmPcpZpfo0I/AAAAAAAAXco/AG_i-TnTRxo/s1600/_DSC3175-Modifica.jpg");

        mydb.addCenterDetails(	"	01010111	"	,	"	Mount Saint Mary's College Namagunga Lugazi	"	);
        mydb.addCenterDetails(	"	01010212	"	,	"	Bishop Kagwa Senior Secondary School	"	);
        mydb.addCenterDetails(	"	01010313	"	,	"	Arasul Secondary School	"	);
        mydb.addCenterDetails(	"	01010414	"	,	"	Buvuma Secondary School	"	);
        mydb.addCenterDetails(	"	01010515	"	,	"	Amir Ntambi Memorial Secondary School	"	);
        mydb.addCenterDetails(	"	01010616	"	,	"	Sserwanga Lwanga Memorial Secondary School	"	);
        mydb.addCenterDetails(	"	01010717	"	,	"	Equator Academy Secondary School	"	);
        mydb.addCenterDetails(	"	01010818	"	,	"	Ggaba Secondary School	"	);
        mydb.addCenterDetails(	"	01010919	"	,	"	Busaana Central Secondary School	"	);
        mydb.addCenterDetails(	"	010110110	"	,	"	Bamusuuta Secondary School	"	);
        mydb.addCenterDetails(	"	010111111	"	,	"	Kiyombya Academy Secondary School	"	);
        mydb.addCenterDetails(	"	010112112	"	,	"	Hillside Academy Secondary School	"	);
        mydb.addCenterDetails(	"	010113113	"	,	"	Bulamazzi Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010114114	"	,	"	Kyabbuza Secondary School	"	);
        mydb.addCenterDetails(	"	010115115	"	,	"	Archbishop Kiwanuka Secondary School Kitovu	"	);
        mydb.addCenterDetails(	"	010116116	"	,	"	Namutamba S Secondary School	"	);
        mydb.addCenterDetails(	"	010117117	"	,	"	Mitala Maria Hill Secondary School	"	);
        mydb.addCenterDetails(	"	010118118	"	,	"	Kikandwa Umea Secondary School	"	);
        mydb.addCenterDetails(	"	010119119	"	,	"	Namataba Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010120120	"	,	"	Nakaseke SDA Secondary School	"	);
        mydb.addCenterDetails(	"	010121121	"	,	"	Ekitangala Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010122122	"	,	"	Kalisizo Voc Secondary School	"	);
        mydb.addCenterDetails(	"	010123123	"	,	"	Besweri Secondary School	"	);
        mydb.addCenterDetails(	"	010124124	"	,	"	Gayaza High School	"	);
        mydb.addCenterDetails(	"	01012521	"	,	"	Labira Girl's Secondary School	"	);
        mydb.addCenterDetails(	"	01012622	"	,	"	Naboa Premier Secondary School	"	);
        mydb.addCenterDetails(	"	01012723	"	,	"	Bududa Secondary School	"	);
        mydb.addCenterDetails(	"	01012824	"	,	"	Busowa Prime Secondary School	"	);
        mydb.addCenterDetails(	"	01012925	"	,	"	Kolir Comprehensive Secondary School	"	);
        mydb.addCenterDetails(	"	01013026	"	,	"	Tunyi Senior Secondary School	"	);
        mydb.addCenterDetails(	"	01013127	"	,	"	Bulaago Senior Secondary School	"	);
        mydb.addCenterDetails(	"	01013228	"	,	"	Buhehe Secondary School	"	);
        mydb.addCenterDetails(	"	01013329	"	,	"	Kangalaba Secondary School	"	);
        mydb.addCenterDetails(	"	010134210	"	,	"	Buligeya Memorial Secondary School	"	);
        mydb.addCenterDetails(	"	010135211	"	,	"	African Standard Academy	"	);
        mydb.addCenterDetails(	"	010136212	"	,	"	Mother Kevin Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010137213	"	,	"	Kaberamaido Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010138214	"	,	"	Nawaikoke Light Secondary School	"	);
        mydb.addCenterDetails(	"	010139215	"	,	"	Namwendwa High School	"	);
        mydb.addCenterDetails(	"	010140216	"	,	"	Gamatui Secondary School	"	);
        mydb.addCenterDetails(	"	010141217	"	,	"	Katakwi Standard Secondary School	"	);
        mydb.addCenterDetails(	"	010142218	"	,	"	Bright Future Secondary School	"	);
        mydb.addCenterDetails(	"	010143219	"	,	"	Dr. Aporu Okol Memorial Secondary School	"	);
        mydb.addCenterDetails(	"	010144220	"	,	"	Toswo Secondary School	"	);
        mydb.addCenterDetails(	"	010145221	"	,	"	Kyanvuma Light Secondary School	"	);
        mydb.addCenterDetails(	"	010146222	"	,	"	Kinyogoga Seed Secondary School	"	);
        mydb.addCenterDetails(	"	010147223	"	,	"	Mayuge International Sen Secondary School	"	);
        mydb.addCenterDetails(	"	010148224	"	,	"	Nakaloke Islamic Secondary School	"	);
        mydb.addCenterDetails(	"	010149225	"	,	"	Global Mixed High School	"	);
        mydb.addCenterDetails(	"	010150226	"	,	"	Kyabazinga Orthodox Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010151227	"	,	"	Okapel High School	"	);
        mydb.addCenterDetails(	"	010152228	"	,	"	Pallisa Modern Secondary School	"	);
        mydb.addCenterDetails(	"	010153229	"	,	"	Centre for Development and Enterprise Secondary School	"	);
        mydb.addCenterDetails(	"	010154230	"	,	"	Sironko Progressive Secondary School	"	);
        mydb.addCenterDetails(	"	010155231	"	,	"	Dakabela Comp Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010156232	"	,	"	Nabuyoga Mixed Senior Secondary School	"	);
        mydb.addCenterDetails(	"	01015731	"	,	"	Lotukei Seed Secondary School	"	);
        mydb.addCenterDetails(	"	01015832	"	,	"	Orungwaa Senior Secondary School	"	);
        mydb.addCenterDetails(	"	01015933	"	,	"	Lira Plawo Senior Secondary School	"	);
        mydb.addCenterDetails(	"	01016034	"	,	"	Aloi SS Secondary School	"	);
        mydb.addCenterDetails(	"	01016135	"	,	"	Kioga Progressive Secondary School	"	);
        mydb.addCenterDetails(	"	01016236	"	,	"	Namasale Seed Secondary School	"	);
        mydb.addCenterDetails(	"	01016337	"	,	"	Amuru High School	"	);
        mydb.addCenterDetails(	"	01016438	"	,	"	Apac Secondary School	"	);
        mydb.addCenterDetails(	"	01016539	"	,	"	Abira Town College	"	);
        mydb.addCenterDetails(	"	010166310	"	,	"	Dokolo Progressive Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010167311	"	,	"	Gulu Parents Secondary School	"	);
        mydb.addCenterDetails(	"	010168312	"	,	"	Jubilee Secondary School 	"	);
        mydb.addCenterDetails(	"	010169313	"	,	"	Saint Bakhita Girls' Secondary School	"	);
        mydb.addCenterDetails(	"	010170314	"	,	"	Homing Crane Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010171315	"	,	"	Father Aloysius Secondary School	"	);
        mydb.addCenterDetails(	"	010172316	"	,	"	Kotido Secondary School	"	);
        mydb.addCenterDetails(	"	010173317	"	,	"	Lamwo Kuc Kigen High School	"	);
        mydb.addCenterDetails(	"	010174318	"	,	"	Cranes Comprehensive Secondary School	"	);
        mydb.addCenterDetails(	"	010175319	"	,	"	Imvepi Self Help Secondary School	"	);
        mydb.addCenterDetails(	"	010176320	"	,	"	Nadunget Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010177321	"	,	"	FR Sierra Memorial Secondary School	"	);
        mydb.addCenterDetails(	"	010178322	"	,	"	Nakapiripirit Seed Secondary School	"	);
        mydb.addCenterDetails(	"	010179323	"	,	"	Kangole Girls Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010180324	"	,	"	Nebbi Progressive Secondary School	"	);
        mydb.addCenterDetails(	"	010181325	"	,	"	Kock Goma Secondary School	"	);
        mydb.addCenterDetails(	"	010182326	"	,	"	Adwari Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010183327	"	,	"	Okwang Secondary School	"	);
        mydb.addCenterDetails(	"	010184328	"	,	"	Orum Secondary School	"	);
        mydb.addCenterDetails(	"	010185329	"	,	"	Otuke Secondary School	"	);
        mydb.addCenterDetails(	"	010186330	"	,	"	Gregory's College Rukidi Senior Secondary School	"	);
        mydb.addCenterDetails(	"	01018741	"	,	"	Biiso War Memorial Secondary School	"	);
        mydb.addCenterDetails(	"	01018842	"	,	"	Bodong Secondary School	"	);
        mydb.addCenterDetails(	"	01018943	"	,	"	Bubandi Seed Secondary School	"	);
        mydb.addCenterDetails(	"	01019044	"	,	"	Bishop Ogez High School	"	);
        mydb.addCenterDetails(	"	01019145	"	,	"	Bushenyi High School	"	);
        mydb.addCenterDetails(	"	01019246	"	,	"	Bushenyi Pioneer High School	"	);
        mydb.addCenterDetails(	"	01019347	"	,	"	Bushenyi Progressive High School	"	);
        mydb.addCenterDetails(	"	01019448	"	,	"	Comprehensive Secondary School	"	);
        mydb.addCenterDetails(	"	01019549	"	,	"	Crane High School	"	);
        mydb.addCenterDetails(	"	010196410	"	,	"	Busiriba Parents Secondary School	"	);
        mydb.addCenterDetails(	"	010197411	"	,	"	Kirima Community Secondary School	"	);
        mydb.addCenterDetails(	"	010198412	"	,	"	Nyakabungo Cou Girls' Boarding Secondary School	"	);
        mydb.addCenterDetails(	"	010199413	"	,	"	Nyakinoni Secondary School	"	);
        mydb.addCenterDetails(	"	010200414	"	,	"	Nyamirama Seed Secondary School	"	);
        mydb.addCenterDetails(	"	010201415	"	,	"	Nyamirama Universal Secondary School	"	);
        mydb.addCenterDetails(	"	010202416	"	,	"	Nyamiyaga Secondary School	"	);
        mydb.addCenterDetails(	"	010203417	"	,	"	Rugyeyo Secondary School	"	);
        mydb.addCenterDetails(	"	010204418	"	,	"	Rusoroza Seed Senior Secondary School	"	);
        mydb.addCenterDetails(	"	010205419	"	,	"	Rwamanyonyi Secondary School	"	);
        mydb.addCenterDetails(	"	010206420	"	,	"	Saint Augustine Secondary School	"	);
        mydb.addCenterDetails(	"	010207421	"	,	"	Saint Charles Lwanga Secondary School	"	);
        mydb.addCenterDetails(	"	010208422	"	,	"	Saint Leo Girl's Secondary School	"	);
        mydb.addCenterDetails(	"	010209423	"	,	"	Ruhanga Adventist Secondary School	"	);
        mydb.addCenterDetails(	"	010210424	"	,	"	Ruhanga Sda Secondary School	"	);
        mydb.addCenterDetails(	"	010211425	"	,	"	Rukoni Secondary School	"	);
        mydb.addCenterDetails(	"	010212426	"	,	"	Rweikiniro Secondary School	"	);


    }
}
