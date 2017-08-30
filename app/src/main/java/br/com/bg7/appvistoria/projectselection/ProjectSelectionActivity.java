package br.com.bg7.appvistoria.projectselection;

import android.os.Bundle;

import br.com.bg7.appvistoria.BaseActivity;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProjectSelectionView view = new ProjectSelectionView(this);

        setContentView(view);
    }
}
