package com.bkacad.nnt.openweatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText edtCity;
    private ImageButton btnSearch;
    private TextView tvResult;
    private ImageView imgResult;


    private void initUI(){
        edtCity = findViewById(R.id.edt_main_city);
        btnSearch = findViewById(R.id.btn_main_search);
        tvResult = findViewById(R.id.tv_main_result);
        imgResult = findViewById(R.id.img_main_result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        // SỰ kiện khi click vào button -> lấy dữ liệu Edittext -> Tạo url
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtCity.getText().toString().trim();
                if(city.isEmpty()){
                    edtCity.setError("Hãy nhập dữ liệu");
                    return;
                }
                // Dữ liệu đã hợp lệ -> request lên OPenweather ....
                String myUrl = WeatherUtils.createURL(city);
                StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                        response -> {
                            try{
                                //Create a JSON object containing information from the API.
                                JSONObject myData = new JSONObject(response);

                                JSONArray tmpJSONArray = myData.getJSONArray("weather");
//                                Log.d("data", myData.toString());
                                JSONObject jsonWeather = tmpJSONArray.getJSONObject(0);
                                Log.d("weather", jsonWeather.toString());

                                String mainWeather = jsonWeather.getString("main") + " - " + jsonWeather.getString("description");
                                tvResult.setText(mainWeather);

                                String imgWeather = jsonWeather.getString("icon");
                                imgWeather = "https://openweathermap.org/img/wn/"+ imgWeather + "@4x.png";

                                // Load ảnh từ đường dẫn về
                                Glide.with(MainActivity.this).load(imgWeather).into(imgResult);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        volleyError -> Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
                );

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(myRequest);
            }
        });
    }
}