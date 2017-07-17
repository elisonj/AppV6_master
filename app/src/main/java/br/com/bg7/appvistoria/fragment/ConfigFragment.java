package br.com.bg7.appvistoria.fragment;

/**
 * Created by: elison
 * Date: 2017-07-13
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.bg7.appvistoria.view.ConfigView;
import br.com.bg7.appvistoria.view.listeners.ButtonCancelConfigListener;
import br.com.bg7.appvistoria.view.listeners.ButtonConfirmConfigListener;

/**
 * Fragment class for Config menu item
 */
public class ConfigFragment extends Fragment {

    private static final String ARG_TEXT = "arg_text";
    private String text;

    private ConfigView view;

    public static Fragment newInstance(String text) {
        Fragment frag = new ConfigFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = new ConfigView(this.getContext());

        configureListeners();

        return view;
    }

    private void configureListeners() {
        view.setConfirmListenner(new ButtonConfirmConfigListener(view));
        view.setCancelListenner(new ButtonCancelConfigListener(view));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, text);
        super.onSaveInstanceState(outState);
    }
}