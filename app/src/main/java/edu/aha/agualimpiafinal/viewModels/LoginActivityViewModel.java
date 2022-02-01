package edu.aha.agualimpiafinal.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.AuthProvider;

import edu.aha.agualimpiafinal.modulos.login.model.User;
import edu.aha.agualimpiafinal.providers.UserProvider;

public class LoginActivityViewModel extends ViewModel {

    UserProvider mUserProvider;
    User mUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private MutableLiveData<String> _message = new MutableLiveData<>();
    private LiveData<String> message = _message;

    private MutableLiveData<Boolean> _isLogin = new MutableLiveData<>();
    private LiveData<Boolean> isLogin = _isLogin;


    public LiveData<String> isEmailLogging()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            _message.setValue("Ya tienes un inicio de session");
        } else {
            // No user is signed in
            _message.setValue("No haz ingresado");
        }

        return _message;

    }

    public LiveData<String> showMessage()
    {
        return _message;
    }

    public LiveData<String> loginWithEmail(String email, String pass)
    {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    _message.setValue("Bienvenido");

                }else
                {
                    _message.setValue("Usuario y/o contraseña incorrectos");
                }

            }
        });

        return _message;

    }

    public LiveData<String> loginAnonymous()
    {
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    _message.setValue("Bienvenido");

                }else
                {
                    _message.setValue("No es posible ingresar. Contacta con soporte");
                }
            }
        });

        return _message;

    }

}
