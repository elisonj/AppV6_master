package br.com.bg7.appvistoria;

/**
 * Created by: elison
 * Date: 2017-07-13
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static br.com.bg7.appvistoria.R.id.textView;

/**
 * Fragment class for each nav menu item
 */
public class MainFragment extends Fragment {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String text;
    private int color;

    public static Fragment newInstance(String text, int color) {
        Fragment frag = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_COLOR, color);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View content;
        TextView textView;

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            text = args.getString(ARG_TEXT);
            color = args.getInt(ARG_COLOR);
        } else {
            text = savedInstanceState.getString(ARG_TEXT);
            color = savedInstanceState.getInt(ARG_COLOR);
        }
        content = view.findViewById(R.id.fragment_content);
        textView = (TextView) view.findViewById(R.id.text);

        textView.setText(text);
        content.setBackgroundColor(color);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, text);
        outState.putInt(ARG_COLOR, color);
        super.onSaveInstanceState(outState);
    }
}