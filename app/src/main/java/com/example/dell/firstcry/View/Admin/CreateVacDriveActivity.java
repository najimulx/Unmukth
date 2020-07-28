package com.example.dell.firstcry.View.Admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.VAC_TYPE;
import static com.example.dell.firstcry.Statics.mREF_USER;
import static com.example.dell.firstcry.Statics.mREF_VACC;

public class CreateVacDriveActivity extends AppCompatActivity {

    EditText title, desc, et_location, et_price, et_date, et_time;
    Button bt_done, bt_select_loc;
    Spinner spinner;
    CheckBox cb_paid, cb_enroll;
    TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vac_drive);
        title = findViewById(R.id.et_title_admin_create_drive);
        desc = findViewById(R.id.et_description_admin_create_vac);
        et_location = findViewById(R.id.et_location_admin);
        bt_done = findViewById(R.id.bt_create_vac_done_admin);
        bt_select_loc = findViewById(R.id.bt_select_location_admin);
        spinner = findViewById(R.id.spinner_admin_create_vac);
        cb_enroll = findViewById(R.id.cb_enable_enroll);
        cb_paid = findViewById(R.id.cb_enable_paid);
        et_price = findViewById(R.id.et_price_create_vac);
        et_date = findViewById(R.id.et_date_createvac);
        et_time = findViewById(R.id.et_time_createvac);
        timePickerDialog =
                new TimePickerDialog(CreateVacDriveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                et_time.setText("H:"+hourOfDay+":M:"+minutes);
                            }
                        }, 0, 0, false);
        et_location.setEnabled(false);
        String result = getIntent().getStringExtra("result");
        String latitude = getIntent().getStringExtra("lat");
        String longitute = getIntent().getStringExtra("long");
        if (result != null && !result.equals("")) {
            et_location.setText(result);
            et_location.setEnabled(true);
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,VAC_TYPE);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        sessionDatePick(et_date);
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }

        });
        cb_paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_price.setVisibility(View.VISIBLE);
                } else {
                    et_price.setVisibility(View.INVISIBLE);
                }
            }
        });
        bt_select_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminMapsActivity.class));
                finish();
            }
        });
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etitle, edesc, eLoc, time, date;
                etitle = title.getText().toString();
                time = et_time.getText().toString();
                date = et_date.getText().toString();
                edesc = desc.getText().toString();
                eLoc = et_location.getText().toString();
                String paid = "Free";
                String enroll = "Direct Visit";

                if (!etitle.isEmpty() && !edesc.isEmpty() && !eLoc.isEmpty() && !time.isEmpty() && !date.isEmpty()) {
                    if (cb_paid.isChecked()) {
                        if (!et_price.getText().toString().isEmpty() && !et_price.getText().equals("")) {
                            paid = et_price.getText().toString();
                        } else {
                            paid = "Error";
                        }
                    }
                    if (cb_enroll.isChecked()) {
                        enroll = "Enrollment Needed";
                    }
                    VacDriveObject vacDriveObject = new VacDriveObject();
                    vacDriveObject.DESC = edesc;
                    vacDriveObject.ENROLL = enroll;
                    vacDriveObject.PAID = paid;
                    vacDriveObject.LOCATION = eLoc;
                    vacDriveObject.TITLE = etitle;
                    vacDriveObject.LAT = latitude;
                    vacDriveObject.LONG = longitute;
                    vacDriveObject.VACC_NAME = String.valueOf(spinner.getSelectedItem());
                    vacDriveObject.DATE = date;
                    vacDriveObject.TIME = time;
                    createVacDrive(vacDriveObject);
                } else {
                    Toast.makeText(CreateVacDriveActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void createVacDrive(VacDriveObject vacDriveObject) {
        String key = mREF_VACC.push().getKey();
        mREF_VACC.child(key).setValue(vacDriveObject);
        mREF_USER.child(FIREBASE_USER.getUid()).child("vacc_index").child(key).setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mREF_VACC.child(key).child("admin").setValue(FIREBASE_USER.getUid());
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

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateVacDriveActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
}
