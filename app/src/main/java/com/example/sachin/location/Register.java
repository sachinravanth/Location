package com.example.sachin.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();
    EditText name, date, age, address, email, password;
    RadioButton male,female,other;
    RadioGroup radioGroup;
    Button register;
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")&&date.getText().toString().equals("")&&age.getText().toString().equals("")&&address.getText().toString().equals("")&&email.getText().toString().equals("")&&password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Some fields missing...",Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final String uid = database.getReference().push().getKey();
                    final String userDetails = getString(R.string.userDetails);
                    DatabaseReference databaseReference = database.getReference(getString(R.string.firebase_path));
                    databaseReference.child(userDetails).child(uid).child("name").setValue(name.getText().toString());
                    databaseReference.child(userDetails).child(uid).child("date").setValue(date.getText().toString());
                    databaseReference.child(userDetails).child(uid).child("age").setValue(age.getText().toString());
                    databaseReference.child(userDetails).child(uid).child("address").setValue(address.getText().toString());
                    databaseReference.child(userDetails).child(uid).child("emailID").setValue(email.getText().toString());
                    databaseReference.child(userDetails).child(uid).child("password").setValue(password.getText().toString());
                    loginToFirebase(uid);
                }
            }
        });
    }

    private void loginToFirebase(final String key) {
        // Authenticate with Firebase, and request location updates
        String email = getString(R.string.firebase_email);
        String password = getString(R.string.firebase_password);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "firebase auth success");
                    requestLocationUpdates(key);
                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }
    private void requestLocationUpdates(String key) {
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        final String path = getString(R.string.firebase_path)+"/"+getString(R.string.transport_id) + "/"+key;
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d(TAG, "location update " + location);
                        ref.setValue(location);
                    }
                }
            }, null);
        }
    }
}
