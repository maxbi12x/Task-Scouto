package com.example.harshitmittalscoupotask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.harshitmittalscoupotask.Models.CarDetailsModel;
import com.example.harshitmittalscoupotask.RoomDB.Cars;
import com.example.harshitmittalscoupotask.RoomDB.Users;
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

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Inflate the layout for each item in the ListView
////        binding = CarListItemBinding = CarListItemBinding.inflate(convertView,LayoutInflater)
//        CarListItemBinding mBinding;
//        if (convertView == null) {
//            mBinding = CarListItemBinding.inflate(getLayoutInflater)
//            convertView = LayoutInflater.from(context).inflate(R.layout.car_list_item, parent, false);
//        }
//        Cars car = (Cars) getItem(position);
//        TextView carMakerText = convertView.findViewById(R.id.text_car_maker);
//        carMakerText.setText(String.valueOf(position));
//
//        // Get the data model for this item position
////        Contact contact = (Contact) getItem(position);
////
////        // Populate the layout with data from the data model
////        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
////        TextView phoneTextView = convertView.findViewById(R.id.phoneTextView);
////        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
////
////        nameTextView.setText(contact.getName());
////        phoneTextView.setText(contact.getPhoneNumber());
////        emailTextView.setText(contact.getEmail());
//
//        // Return the populated layout for this item position
//        return convertView;
//    }
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

//            binding.itemTitle.setText(item.getTitle());
//            binding.itemDescription.setText(item.getDescription());
        }
    }
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (binding == null) {
//            binding = CarListItemBinding.inflate(LayoutInflater.from(context),parent,false);
//        }
//        Cars car = (Cars) getItem(position);
//        Log.e("Running",car.getMakerName());
//        binding.textCarMaker.setText(car.getMakerName());
//        binding.textCarModel.setText(car.getModelName());
//        return binding.getRoot();
//    }
}
interface OnClickListener {
    void onDelete(Cars car);
    void onAddImage(Cars car);
}
