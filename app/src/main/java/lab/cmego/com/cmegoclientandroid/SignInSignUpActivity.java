package lab.cmego.com.cmegoclientandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInSignUpActivity extends AppCompatActivity {

    private static final String TAG = SignInSignUpActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private View mNotSignedInFrame;
    private Button mSignInButton;
    private Button mSignUpButton;
    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);

        mAuth = FirebaseAuth.getInstance();

        mNotSignedInFrame = findViewById(R.id.notSignedInFrame);
        mSignInButton = (Button)findViewById(R.id.signInButton);
        mSignUpButton = (Button)findViewById(R.id.signUpButton);
        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    //                    showUI();

                    startActivity(new Intent(SignInSignUpActivity.this, MainActivity.class));
                    finish();

                    //                    addUserData();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    showUI();
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        showUI();
    }

    private void showUI() {
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        startActivity(new Intent(SignInSignUpActivity.this, SignUpActivity.class));
    }

    private void signIn() {
        Editable emailEditable = mEmail.getEditableText();

        if(emailEditable == null || TextUtils.isEmpty(emailEditable.toString())){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailEditable.toString();

        Editable passwordEditable = mPassword.getEditableText();

        if(passwordEditable == null || TextUtils.isEmpty(passwordEditable.toString())){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = passwordEditable.toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignInSignUpActivity.this, "Sign in failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
