package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkCollectorActivity extends AppCompatActivity {

    //Creating the essential parts needed for a Recycler view to work: RecyclerView, Adapter, LayoutManager
    private ArrayList<MyItemCard> itemList = new ArrayList<>();


    private RecyclerView recyclerView;
    private MyRviewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private FloatingActionButton addButton;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        init(savedInstanceState);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                addItem(pos);
            }
        });

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //Toast.makeText(LinkCollectorActivity.this, "Delete an item", Toast.LENGTH_SHORT).show();
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Link Entry deleted", Snackbar.LENGTH_LONG).show();

                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);

                rviewAdapter.notifyItemRemoved(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }



    // Handling Orientation Changes on Android
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
            //ItemCard item1 = new ItemCard(R.drawable.pic_gmail_01, "Gmail", "Example description", false);
            //ItemCard item2 = new ItemCard(R.drawable.pic_google_01, "Google", "Example description", false);
            //ItemCard item3 = new ItemCard(R.drawable.pic_youtube_01, "Youtube", "Example description", false);
            //itemList.add(item1);
            //itemList.add(item2);
            //itemList.add(item3);
        }

    }

    private void createRecyclerView() {


        rLayoutManger = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new MyRviewAdapter(itemList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //attributions bond to the item has been changed
                itemList.get(position).onItemClick(position);
                // Code for web page
                //showWebPage(null, itemList.get(position).getItemDesc());
                rviewAdapter.notifyItemChanged(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemList.get(position).getItemDesc()));
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    //Toast.makeText(LinkCollectorActivity.this, "Invalid Link!", Toast.LENGTH_SHORT).show();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Invalid Link!", Snackbar.LENGTH_LONG).show();

                }

            }



            @Override
            public void onCheckBoxClick(int position) {
                //attributions bond to the item has been changed
                itemList.get(position).onCheckBoxClick(position);

                rviewAdapter.notifyItemChanged(position);
            }
        };
        rviewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);


    }

    private void addItem(int position) {
        EditText nameText = (EditText)findViewById(R.id.name);
        EditText urlText = (EditText)findViewById(R.id.url);

        Pattern pattern1 = Pattern.compile("http://www", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(urlText.getText().toString());

        Pattern pattern2 = Pattern.compile("https://www", Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = pattern2.matcher(urlText.getText().toString());

        if (! matcher1.find() && ! matcher2.find()) {
            //Toast.makeText(LinkCollectorActivity.this, "Not Added! use http://www.", Toast.LENGTH_SHORT).show();
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Not Added! use http://www.", Snackbar.LENGTH_LONG).show();

            return;
        }

        itemList.add(position, new MyItemCard(R.drawable.empty, nameText.getText().toString(), urlText.getText().toString(), false));
        //Toast.makeText(LinkCollectorActivity.this, "Link Added!", Toast.LENGTH_SHORT).show();
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Link Added!", Snackbar.LENGTH_LONG).show();


        nameText.getText().clear();
        urlText.getText().clear();
        View myLayout = findViewById(android.R.id.content);
        myLayout.requestFocus();

        //findViewById(R.id.addButton).requestFocus();


        rviewAdapter.notifyItemInserted(position);
    }

    /*
    public void showWebPage(View view, String urlLink) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlLink));
        startActivity(intent);
    }

     */


}