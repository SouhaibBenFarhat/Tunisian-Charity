package aiesec.esprit.com.hackaton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import aiesec.esprit.com.hackaton.Entity.Donation;
import aiesec.esprit.com.hackaton.adapter.DonationAdapter;

public class DonationListActivity extends AppCompatActivity {



    public static ArrayList<Donation> donationArray =new ArrayList<Donation>();
    public DonationAdapter donationAdapter ;
    public ListView lv_donationList ;


    public Button btn_yes , btn_no ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list);






        lv_donationList=(ListView) findViewById(R.id.lv_donationList);
        donationAdapter = new DonationAdapter(this, R.layout.item_donation, donationArray);












        getSupportActionBar().setTitle("                    "+donationArray.size()+ " Contributors");


        lv_donationList.setAdapter(donationAdapter);
    }
}
