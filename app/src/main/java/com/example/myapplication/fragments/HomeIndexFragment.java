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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DestinationAdapter;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.contracts.HomeContract;
import com.example.myapplication.models.Destination;
import com.example.myapplication.presenter.HomePresenter;
import com.example.myapplication.services.DestinationService;
import com.example.myapplication.services.UserService;
import com.example.myapplication.shared.RetrofitHelper;
import com.example.myapplication.shared.SharedLocalData;
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
 * {@link HomeIndexFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeIndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeIndexFragment extends Fragment implements HomeContract.View, AppActivity {


    private HomePresenter presenter = new HomePresenter();
    Context context = null;

    int currentPage;
    int totalPage;
    int perPage;

    RecyclerView recyclerViewDestination;
    DestinationAdapter mAdapter;
    List<Destination> listDestinations;
    ProgressBar progressBar;

    EditText text_search;
    ImageButton search_button;

    private OnFragmentInteractionListener mListener;

    public static HomeIndexFragment newInstance(String param1, String param2) {
        HomeIndexFragment fragment = new HomeIndexFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
    }

    public void initView(View v) {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void registerListener() {
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = text_search.getText().toString();

                if (searchString.isEmpty()) {
                    Toast.makeText(context, "Please enter search keyword", Toast.LENGTH_SHORT).show();
                    return;
                } else search(searchString);
            }
        });

        text_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard();
                }
            }
        });
    }

    void search(String searchString) {

        Retrofit retrofit = RetrofitHelper.create();

        recyclerViewDestination.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        DestinationService service = retrofit.create(DestinationService.class);

        Call<Object> call = service.search(searchString);

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

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(text_search.getWindowToken(), 0);
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
        recyclerViewDestination.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(context, "Check network and try again", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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

        text_search = view.findViewById(R.id.txt_search);
        search_button = view.findViewById(R.id.btn_search);

        registerListener();

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
