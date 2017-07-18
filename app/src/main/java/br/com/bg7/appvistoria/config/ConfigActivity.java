package br.com.bg7.appvistoria.config;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.bg7.appvistoria.MainFragment;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.fragment.ConfigFragment;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public class ConfigActivity extends AppCompatActivity {
    private static final String SELECTED_ITEM_KEY = "SELECTED_ITEM_KEY";
    private BottomNavigationView navigation;
    private int selectedItem = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            selectedItem = savedInstanceState.getInt(SELECTED_ITEM_KEY, 3);
            menuSelectedItem = navigation.getMenu().findItem(selectedItem);
        } else {
            menuSelectedItem = navigation.getMenu().getItem(3);
        }
        selectFragment(menuSelectedItem);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM_KEY, selectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = navigation.getMenu().getItem(0);
        if (selectedItem != homeItem.getItemId()) {
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
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
                frag = ConfigFragment.newInstance(getString(R.string.menu_config));
                break;
        }
        selectedItem = item.getItemId();

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
