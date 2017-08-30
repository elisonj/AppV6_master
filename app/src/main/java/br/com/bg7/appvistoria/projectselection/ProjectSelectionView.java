package br.com.bg7.appvistoria.projectselection;


import android.content.Context;
import android.support.constraint.ConstraintLayout;

import br.com.bg7.appvistoria.R;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionView extends ConstraintLayout implements  ProjectSelectionContract.View {

    public ProjectSelectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_project_selection, this);
    }

    @Override
    public void setPresenter(ProjectSelectionContract.Presenter presenter) {

    }

}
