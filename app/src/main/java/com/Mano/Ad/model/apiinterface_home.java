package com.Mano.Ad.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiinterface_home {
//getcontacts_searchcode
@GET("Mano_annonce.php")
Call<List<contact_annonce>> getcontacts_annonce();
    @GET("Mano_first_category.php")
    Call<List<contact_home>> getcontacts_first();
    @FormUrlEncoded
    @POST("Mano_searchcode.php")
    Call<List<ad_code>> getcontacts_searchcode(@Field("code") String id);
    @FormUrlEncoded
    @POST("Mano_codedel.php")
    Call<ResponseBody> getcontacts_delcode(@Field("code") String id);
    @FormUrlEncoded
    @POST("ManoUpdatePoint.php")
    Call<ResponseBody> getcontacts_addpoint(@Field("point") int name,@Field("phone") String phone);
    @FormUrlEncoded
    @POST("Mano_updateuser.php")
    Call<ResponseBody> getcontacts_updateaccount(@Field("name") String name,@Field("address") String address, @Field("phonee")String phonee,
                                                 @Field("passw") String passw,  @Field("oldphone") String oldphone , @Field("newpassword")String newpassword
            , @Field("country")String country,@Field("age") int age);
    @FormUrlEncoded
    @POST("Mano_second_category.php")
    Call<List<contact_second_home>> getcontacts(@Field("id") int id);
    @FormUrlEncoded
    @POST("Mano_login.php")
    Call<List<contact_userinfo>> getcontacts_login(@Field("phonee") String phone ,@Field("passw") String password);
    @FormUrlEncoded
    @POST("ManoUpdatePointid.php")
    Call<ResponseBody> getcontacts_addpointid(@Field("point") int name,@Field("id") int phone);
    @GET("Mano_users_list.php")
    Call<List<contact_users>> getcontacts_users();
    @FormUrlEncoded
    @POST("Mano_searchadview.php")
    Call<List<contact_ad>> getcontacts_searchadview(@Field("ad_id") int ad_id ,@Field("user_phone") String user_phone,@Field("date ") String date);
    @FormUrlEncoded
    @POST("Mano_addtoshow.php")
    Call<ResponseBody> getcontacts_addtoview(@Field("ad_id") int ad_id ,@Field("user_phone") String user_phone,@Field("date ") String date);
    @FormUrlEncoded
    @POST("Mano_searchshare.php")
    Call<List<contact_ad>> getcontacts_searchadview_share(@Field("ad_id") int ad_id ,@Field("user_phone") String user_phone);
    @FormUrlEncoded
    @POST("Mano_adtoshare.php")
    Call<ResponseBody> getcontacts_addtoview_share(@Field("ad_id") int ad_id ,@Field("user_phone") String user_phone);
    @FormUrlEncoded
    @POST("Mano_updateuser.php")
    Call<ResponseBody> getcontacts_newaccount(@Field("name") String name,@Field("password") String password, @Field("address")String address,
                                                 @Field("phone") String phone ,@Field("country")String country,  @Field("age") int age ,@Field("id")int id);

}

