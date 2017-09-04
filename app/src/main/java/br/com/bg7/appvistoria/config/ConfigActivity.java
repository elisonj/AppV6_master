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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.MainFragment;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.servicelocator.ServiceLocator;
import br.com.bg7.appvistoria.workorder.WorkOrderFragment;
import br.com.bg7.appvistoria.workorder.WorkOrderPresenter;

import static br.com.bg7.appvistoria.Constants.FONT_NAME_NUNITO_REGULAR;

/**
 * Created by: luciolucio
 * Date: 2017-07-17
 *
 * TODO: Remover duplicacao que existe entre configureSearchButton e search
 */

public class ConfigActivity extends BaseActivity {
    private static final String SELECTED_MENU_ITEM_KEY = "SELECTED_MENU_ITEM_KEY";
    private static final int DEFAULT_SCREEN_MENU_ITEM_INDEX = 2;

    private int selectedItem = DEFAULT_SCREEN_MENU_ITEM_INDEX;
    private Menu menu = null;
    private Typeface nunito;
    private TypefaceSpan nunitoSpan = new TypefaceSpan(FONT_NAME_NUNITO_REGULAR);

    private final ServiceLocator services = ServiceLocator.create(this, this);

    private String title = null;

    private LinearLayout searchLayout = null;

    View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            View inflate = getLayoutInflater().inflate(R.layout.search_button, null);
            ImageView buttonSearch = inflate.findViewById(R.id.search_button_bar);

            ActionBar actionBar = getSupportActionBar();
            if(actionBar == null) {
                return;
            }

            actionBar.setDisplayShowCustomEnabled(true);

            TextView textView = inflate.findViewById(R.id.title);
            textView.setTypeface(nunito);
            textView.setText(title);
            buttonSearch.setOnClickListener(this);

            if(!searchLayout.isShown()) {
                buttonSearch.setImageResource(R.drawable.ic_search_bar_active);
                searchLayout.setVisibility(View.VISIBLE);
                actionBar.setCustomView(inflate);
                return;
            }
            buttonSearch.setImageResource(R.drawable.ic_search_bar);
            searchLayout.setVisibility(View.GONE);
            actionBar.setCustomView(inflate);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nunito = Typeface.createFromAsset(getApplicationContext().getAssets(), FONT_NAME_NUNITO_REGULAR);

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
        title = menuSelectedItem.getTitle().toString();
        selectFragment(menuSelectedItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchLayout = findViewById(R.id.search);
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

        if(searchLayout != null) searchLayout.setVisibility(View.GONE);

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

                title = item.getTitle().toString();
                configureSearchButton(true);

                break;
            case R.id.menu_sync:
                fragment = MainFragment.newInstance(getString(R.string.menu_sync),
                        ContextCompat.getColor(this, R.color.color_sync));
                configureSearchButton(false);
                break;

            case R.id.menu_config:
                ConfigFragment configFrag = new ConfigFragment();
                fragment = configFrag;
                fragment.setRetainInstance(true);
                new ConfigPresenter(
                        services.getConfigRepository(),
                        services.getLanguageRepository(),
                        configFrag);
                configureSearchButton(false);
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

    private void configureSearchButton(boolean showSearch) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayShowCustomEnabled(true);

        View view = getLayoutInflater().inflate(R.layout.search_button, null);
        TextView textView = view.findViewById(R.id.title);
        textView.setTypeface(nunito);
        textView.setText(title);

        ImageView buttonSearch = view.findViewById(R.id.search_button_bar);
        if(showSearch) {
            textView.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            actionBar.setCustomView(view);
            buttonSearch.setOnClickListener(search);
            return;
        }
        textView.setVisibility(View.GONE);
        buttonSearch.setVisibility(View.GONE);
        actionBar.setCustomView(view);
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
