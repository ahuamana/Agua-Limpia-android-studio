package edu.aha.agualimpiafinal.modulos.login.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityLoginBinding;
import edu.aha.helper.TextUtilsText;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    boolean isValidEmail= false,isValidPass=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        validateFields();


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

    public void _showMessage(String message)
    {
        Snackbar.make(findViewById(android.R.id.content),""+message, Snackbar.LENGTH_SHORT).show();
    }


}