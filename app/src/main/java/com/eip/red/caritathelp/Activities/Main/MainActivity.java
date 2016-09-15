package com.eip.red.caritathelp.Activities.Main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;

import com.eip.red.caritathelp.Activities.Main.ViewPager.MyPageTransformer;
import com.eip.red.caritathelp.Activities.Main.ViewPager.MyPagerAdapter;
import com.eip.red.caritathelp.Activities.Sign.SignActivity;
import com.eip.red.caritathelp.Models.ModelManager;
import com.eip.red.caritathelp.R;
import com.eip.red.caritathelp.Tools;
import com.eip.red.caritathelp.Views.Search.MySearchView;

public class MainActivity extends AppCompatActivity {

    private ModelManager            modelManager;

    private MySearchView            mySearchView;
    private MyNavigationBottomBar   myNavigationBottomBar;
    private MyWebSocket             myWebSocket;

    private ViewPager               viewPager;
    private MyPagerAdapter          myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ION DEBUG
//        Ion.getDefault(getApplicationContext()).configure().setLogging("MyLogs", Log.DEBUG);

        // Set View
        setContentView(R.layout.activity_main);

        // Init ModelManager
        modelManager = new ModelManager(getIntent());

        // Init ViewPager & Adapter
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageTransformer(false, new MyPageTransformer());

        // Setting Adapter
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(0);

        // Init Navigation Bottom Bar
        myNavigationBottomBar = new MyNavigationBottomBar(this, viewPager);

        // Init WebSocket
//        System.setProperty("java.net.preferIPv6Addresses", "false");
//        System.setProperty("java.net.preferIPv4Stack", "true");
        myWebSocket = new MyWebSocket(myPagerAdapter);
        myWebSocket.connectWebSocket(modelManager.getUser().getToken());
        myWebSocket.sendMessage();

        // Init ViewPager Listener
        initViewPagerListener();

        // Init ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set Status Bar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
    }

    private void initViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Set Navigation Bottom bar Position
                if (myNavigationBottomBar.getBar().getCurrentItem() != viewPager.getCurrentItem())
                    myNavigationBottomBar.getBar().setCurrentItem(viewPager.getCurrentItem());

                // Set View Data && ToolBar Title
                Fragment    fragment = Tools.getLastFragment(myPagerAdapter.getFragment(position).getChildFragmentManager());
                Bundle      bundle = fragment.getArguments();

//                fragment.onViewCreated(fragment.getView(), bundle);
                if (bundle != null) {
                    int stringId = bundle.getInt("page");

                    if (stringId != 0)
                        setTitle(stringId);
                    else
                        setTitle(bundle.getString("page"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if (state == ViewPager.SCROLL_STATE_IDLE)
//                    bottomNavigation.setCurrentItem(viewPager.getCurrentItem());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);

        // Init MySearchView
        mySearchView = new MySearchView(this, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (mySearchView.isIconified()) {
            for (Fragment frag : fm.getFragments()) {
                if (frag != null && frag.isVisible()) {
                    FragmentManager childFm = frag.getChildFragmentManager();
                    if (childFm.getBackStackEntryCount() > 1) {
                        childFm.popBackStack();
                        return;
                    }
                }
            }
            super.onBackPressed();
        }
        else
            mySearchView.setIconified(true);
    }

    public Fragment getCurrentFragment() {
        int             currentPos = viewPager.getCurrentItem();
        FragmentManager childFm = myPagerAdapter.getItem(currentPos).getChildFragmentManager();

        return (childFm.getFragments().get(childFm.getBackStackEntryCount() - 1));
    }

    public void logout() {
        startActivity(new Intent(this, SignActivity.class));
        finish();
    }

    public ModelManager getModelManager() {
        return (modelManager);
    }

    public MySearchView getMySearchView() {
        return mySearchView;
    }

    public MyNavigationBottomBar getMyNavigationBottomBar() {
        return myNavigationBottomBar;
    }
}
