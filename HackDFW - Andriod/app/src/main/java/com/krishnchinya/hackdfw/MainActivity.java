package com.krishnchinya.hackdfw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.krishnchinya.hackdfw.GlobalVars;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login,newuser,addride;
    Toast toast;

    DBhandler dBhandler;
   // String[] details;
   GlobalVars globalVars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dBhandler = new DBhandler(MainActivity.this);

        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        newuser = (Button) findViewById(R.id.Newuser);
        addride = (Button) findViewById(R.id.AddNewRide);

        addride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRide.class);
                startActivityForResult(intent,1);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty()) {
                    toast.setText("UserName Cannot be empty");
                    toast.show();
                    return;
                }

                if (password.getText().toString().isEmpty())
                {
                    toast.setText("Password Cannot be empty");
                    toast.show();
                    return;
                }

                DB_Setter_Getter db_setter_getter = new DB_Setter_Getter(username.getText().toString(),password.getText().toString());

                String[] details = dBhandler.getcredentials(db_setter_getter);

                if(details[0].equals(username.getText().toString()) && details[1].equals(password.getText().toString()))
                {
                    //set the global variables for further use.
                    globalVars = (GlobalVars) getApplicationContext();
                    globalVars.setUsername(details[2].toString());
                    globalVars.setMailid(details[0].toString());

                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivityForResult(intent,1);
                }



            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivityForResult(intent,1);
            }
        });

    }
}
