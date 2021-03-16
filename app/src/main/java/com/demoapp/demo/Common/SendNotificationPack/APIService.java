package com.demoapp.demo.Common.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAm0oTt_c:APA91bGJCt5wPhsNzNFjp_AVxHXuWp_m4a_9r3HHcTzK5god1HMEUlsGUewvEQfwZoaEWAYSDeULpf4wRl4QyvQWTv0ja-UjhCkHtx-zEJRbNj0nHu-E_xnwEaPKAe2Y0fseLasPeyJw" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

