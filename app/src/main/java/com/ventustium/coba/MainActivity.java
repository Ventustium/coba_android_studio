package com.ventustium.coba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    String id, name, state, error;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.Data);

        getData();

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this::getData);
    }

    private void getData() {
        executor.execute(() -> {
            String result = null;
            try {
                getREST();
                swipeRefreshLayout.setRefreshing(false);
            } catch (Exception e){
                error = e.toString();
                swipeRefreshLayout.setRefreshing(false);
            }

            handler.post(()-> {
                if(id != null){
                    textView.setText("ID: " + id + "\nName: " + name+ "\nstate: " +state);
                }
                else{
                    textView.setText(error);
                }
            });
        });
    }

    private void getREST() throws Exception{
        String url = "https://coba.petra.ac.id/laravel/public/api/v1/data/users";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );
        String input;
        StringBuilder response = new StringBuilder();
        while ((input = in.readLine()) != null) {
            response.append(input);
        }
        in.close();

        JSONArray myArray = new JSONArray(response.toString());
        JSONObject arrObj = myArray.getJSONObject(0);
        id = arrObj.getString("id");
        name = arrObj.getString("name");
        state = arrObj.getString("state");
    }
}