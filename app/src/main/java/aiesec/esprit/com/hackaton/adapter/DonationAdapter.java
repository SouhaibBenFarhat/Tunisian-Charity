package aiesec.esprit.com.hackaton.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import aiesec.esprit.com.hackaton.DonationListActivity;
import aiesec.esprit.com.hackaton.Entity.Donation;
import aiesec.esprit.com.hackaton.Entity.User;
import aiesec.esprit.com.hackaton.R;

import static aiesec.esprit.com.hackaton.R.id.btn_no;
import static aiesec.esprit.com.hackaton.R.id.btn_yes;

/**
 * Created by bechirkaddech on 2/1/17.
 */

public class DonationAdapter extends ArrayAdapter<Donation> {





    Context context;
    int ressource;
    public DatabaseReference mDatabase;






    public DonationAdapter(Context context, int resource, List<Donation> products) {
        super(context, resource, products);
        this.context=context;
        this.ressource=resource;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        mDatabase = FirebaseDatabase.getInstance().getReference();


        View view=convertView;
        DonationAdapter_Holder holder=new DonationAdapter_Holder();
        if (view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource,parent,false);

            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            holder.tv_type = (TextView) view.findViewById(R.id.tv_type);
            holder.picture = (ImageView) view.findViewById(R.id.picture);

            holder.btn_yes = (Button) view.findViewById(btn_yes);
            holder.btn_no = (Button) view.findViewById(btn_no);




            holder.btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                    alertDialog.setTitle("User upvoted");

                    // Setting Dialog Message
                    alertDialog
                            .setMessage("you approve that this user was present during the charity event 3 likes will be added" +
                                    "to his score");

                    // On pressing Settings button
                    alertDialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });


                    // Showing Alert Message
                    alertDialog.show();

                }
            });


            holder.btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                    alertDialog.setTitle("User Reported");

                    // Setting Dialog Message
                    alertDialog
                            .setMessage("you approve that this user wasn't present during the charity event 3 likes will be taken" +
                                    "to his score");

                    // On pressing Settings button
                    alertDialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });


                    // Showing Alert Message
                    alertDialog.show();

                }
            });






            view.setTag(holder);
        }
        else {
            holder=(DonationAdapter_Holder) view.getTag();
        }


        final DonationAdapter_Holder finalHolder = holder;
        mDatabase.child("Users").child(getItem(position).getUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("********");
                System.out.println(dataSnapshot);

                User user = dataSnapshot.getValue(User.class);



                finalHolder.tv_name.setText(user.getUserName());
                Picasso.with(getContext()).load(Uri.parse(user.getPictureUrl())).into(finalHolder.picture);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        holder.tv_quantity.setText(getItem(position).getQuantity());
        holder.tv_type.setText(getItem(position).getTypeObject());




        // System.out.println(getItem(position).getProfilePictureURL());





        return view;
    }


    class DonationAdapter_Holder{
        TextView tv_name;
        TextView tv_quantity;
        TextView tv_type;
        Button btn_yes,btn_no;

        ImageView picture;


    }





}
