package com.example.msk.onlinebotique;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.msk.onlinebotique.Patterns.MIN_PASSWORD_LENGTH;


/**
 * Created by MSk on 06/03/2017.
 */

public class PlaceHolderSignUpFragment extends Fragment {




    // Bind Views

    @BindView(R.id.editText_email)
    EditText emailSIgnUpEditText;

    @BindView(R.id.editText_userID)
    EditText UserIdSignUpEditText;

    @BindView(R.id.editText_fname)
    EditText FNameEditText;

    @BindView(R.id.editText_lastName)
    EditText LNameEditText;

    @BindView(R.id.editText_password)
    EditText passwordEditText;

    @BindView(R.id.editText_confirmPassword)
    EditText confrimPassEditText;
    private ProgressDialog mProgress;

    @BindView(R.id.buttonSignUp)
    Button signUpButton;

    private FirebaseAuth mAuth;
    private static final String TAG = "User signup :" ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_signup,container, false);

        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(getActivity());

        ButterKnife.bind(this , rootView);


        AddOnFocusListners();

        emailSIgnUpEditText.addTextChangedListener(mTextWatcher);
        UserIdSignUpEditText.addTextChangedListener(mTextWatcher);
        FNameEditText.addTextChangedListener(mTextWatcher);
        LNameEditText.addTextChangedListener(mTextWatcher);
        passwordEditText.addTextChangedListener(mTextWatcher);
        confrimPassEditText.addTextChangedListener(mTextWatcher);



        return rootView;


    }



    // SignUp Button

    @OnClick(R.id.buttonSignUp)
    public void SignUp(){


        Toast.makeText(getActivity(), "Working singIn", Toast.LENGTH_SHORT).show();

        final String userEmail, userId, userPassword, userConfirmPassword, userFirstName, userLastName;


        userEmail = emailSIgnUpEditText.getText().toString().trim();
        userId = UserIdSignUpEditText.getText().toString().trim();
        userFirstName = FNameEditText.getText().toString().trim();
        userLastName = LNameEditText.getText().toString().trim();
        userPassword = passwordEditText.getText().toString().trim();
        userConfirmPassword = confrimPassEditText.getText().toString().trim();


        if(TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userId) ||TextUtils.isEmpty(userFirstName)
                || TextUtils.isEmpty(userLastName) || TextUtils.isEmpty(userPassword)
                || TextUtils.isEmpty(userConfirmPassword)){

            Toast.makeText(getActivity() , "Fields Should not be left Empty" ,
                    Toast.LENGTH_SHORT).show();

        }else if(userEmail.length()==0 || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() ){
            emailSIgnUpEditText.setError("Enter Valid Email Address");
            Toast.makeText(getActivity() , "Invalid Email" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(userFirstName.length()== 0 || !userFirstName.matches("[a-zA-Z ]+")){
            FNameEditText.setError("Invalid Name");
            Toast.makeText(getActivity() , "Invalid first Name" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(userLastName.length() == 0 || !userLastName.matches("[a-zA-Z ]+")){
            LNameEditText.setError("Invalid Name");
            Toast.makeText(getActivity() , "Invalid Last Name" ,
                    Toast.LENGTH_SHORT).show();

        }
        else if(userPassword.length()<MIN_PASSWORD_LENGTH){
            passwordEditText.setError("Passwords should not less than 6");
            Toast.makeText(getActivity() , "password should not less than 6" ,
                    Toast.LENGTH_SHORT).show();


        }
        else if (!userPassword.equals(userConfirmPassword)) {
           confrimPassEditText.setError("Passwords does not match");
            Toast.makeText(getActivity() , "password does'not match" ,
                    Toast.LENGTH_SHORT).show();

        }

        else if(!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userId) &&
                !TextUtils.isEmpty(userFirstName) && !TextUtils.isEmpty(userLastName)
                && !TextUtils.isEmpty(userPassword)
                && !TextUtils.isEmpty(userConfirmPassword)) {

            mProgress.setMessage("Signing Up...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if(task.isSuccessful()) {
                                String user_id = mAuth.getCurrentUser().getUid();


//                                DatabaseReference current_user_db = mDatabaseUsersignUp.child(user_id);


//                                current_user_db.child("Email").setValue(userEmail);
//                                current_user_db.child("Name").setValue(userFirstName + " " + userLastName);
//                                current_user_db.child("Batch").setValue(userBatchNo);
                                mProgress.dismiss();

                                Intent signinIntent = new Intent(getActivity(), LoginActivity.class);
                                signinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(signinIntent);

                            }

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                mProgress.dismiss();
                                Toast.makeText(getActivity(), "SomeThing Went Wrong",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // ...
                        }
                    });
        }

    }

    private void AddOnFocusListners(){

        emailSIgnUpEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    String  text= ((EditText)v).getText().toString().trim();
                    if (text.length()==0){
                        emailSIgnUpEditText.setError("Enter Email Address");
                    }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                        emailSIgnUpEditText.setError("Enter Valid Email Address");
                    }
                }

            }
        });

        FNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String  text= ((EditText)v).getText().toString().trim();
                    if (text.length()==0){
                        FNameEditText.setError("Enter First Name");
                    } else if (!text.matches("[a-zA-Z ]+")) {
                        FNameEditText.setError("Invalid Name");
                    }
                }
            }
        });
        LNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String text = ((EditText) v).getText().toString().trim();
                    if (text.length() == 0) {
                        LNameEditText.setError("Enter Last Name");
                    } else if (!text.matches("[a-zA-Z ]+")) {
                        LNameEditText.setError("Invalid Name");
                    }
                }
            }
        });


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


        final String userEmail, userId, userPassword, userConfirmPassword, userFirstName, userLastName;


        userEmail = emailSIgnUpEditText.getText().toString().trim();
        userId = UserIdSignUpEditText.getText().toString().trim();
        userFirstName = FNameEditText.getText().toString().trim();
        userLastName = LNameEditText.getText().toString().trim();
        userPassword = passwordEditText.getText().toString().trim();
        userConfirmPassword = confrimPassEditText.getText().toString().trim();


        if(TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userId) ||TextUtils.isEmpty(userFirstName)
                || TextUtils.isEmpty(userLastName) || TextUtils.isEmpty(userPassword)
                || TextUtils.isEmpty(userConfirmPassword)) {

            signUpButton.setEnabled(false);
            signUpButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        }
        else if(!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userId) &&
                !TextUtils.isEmpty(userFirstName) && !TextUtils.isEmpty(userLastName)
                && !TextUtils.isEmpty(userPassword)
                && !TextUtils.isEmpty(userConfirmPassword)){

            signUpButton.setEnabled(true);
            signUpButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }




    }





}
