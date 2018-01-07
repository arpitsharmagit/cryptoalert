package com.arpit.alerto.cryptoalert;

import android.util.Log;

import java.io.IOException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "FirebaseIIDService";
    private static final String ServerUrl = "http://localhost:8888/alerts";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    try {
        sendRegistrationToServer(refreshedToken);
    }
    catch(Exception e){
        Log.d(TAG,"unable to send token to server");
    }
    }
    private void sendRegistrationToServer(String token) throws IOException {
        // TODO: Implement this method to send token to your app server.
        OkHttpClient client = new OkHttpClient();
        String devideBody ="{'deviceId':"+token+"}";
        RequestBody body = RequestBody.create(JSON, devideBody);
        Request request = new Request.Builder()
                .url(ServerUrl)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Log.d("Notification response",response.body().string());
    }
}
