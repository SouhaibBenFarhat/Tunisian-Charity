package aiesec.esprit.com.hackaton;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import aiesec.esprit.com.hackaton.Entity.Donation;
import aiesec.esprit.com.hackaton.Entity.Evenement;
import aiesec.esprit.com.hackaton.notification.psuhNotificationAllUsers;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    private Button btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseMessaging.getInstance().subscribeToTopic("news");


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirebaseAuth = FirebaseAuth.getInstance();
        insertTest();
        insertTest();
        insertTest();
        fetchTest();

        btn = (Button) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        psuhNotificationAllUsers.sendAndroidNotification("/topics/news",
                                "TEST", "HACK");

                        return null;
                    }
                }.execute();

            }
        });
        SharedPreferences prefs = getSharedPreferences("deviceToken", MODE_PRIVATE);

        String token = prefs.getString("Token", "Empty");//"No name defined" is the default value.

        if (!token.equals("Empty")){
            mDatabase.child("Users").child(mFirebaseAuth.getCurrentUser().getUid()).child("Token").setValue(token);
        }else {
            String tokenFirebase = FirebaseInstanceId.getInstance().getToken();
            mDatabase.child("Users").child(mFirebaseAuth.getCurrentUser().getUid()).child("Token").setValue(tokenFirebase);

        }


    }




    public void insertTest(){

      String keyGnerated = mDatabase.push().getKey();

        ArrayList<Donation> donations = new ArrayList<Donation>();

        Donation donation = new Donation();
        donation.setEvenementId(keyGnerated);
        donation.setUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        donation.setObjectUrl("test");
        donation.setQuantity("20");
        donation.setTypeObject("argent");
        donation.setId("mmmmmmmmmm");

        mDatabase.push().getKey();

        donations.add(donation);
        donations.add(donation);





        Evenement evenement = new Evenement();
        evenement.setId(keyGnerated);
        evenement.setAdresse("marsa");
        evenement.setCurrentArgent("0");
        evenement.setCurrentNourriture("0");
        evenement.setCurrentVetement("0");
        evenement.setObjectifArgent("20");
        evenement.setObjectifNourriture("30");
        evenement.setObjectiVetement("40");
        evenement.setDescription("Description");
        evenement.setGouvernerat("tunis");
        evenement.setDateDebut("25/20");
        evenement.setDateFin("40/40");
        evenement.setImageUrl("test");
        evenement.setUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        evenement.setTitle("title");
        //evenement.setDonations(donations);




        mDatabase.child("Evenement").child(keyGnerated).setValue(evenement);





    }



    public void fetchTest(){


        mDatabase.child("Evenement").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Evenement evenement = dataSnapshot.getValue(Evenement.class);
                System.out.println(dataSnapshot);
                System.out.println("test");
                System.out.println(evenement.getAdresse());
//                for (Donation d : evenement.getDonations())
//                {
//                    System.out.println(d.getEvenementId());
//                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }






}
