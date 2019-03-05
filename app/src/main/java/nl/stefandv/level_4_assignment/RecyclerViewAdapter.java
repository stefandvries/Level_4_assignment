package nl.stefandv.level_4_assignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Item> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    RecyclerViewAdapter(Context context, ArrayList<Item> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final Item item = mData.get(position);
        holder.titleTextView.setText((item.getTitle()));
        holder.descriptionTextView.setText((item.getDescription()));


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            if (isChecked){
                //Als je op de view klikt
                holder.titleTextView.setPaintFlags(holder.titleTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.descriptionTextView.setPaintFlags(holder.descriptionTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }else{
                holder.titleTextView.setPaintFlags(holder.titleTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                holder.descriptionTextView.setPaintFlags(holder.descriptionTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }


        }
                                                   }
        );

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        LinearLayout parentLayout;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            parentLayout = itemView.findViewById(R.id.parentalLayout);
            parentLayout.setClickable(true);


            checkBox = itemView.findViewById(R.id.checkBox);


        }
    }

}





