package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;

public class AtYourServiceActivity extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";

    private ProgressBar progressBar;

    private ArrayList<MyItemCard> itemList = new ArrayList<>();

    //private EditText mURLEditText;
    //private String mURLEditText = "https://jsonplaceholder.typicode.com/posts/1";

    //private String mURLEditText = "https://api.ip2country.info/ip?160.185.160.90";
    private String mURLEditText = "https://api.ip2country.info/ip?";
    private TextView mTitleTextView;

    private JSONObject ipObject;
    private int displayIp = 1;
    private Button playButton;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    private RecyclerView recyclerView;
    private MyRviewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        init(savedInstanceState);

        playButton = (Button) findViewById(R.id.learn_more_button);
        playButton.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar) findViewById(R.id.progress_loader);
        progressBar.setVisibility(View.INVISIBLE);
        //mURLEditText = (EditText)findViewById(R.id.URL_editText);
        mTitleTextView = (TextView)findViewById(R.id.result_textview);
        //callWebserviceButtonHandler(findViewById(android.R.id.content));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {


        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put image information id into instance
            //outState.putInt(KEY_OF_INSTANCE + i + "0", itemList.get(i).getImageSource());
            // put itemName information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getItemName());
            // put itemDesc information into instance
            outState.putString(KEY_OF_INSTANCE + i + "2", itemList.get(i).getItemDesc());
            // put isChecked information into instance
            //outState.putBoolean(KEY_OF_INSTANCE + i + "3", itemList.get(i).getStatus());
        }
        super.onSaveInstanceState(outState);

    }

    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState);
        createRecyclerView();
    }


    private void initialItemData(Bundle savedInstanceState) {

        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    Integer imgId = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "0");
                    String itemName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String itemDesc = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    boolean isChecked = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + "3");

                    // We need to make sure names such as "XXX(checked)" will not duplicate
                    // Use a tricky way to solve this problem, not the best though
                    if (isChecked) {
                        itemName = itemName.substring(0, itemName.lastIndexOf("("));
                    }
                    MyItemCard itemCard = new MyItemCard(imgId, itemName, itemDesc, isChecked);

                    itemList.add(itemCard);
                }
            }
        }
        // The first time to open this Activity
        else {
            MyItemCard item1 = new MyItemCard(R.drawable.pic_gmail_01, "Gmail", "Example description", false);
            //ItemCard item2 = new ItemCard(R.drawable.pic_google_01, "Google", "Example description", false);
            //ItemCard item3 = new ItemCard(R.drawable.pic_youtube_01, "Youtube", "Example description", false);
            itemList.add(item1);
            //itemList.add(item2);
            //itemList.add(item3);
        }

    }




    private void createRecyclerView() {
        rLayoutManger = new LinearLayoutManager(this);

       recyclerView = findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new MyRviewAdapter(itemList);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);

    }

    public void getIp(View view) {
        playButton.setVisibility(View.INVISIBLE);
        TextView country_view = (TextView) findViewById(R.id.capital);
        country_view.setText("");
        callWebserviceButtonHandler(findViewById(android.R.id.content));

    }

    public void callWebserviceButtonHandler(View view){
        progressBar.setVisibility(View.VISIBLE);
        PingWebServiceTask task = new PingWebServiceTask();
        displayIp = 1;

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


    public void getCountryInfo(View view){
        progressBar.setVisibility(View.VISIBLE);
        PingWebServiceTask task = new PingWebServiceTask();
        displayIp = 0;
        playButton.setVisibility(View.INVISIBLE);

        try {
            //String url = NetworkUtil.validInput(mURLEditText.getText().toString());
            //String url = NetworkUtil.validInput(mURLEditText);
            EditText onlyURL = (EditText)findViewById(R.id.URL_editText);

            String urlStringAll = null;
            try {
                urlStringAll = "https://restcountries.eu/rest/v2/alpha/" + ipObject.getString("countryCode3");
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                //
                StringBuffer sb = new StringBuffer(resp);
                if (sb.charAt(0) == '[') {
                    sb.delete(resp.length() - 1, resp.length());
                    sb.delete(0, 1);

                }

                //
                //jObject = new JSONObject(resp);
                jObject = new JSONObject(sb.toString());

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
            TextView country_view = (TextView) findViewById(R.id.capital);

            try {
                //result_view.setText(jObject.getString("title"));
                progressBar.setVisibility(View.INVISIBLE);
                ipObject = jObject;
                if (displayIp == 1) {
                    //displayIp = 0;
                    if (jObject.getString("countryName").length() == 0) {
                        result_view.setText(jObject.getString("IP Location Unknown"));
                    } else {
                        String output = jObject.getString("countryName") + " "
                                + jObject.getString("countryEmoji");
                        //result_view.setText(jObject.getString("countryName"));
                        result_view.setText(output);
                        playButton.setText("want more info of " + jObject.getString("countryName"));
                        playButton.setVisibility(View.VISIBLE);
                    }
                    //result_view.setText(jObject.getString("countryName"));
                } else {
                    // Show country info
                    country_view.setText("Capital: " + jObject.getString("capital"));
                    //List<String> altNames = new ArrayList<String>();
                    String altNames = jObject.getString("altSpellings");
                    //
                    StringBuffer sb = new StringBuffer(altNames);
                    if (sb.charAt(0) == '[') {
                        sb.delete(altNames.length() - 1, altNames.length());
                        sb.delete(0, 1);

                    }

                    String [] splitted = sb.toString().split(",");
                    for (int idx = 0; idx <splitted.length; idx++) {
                        StringBuffer sb2 = new StringBuffer(splitted[idx]);
                        if (sb2.charAt(0) == '\"') {
                            sb2.delete(splitted[idx].length() - 1, splitted[idx].length());
                            sb2.delete(0, 1);

                        }
                        MyItemCard itemCard = new MyItemCard(0, String.valueOf(idx + 1), sb2.toString(), false);
                        itemList.add(itemCard);
                        country_view.setText(sb2.toString());
                    }



                    //
                    //System.out.print(altNames);
                    //country_view.setText(sb.toString());
                }
            } catch (JSONException e) {
                progressBar.setVisibility(View.INVISIBLE);
                if (displayIp == 1) {
                    result_view.setText("Not a valid ip");
                } else {
                    // show country info
                    country_view.setText("sorry, no info available");
                }
            }

        }
    }
}