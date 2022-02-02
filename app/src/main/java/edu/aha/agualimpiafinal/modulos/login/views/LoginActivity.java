package edu.aha.agualimpiafinal.modulos.login.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityLoginBinding;
import edu.aha.agualimpiafinal.viewModels.LoginActivityViewModel;
import edu.aha.helper.TextUtilsText;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    boolean isValidEmail= false,isValidPass=false;
    LoginActivityViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewmodel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        validateFields();
        loginFirebase();

        showObservables();

    }

    private void showObservables() {

        viewmodel.showMessage().observe(this, message -> {
            _showMessageMainThread(message);
        });

    }

    private void loginFirebase() {

        binding.ingresarLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextUtilsText.hideKeyboard(LoginActivity.this);

                if(TextUtilsText.isConnected(getApplicationContext())) viewmodel.loginWithEmail(binding.email.toString().trim(),binding.pass.toString().trim());
                else _showMessageMainThread("Sin conexion a internet");
            }
        });

        binding.continuarAnonimoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtilsText.isConnected(getApplicationContext())) viewmodel.loginAnonymous();
                else _showMessageMainThread("Sin conexion a internet");
            }
        });
    }

    private void validateFields() {


        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) validEmail(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() > 5)
                {
                    binding.passLayout.setError(null);
                    isValidPass = true;
                }
                else
                    {
                        binding.passLayout.setError("La contraseña debe tener minimo 6 caracteres");
                        isValidPass = false;
                    }

                validEmailPass();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void validEmail(CharSequence s) {

        if(TextUtilsText.isValidEmail(s))
        {
            binding.emailLayout.setError(null);
            isValidEmail = true;
        }
        else
            {
            binding.emailLayout.setError("Correo electrónico invalido");
            isValidEmail=false;
            }

        validEmailPass();
    }

    private void validEmailPass()
    {
        if(isValidEmail&&isValidPass)
        {
            binding.ingresarLoginButton.setEnabled(true);
            binding.ingresarLoginButton.setBackgroundTintMode(PorterDuff.Mode.SCREEN);
            binding.ingresarLoginButton.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.greenPrimary));
            binding.ingresarLoginButton.setTextColor(ContextCompat.getColor(this,R.color.white));

        }else
        {
            binding.ingresarLoginButton.setEnabled(false);
            binding.ingresarLoginButton.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
            binding.ingresarLoginButton.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.color_input_text));
            binding.ingresarLoginButton.setTextColor(ContextCompat.getColor(this,R.color.color_input_text));
        }
    }

    private void _showMessageMainThread(String message)
    {
       Snackbar.make(findViewById(android.R.id.content),""+message, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

    }
}