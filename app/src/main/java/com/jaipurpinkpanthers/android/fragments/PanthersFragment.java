package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.PanthersAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class PanthersFragment extends Fragment {

    public GridView gvPlayers;
    public View view;

    private ArrayList<HashMap<String,String>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_panthers, container, false);

        //int id = ((MainActivity)this.getActivity()).getPlayerId();
        ((MainActivity)this.getActivity()).setToolbarText("KNOW YOUR PANTHERS");
        initilizeView();

        return view;
    }

    public void initilizeView(){

        gvPlayers = (GridView) view.findViewById(R.id.gvPlayers);

        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();

        String[] players = getResources().getStringArray(R.array.player);

        for(int i = 0; i < players.length; i++)
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("PlayerInfo", players[i]); //this will send id, player name and image
            list.add(map);
        }

        PanthersAdapter panthersAdapter = new PanthersAdapter(getActivity(),list);

        gvPlayers.setAdapter(panthersAdapter);
    }

}
