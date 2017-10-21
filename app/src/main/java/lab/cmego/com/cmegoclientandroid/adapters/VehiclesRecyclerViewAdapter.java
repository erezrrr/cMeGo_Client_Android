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
import lab.cmego.com.cmegoclientandroid.model.Vehicle.Vehicle;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.VehicleIdentifier;

/**
 * Created by Amit Ishai on 10/11/2017.
 */

public class VehiclesRecyclerViewAdapter extends RecyclerView.Adapter<VehiclesRecyclerViewAdapter.ViewHolder> {

    private List<Vehicle> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public VehiclesRecyclerViewAdapter(Context context, List<Vehicle> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    public void setData(List<Vehicle> data) {
        mData = data;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.vehicle_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vehicle vehicle = mData.get(position);
        bind(holder, vehicle);
    }

    private void bind(ViewHolder holder, Vehicle vehicle) {
        holder.vehicleIdTV.setText("Vehicle Id: " + vehicle.getId());
        holder.vehicleTypeTV.setText("Vehicle Type: " + vehicle.getType());

        StringBuilder idStringBuilder = new StringBuilder();

        if (vehicle.getVehicleIdentifiers() == null) {
            idStringBuilder.append("NONE");
        } else {

            for (VehicleIdentifier vehicleIdentifier : vehicle.getVehicleIdentifiers()){
                 idStringBuilder.append(vehicleIdentifier.toString() + "\n");
            }
        }

        holder.vehicleIdentifiersTV.setText("Vehicle Identifiers: \n" + idStringBuilder.toString());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView vehicleIdTV;
        public TextView vehicleTypeTV;
        public TextView vehicleIdentifiersTV;

        public ViewHolder(View itemView) {
            super(itemView);

            vehicleIdTV = (TextView)itemView.findViewById(R.id.vehicleId);
            vehicleTypeTV = (TextView)itemView.findViewById(R.id.vehicleType);
            vehicleIdentifiersTV = (TextView)itemView.findViewById(R.id.vehicleIdentifiers);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Vehicle getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}