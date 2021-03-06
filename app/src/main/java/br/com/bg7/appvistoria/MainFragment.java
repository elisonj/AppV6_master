package br.com.bg7.appvistoria;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by: elison
 * Date: 2017-07-13
 *
 * Fragment class for each nav menu item
 */
public class MainFragment extends Fragment {
    private static final String TEXT_KEY = "text_key";
    private static final String COLOR_KEY = "color_key";

    private String text;
    private int color;

    public static Fragment newInstance(String text, int color) {
        Fragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(TEXT_KEY, text);
        args.putInt(COLOR_KEY, color);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View content;
        TextView textView;

        Bundle args = getArguments();
        text = args.getString(TEXT_KEY);
        color = args.getInt(COLOR_KEY);

        if (savedInstanceState != null) {
            text = savedInstanceState.getString(TEXT_KEY);
            color = savedInstanceState.getInt(COLOR_KEY);
        }

        content = view.findViewById(R.id.fragment_content);
        textView = view.findViewById(R.id.text);

        textView.setText(text);
        content.setBackgroundColor(color);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TEXT_KEY, text);
        outState.putInt(COLOR_KEY, color);
        super.onSaveInstanceState(outState);
    }
}