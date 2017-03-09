package com.example.msk.onlinebotique;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * Created by MSk on 06/03/2017.
 */

public class PlaceHolderLoginFragment extends Fragment {


 // Bind Views Using ButterKnife Library

    @BindView(R.id.signin)
    Button SignIn;

    @BindView(R.id.editTextEmail)
    EditText emailEditText;

    @BindView(R.id.editTextPassword)
    EditText passwordEditText;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mprogress;
    // [END declare_auth_listener]




    public PlaceHolderLoginFragment() {
//        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_login, container ,false);
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
// [END initialize_auth]


        mprogress =new ProgressDialog(getActivity());


        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    Intent mainActivityIntent =  new Intent(getActivity() , HomeActivity.class);
                    mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainActivityIntent);



                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
// [END auth_state_listener]




        ButterKnife.bind(this , rootView);

//
//        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
//            getActivity().getSupportFragmentManager()
//                    .beginTransaction().
//                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
//        }



        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    // Open SignUp fragment

    @OnClick(R.id.textViewSignUp)
    public void SignUp(){

//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container,new PlaceHolderSignUpFragment()).addToBackStack(null).commit(); // replace flContainer


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);

        PlaceHolderSignUpFragment newFragment = new PlaceHolderSignUpFragment();

        ft.replace(R.id.container, newFragment, "detailFragment").addToBackStack(null);

// Start the animated transition.
        ft.commit();

    }


    // Sign In by clicking SignIn Button

    @OnClick(R.id.signin)
    public void SignIn(){



        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        Toast.makeText(getActivity(), "Working singIn", Toast.LENGTH_SHORT).show();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(getActivity() , "Fields Should not be left Empty" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(email.length()==0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
            emailEditText.setError("Enter Valid Email Address");
            Toast.makeText(getActivity(), "Invalid Email",
                    Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<Patterns.MIN_PASSWORD_LENGTH){
            passwordEditText.setError("Passwords should not less than 6");
            Toast.makeText(getActivity() , "password should not less than 6" ,
                    Toast.LENGTH_SHORT).show();


        }
        else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){



            mprogress.setMessage("Signing In");
            mprogress.show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            if(task.isSuccessful()){

                                mprogress.dismiss();

                                Intent mainActvityIntent = new Intent(getActivity() , HomeActivity.class);
                                mainActvityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainActvityIntent);


                            }

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {

                                mprogress.dismiss();

                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(getActivity(), "WRONG CREDENTIALS ",
                                        Toast.LENGTH_SHORT).show();


                            }

                        }
                    });

        }


    }



    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkingSignInforeileds();
        }
    };

    private void checkingSignInforeileds() {



        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            SignIn.setEnabled(false);
            SignIn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        }
        else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            SignIn.setEnabled(true);
            SignIn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }







    }







}
