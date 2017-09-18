package br.com.bg7.appvistoria.projectselection;

import android.os.Bundle;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.data.servicelocator.ServiceLocator;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionActivity extends BaseActivity {

    private final ServiceLocator services = ServiceLocator.create(this, this);
    private ProjectSelectionPresenter projectSelectionPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProjectSelectionView view = new ProjectSelectionView(this);

        projectSelectionPresenter = new ProjectSelectionPresenter(services.getProjectService(), view);

        setContentView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        projectSelectionPresenter.start();
    }
}
