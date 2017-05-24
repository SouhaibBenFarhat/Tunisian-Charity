package aiesec.esprit.com.hackaton;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aiesec.esprit.com.hackaton.Entity.Donation;
import aiesec.esprit.com.hackaton.Entity.Evenement;
import aiesec.esprit.com.hackaton.Entity.Rating;
import aiesec.esprit.com.hackaton.Entity.User;
import aiesec.esprit.com.hackaton.notification.psuhNotificationAllUsers;

public class EvenementDetailActivity extends AppCompatActivity {


    public Button btn_viewDonation, btn_addDonation, btn_like, btn_dislike;
    AppCompatButton shareFacebookButton;
    public static String evenementid;
    public static String userEventUid;
    public static String userId;
    private DatabaseReference mDatabase;
    private IconRoundCornerProgressBar progressOne;
    private IconRoundCornerProgressBar progressTwo;
    public TextView tv_titleOne ,tv_two ,tv_one;
    public TextView tv_titleTwo, tv_title, tv_description, tv_dateDebut, tv_dateFin, tv_gouvernat, tv_adresse;
    public ImageView backdrop;
    public Evenement evenement;
    public User user;
    public User user2;
    private FirebaseAuth mFirebaseAuth;











    public  int on ;
    public  int cn;
    public  int ov;
    public  int cv;




    final Rating ratingB = new Rating();
    public boolean test = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_evenement_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final String idUserVoted = mFirebaseAuth.getCurrentUser().getUid();
        final String idUserVote = userEventUid;


        progressOne = (IconRoundCornerProgressBar) findViewById(R.id.progressOne);
        progressTwo = (IconRoundCornerProgressBar) findViewById(R.id.progressTwo);
        tv_titleOne = (TextView) findViewById(R.id.tv_titleOne);
        tv_titleTwo = (TextView) findViewById(R.id.tv_titleTwo);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_dateDebut = (TextView) findViewById(R.id.tv_dateDebut);
        tv_dateFin = (TextView) findViewById(R.id.tv_dateFin);
        tv_gouvernat = (TextView) findViewById(R.id.tv_gouvernat);
        tv_adresse = (TextView) findViewById(R.id.tv_adresse);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        shareFacebookButton = (AppCompatButton) findViewById(R.id.btn_facebook_share);




        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);

        shareFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog shareDialog;
                FacebookSdk.sdkInitialize(EvenementDetailActivity.this);
                shareDialog = new ShareDialog(EvenementDetailActivity.this);
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(evenement.getTitle())
                        .setContentDescription(
                                evenement.getDescription())

                        .setContentUrl(Uri.parse("https://www.facebook.com/events/667240263448923/")).build();
                shareDialog.show(linkContent);
            }
        });


        //fetch event info


        mDatabase.child("Evenement").child(evenementid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                mDatabase.child("Evenement").child(evenementid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        evenement = dataSnapshot.getValue(Evenement.class);

                        progressOne.setMax(Float.parseFloat(evenement.getObjectifNourriture()));
                        progressTwo.setMax(Float.parseFloat(evenement.getObjectiVetement()));

                        progressOne.setProgress(Float.parseFloat(evenement.getCurrentNourriture()));
                        progressTwo.setProgress(Float.parseFloat(evenement.getCurrentVetement()));

                        tv_title.setText(evenement.getTitle());
                        tv_description.setText(evenement.getDescription());


                        tv_dateDebut.setText("From :" + evenement.getDateDebut());
                        tv_dateFin.setText("To :" + evenement.getDateFin());


                        tv_gouvernat.setText(evenement.getGouvernerat());
                        tv_adresse.setText(evenement.getAdresse());


                        tv_one.setText(evenement.getCurrentNourriture().toString()+"/"+evenement.getObjectifNourriture().toString());
                        tv_two.setText(evenement.getCurrentVetement().toString()+"/"+evenement.getObjectiVetement().toString());








                        Picasso.with(getApplicationContext()).load(evenement.getImageUrl()).into(backdrop);


                        mDatabase.child("Users").child(evenement.getUserUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                System.out.println("********");
                                System.out.println(dataSnapshot);

                                user = dataSnapshot.getValue(User.class);


                                tv_titleOne.setText(user.getUserName());
                                tv_titleTwo.setText(user.getVote());


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




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




mDatabase.child("Evenement").child(evenementid).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        System.out.println("ppppppppppp");
        System.out.println(dataSnapshot);

        Evenement ev = dataSnapshot.getValue(Evenement.class);



        on  = Integer.parseInt(ev.getObjectifNourriture().toString());
        cn=Integer.parseInt(ev.getCurrentNourriture().toString());

        ov = Integer.parseInt(ev.getObjectiVetement().toString());
        cv = Integer.parseInt(ev.getCurrentVetement().toString());






        if(cn>=on && cv>=ov)
        {

            System.out.println("sendddddddd");
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    psuhNotificationAllUsers.sendAndroidNotification("/topics/news",
                            "TEST", "HACK");

                    return null;
                }
            }.execute();



        }







    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});





        mDatabase.child("Users").child(idUserVoted).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("********");
                System.out.println(dataSnapshot);

                user2 = dataSnapshot.getValue(User.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //fetch user info


        btn_viewDonation = (Button) findViewById(R.id.btn_viewDonation);
        btn_addDonation = (Button) findViewById(R.id.btn_addDonation);


        btn_viewDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (evenement.getDonations() == null) {

                    Toast.makeText(getApplicationContext(), "There is no donnations", Toast.LENGTH_SHORT).show();

                } else {
                    ArrayList<Donation> ListTopass = new ArrayList<Donation>(evenement.getDonations().values());


                    Intent intent = new Intent(getApplication(), DonationListActivity.class);
                    DonationListActivity.donationArray = ListTopass;
                    startActivity(intent);
                }


            }
        });


        btn_addDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplication(), AddDonateActivity.class);
                AddDonateActivity.eventId = evenement;
                AddDonateActivity.user = user2;
                startActivity(intent);

            }
        });


        //Like Dislike


        btn_like = (Button) findViewById(R.id.btn_like);
        btn_dislike = (Button) findViewById(R.id.btn_dislike);


        mDatabase.child("Rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println(dataSnapshot.child(userEventUid + "_" + idUserVoted).child("note").getValue());
                if (dataSnapshot.child(userEventUid + "_" + idUserVoted).child("note").getValue() == null) {
                    test = false;
                } else {
                    test = true;
                    ratingB.setNote(dataSnapshot.child(userEventUid + "_" + idUserVoted).child("note").getValue() + "");
                }


                btn_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final Rating ratingU = new Rating();

                        ratingU.setNote("-1");


                        if (test == true && ratingB.getNote().toString().equals("1")) {
                            System.out.println("************************************* vote = " + (Integer.parseInt(user.getVote()) + 1 - Integer.parseInt(ratingB.getNote())));
                            mDatabase.child("Users").child(userEventUid).child("Vote").setValue((Integer.parseInt(user.getVote()) + 1 + ""));
                            mDatabase.child("Rating").child(userEventUid + "_" + idUserVoted).setValue(ratingU);

                        } else {
                            mDatabase.child("Rating").child(userEventUid + "_" + idUserVoted).setValue(ratingU);
                            mDatabase.child("Users").child(userEventUid).child("Vote").setValue((Integer.parseInt(user.getVote()) + 1 + ""));

                        }


                    }
                });


                btn_dislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Rating ratingU = new Rating();
                        ratingU.setNote("1");


                        if ((test == true) && (ratingB.getNote().toString().equals("-1"))) {
                            System.out.println("************************************* vote = " + (Integer.parseInt(user.getVote()) - 1 - Integer.parseInt(ratingB.getNote())));
                            mDatabase.child("Users").child(userEventUid).child("Vote").setValue((Integer.parseInt(user.getVote()) - 1 + ""));
                            mDatabase.child("Rating").child(idUserVote + "_" + idUserVoted).setValue(ratingU);

                        } else {
                            mDatabase.child("Rating").child(idUserVote + "_" + idUserVoted).setValue(ratingU);
                        }


                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        try {
            // Picasso.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


}
