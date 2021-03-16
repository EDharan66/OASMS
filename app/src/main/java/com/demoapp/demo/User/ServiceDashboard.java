package com.demoapp.demo.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demoapp.demo.Common.Booking.BookingNotificationService;
import com.demoapp.demo.Common.MapViewer.MapViewr;
import com.demoapp.demo.Common.MapViewer.NearByPlace.ServiceMapActivity;
import com.demoapp.demo.Common.ReportActivityService;
import com.demoapp.demo.HelperClass.HomeAdapter.CategoriesAdapter;
import com.demoapp.demo.HelperClass.HomeAdapter.CategoriesHelperClass;
import com.demoapp.demo.HelperClass.HomeAdapter.RecentAdapter;
import com.demoapp.demo.HelperClass.HomeAdapter.RecentHelperClass;
import com.demoapp.demo.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ServiceDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    static final float END_SCALE = 0.7f;

    RecyclerView recentSearchRecycler,categoriesRecycler;
    RecyclerView.Adapter adapter;
    LinearLayout Map_view_booking;
    ImageView notification_icon, menu_icon;
    LinearLayout contentView;
    private GradientDrawable gradient1, gradient2, gradient3;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_dashboard);

        recentSearchRecycler = findViewById(R.id.service_recent_search_recycler);
        categoriesRecycler = findViewById(R.id.service_categories_recycler);
        Map_view_booking = findViewById(R.id.service_Map_view_booking);
        notification_icon = findViewById(R.id.notification_icon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menu_icon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content_view);

        navigationDrawer();
        recentSearchRecycler();
        categoriesRecycler();

        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BookingNotificationService.class));
            }
        });
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.ColorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }






    private void categoriesRecycler() {

        //All Gradients
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.purchase, "Accessories store"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.report_generate, "Report Generate"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.nearby_petrol_bunk, "Petrol and Gas Station"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }
    private void recentSearchRecycler() {
        recentSearchRecycler.setHasFixedSize(true);
        recentSearchRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<RecentHelperClass> recentLocation = new ArrayList<>();

        recentLocation.add(new RecentHelperClass(R.drawable.background1,"recent view title1","recent view desc"));
        recentLocation.add(new RecentHelperClass(R.drawable.background2,"recent view title2","recent view desc"));
        recentLocation.add(new RecentHelperClass(R.drawable.background3,"recent view title3","recent view desc"));

        adapter = new RecentAdapter(recentLocation);
        recentSearchRecycler.setAdapter(adapter);

        //gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffeff600});
    }
    public void CallForServiceMapPage(View view) {
        startActivity(new Intent(getApplicationContext(), MapViewr.class));

    }
    public void ServiceCallNearByLocation(View view) {
        startActivity(new Intent(getApplicationContext(), ServiceMapActivity.class));
    }
    public void callServiceReportpage(View view) {

        startActivity(new Intent(getApplicationContext(), ReportActivityService.class));
    }


}