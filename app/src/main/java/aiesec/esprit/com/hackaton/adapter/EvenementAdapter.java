package aiesec.esprit.com.hackaton.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import aiesec.esprit.com.hackaton.Entity.Evenement;
import aiesec.esprit.com.hackaton.Entity.User;
import aiesec.esprit.com.hackaton.R;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by maher on 11/02/2017.
 */
public class EvenementAdapter extends RecyclerView.Adapter<EvenementAdapter.MyViewHolder2> {


    List<Evenement> evenements;
    private LayoutInflater inflater;
    private Context context;


    public EvenementAdapter(Context context, List<Evenement> evenements) {
        inflater = LayoutInflater.from(context);
        this.evenements = evenements;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int position) {
        Evenement current = evenements.get(position);
        holder.setData(current, position);

    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_main_list_item, parent, false);
        MyViewHolder2 holder = new MyViewHolder2(view);

        return holder;
    }


    @Override
    public int getItemCount() {
        return evenements.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, tvDescription, username, startDate, endDate;
        ImageView imgThumb;
        CircleImageView userImage;

        int position;

        Evenement current;

        public MyViewHolder2(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            imgThumb = (ImageView) itemView.findViewById(R.id.img_row);
            tvDescription = (TextView) itemView.findViewById(R.id.event_disp_description);
            userImage = (CircleImageView) itemView.findViewById(R.id.user_event);
            username = (TextView) itemView.findViewById(R.id.tv_username);
            startDate = (TextView) itemView.findViewById(R.id.start_date);
            endDate = (TextView) itemView.findViewById(R.id.end_date);
        }

        public void setData(Evenement current, int position) {
            this.title.setText(current.getTitle());
            this.tvDescription.setText(current.getDescription());
            this.position = position;
            this.current = current;

            startDate.setText(current.getDateDebut());
            endDate.setText(current.getDateFin());


            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();


            mDatabase.child("Users").child(current.getUserUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getValue(User.class);
                    username.setText(user.getUserName());
                    Picasso.with(context).load(Uri.parse(user.getPictureUrl())).into(userImage);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            String source = current.getImageUrl();
            Picasso.with(context)
                    .load(source)
                    .into(imgThumb);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
