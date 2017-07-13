package br.com.bg7.appvistoria;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by: elison
 * Date: 2017-07-10
 */

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView navigation;
    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Intent i = new Intent(this, LoginActivity.class);
       // startActivity(i);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem menuSelectedItem;

        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            menuSelectedItem = navigation.getMenu().findItem(selectedItem);
        } else {
            menuSelectedItem = navigation.getMenu().getItem(0);
        }
        selectFragment(menuSelectedItem);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, selectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = navigation.getMenu().getItem(0);
        if (selectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.menu_visita:
                frag = MainFragment.newInstance(getString(R.string.menu_visita),
                        getColorFromRes(R.color.color_visita));
                break;
            case R.id.menu_sync:
                frag = MainFragment.newInstance(getString(R.string.menu_sync),
                        getColorFromRes(R.color.color_sync));
                break;
            case R.id.menu_historic:
                frag = MainFragment.newInstance(getString(R.string.menu_historic),
                        getColorFromRes(R.color.color_historic));
                break;
            case R.id.menu_config:
                frag = MainFragment.newInstance(getString(R.string.menu_config),
                        getColorFromRes(R.color.color_config));
                break;
        }
        // update selected item
        selectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i < navigation.getMenu().size(); i++) {
            MenuItem menuItem = navigation.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, frag, frag.getTag());
            ft.commit();
        }
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }
}
