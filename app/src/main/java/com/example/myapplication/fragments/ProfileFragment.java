package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.HomeScreen;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.models.Destination;
import com.example.myapplication.models.User;
import com.example.myapplication.models.Wishlist;
import com.example.myapplication.services.UserService;
import com.example.myapplication.services.WishListService;
import com.example.myapplication.shared.RetrofitHelper;
import com.example.myapplication.shared.SharedLocalData;
import com.example.myapplication.utils.RequireLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProfileFragment extends Fragment {


    TextView username, email, dob, logout;
    private OnFragmentInteractionListener mListener;
    Context mContext;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        username = view.findViewById(R.id.profile_username);
        email = view.findViewById(R.id.profile_email);
        dob = view.findViewById(R.id.profile_dob);
        logout = view.findViewById(R.id.profile_btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedLocalData.saveAccessToken("");
                Intent homeIntent = new Intent(getActivity(), HomeScreen.class);
                startActivity(homeIntent);
            }
        });
        getData();
        return view;
    }

    void getData() {
        String accessToken = SharedLocalData.getAccessToken();

        if (accessToken.isEmpty()) return;
        Retrofit retrofit = RetrofitHelper.create();

        UserService service = retrofit.create(UserService.class);

        Call<Object> call = service.me("Bearer " + accessToken);

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

//            User user = gson.fromJson(data.toString(), new TypeToken<User>() {
//            }.getType());

            String name = data.getString("username");
            username.setText(data.getString("username"));
            email.setText(data.getString("email"));

//            String date =  DateUtils.formatDateTime(getActivity(), Long.parseLong(data.getString("dob")), DateUtils.FORMAT_SHOW_DATE);
            dob.setText(data.getString("address"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Something went wrong! Please try late", Toast.LENGTH_SHORT).show();
        }
    }

    void onGetDataFail(String error) {
        Toast.makeText(getActivity(), "Something went wrong! Please try late", Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        RequireLogin.require(context);
        if (SharedLocalData.getAccessToken().isEmpty())
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
