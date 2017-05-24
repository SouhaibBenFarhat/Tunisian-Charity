package aiesec.esprit.com.hackaton.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import aiesec.esprit.com.hackaton.Entity.Evenement;
import aiesec.esprit.com.hackaton.EvenementDetailActivity;
import aiesec.esprit.com.hackaton.R;
import aiesec.esprit.com.hackaton.adapter.EvenementAdapter;
import aiesec.esprit.com.hackaton.utils.RecyclerItemClickListener;

/**
 * Created by maher on 11/02/2017.
 */

public class EventsDisplayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static boolean toPager = false;
    public static Evenement evenement = null;
    private final String REFRESH = "dont refrsh";
    public List<Evenement> evenements;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerView;
    private List<Evenement> inter;
    EvenementAdapter adapter;


    public DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;


    public void displayEvents() {


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        evenements.clear();
        mDatabase.child("Evenement").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 Evenement evenement = dataSnapshot.getValue(Evenement.class);
                evenements.add(evenement);
                setuprecyclerView();
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


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evenements_display, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.assoc_disp_swipeRefreshLayout);
        swipeLayout.setOnRefreshListener(this);
        evenements = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewk);
        displayEvents();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void  setuprecyclerView(){
      adapter = new EvenementAdapter(getActivity(), evenements);
      recyclerView.setAdapter(adapter);
      LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getActivity()); // (C ontext context, int spanCount)
      mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
      recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.addOnItemTouchListener(
              new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                  @Override
                  public void onItemClick(View view, int position) {

                      Intent intent = new Intent(getActivity(), EvenementDetailActivity.class);
                      EvenementDetailActivity.evenementid = evenements.get(position).getId();
                      EvenementDetailActivity.userEventUid = evenements.get(position).getUserUid();
                      startActivity(intent);


                      // association=associations.get(position);
                      // startActivity(new Intent(getActivity(),AssociationDetailActivity.class));
                  }

                  @Override
                  public void onLongItemClick(View view, int position) {

                  }
              })
      );
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
       // displayEvents();
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                displayEvents();
                swipeLayout.setRefreshing(false);
            }

        }, 2000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
