package com.moive.sus.moviemodule;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TheatersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TheatersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TheatersFragment extends Fragment {



    public TheatersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment TheatersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TheatersFragment newInstance() {
        TheatersFragment fragment = new TheatersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theaters, container, false);
    }


}
