package com.example.travelapp.retrofit;

import com.example.travelapp.model.ArticlesModel;
import com.example.travelapp.model.BookOrderModel;
import com.example.travelapp.model.CommentModel;
import com.example.travelapp.model.HotelModel;
import com.example.travelapp.model.MessageModel;
import com.example.travelapp.model.NewTourModel;
import com.example.travelapp.model.TypeServiceModel;
import com.example.travelapp.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiTravel {
    @GET("getService.php")
    Observable<TypeServiceModel> getTypeService();


    @GET("getNewTour.php")
    Observable<NewTourModel> getNewTour();

    @GET("detailsArticles.php")
    Observable<ArticlesModel> getNewArticles();

    @GET("comment.php")
    Observable<CommentModel> getComment();

    @POST("details.php")
    @FormUrlEncoded
    Observable<NewTourModel> getTour(
            @Field("page") int page,
            @Field("t_service_id") int t_service_id
    );



    @POST("detailsHotel.php")
    @FormUrlEncoded
    Observable<HotelModel> getHotel(
            @Field("page") int page,
            @Field("h_service_id") int h_service_id
    );

    @POST("detailsArticles.php")
    @FormUrlEncoded
    Observable<ArticlesModel> getArticles(
            @Field("page") int page,
            @Field("a_category_id") int a_category_id
    );

    @POST("register.php")
    @FormUrlEncoded
    Observable<UserModel> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("uid") String uid
    );

    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email

    );

    @POST("book.php")
    @FormUrlEncoded
    Observable<UserModel> createBook(
            @Field("b_user_id") int b_user_id,
            @Field("b_name") String b_name,
            @Field("b_email") String b_email,
            @Field("b_phone") String b_phone,
            @Field("b_address") String b_address,
            @Field("b_note") String b_note,
            @Field("b_number_adults") int b_number_adults,
            @Field("b_price_adults") String b_price_adults,
            @Field("details") String details
    );

    @POST("updateInfo.php")
    @FormUrlEncoded
    Observable<UserModel> updateInfo(
            @Field("b_name") String b_name,
            @Field("b_email") String b_email,
            @Field("b_phone") String b_phone,
            @Field("b_address") String b_address
    );

    @POST("view_book_order.php")
    @FormUrlEncoded
    Observable<BookOrderModel> viewBookOrder(
            @Field("b_user_id") int b_user_id

    );

    @POST("search.php")
    @FormUrlEncoded
    Observable<NewTourModel> search(
            @Field("search") String search

    );

    @POST("update_token.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("remember_token") String remember_token

    );




}
