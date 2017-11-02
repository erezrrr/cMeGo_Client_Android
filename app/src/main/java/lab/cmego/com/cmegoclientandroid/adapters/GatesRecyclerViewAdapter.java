package lab.cmego.com.cmegoclientandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.Checkpoint;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;

/**
 * Created by Amit Ishai on 10/11/2017.
 */

public class GatesRecyclerViewAdapter extends RecyclerView.Adapter<GatesRecyclerViewAdapter.ViewHolder> {

    private List<Gate> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public GatesRecyclerViewAdapter(Context context, List<Gate> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    public void setData(List<Gate> data) {
        mData = data;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gate_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gate gate = mData.get(position);
        bind(holder, gate);
    }

    private void bind(ViewHolder holder, Gate gate) {

        Checkpoint checkpoint = ContentProvider.getInstance().getCheckpointForGate(gate);

        String checkpointName = checkpoint == null ? "NONE" : checkpoint.getName();

        holder.checkpointNameTV.setText("Checkpoint Name: " + checkpointName);

        holder.gateNameTV.setText("Gate Name: " + gate.getName());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView checkpointNameTV;
        public TextView gateNameTV;
        public TextView wifiRangeStatusTV;
        public TextView bleRangeStatusTV;

        public ViewHolder(View itemView) {
            super(itemView);

            checkpointNameTV = (TextView)itemView.findViewById(R.id.checkpointName);
            gateNameTV = (TextView)itemView.findViewById(R.id.gateName);
            wifiRangeStatusTV = (TextView)itemView.findViewById(R.id.wifiRangeStatus);
            bleRangeStatusTV = (TextView)itemView.findViewById(R.id.bleRangeStatus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    public Gate getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}