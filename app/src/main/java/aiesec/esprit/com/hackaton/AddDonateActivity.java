package aiesec.esprit.com.hackaton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import aiesec.esprit.com.hackaton.Entity.Donation;
import aiesec.esprit.com.hackaton.Entity.Evenement;
import aiesec.esprit.com.hackaton.Entity.User;

import static aiesec.esprit.com.hackaton.fragments.EventsDisplayFragment.evenement;

public class AddDonateActivity extends AppCompatActivity {




    private RadioGroup radioGroupDonate;
    private RadioButton radioButton;
    private EditText quantity_id;
    AppCompatButton addEventButton;


    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;


    public static Evenement eventId;
    public static User userId;
    public static User user;


    String newVetement;
    String newNourriture;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donate);
        getSupportActionBar().setTitle("");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        radioGroupDonate = (RadioGroup) findViewById(R.id.radio_group_id);

        quantity_id = (EditText) findViewById(R.id.quantity_id);

        addEventButton = (AppCompatButton) findViewById(R.id.btn_add_donate);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // storageReference = storageReference.child(keyGnerated + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");

                // get selected radio button from radioGroup
                int selectedId = radioGroupDonate.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                //radioButton.getText()

                //Objet Donations
                Donation donation = new Donation();
                final String keyGnerated = mDatabase.push().getKey();
                donation.setId(keyGnerated);
                donation.setEvenementId(eventId.getId()); //****
                donation.setObjectUrl("");
                donation.setQuantity(quantity_id.getText().toString());
                donation.setTypeObject(radioButton.getText().toString());
                donation.setUserUid(mFirebaseAuth.getCurrentUser().getUid());



                mDatabase.child("Evenement").child(eventId.getId()).child("donations").child(keyGnerated).setValue(donation);







                String userDonations =String.valueOf( Integer.parseInt( user.getDonationNumber().toString())+1);
                System.out.println(userDonations);
                mDatabase.child("Users").child(mFirebaseAuth.getCurrentUser().getUid()).child("DonationNumber").setValue(userDonations);













                if (selectedId == R.id.radio_button_food) {

                     newNourriture =String.valueOf( Integer.parseInt( eventId.getCurrentNourriture().toString())+Integer.parseInt(quantity_id.getText().toString()));

                    mDatabase.child("Evenement").child(eventId.getId()).child("currentNourriture").setValue(newNourriture);




                }
                else {

                     newVetement =String.valueOf( Integer.parseInt( eventId.getCurrentVetement().toString())+Integer.parseInt(quantity_id.getText().toString()));

                    mDatabase.child("Evenement").child(eventId.getId()).child("currentVetement").setValue(newVetement);




                }




                Intent intent = new Intent(getApplication(), EvenementDetailActivity.class);
                AddDonateActivity.eventId = evenement;




                startActivity(intent);







            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);





    }




}
