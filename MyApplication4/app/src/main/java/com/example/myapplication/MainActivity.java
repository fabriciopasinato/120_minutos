package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mfirebaseauth;
    private FirebaseAuth.AuthStateListener maulistener;
    public static final int SING_IN =1;
    List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.spash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfirebaseauth = FirebaseAuth.getInstance();
        maulistener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    vueltahome();
                    Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(provider).setIsSmartLockEnabled(false).build(), SING_IN);
                }
            }

            };
        }


    //fin de oncreate

    @Override
    protected void onResume() {
        super.onResume();
        mfirebaseauth.addAuthStateListener(maulistener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mfirebaseauth.removeAuthStateListener(maulistener);
    }

    private void vueltahome(){
        Intent i = new Intent(this, homeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
