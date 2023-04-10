package com.example.harshitmittalscoupotask.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.harshitmittalscoupotask.RoomDB.Cars;
import com.example.harshitmittalscoupotask.databinding.CarListItemBinding;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private List<Cars> contacts;
    private CarListItemBinding binding;
    private OnClickListener onClickListener;

    public CustomListAdapter(Context context, List<Cars> contacts,OnClickListener onClickListener) {
        this.context = context;
        this.contacts = contacts;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    @SuppressLint("ViewHolder") CarListItemBinding binding = CarListItemBinding.inflate(LayoutInflater.from(context), parent, false);
    MyViewHolder viewHolder = new MyViewHolder(binding);

    Cars item = (Cars)getItem(position);
    viewHolder.bind(item);

    return binding.getRoot();
}
    public class MyViewHolder {
        private final CarListItemBinding binding;

        public MyViewHolder(CarListItemBinding binding) {
            this.binding = binding;
        }

        public void bind(Cars item) {
            binding.textCarModel.setText(item.getModelName());
            binding.textCarMaker.setText(item.getMakerName());
            Glide.with(context).load(item.getImage()).into(binding.carImage);
            binding.addImageButton.setOnClickListener(view->{
                onClickListener.onAddImage(item);
            });
            binding.deleteButton.setOnClickListener(view->{
                onClickListener.onDelete(item);
            });


        }
    }

}
interface OnClickListener {
    void onDelete(Cars car);
    void onAddImage(Cars car);
}
