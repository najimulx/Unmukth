package com.example.dell.firstcry.View;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.Admin.AdminMainActivity;
import com.example.dell.firstcry.View.User.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static android.support.constraint.Constraints.TAG;
import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.mAUTH;
import static com.example.dell.firstcry.Statics.mREF_USER;

public class LoginActivity extends Activity {

    TextView iv_goto_signup,login;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iv_goto_signup=findViewById(R.id.tv_login_signup);
        email = findViewById(R.id.et_login_email);
        password = findViewById(R.id.et_login_password);
        login = findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setEnabled(false);
                if (!email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()){
                mAUTH.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                login.setEnabled(true);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FIREBASE_USER = mAUTH.getCurrentUser();
                                    mREF_USER.child(FIREBASE_USER.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String admin = dataSnapshot.child("ADMIN").getValue().toString();
                                            if (admin.equals("true")){
                                                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                                                finish();
                                            }else{
                                                startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });
        iv_goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                finish();
            }
        });

    }


}
