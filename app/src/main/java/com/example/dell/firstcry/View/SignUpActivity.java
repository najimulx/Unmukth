package com.example.dell.firstcry.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.firstcry.Model.UserObject;
import com.example.dell.firstcry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.mAUTH;
import static com.example.dell.firstcry.Statics.mREF_USER;

public class SignUpActivity extends AppCompatActivity {
    ImageView iv_goto_login;
    EditText email,password1,dob,password2;
    TextView bt_signup;
    CheckBox cb_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        iv_goto_login = findViewById(R.id.tv_signup_login);
        email = findViewById(R.id.et_signup_email);
        password1 = findViewById(R.id.et_signup_pass1);
        password2 = findViewById(R.id.et_signup_pass2);
        bt_signup = findViewById(R.id.bt_signup);
        cb_admin = findViewById(R.id.checkBox);
        dob = findViewById(R.id.et_signup_dob);
        sessionDatePick(dob);
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().isEmpty() && !password1.getText().toString().isEmpty() &&
                        (password1.getText().toString().equals(password2.getText().toString()))){
                    mAUTH.createUserWithEmailAndPassword(email.getText().toString(),password1.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    bt_signup.setEnabled(true);
                                    if (task.isSuccessful()) {
                                        FIREBASE_USER = mAUTH.getCurrentUser();
                                        UserObject userobj = new UserObject();
                                        userobj.UID = FIREBASE_USER.getUid();
                                        userobj.EMAIL = email.getText().toString();
                                        userobj.NAME="null";
                                        userobj.PHONE="null";
                                        userobj.DOB = dob.getText().toString();
                                        if (cb_admin.isChecked()){
                                            userobj.ADMIN = "true";
                                        }
                                        else{
                                            userobj.ADMIN = "false";
                                        }
                                        mREF_USER.child(userobj.UID).setValue(userobj);
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                        mAUTH.signOut();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Sign up failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(SignUpActivity.this,"Please enter valid data",Toast.LENGTH_SHORT).show();
                }
            }
        });
        iv_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

    private void sessionDatePick(final EditText editText) {
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
}
