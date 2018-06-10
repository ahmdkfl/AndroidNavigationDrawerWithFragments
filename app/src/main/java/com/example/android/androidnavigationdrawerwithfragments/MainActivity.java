package com.example.android.androidnavigationdrawerwithfragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//Reference: https://chrisrisner.com/Using-Fragments-with-the-Navigation-Drawer-Activity
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentOne.OnFragmentInteractionListener, FragmentTwo.OnFragmentInteractionListener, DrawerLayout.DrawerListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    //Variable used to highlight the option selected in the navigation menu
    static MenuItem drawerMenuItemSelected;
    ActionBarDrawerToggle toggle;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //add fade in and fade out animations when changing the fragment
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = FragmentOne.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment, TAG).commit();
            //get the first item from the drawer menu
            MenuItem item = ((NavigationView) findViewById(R.id.nav_view)).getMenu().getItem(0);
            //highlight it
            item.setChecked(true);
            //set the title of the fragment to match the name of the item selected
            setTitle(item.getTitle());
        } else {
            //restore the title if the state changes and if the savedInstanceState is not null
            setTitle(savedInstanceState.getString("fragmentTitle"));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the title of the current fragment selected
        outState.putString("fragmentTitle", String.valueOf(getTitle()));
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //TODO: Change the options to reflect the corresponding menu
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
        drawerMenuItemSelected = item;
        fragment = null;
        Class fragmentClass = null;

        //TODO: Change the navigation view items to match the application's fragments
        switch (id) {
            case R.id.nav_camera:
                fragmentClass = FragmentOne.class;
                break;
            case R.id.nav_gallery:
                fragmentClass = FragmentTwo.class;
                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Replace the fragment selected after the drawer is closed
        drawer.addDrawerListener(this);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        //force the Action Bar to be redrawn
        invalidateOptionsMenu();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                replaceFragment();
            }
        };
        runnable.run();
        //new Handler().post(runnable);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    /**
     * Replace fragment, set the title and check the relevant navigation menu item
     */
    private void replaceFragment() {
        //Replace fragment in the fragmentContent
        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //TODO: Replace the enter and exit animations if needed
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragmentContent, fragment, TAG)
                    .addToBackStack(null)
                    .commit();

            // Highlight the selected item has been done by NavigationView
            drawerMenuItemSelected.setChecked(true);
            // Set action bar title
            setTitle(drawerMenuItemSelected.getTitle());
        }
    }
}
