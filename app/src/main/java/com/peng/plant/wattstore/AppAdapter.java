package com.peng.plant.wattstore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private static final String TAG = "AppAdapter";







    Context context;

    ArrayList<AppData> list;

    public AppAdapter(ArrayList<AppData> list, Context context) {
        this.list = list;
        this.context = context;



    }

    public interface OnItemClicklistener {
        void onItemClick(View v, int pos);
    }

    private OnItemClicklistener mListener = null;

    public void setOnClickListener(OnItemClicklistener listener){
        this.mListener = listener;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppData data = list.get(position);
        holder.title.setText(data.appnamekor);




    }


    @Override
    public int getItemCount() {
        return
        list.size(); //전체 목록
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;



        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getBindingAdapterPosition() + " clicked.");
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });
            title = (TextView) v.findViewById(R.id.title);


        }



    }





}
