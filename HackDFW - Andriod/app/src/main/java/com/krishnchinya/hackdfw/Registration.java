package com.krishnchinya.hackdfw;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;


/**
 * Created by KrishnChinya on 2/11/17.
 */

public class Registration extends Activity {

    EditText dob, etFirstName, etLastName, etPhone, etEmail, etPassword, etRePass, etContact,etCarNo,etCompany,etHome;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    RadioButton btnMale, btnFemale;
    RadioGroup rgGender;

    Button signup;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //toast = new Toast(Registration.this);

        final DBhandler db_handler = new DBhandler(Registration.this);

        dob = (EditText) findViewById(R.id.etdob);
        signup = (Button) findViewById(R.id.btnSignup);
        dob.setInputType(InputType.TYPE_NULL);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide the keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                Calendar calenderinstance = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newdate = Calendar.getInstance();
                        newdate.set(year, month, dayOfMonth);
                        dob.setText(dateFormat.format(newdate.getTime()));

                    }
                }, calenderinstance.get(Calendar.YEAR), calenderinstance.get(Calendar.MONTH), calenderinstance.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePass = (EditText) findViewById(R.id.etRePass);
        //btnMale = (RadioButton) findViewById(R.id.btnMale);
        //btnFemale = (RadioButton) findViewById(R.id.btnFeale);

        etCarNo = (EditText) findViewById(R.id.etcarNo);
        etCompany = (EditText) findViewById(R.id.etOfficepin);
        etHome = (EditText) findViewById(R.id.etpinCode);
        //rgGender = (RadioGroup) findViewById(R.id.rgGender);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etFirstName.getText().toString().isEmpty())
                {
                    toast = toast.makeText(Registration.this,"First Name Cannot be Empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(etLastName.getText().toString().isEmpty())
                {
                    toast = toast.makeText(Registration.this,"Last Name Cannot be Empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if (etPhone.getText().toString().isEmpty() | etPhone.getText().toString().length() < 10)
                {
                    toast = toast.makeText(Registration.this,"Lenght cannot be less than 10 or empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(etEmail.getText().toString().isEmpty())
                {
                    toast = toast.makeText(Registration.this,"Email Cannot be Empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(etCarNo.getText().toString().isEmpty())
                {
                    toast = toast.makeText(Registration.this,"Car Number Cannot be Empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }


                if(etHome.getText().toString().isEmpty())
                {
                    toast = toast.makeText(Registration.this,"Home Pin Code Cannot be empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(etCompany.getText().toString().isEmpty())
                {

                    toast = toast.makeText(Registration.this,"Office Pin Code Cannot be empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }


                if(etPassword.getText().toString().isEmpty())
                {

                    toast = toast.makeText(Registration.this,"Password Cannot be Empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(etPassword.getText().toString().isEmpty())
                {
                    toast = toast.makeText(Registration.this,"Repeat Password Cannot be Empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(etRePass.getText().toString().isEmpty())
                {
                    toast = toast.makeText(Registration.this,"Repeat Password Cannot be Empty",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(!etRePass.getText().toString().equals(etPassword.getText().toString()))
                {
                    toast = toast.makeText(Registration.this,"Password must be same",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                DB_Setter_Getter dbsetter_getter = new DB_Setter_Getter(etFirstName.getText().toString(),etLastName.getText().toString()
                ,dob.getText().toString(), etPhone.getText().toString(),etEmail.getText().toString(),etCarNo.getText().toString(),
                        etHome.getText().toString(),etCompany.getText().toString(),etPassword.getText().toString());

                Boolean exists =  db_handler.checkMail(dbsetter_getter);

                if(exists == false)
                {
                    toast = toast.makeText(Registration.this,"Email ID already exists",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }


                db_handler.addRegistration(dbsetter_getter);
                db_handler.addLogin(dbsetter_getter);

                finish();


            }
        });

    }
}
