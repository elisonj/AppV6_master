package br.com.bg7.appvistoria.projectselection;


import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static br.com.bg7.appvistoria.Constants.FONT_NAME_NUNITO_REGULAR;
import static br.com.bg7.appvistoria.Constants.FONT_NAME_ROBOTO_REGULAR;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionView extends ConstraintLayout implements  ProjectSelectionContract.View {

    private Typeface roboto = null;
    private Typeface nunitoRegular = null;
    private ProjectSelectionContract.Presenter projectSelectionPresenter;

    public ProjectSelectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_project_selection, this);
        roboto = Typeface.createFromAsset(getContext().getAssets(),FONT_NAME_ROBOTO_REGULAR);
        nunitoRegular = Typeface.createFromAsset(getContext().getAssets(),FONT_NAME_NUNITO_REGULAR);

        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        title.setTypeface(nunitoRegular);
        subtitle.setTypeface(roboto);
    }

    @Override
    public void setPresenter(ProjectSelectionContract.Presenter presenter) {
        projectSelectionPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showProjectResults(List<Project> projectList) {

    }
}
