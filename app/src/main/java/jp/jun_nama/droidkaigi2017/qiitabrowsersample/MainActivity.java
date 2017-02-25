package jp.jun_nama.droidkaigi2017.qiitabrowsersample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import jp.jun_nama.droidkaigi2017.qiitabrowsersample.api.QiitaItemsApi;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.api.UsersApi;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.databinding.ActivityMainBinding;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.databinding.NavHeaderMainBinding;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.QiitaItem;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.model.User;
import jp.jun_nama.droidkaigi2017.qiitabrowsersample.viewmodel.MyProfile;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private NavHeaderMainBinding navHeaderBinding;
    private Subject<User, User> myProfileSubject;
    private UsersApi usersApi;
    public MainApplication mainApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHeaderBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0));
        setSupportActionBar(binding.appBarMain.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.setDrawerListener(toggle);
        toggle.syncState();
        binding.setNavigationItemSelectedListener(this);
        mainApplication = (MainApplication) getApplicationContext();
        myProfileSubject = mainApplication.getMyProfileSubject();
        Subject<List<QiitaItem>, List<QiitaItem>> qiitaItemsSubject = mainApplication.getQiitaItemsSubject();

        Retrofit retrofit = mainApplication.getRetrofit();
        usersApi = retrofit.create(UsersApi.class);
        QiitaItemsApi qiitaItemsApi = retrofit.create(QiitaItemsApi.class);

        usersApi.getUser("sumio").subscribeOn(Schedulers.io()).subscribe(myProfileSubject::onNext);
        qiitaItemsApi.getQiitaItemsFirstPage(1, 20).subscribeOn(Schedulers.io()).subscribe(qiitaItemsSubject::onNext);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myProfileSubject
                .subscribeOn(Schedulers.io())
                .map(MyProfile::from)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(navHeaderBinding::setMyProfile);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = binding.drawerLayout;
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

        if (id == R.id.nav_camera) {
            Fragment fragment = new QiitaItemsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_placeholder, fragment)
                    .commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
