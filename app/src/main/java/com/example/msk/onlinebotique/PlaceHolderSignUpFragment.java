package com.example.msk.onlinebotique;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;


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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_signup,container, false);

        mProgress = new ProgressDialog(getActivity());

        return rootView;


    }



}
