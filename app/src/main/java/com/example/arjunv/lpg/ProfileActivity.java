package com.example.arjunv.lpg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String phoneNumber ;
    private TextView userPhone;
    private TextView userName;
    private TextView consumerNo;
    private TextView consumerId;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser=mAuth.getCurrentUser();
    private FirebaseFirestore db= FirebaseFirestore.getInstance();


    @Override
    protected void onStart() {
        super.onStart();

        db.collection("users").document(firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                String phoneNo=documentSnapshot.getString("phone");
                String uName=documentSnapshot.getString("name");
                String custId=documentSnapshot.getString("consumer_id");
                String custNo =documentSnapshot.getString("consumer_no");
                userPhone.setText("Phone:"+ phoneNo);
                userName.setText(uName);
                consumerId.setText("Consumer ID:"+ custId);
                consumerNo.setText("Consumer No:" + custNo);

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        phoneNumber = getIntent().getStringExtra("phoneNumber");

        sendUserData();







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userPhone = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_phone);
        userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        consumerId = (TextView) navigationView.getHeaderView(0).findViewById(R.id.consumer_id);
        consumerNo = (TextView) navigationView.getHeaderView(0).findViewById(R.id.consumer_no);


        if (savedInstanceState == null) {
            setTitle("Home");
            HomeFrag homeFrag = new HomeFrag();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homeFrag).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }

    }

    private void sendUserData() {
        String phone=phoneNumber;
        Map<String, String> user = new HashMap<>();
        user.put("phone",phone);
        db.collection("users").document(firebaseUser.getUid()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    }
                });


    }









    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setTitle("Home");
            HomeFrag homeFrag = new HomeFrag();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,  homeFrag).commit();




        }else if (id==R.id.nav_talk_to_us) {


            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:+917710927028"));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return true;
            } else {     //have got permission
                try {
                    startActivity(callIntent);  //call activity and make phone call
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                }
            }






        } else if (id == R.id.nav_request_sec_cylinder) {


        } else if (id == R.id.nav_select_language) {


        } else if (id == R.id.nav_notifications) {


        } else if(id==R.id.nav_instructions){
            setTitle("Safty Clips");
            InstructionsFrag instructionsFrag=new InstructionsFrag();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment, instructionsFrag).commit();

        } else if(id == R.id.nav_logout){
            LogoutFrag logoutFrag = new LogoutFrag();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,  logoutFrag).commit();


        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
