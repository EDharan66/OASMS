package com.demoapp.demo.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.demoapp.demo.Common.LoginSingUp.Service_center_signUp_page1;
import com.demoapp.demo.Common.LoginSingUp.Service_center_signUp_page2;
import com.demoapp.demo.Common.MapViewer.MapCurrentLocation;
import com.demoapp.demo.Common.MapViewer.MapViewr;
import com.demoapp.demo.Common.MapViewer.NearByPlace.MapsActivity;
import com.demoapp.demo.HelperClass.HomeAdapter.CategoriesAdapter;
import com.demoapp.demo.HelperClass.HomeAdapter.CategoriesHelperClass;
import com.demoapp.demo.HelperClass.HomeAdapter.RecentAdapter;
import com.demoapp.demo.HelperClass.HomeAdapter.RecentHelperClass;
import com.demoapp.demo.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView recentSearchRecycler,categoriesRecycler;
    RecyclerView.Adapter adapter;
    LinearLayout Map_view_booking;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);


        recentSearchRecycler = findViewById(R.id.recent_search_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        Map_view_booking = findViewById(R.id.Map_view_booking);

        recentSearchRecycler();
        categoriesRecycler();
    }

    private void categoriesRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.call_service, "call for Service"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.purchase, "Accessories store"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.onspot_service, "On spot Service"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient4, R.drawable.map_on_bording_page, "Nearby Location"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.nearby_petrol_bunk, "Petrol and Gas Station"));


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


        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xffeff400,0xffeff600});

    }

    public void CallForMapPage(View view) {
        startActivity(new Intent(getApplicationContext(), MapViewr.class));
        finish();
    }

    public void callCurrentLocationPage(View view) {
        startActivity(new Intent(getApplicationContext(), MapCurrentLocation.class));
        finish();
    }

    public void callNearByLocation(View view) {

        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _phoneNo = getIntent().getStringExtra("phoneNo");

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("phoneNo", _phoneNo);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(Map_view_booking, "transition_btn");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserDashboard.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

}