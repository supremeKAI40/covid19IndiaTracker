package com.supremekai40.covid19tracker;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.androdocs.httprequest.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tvTotal;
    TextView tvTotalData;
    TextView tvDeath;
    TextView tvDeathData;
    TextView tvToday;
    TextView tvTodayData;
    TextView tvRecover, tvRecoverData;
    public String mTotalData;
    public String mDeathData;
    public String mTodayData;
    public String mRecoverData;

    /*String totalMsg="Total Cases of India";
    String todayMsg="Total Cases reported today";*/



    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Theme_Beta.onActivityCreateSetTheme(this);


        //Theme_Beta.setBackground(this);    F01E1E

        //tvAddress = findViewById(R.id.address);


    tvTotal= findViewById(R.id.tvTotal);
    tvTotalData = findViewById(R.id.tvTotalData);
    tvToday=findViewById(R.id.tvToday);
    tvTodayData = findViewById(R.id.tvTodayData);
    tvDeath=findViewById(R.id.tvDeath);
    tvDeathData=findViewById(R.id.tvDeathData);
    tvRecover=findViewById(R.id.tvRecover);
    tvRecoverData=findViewById(R.id.tvRecoveredData);

        if (savedInstanceState == null ) {
            new tracker().execute();
        } else {
            restoreState(savedInstanceState);
            setUIValues();
        }
    }

    protected void setUIValues(){
        //tvAddress.setText(mAddress);
        //String exist=mUpdatedAtText;

        tvTotal.setText(R.string.totalMsg);
        tvTotalData.setText(mTotalData);
        tvToday.setText(R.string.todayMsg);
        tvTodayData.setText(mTodayData);
        tvDeath.setText(R.string.deathMsg);
        tvDeathData.setText(mDeathData);
        tvRecover.setText("Total Recovered Cases");
        tvRecoverData.setText(mRecoverData);
    }

    protected void restoreState(Bundle savedInstanceState) {
        mTotalData = savedInstanceState.getString(mTotalData);
        mTodayData = savedInstanceState.getString(mTodayData);
        mDeathData= savedInstanceState.getString(mDeathData);
        mRecoverData=savedInstanceState.getString(mRecoverData);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(String.valueOf(tvTotalData),mTotalData);
        outState.putString(String.valueOf(tvTodayData),mTodayData);
        outState.putString(String.valueOf(tvDeathData),mDeathData);
        outState.putString(String.valueOf(tvRecoverData),mRecoverData);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("StaticFieldLeak")
    class tracker extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.mainContainer).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = HttpRequest.excuteGet("https://corona.lmao.ninja/v2/countries/India");
                return response;
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                //JSONObject main = jsonObj.getJSONObject("cases");
                /*JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);*/


                mTotalData = jsonObj.getString("active");
                mTodayData=jsonObj.getString("todayCases");
                mDeathData=jsonObj.getString("deaths");
                mRecoverData=jsonObj.getString("recovered");


                /*mTemp = main.getString("temp") + "°C";
                mTempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                mTempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                mPressure = main.getString("pressure");
                mHumidity = main.getString("humidity");
                mWindSpeed = wind.getString("speed");
                mWeatherDescription = (weather.getString("description")).toUpperCase();
                mAddress = jsonObj.getString("name") + ", " + sys.getString("country");*/


                    /* Populating extracted data into our views */

                setUIValues();

                /*Views populated, Hiding the loader, Showing the main design */

                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }
    }
}
