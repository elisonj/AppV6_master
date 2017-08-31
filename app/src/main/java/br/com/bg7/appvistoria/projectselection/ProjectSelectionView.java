package br.com.bg7.appvistoria.projectselection;


import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import br.com.bg7.appvistoria.R;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionView extends ConstraintLayout implements  ProjectSelectionContract.View {

    private Typeface roboto = null;
    private Typeface nunitoRegular = null;

    public ProjectSelectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_project_selection, this);
        roboto = Typeface.createFromAsset(getContext().getAssets(),"robotoregular.ttf");
        nunitoRegular = Typeface.createFromAsset(getContext().getAssets(),"nunitoregular.ttf");

        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        title.setTypeface(nunitoRegular);
        subtitle.setTypeface(roboto);
    }

    @Override
    public void setPresenter(ProjectSelectionContract.Presenter presenter) {

    }

}
