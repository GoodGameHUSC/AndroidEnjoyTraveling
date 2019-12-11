package com.example.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DestinationAdapter;
import com.example.myapplication.adapter.WishListAdaper;
import com.example.myapplication.models.Destination;
import com.example.myapplication.models.Wishlist;
import com.example.myapplication.services.DestinationService;
import com.example.myapplication.services.WishListService;
import com.example.myapplication.shared.RetrofitHelper;
import com.example.myapplication.shared.SharedLocalData;
import com.example.myapplication.utils.RequireLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WishListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WishListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishListFragment extends Fragment {

    RecyclerView recyclerViewDestination;
    WishListAdaper mAdapter;
    List<Destination> listDestinations;
    ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public WishListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishListFragment newInstance(String param1, String param2) {
        WishListFragment fragment = new WishListFragment();

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
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        // Get view
        recyclerViewDestination = view.findViewById(R.id.wishlist_recycler_content);

        progressBar = view.findViewById(R.id.wishlist_progress_bar);
        // Init data
        listDestinations = new ArrayList<>();

        //set adapter
        mAdapter = new WishListAdaper(getActivity(), listDestinations);


        // Manage layout

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDestination.setVisibility(View.INVISIBLE);
        recyclerViewDestination.setLayoutManager(mLayoutManager);
        recyclerViewDestination.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDestination.setAdapter(mAdapter);
        recyclerViewDestination.setNestedScrollingEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        getData();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    void getData() {
        String accessToken = SharedLocalData.getAccessToken();


        Retrofit retrofit = RetrofitHelper.create();

        WishListService service = retrofit.create(WishListService.class);

        Call<Object> call = service.getAll("Bearer " + accessToken);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                try {
                    if (code == 200) {

                        Gson gson = new Gson();
                        JSONObject data = new JSONObject(gson.toJson(response.body()));
                        data = data.getJSONObject("data");
                        onGetDataSuccess(data);
                    } else {

                        JSONObject error = new JSONObject(response.errorBody().string());
                        onGetDataFail(error.getString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onGetDataFail(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                onGetDataFail(t.getMessage());
            }
        });
    }


    void onGetDataSuccess(JSONObject data) {
        try {

            Gson gson = new Gson();

            JSONArray listdestinationRaw = data.getJSONArray("data");

            List<Destination> destinations = new ArrayList<>();

            List<Wishlist> wishlists = gson.fromJson(listdestinationRaw.toString(), new TypeToken<List<Wishlist>>() {
            }.getType());

            for (Wishlist wishlist :wishlists ){
                destinations.add(wishlist.destination);
            }


            listDestinations.clear();
            listDestinations.addAll(destinations);

            mAdapter.notifyDataSetChanged();
            recyclerViewDestination.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong! Please try late", Toast.LENGTH_SHORT).show();
        }
    }

    void onGetDataFail(String error) {
        Toast.makeText(getActivity(), "Something went wrong! Please try late", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onAttach(Context context) {

        RequireLogin.require(context);
        if(SharedLocalData.getAccessToken().isEmpty())
            getActivity().finish();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
