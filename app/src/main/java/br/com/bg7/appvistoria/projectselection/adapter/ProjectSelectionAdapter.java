package br.com.bg7.appvistoria.projectselection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.projectselection.ProjectSelectionView;
import br.com.bg7.appvistoria.projectselection.vo.Project;

/**
 * Created by: luciolucio
 * Date: 2017-09-16
 */
public class ProjectSelectionAdapter extends SelectionAdapter<Project> {

    public ProjectSelectionAdapter(List<Project> items, Context context) {
        super(items, context);
    }

    @Override
    String getText(Project item) {
        return item.getDescription();
    }
}
