package com.example.finalorangeproject.screens.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPassword;
    private TextView mNewAccount;
    private AlertDialog mAlertDialog;
    EditText edt1;
    EditText edt2;
    Button loginbt;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPassword);
        mNewAccount = findViewById(R.id.newAccount);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Log in your Account");
        builder.setTitle("Please Wait");
        builder.setCancelable(false);
        mAlertDialog = builder.create();
        mNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
        });

        if (new SaveData(LoginActivity.this).getUserStatus()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        loginbt = findViewById(R.id.loginbt);
        signUpText = findViewById(R.id.newAccount);
        loginbt.setOnClickListener(view -> {
//                    if (checkEmail(edt1, edt2)) {
                    logging();
//                    }
                }

        );
        signUpText.setOnClickListener(view -> {
                    Intent i5 = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(i5);
                }
        );

    }

    boolean checkEmail(EditText email, EditText pass) {
        String emailInput = email.getText().toString().trim();
        String passInput = pass.getText().toString().trim();
        if ((!emailInput.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) && !passInput.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Succesed", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent2);
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

            return false;
        }
    }

    public void logging() {
        mAlertDialog.show();
        if (mEmail.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty()) {
            mAlertDialog.dismiss();
            Toast.makeText(this, "Enter You Info", Toast.LENGTH_SHORT).show();
        } else {
            UserClient.getInstance().signIn(mEmail.getText().toString(), mPassword.getText().toString()).enqueue(new Callback<UserResponseModel>() {
                @Override
                public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                    mAlertDialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getResponse().equals("تأكد من البريد او الرقم السرى"))
                            Toast.makeText(LoginActivity.this,  response.body().getResponse().toString(), Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(LoginActivity.this,  response.body().getResponse().toString(), Toast.LENGTH_SHORT).show();
                            new SaveData(LoginActivity.this).updateUserStatus(true);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                    } else
                        Toast.makeText(LoginActivity.this, response.body().getResponse().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<UserResponseModel> call, Throwable t) {
                    mAlertDialog.dismiss();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}