package com.Mano.Ad.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.Mano.Ad.model.Apiclient_home;
import com.Mano.Ad.model.apiinterface_home;
import com.Mano.Ad.model.contact_userinfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login__ {
    private List<contact_userinfo> contactList;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    ProgressDialog progressDialog;
    public void fetchInfo(final Context context, String phone, String password){
        sharedpref = context.getSharedPreferences("ManoAd", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_userinfo>> call= apiinterface.getcontacts_login(phone,
               password);
        call.enqueue(new Callback<List<contact_userinfo>>() {
            @Override
            public void onResponse(Call<List<contact_userinfo>> call, Response<List<contact_userinfo>> response) {
                contactList = response.body();
                edt.putInt("id",contactList.get(0).getId());
                edt.putString("name",contactList.get(0).getName());
                edt.putString("phone",contactList.get(0).getPhone());
                edt.putString("address",contactList.get(0).getmaddress());
                edt.putString("password",contactList.get(0).getPassword());
                edt.putString("points",contactList.get(0).getPoints());
                edt.putString("country",contactList.get(0).getCountry());
                edt.putString("age", String.valueOf(contactList.get(0).getAge()));
                edt.putString("remember","yes");
                edt.apply();
             //   context.startActivity(new Intent(context,MainActivity.class));
            }

            @Override
            public void onFailure(Call<List<contact_userinfo>> call, Throwable t) {
                Toast.makeText(context,"تأكد من اتصال الانترنت",Toast.LENGTH_LONG).show();
            }
        });
    }

}
