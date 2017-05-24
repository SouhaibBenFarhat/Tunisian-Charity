package aiesec.esprit.com.hackaton.fragments.intro;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import aiesec.esprit.com.hackaton.Home_ViewPager_Activity;
import aiesec.esprit.com.hackaton.R;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.login.widget.ProfilePictureView.TAG;


public class IntroFourFragment extends Fragment {


    private GoogleApiClient googleApiClient;
    private SignInButton googleSigninButton;
    private LoginButton facebookLoginButton;
    AppCompatButton googleButton, FacebookButton;
    private static final int RC_GOOGLE_SIGN_IN = 1;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    private CallbackManager mCallbackManager;
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("VIVZ", "onSuccess");
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Signin with facebook");
            progressDialog.setMessage("Facebook signin processing...");
            progressDialog.show();
            handleFacebookAccessToken(accessToken);


        }


        @Override
        public void onCancel() {
            Log.d("VIVZ", "onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("VIVZ", "onError " + e);
            Toast.makeText(getActivity(), "Facebook Error", Toast.LENGTH_SHORT).show();
        }
    };


    private DatabaseReference mDatabase;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            System.out.println("**********************************************");
                            System.out.println(task.getException().toString());
                        } else {

                            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Email").setValue(mAuth.getCurrentUser().getEmail());
                            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("pictureUrl").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());
                            //   mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Vote").setValue("0");
                            //   mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("DonationNumber").setValue("0");
                            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("userName").setValue(mAuth.getCurrentUser().getDisplayName());


                            SharedPreferences prefs = getActivity().getSharedPreferences("deviceToken", MODE_PRIVATE);

                            String token = prefs.getString("Token", "Empty");//"No name defined" is the default value.

                            if (!token.equals("Empty")) {
                                mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Token").setValue(token);
                            } else {
                                String tokenFirebase = FirebaseInstanceId.getInstance().getToken();
                                mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Token").setValue(tokenFirebase);

                            }

                            Intent intent = new Intent(getActivity(), Home_ViewPager_Activity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        facebookLoginButton = (LoginButton) getActivity().findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("email", "public_profile");
        facebookLoginButton.setFragment(this);
        mCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(mCallbackManager, mFacebookCallback);
    }


    public IntroFourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroFourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroFourFragment newInstance(String param1, String param2) {
        IntroFourFragment fragment = new IntroFourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_four, container, false);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleButton = (AppCompatButton) getActivity().findViewById(R.id.btn_google);
        FacebookButton = (AppCompatButton) getActivity().findViewById(R.id.btn_facebook);
        FacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLoginButton.performClick();
            }
        });
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Failed to connect with Google Account")
                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();


            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        googleSigninButton = (SignInButton) getActivity().findViewById(R.id.btn_google_signin);
        googleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                //-----> he you can change the redirection
                firebaseAuthWithGoogle(account);
            } else {
                System.out.println("$$$$$$$$$$$");
                System.out.println(result.getStatus().toString());
                final Snackbar snackBar = Snackbar.make(getView(),
                        "Sorry something went wrong with Google Account..",
                        Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBar.dismiss();

                    }
                });
                snackBar.show();
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Google Account");
        progressDialog.setMessage("Signin with google Account");
        progressDialog.show();

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

//                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getContext(), "Sorry Try Again", Toast.LENGTH_SHORT).show();


                        } else {


                            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Email").setValue(mAuth.getCurrentUser().getEmail());
                            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("pictureUrl").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());
                            //   mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Vote").setValue("0");
                            //   mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("DonationNumber").setValue("0");
                            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("userName").setValue(mAuth.getCurrentUser().getDisplayName());


                            SharedPreferences prefs = getActivity().getSharedPreferences("deviceToken", MODE_PRIVATE);

                            String token = prefs.getString("Token", "Empty");//"No name defined" is the default value.

                            if (!token.equals("Empty")) {
                                mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Token").setValue(token);
                            } else {
                                String tokenFirebase = FirebaseInstanceId.getInstance().getToken();
                                mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("Token").setValue(tokenFirebase);

                            }


                            progressDialog.dismiss();
                            Intent intent = new Intent(getActivity(), Home_ViewPager_Activity.class);
                            startActivity(intent);


                        }
                        // ...
                    }


                });

    }
}
