package aiesec.esprit.com.hackaton.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import aiesec.esprit.com.hackaton.HomeActivity;
import aiesec.esprit.com.hackaton.R;


public class SigninFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private AppCompatButton btnLogin;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = (EditText) getActivity().findViewById(R.id.et_login_email);
        passwordEditText = (EditText) getActivity().findViewById(R.id.et_login_password);

        btnLogin = (AppCompatButton) getActivity().findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.equals("") || password.equals("")) {
                    final Snackbar snackBar = Snackbar.make(getActivity().findViewById(R.id.signin_frame), "Please fill all fields", Snackbar.LENGTH_LONG);

                    snackBar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                final Snackbar snackBar = Snackbar.make(getActivity().findViewById(R.id.signin_frame), "Something went wrong with Signin", Snackbar.LENGTH_LONG);
                                snackBar.setAction("Dismiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        snackBar.dismiss();
                                    }
                                });
                                snackBar.show();
                            } else {
                               // Toast.makeText(getActivity(), "signin Ok", Toast.LENGTH_SHORT).show();

                                //Redirection


                                getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
                            }
                        }
                    });

                }
            }
        });
    }
}
