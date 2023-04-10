package com.example.harshitmittalscoupotask;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshitmittalscoupotask.Adapters.DropdownAdapter;
import com.example.harshitmittalscoupotask.Models.CarDetailsModel;
import com.example.harshitmittalscoupotask.Models.MakerDetailsModel;
import com.example.harshitmittalscoupotask.RoomDB.Cars;
import com.example.harshitmittalscoupotask.ViewModel.MainViewModel;
import com.example.harshitmittalscoupotask.databinding.ActivityMainBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity{

    private MainViewModel viewModel;
    private ActivityResultLauncher<Intent> cameraResultLauncher;
    private ActivityResultLauncher<Intent> galleryResultLauncher;
    private ActivityMainBinding binding;
    private String makeName = "";
    private String modelName = "";
    private int userDBID = -1;
    private static final int PICK_FROM_GALLERY = 200;
    private static final int REQUEST_CAMERA = 201;
    private Cars recivedCar;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent mIntent = getIntent();
        userDBID = mIntent.getIntExtra("userDBID", 0);
        viewModel = new MainViewModel(getApplication());
        searchMakers();
        getCarsList(userDBID);
        handlerForOnResultActivity();
        viewModel.getMaker().observe(this, data -> setMakerSpinner(data.getResults()));
        viewModel.getModel().observe(this,data-> setModelSpinner(data.getResults()));
        viewModel.getCarsList().observe(this,data -> refreshListView(data));
        binding.addCarButton.setOnClickListener(view ->{
            if(modelName!=null&&modelName.length()>0 && makeName!=null && makeName.length()>0 ){
                Cars car = new Cars();
                car.setMakerName(makeName);
                car.setModelName(modelName);
                car.setImage("");
                car.setUserId(userDBID);
                viewModel.addCar(car);
            }
            getCarsList(userDBID);
        });
        binding.exitButton.setOnClickListener(view->{
            Intent myIntent = new Intent( MainActivity.this, LoginActivity.class); //Optional parameters
            MainActivity.this.startActivity(myIntent);
            finish();
        });
    }
    private void handlerForOnResultActivity(){
        galleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data != null) {
                            Uri contentURI = data.getData();
                            try {
                                Bitmap selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                                Uri savedImageUri = saveImageToInternalStorage(selectedImageBitmap);
                                recivedCar.setImage(String.valueOf(savedImageUri));
                                viewModel.updateCar(recivedCar);
                                adapter.notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Failed To Save Image", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
        cameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Bitmap thumbnail = (Bitmap) data.getExtras().get("data"); // Bitmap from camera
                        Uri saveImageToInternalStorage = saveImageToInternalStorage(thumbnail);
                        recivedCar.setImage(String.valueOf(saveImageToInternalStorage));
                        viewModel.updateCar(recivedCar);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    private void refreshListView(List<Cars> list){
        adapter = new CustomListAdapter(this, list, new OnClickListener() {
            @Override
            public void onDelete(Cars item) {
                recivedCar = item;
                viewModel.deleteCar(item);
                getCarsList(userDBID);
            }
            @Override
            public void onAddImage(Cars item) {
                recivedCar = item;
                openGallery();
            }
        });
        adapter.notifyDataSetChanged();
        binding.listView.setAdapter(adapter);
    }

    private  void searchMakers(){ viewModel.requestMaker();}
    private void searchModels(int makeId){
        viewModel.requestModel(makeId);
    }
    private void getCarsList(int id){ viewModel.carList(id);}
    private void setMakerSpinner(List<MakerDetailsModel> list){
         DropdownAdapter<MakerDetailsModel> adapter = new DropdownAdapter<MakerDetailsModel>(this, list);

         binding.makerNameDropdown.setAdapter(adapter);
         binding.makerNameDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                makeName = list.get(position).getMakeName();
                searchModels(list.get(position).getMakeId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });
    }
    private void setModelSpinner(List<CarDetailsModel> list){
        DropdownAdapter<CarDetailsModel> adapter = new DropdownAdapter<CarDetailsModel>(this, list);

        binding.modelNameDropdown.setAdapter(adapter);
        binding.modelNameDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(list.get(position).getModel_Name()!=null){
                    modelName = list.get(position).getModel_Name();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });
    }

    private Uri saveImageToInternalStorage(Bitmap bitmap) {

        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("IMAGE_DIRECTORY", Context.MODE_PRIVATE);

        file = new File(file, UUID.randomUUID().toString() + ".jpg");

        try {
            OutputStream stream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            stream.flush();

            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.parse(file.getAbsolutePath());
    }

    private void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
            } else {
                openDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openDialog() {
        Dialog dialog = new Dialog(this,R.style.Dialog_No_Border);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.camera_gallery_picker_view);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        dialog.setCanceledOnTouchOutside(true);
        TextView gallery = dialog.findViewById(R.id.gallery);
        TextView camera = dialog.findViewById(R.id.camera);
        gallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            galleryResultLauncher.launch(intent);
            dialog.dismiss();
        });

        camera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResultLauncher.launch(intent);
            dialog.dismiss();
        });
        dialog.show();

    }

}