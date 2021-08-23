package com.example.arjunv.lpg;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;



public class Profile2Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);




        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);


        if(savedInstanceState==null) {
            setTitle("Home");
            Home1Frag homeFrag = new Home1Frag();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homeFrag).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager=getSupportFragmentManager();



                switch (item.getItemId()){
                    case(R.id.nav_home):
                        setTitle("Home");
                        Home1Frag homeFrag = new Home1Frag();
                        fragmentManager.beginTransaction().replace(R.id.fragment,  homeFrag).commit();
                        break;






                    case(R.id.nav_instructions):
                        setTitle("Safty Clips");
                        InstructionsFrag instructionsFrag=new InstructionsFrag();
                        fragmentManager.beginTransaction().replace(R.id.fragment,instructionsFrag).commit();
                        break;


                    case(R.id.nav_select_language):








                }




                drawerLayout.closeDrawer(GravityCompat.START);
                return true;





            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
