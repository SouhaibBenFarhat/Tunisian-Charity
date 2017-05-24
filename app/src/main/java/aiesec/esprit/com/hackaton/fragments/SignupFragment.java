package aiesec.esprit.com.hackaton.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aiesec.esprit.com.hackaton.R;


public class SignupFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private AppCompatButton btnRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        emailEditText = (EditText) getActivity().findViewById(R.id.et_email);
        passwordEditText = (EditText) getActivity().findViewById(R.id.et_password);
        btnRegister = (AppCompatButton) getActivity().findViewById(R.id.btn_register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (emailEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
                    final Snackbar snackBar = Snackbar.make(getActivity().findViewById(R.id.signup_frame), "Please fill all fields", Snackbar.LENGTH_LONG);

                    snackBar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                } else {
                    startSignup(emailEditText.getText().toString(), passwordEditText.getText().toString());
                }

            }
        });
    }


    private void startSignup(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.container);

                    emailEditText.setText("");
                    passwordEditText.setText("");

                    mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Email").setValue(email);
                    mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Password").setValue(password);

                    final Snackbar snackBar = Snackbar.make(getActivity().findViewById(R.id.signup_frame), "Signup Success", Snackbar.LENGTH_INDEFINITE);
                    snackBar.setAction("Sign in", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                            viewPager.setCurrentItem(0);
                        }
                    });
                    snackBar.show();
                } else {
                    final Snackbar snackBar = Snackbar.make(getActivity().findViewById(R.id.signup_frame), "Something went wrong with Signin", Snackbar.LENGTH_LONG);
                    snackBar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                }
            }
        });

    }
}
