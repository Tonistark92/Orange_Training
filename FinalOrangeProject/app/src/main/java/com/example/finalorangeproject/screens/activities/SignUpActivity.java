package com.example.finalorangeproject.screens.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalorangeproject.R;
import com.example.finalorangeproject.database.SaveData;
import com.example.finalorangeproject.model.UserResponseModel;
import com.example.finalorangeproject.network.user.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private AlertDialog mAlertDialog;
    Button signUpbt;
    String name,email;
    TextView logInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mUsername = findViewById(R.id.etUserName);
        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPassword);
        logInText = findViewById(R.id.logintext);
        signUpbt = findViewById(R.id.signup_button);
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setMessage("Creating Account....");
        builder.setTitle("Please Wait");
        builder.setCancelable(false);
        mAlertDialog = builder.create();

        signUpbt.setOnClickListener(view -> {
            if(checkEmail(mEmail, mPassword, mUsername)){
                onSignUp();
            }
        });
        logInText.setOnClickListener(view -> {
                    Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(i);
                }
        );
        if (new SaveData(SignUpActivity.this).getUserStatus()) {
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
            finish();
        }
    }

    boolean checkEmail(EditText email, EditText pass, EditText user) {
        String emailInput = email.getText().toString().trim();
        String passInput = pass.getText().toString().trim();
        String userName = user.getText().toString().trim();
        if ((!emailInput.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) && !passInput.isEmpty() && !userName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Succesed", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent2);
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void onSignUp() {
        if (mUsername.getText().toString().isEmpty() ||
                mEmail.getText().toString().isEmpty() ||
                mPassword.getText().toString().isEmpty()
        ) Toast.makeText(this, "Check your Information", Toast.LENGTH_SHORT).show();
        else {
            mAlertDialog.show();
            UserClient.getInstance().signUp(mUsername.getText().toString(),
                    mEmail.getText().toString(),
                    mPassword.getText().toString()).enqueue(new Callback<UserResponseModel>() {
                @Override
                public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                    mAlertDialog.dismiss();
                    if (response.isSuccessful()) {

                        if (response.body().getResponse().equals("هذا البريد مسجل من قبل")){
                            email=mEmail.getText().toString().trim();
                            name=mUsername.getText().toString().trim();
                            new SaveData(getApplicationContext()).setUserData(email,name);
                            Toast.makeText(SignUpActivity.this, response.body().getResponse().toString(), Toast.LENGTH_SHORT).show();

                        }

                        else {
                            Toast.makeText(SignUpActivity.this,  response.body().getResponse().toString(), Toast.LENGTH_SHORT).show();
                            email=mEmail.getText().toString().trim();
                            name=mUsername.getText().toString().trim();
                            new SaveData(getApplicationContext()).setUserData(email,name);
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                    } else
                        Toast.makeText(SignUpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<UserResponseModel> call, Throwable t) {
                    mAlertDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


}