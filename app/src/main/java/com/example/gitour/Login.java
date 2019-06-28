package com.example.gitour;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;

import static android.content.ContentValues.TAG;


public class Login extends Activity {


    Button loginButton;
    FirebaseAuth mAuth;
    String serverResponse = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText(R.string.login_wait);
                getGithubToken();
            }
        });

   //     FirebaseUser currentUser = mAuth.getCurrentUser();
    //    Log.i("user_name", currentUser.getDisplayName());
       // updateUI(currentUser);

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    //    FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    void login() {

        AuthCredential credential = GithubAuthProvider.getCredential(serverResponse);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();                       }
                    }
                });
    }

    void getGithubToken()
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/login/oauth/authorize?client_id=a646e298c59e7e31ce49"));
        startActivity(browserIntent);
        finish();
    }
}
