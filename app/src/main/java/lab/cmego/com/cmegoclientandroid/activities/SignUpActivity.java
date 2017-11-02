package lab.cmego.com.cmegoclientandroid.activities;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lab.cmego.com.cmegoclientandroid.R;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mSignUpButton;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    Toast.makeText(SignUpActivity.this, "User created. UserId: " + user.getUid(), Toast.LENGTH_LONG).show();

                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        if(mSignUpButton == null){
            mSignUpButton = (Button)findViewById(R.id.signUpButton);
            mEmail = (EditText)findViewById(R.id.email);
            mPassword = (EditText)findViewById(R.id.password);
            mConfirmPassword = (EditText)findViewById(R.id.confirmPassword);
        }

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
         Editable emailEditable = mEmail.getEditableText();

        if(emailEditable == null || TextUtils.isEmpty(emailEditable.toString())){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailEditable.toString();

        if(!isEmailValid(email)){
            Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        Editable passwordEditable = mPassword.getEditableText();
        Editable confirmPasswordEditable = mConfirmPassword.getEditableText();

        if(passwordEditable == null || TextUtils.isEmpty(passwordEditable.toString())){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(confirmPasswordEditable == null || TextUtils.isEmpty(confirmPasswordEditable.toString())){
            Toast.makeText(this, "Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = passwordEditable.toString();
        String confirmPassword = confirmPasswordEditable.toString();

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(this, "Passwords should be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sign up failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
