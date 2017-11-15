package lab.cmego.com.cmegoclientandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.Checkpoint;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;

public class GateSpinnerListAdapter extends ArrayAdapter<Gate> {

    LayoutInflater flater;

    public GateSpinnerListAdapter(Context context, int resouceId, int textviewId, List<Gate> list){

        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }

//    public void setData(List<Gate> data) {
//        notifyDataSetChanged();
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        Gate rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.gate_spinner_list_item, null, false);

            holder.checkPointName = (TextView) rowview.findViewById(R.id.checkpointName);
            holder.gateName = (TextView) rowview.findViewById(R.id.gateName);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        Checkpoint checkpoint = ContentProvider.getInstance().getCheckpointForGate(rowItem);

        holder.checkPointName.setText(checkpoint.getName());
        holder.gateName.setText(rowItem.getName());

        return rowview;
    }

    private class viewHolder{
        TextView checkPointName;
        TextView gateName;
    }
}