package com.peng.plant.wattstore;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peng.recyclerview_launcher.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private static final String TAG = "AppAdapter";






//    PackageManager pm;

    private Context context;
    private ArrayList<AppData> list;
    private PowerClientObserver observer;



    public AppAdapter(Context context, ArrayList<AppData> list, PowerClientObserver observer) {
        this.context = context;
        this.list = list;
        this.observer = observer;



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

//        holder.getTextView().setText(list.get(position).loadLabel(pm));
//        holder.getImageView().setImageDrawable(list.get(position).loadIcon(pm));
        AppData data = list.get(position);





    }


    @Override
    public int getItemCount() {
        return
        list.size(); //전체 목록
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;


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
            img = (ImageView) v.findViewById(R.id.img);

        }

//        public TextView getTextView() {
//            return title;
//        }
//        public ImageView getImageView(){
//            return img;
//        }



    }





}
