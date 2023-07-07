package cn.gankao.bbbcheck.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cn.gankao.bbbcheck.R;

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 18:09
 */
public class PenSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater inflater;
    private OnItemClickListener listener;

    private final ArrayList<String> addresses;
    private final ArrayList<BluetoothDevice> devices;


    public PenSearchAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        devices = new ArrayList<>();
        addresses = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.item_pen_search, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;

        mHolder.name.setText("设备名称："+devices.get(i).getName());
        mHolder.address.setText("设备地址："+devices.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, address,text_connect,text_last;
        private RelativeLayout relativeItem;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            text_connect = itemView.findViewById(R.id.text_connect);
            text_last = itemView.findViewById(R.id.text_last);
            relativeItem = itemView.findViewById(R.id.relativeItem);
            relativeItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition()<0 || getAdapterPosition()>=devices.size()){
                return;
            }
            if (listener != null)
                listener.onItemClick(devices.get(getAdapterPosition()),getAdapterPosition());
        }
    }


    public void addDevice(BluetoothDevice device) {
        if (addresses.contains(device.getAddress())) {
            devices.set(addresses.indexOf(device.getAddress()), device);
            return;
        }

        devices.add(device);
        addresses.add(device.getAddress());

        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(BluetoothDevice device, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}