package com.example.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DestinationAdapter;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.contracts.HomeContract;
import com.example.myapplication.models.Destination;
import com.example.myapplication.presenter.HomePresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeIndexFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeIndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeIndexFragment extends Fragment implements HomeContract.View {


    private HomePresenter presenter = new HomePresenter();
    Context context = null;

    int currentPage;
    int totalPage;
    int perPage;

    RecyclerView recyclerViewDestination;
    DestinationAdapter mAdapter;
    List<Destination> listDestinations;
    ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public static HomeIndexFragment newInstance(String param1, String param2) {
        HomeIndexFragment fragment = new HomeIndexFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    public HomeIndexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onGetDataSuccess(JSONObject data) {
        try {

            Gson gson = new Gson();

            currentPage = data.getInt("current_page");
            totalPage = data.getInt("total");
            perPage = data.getInt("per_page");

            JSONArray listdestinationRaw = data.getJSONArray("data");

            List<Destination> destinations = gson.fromJson(listdestinationRaw.toString(), new TypeToken<List<Destination>>() {
            }.getType());

            listDestinations.clear();
            listDestinations.addAll(destinations);

            mAdapter.notifyDataSetChanged();
            recyclerViewDestination.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong! Please try late", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onGetDataFail(String errors) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_index, container, false);

        // Get view
        recyclerViewDestination = view.findViewById(R.id.home_recycler_content);

        progressBar = view.findViewById(R.id.home_progress_bar);
        // Init data
        listDestinations = new ArrayList<>();

        //set adapter
        mAdapter = new DestinationAdapter(getActivity(), listDestinations);


        // Manage layout

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDestination.setVisibility(View.INVISIBLE);
        recyclerViewDestination.setLayoutManager(mLayoutManager);
        recyclerViewDestination.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDestination.setAdapter(mAdapter);
        recyclerViewDestination.setNestedScrollingEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        presenter.getData();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        presenter.setView(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
