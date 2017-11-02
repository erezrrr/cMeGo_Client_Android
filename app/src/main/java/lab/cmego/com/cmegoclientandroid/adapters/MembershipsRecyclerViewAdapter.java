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
import lab.cmego.com.cmegoclientandroid.model.Membership;

/**
 * Created by Amit Ishai on 10/11/2017.
 */

public class MembershipsRecyclerViewAdapter extends RecyclerView.Adapter<MembershipsRecyclerViewAdapter.ViewHolder> {

    private List<Membership> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MembershipsRecyclerViewAdapter(Context context, List<Membership> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    public void setData(List<Membership> data) {
        mData = data;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.membership_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Membership membership = mData.get(position);
        bind(holder, membership);
    }

    private void bind(ViewHolder holder, Membership membership) {
        holder.membershipIdTV.setText("Membership Id: " + membership.getId());
        holder.membershipTypeTV.setText("Membership Type: " + membership.getType());
        holder.userIdTV.setText("User Id: " + membership.getUserId());
        holder.vehicleIdTV.setText("Vehicle Id: " + membership.getVehicleId());
        holder.checkPointIdTV.setText("Checkpoint Id: " + membership.getCheckpointId());
        holder.accountIdTV.setText("Account Id: " + membership.getAccountId());
        holder.activationDateTV.setText("Activation Date: " + membership.getActivationDate());
        holder.expirationDateTV.setText("Expiration Date: " + membership.getExpirationDate());
        holder.internalAddressTV.setText("Internal Address: " + membership.getInternalAddress());
        holder.userAuthMethodsTV.setText("User Auth Methods: " + (membership.getUserAuthenticationMethods() == null ? "NONE" : "" + membership.getUserAuthenticationMethods().size()));
        holder.vehicleAuthMethodsTV.setText("Vehicle Auth Methods: " + (membership.getVehicleAuthenticationMethods() == null ? "NONE" : "" + membership.getVehicleAuthenticationMethods().size()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView membershipIdTV;
        public TextView membershipTypeTV;
        public TextView userIdTV;
        public TextView vehicleIdTV;
        public TextView checkPointIdTV;
        public TextView accountIdTV;
        public TextView activationDateTV;
        public TextView expirationDateTV;
        public TextView internalAddressTV;
        public TextView userAuthMethodsTV;
        public TextView vehicleAuthMethodsTV;

        public ViewHolder(View itemView) {
            super(itemView);

            membershipIdTV = (TextView)itemView.findViewById(R.id.membershipId);
            membershipTypeTV = (TextView)itemView.findViewById(R.id.membershipType);
            userIdTV = (TextView)itemView.findViewById(R.id.userId);
            vehicleIdTV = (TextView)itemView.findViewById(R.id.vehicleId);
            checkPointIdTV = (TextView)itemView.findViewById(R.id.checkPointId);
            accountIdTV = (TextView)itemView.findViewById(R.id.accountId);
            activationDateTV = (TextView)itemView.findViewById(R.id.activationDate);
            expirationDateTV = (TextView)itemView.findViewById(R.id.expirationDate);
            internalAddressTV = (TextView)itemView.findViewById(R.id.internalAddress);
            userAuthMethodsTV = (TextView)itemView.findViewById(R.id.userAuthMethods);
            vehicleAuthMethodsTV = (TextView)itemView.findViewById(R.id.vehicleAuthMethods);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Membership getItem(int id) {
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