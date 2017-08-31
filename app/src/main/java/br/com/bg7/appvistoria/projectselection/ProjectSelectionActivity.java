package br.com.bg7.appvistoria.projectselection;

import android.os.Bundle;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.data.source.remote.stub.StubProjectService;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionActivity extends BaseActivity {

    private final StubProjectService projectService = new StubProjectService();
    private ProjectSelectionPresenter projectSelectionPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProjectSelectionView view = new ProjectSelectionView(this);

        projectSelectionPresenter = new ProjectSelectionPresenter(projectService, view);

        setContentView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        projectSelectionPresenter.start();
    }
}
