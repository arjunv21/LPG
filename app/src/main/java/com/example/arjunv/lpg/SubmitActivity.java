package com.example.arjunv.lpg;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SubmitActivity extends AppCompatActivity {

    private static final String TAG="SubmitActivity";
    private Context context=this;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser=mAuth.getCurrentUser();
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    private Toolbar toolBar;
    private String orderId=generateString();
    private String refillId=generateString();
    private TextView OrderNo, RefillId, cName,cId;
    String consname, consid;
    private Button btPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        toolBar=findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        consname=getIntent().getStringExtra("consName");
        consid=getIntent().getStringExtra("consId");
        cName=findViewById(R.id.tvName);
        cId=findViewById(R.id.tvConsId);
        OrderNo=findViewById(R.id.tvOrderNo);
        RefillId=findViewById(R.id.tvRefillId);
        btPayment=findViewById(R.id.mPayment);

        OrderNo.setText("Order No: " +orderId);
        RefillId.setText("Refill ID: "+refillId);
        cName.setText("Consumer Name: "+consname);
        cId.setText("Cust ID: "+consid);

        sendUserData();

        btPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitActivity.this, checksum.class);
                intent.putExtra("orderid", orderId);
                intent.putExtra("custid", consid);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(SubmitActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SubmitActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }

    private void sendUserData() {
        String oid=orderId;
        String cid=consid;
        String rid=refillId;
        String cname=consname;
        Map<String, String> bRequest = new HashMap<>();
        bRequest.put("Order Id",oid);
        bRequest.put("Refill ID",rid);
        bRequest.put("Cust Name", cname);
        bRequest.put("Cust Id",cid);
        db.collection("users").document(firebaseUser.getUid()).collection("booking request").add(bRequest)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private String generateString(){
        String uuid= UUID.randomUUID().toString();
        return uuid.replaceAll("-","");

    }
}
