package com.example.arjunv.lpg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookActivity extends AppCompatActivity {

    final Context context = this;
    private Toolbar toolBar;
    private TextView userName;
    private TextView distbName;
    private TextView distbCode;
    private TextView consumerId;
    private Button bSubmit;
    String custId="";
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser=mAuth.getCurrentUser();
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        db.collection("users").document(firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                String uName=documentSnapshot.getString("name");
                String dName=documentSnapshot.getString("distributor_name");
                String dCode=documentSnapshot.getString("distributor_code");
                custId=documentSnapshot.getString("consumer_id");


                userName.setText(uName);
                distbName.setText(dName);
                distbCode.setText("Code:"+ dCode);
                consumerId.setText("Consumer ID:"+ custId);


            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        userName=findViewById(R.id.tvUserName);
        distbName=findViewById(R.id.tvDistbName);
        distbCode=findViewById(R.id.tvDistbCode);
        consumerId=findViewById(R.id.tvConsumerId);
        bSubmit=findViewById(R.id.btSubmit);

        toolBar=findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Confirm Booking!");
                adb.setMessage("if you do not make payment online after booking, your delivery will converted as COD automatically.");
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("BOOK NOW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(BookActivity.this, SubmitActivity.class);
                        intent.putExtra("consName", userName.getText().toString());

                        intent.putExtra("consId",custId);
                        startActivity(intent);
                    }
                });
                adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();
            }
        });






    }


}
