package br.com.bg7.appvistoria.config;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.MainFragment;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.servicelocator.ServiceLocator;
import br.com.bg7.appvistoria.workorder.WorkOrderFragment;
import br.com.bg7.appvistoria.workorder.WorkOrderPresenter;

import static br.com.bg7.appvistoria.Constants.FONT_NAME_NUNITO_REGULAR;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 */

public class ConfigActivity extends BaseActivity {
    private static final String SELECTED_MENU_ITEM_KEY = "SELECTED_MENU_ITEM_KEY";
    private static final int DEFAULT_SCREEN_MENU_ITEM_INDEX = 2;

    private int selectedItem = DEFAULT_SCREEN_MENU_ITEM_INDEX;
    private Menu menu = null;
    private TypefaceSpan nunitoSpan = new TypefaceSpan(FONT_NAME_NUNITO_REGULAR);

    private final ServiceLocator services = ServiceLocator.create(this, this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface nunito = Typeface.createFromAsset(getApplicationContext().getAssets(), FONT_NAME_NUNITO_REGULAR);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.actionbar_logo);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);


        TextView label = navigation.findViewById(R.id.largeLabel);
        label.setTypeface(nunito);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        menu = navigation.getMenu();

        MenuItem menuSelectedItem = menu.getItem(DEFAULT_SCREEN_MENU_ITEM_INDEX);

        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt(SELECTED_MENU_ITEM_KEY, DEFAULT_SCREEN_MENU_ITEM_INDEX);
            menuSelectedItem = menu.findItem(selectedItem);
        }
        selectFragment(menuSelectedItem);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_MENU_ITEM_KEY, selectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = menu.getItem(0);
        if (selectedItem != homeItem.getItemId()) {
            selectFragment(homeItem);
            return;
        }
        super.onBackPressed();
    }

    // TODO: Separar telas em fragments próprios
    private void selectFragment(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_visita:

                WorkOrderFragment workOrderFragment = new WorkOrderFragment();
                fragment = workOrderFragment;
                fragment.setRetainInstance(true);
                new WorkOrderPresenter(
                        services.getWorkOrderRepository(),
                        services.getConfigRepository(),
                        workOrderFragment
                );

                break;
            case R.id.menu_sync:
                fragment = MainFragment.newInstance(getString(R.string.menu_sync),
                        ContextCompat.getColor(this, R.color.color_sync));
                break;

            case R.id.menu_config:
                ConfigFragment configFrag = new ConfigFragment();
                fragment = configFrag;
                fragment.setRetainInstance(true);
                new ConfigPresenter(
                        services.getConfigRepository(),
                        services.getLanguageRepository(),
                        configFrag);
                break;
        }
        selectedItem = item.getItemId();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, fragment, fragment.getTag());
            ft.commit();
        }
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            SpannableString span = new SpannableString(text);
            span.setSpan(nunitoSpan, 0, span.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            actionBar.setTitle(span);
        }
    }

}
