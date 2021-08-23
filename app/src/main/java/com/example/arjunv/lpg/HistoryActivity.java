package com.example.arjunv.lpg;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG="HistoryActivity";
    private TextView tvOrderNo,tvRefillId,tvConsId,tvName;
    private Toolbar toolBar;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser=mAuth.getCurrentUser();
    private FirebaseFirestore db= FirebaseFirestore.getInstance();



    @Override
    protected void onStart() {
        super.onStart();
        db.collection("users").document(firebaseUser.getUid()).collection("booking request").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String oid=document.getString("Order Id");
                                String rid=document.getString("Refill ID");
                                String cid=document.getString("Cust Id");
                                String cname=document.getString("Cust Name");


                                tvOrderNo.setText("Order No: " +oid);
                                tvRefillId.setText("Refill ID: "+rid);
                                tvConsId.setText("Cust ID: "+cid);
                                tvName.setText("Consumer Name: "+ cname);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });













    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolBar=findViewById(R.id.toolbar);
        tvOrderNo=findViewById(R.id.tvOrderNo);
        tvRefillId=findViewById(R.id.tvRefillId);
        tvConsId=findViewById(R.id.tvConsId);
        tvName=findViewById(R.id.tvName);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



}
