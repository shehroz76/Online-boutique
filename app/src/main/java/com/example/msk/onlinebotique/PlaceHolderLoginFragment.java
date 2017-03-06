package com.example.msk.onlinebotique;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MSk on 06/03/2017.
 */

public class PlaceHolderLoginFragment extends Fragment {


//    @BindView(R.id.textViewSignUp) TextView SignUp;


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


        ButterKnife.bind(this , rootView);

//        TextView textView = (TextView) rootView.findViewById(R.id.textViewSignUp);
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getActivity(), "Working", Toast.LENGTH_SHORT).show();
//            }
//        });







        return rootView;
    }

    @OnClick(R.id.textViewSignUp)
    public void SignUp(){

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new PlaceHolderSignUpFragment()).addToBackStack(null).commit(); // replace flContainer
    }

    @OnClick(R.id.signin)
    public void SignIn(){

        Toast.makeText(getActivity(), "Working singIn", Toast.LENGTH_SHORT).show();

    }



}
