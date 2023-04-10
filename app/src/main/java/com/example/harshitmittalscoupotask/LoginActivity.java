package com.example.harshitmittalscoupotask;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Toast;
import com.example.harshitmittalscoupotask.RoomDB.Users;
import com.example.harshitmittalscoupotask.ViewModel.LoginViewModel;
import com.example.harshitmittalscoupotask.databinding.ActivityLoginBinding;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private boolean isSignup = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new LoginViewModel(getApplication());


        binding.signupButton.setOnClickListener(v -> {
            if(isValid()){
                loginViewModel.findUserWithUsername( Objects.requireNonNull(binding.inputName.getText()).toString());
            }
        });


        binding.userStatusText.setOnClickListener(v ->{ if(isSignup){ setSignIn(); }else{ setSignUp();}});



        loginViewModel.getIsUsername().observe(this,data ->{
            if(isSignup){
                if(data!=null){
                    Toast.makeText(LoginActivity.this,"Username Already Exist",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"Logging in as new user",Toast.LENGTH_SHORT).show();
                    Users user = new Users();
                    user.setEmail(Objects.requireNonNull(binding.inputName.getText()).toString());
                    user.setPassword(Objects.requireNonNull(binding.password.getText()).toString());
                    loginViewModel.addUser(user);
                    loggedIn(user);
                }
            }else{
                if(data!=null&&data.getPassword().equals(Objects.requireNonNull(binding.password.getText()).toString())){
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    loggedIn(data);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Wrong Password or Username",Toast.LENGTH_SHORT).show();

                }
            }

        });

        loginViewModel.getIsUsername().observe(this,data ->{

        });


        setSignIn();

    }

    private void loggedIn(Users user){
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class); //Optional parameters
        myIntent.putExtra("userDBID",user.getId());
        LoginActivity.this.startActivity(myIntent);
        finish();
    }

    private Boolean isValid(){
        if(binding.inputName.getText() == null
                || binding.inputName.getText().length()==0
                || binding.password.getText() == null
                || binding.password.getText().length() == 0
                || (isSignup && (binding.confirmPassword.getText() == null
                || binding.confirmPassword.getText().length() == 0))){
            Toast.makeText(LoginActivity.this,"Please fill input fields",Toast.LENGTH_SHORT).show();
            return false;
        }else if(isSignup && !binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())){
            Toast.makeText(LoginActivity.this,"Passwords not matching",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void setSignUp(){
        binding.confirmPasswordLayout.setVisibility(View.VISIBLE);
        TransitionManager.beginDelayedTransition(binding.textLayout, new AutoTransition());
        binding.userStatusText.setText("Existing User");
        binding.signupButton.setText("SignUp");
        isSignup = true;
    }
    private void setSignIn(){
        binding.confirmPasswordLayout.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(binding.textLayout, new AutoTransition());
        binding.userStatusText.setText("New User");
        binding.signupButton.setText("SignIn");
        isSignup = false;
    }
}