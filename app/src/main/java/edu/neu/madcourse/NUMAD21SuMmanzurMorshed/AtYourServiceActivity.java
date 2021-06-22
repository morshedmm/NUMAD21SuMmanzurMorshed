package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AtYourServiceActivity extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";

    private ProgressBar progressBar;

    //private EditText mURLEditText;
    //private String mURLEditText = "https://jsonplaceholder.typicode.com/posts/1";

    //private String mURLEditText = "https://api.ip2country.info/ip?160.185.160.90";
    private String mURLEditText = "https://api.ip2country.info/ip?";
    private TextView mTitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        progressBar = (ProgressBar) findViewById(R.id.progress_loader);
        progressBar.setVisibility(View.INVISIBLE);
        //mURLEditText = (EditText)findViewById(R.id.URL_editText);
        mTitleTextView = (TextView)findViewById(R.id.result_textview);
        //callWebserviceButtonHandler(findViewById(android.R.id.content));
    }

    public void getIp(View view) {
        callWebserviceButtonHandler(findViewById(android.R.id.content));

    }

    public void callWebserviceButtonHandler(View view){
        progressBar.setVisibility(View.VISIBLE);
        PingWebServiceTask task = new PingWebServiceTask();
        
        try {
            //String url = NetworkUtil.validInput(mURLEditText.getText().toString());
            //String url = NetworkUtil.validInput(mURLEditText);
            EditText onlyURL = (EditText)findViewById(R.id.URL_editText);
            String urlStringAll = mURLEditText + onlyURL.getText().toString();
            String url = NetworkUtil.validInput(urlStringAll);
            task.execute(url); // This is a security risk.  Don't let your user enter the URL in a real app.
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    private class PingWebServiceTask  extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jObject = new JSONObject();
            try {

                // Initial website is "https://jsonplaceholder.typicode.com/posts/1"
                URL url = new URL(params[0]);
                // Get String response from the url address
                String resp = NetworkUtil.httpResponse(url);
                //Log.i("resp",resp);

                // JSONArray jArray = new JSONArray(resp);    // Use this if your web service returns an array of objects.  Arrays are in [ ] brackets.
                // Transform String into JSONObject
                jObject = new JSONObject(resp);

                //Log.i("jTitle",jObject.getString("title"));
                //Log.i("jBody",jObject.getString("body"));
                return jObject;

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "JSONException");
                e.printStackTrace();
            }

            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            TextView result_view = (TextView) findViewById(R.id.result_textview);

            try {
                //result_view.setText(jObject.getString("title"));
                progressBar.setVisibility(View.INVISIBLE);
                if (jObject.getString("countryName").length() == 0) {
                    result_view.setText(jObject.getString("IP Location Unknown"));
                } else {
                    result_view.setText(jObject.getString("countryName"));
                }
                //result_view.setText(jObject.getString("countryName"));
            } catch (JSONException e) {
                progressBar.setVisibility(View.INVISIBLE);
                result_view.setText("Not a valid ip");
            }

        }
    }
}