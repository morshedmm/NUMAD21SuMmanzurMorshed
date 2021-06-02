package edu.neu.madcourse.NUMAD21SuMmanzurMorshed;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyRviewHolder extends RecyclerView.ViewHolder {
    //public ImageView itemIcon;
    public TextView itemName;
    public TextView itemDesc;
    //public CheckBox checkBox;

    public MyRviewHolder(View itemView, final ItemClickListener listener) {
        super(itemView);
        //itemIcon = itemView.findViewById(R.id.item_icon);
        itemName = itemView.findViewById(R.id.my_item_name);
        itemDesc = itemView.findViewById(R.id.my_item_desc);
        //checkBox = itemView.findViewById(R.id.checkbox);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        listener.onItemClick(position);
                    }
                }
            }
        });

        /*checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCheckBoxClick(position);
                    }
                }
            }
        })*/;
    }
}