package com.example.sachin.location;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText name, date, age, address, email, password;
    RadioButton male,female,other;
    RadioGroup radioGroup;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.name);
        date=(EditText)findViewById(R.id.date);
        age=(EditText)findViewById(R.id.age);
        address=(EditText)findViewById(R.id.address);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        male=(RadioButton)findViewById(R.id.male);
        female=(RadioButton)findViewById(R.id.female);
        other=(RadioButton)findViewById(R.id.other);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")&&date.getText().toString().equals("")&&age.getText().toString().equals("")&&address.getText().toString().equals("")&&email.getText().toString().equals("")&&password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Some fields missing...",Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final String uid = database.getReference().push().getKey();
                    DatabaseReference databaseReference = database.getReference(getString(R.string.firebase_path));
                    databaseReference.child("userDetails").child(uid).child("name").setValue(name.getText().toString());
                    databaseReference.child("userDetails").child(uid).child("date").setValue(date.getText().toString());
                    databaseReference.child("userDetails").child(uid).child("age").setValue(age.getText().toString());
                    databaseReference.child("userDetails").child(uid).child("address").setValue(address.getText().toString());
                    databaseReference.child("userDetails").child(uid).child("emailID").setValue(email.getText().toString());
                    databaseReference.child("userDetails").child(uid).child("password").setValue(password.getText().toString());
                }
            }
        });
    }
}
