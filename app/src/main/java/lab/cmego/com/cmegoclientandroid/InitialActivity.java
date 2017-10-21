package lab.cmego.com.cmegoclientandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class InitialActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(this, SignInSignUpActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

        finish();
    }
}
