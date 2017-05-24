package aiesec.esprit.com.hackaton.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import aiesec.esprit.com.hackaton.Entity.User;
import aiesec.esprit.com.hackaton.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by maher on 11/02/2017.
 */

public class ProfileFragment extends Fragment  {



    public DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;
    private TextView tvName,tvEmail,tvLike,tvDislike,tvDonation;
    private CircleImageView imgProfile;





    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
      tvName =(TextView) view.findViewById(R.id.TvName);
      tvEmail=(TextView) view.findViewById(R.id.TvEmail);
      tvDonation =(TextView) view.findViewById(R.id.tvNbDon);
      tvLike =(TextView) view.findViewById(R.id.tvLike);
        imgProfile=(CircleImageView) view.findViewById(R.id.profile_image);
        displayEvents();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

  public void displayEvents() {

    mFirebaseAuth = FirebaseAuth.getInstance();
    mFirebaseAuth.getCurrentUser().getUid();
    mDatabase = FirebaseDatabase.getInstance().getReference();

    DatabaseReference mDatabase;
    mDatabase = FirebaseDatabase.getInstance().getReference();


    mDatabase.child("Users").child(mFirebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        User user = dataSnapshot.getValue(User.class);
        tvName.setText(user.getUserName());
        tvEmail.setText( mFirebaseAuth.getCurrentUser().getEmail());
        tvDonation.setText(user.getDonationNumber()+"\n"+"Donnations");
        tvLike.setText(user.getVote()+"\n"+"Votes");
          Picasso.with(getActivity()).load(Uri.parse(user.getPictureUrl())).into(imgProfile);



      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }


    @Override
    public void onStart() {
        super.onStart();
        displayEvents();
    }
}
