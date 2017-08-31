package br.com.bg7.appvistoria.projectselection;


import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
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

    private static final String DIVISOR = " | ";

    private Typeface roboto = null;
    private Typeface nunitoRegular = null;
    private ProjectSelectionContract.Presenter projectSelectionPresenter;
    private ListView listViewProjects;
    private LinearLayout layoutListViewProjects;
    private EditText editIdProject;
    private EditText editAddress;
    private Project project;

    public ProjectSelectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_project_selection, this);
        roboto = Typeface.createFromAsset(getContext().getAssets(),FONT_NAME_ROBOTO_REGULAR);
        nunitoRegular = Typeface.createFromAsset(getContext().getAssets(),FONT_NAME_NUNITO_REGULAR);

        editIdProject = findViewById(R.id.editText_idproject);
        editAddress = findViewById(R.id.editText_address);

        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        subtitle.setTypeface(roboto);
        title.setTypeface(nunitoRegular);
        listViewProjects = findViewById(R.id.listView_project);
        layoutListViewProjects = findViewById(R.id.list_layout);

        configureListeners();

    }

    private void configureListeners() {
        editIdProject.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                    projectSelectionPresenter.search(sequence.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable sequence) {
            }
        });
    }

    @Override
    public void showSelectedProject(Project project, List<String> addresses) {
        editIdProject.setText(project.getId() + DIVISOR + project.getDescription());
        layoutListViewProjects.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(ProjectSelectionContract.Presenter presenter) {
        projectSelectionPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showProjectResults(List<Project> projectList) {
        if(projectList.size() <= 0) return;

        ProjectSelectionAdapter adapter = new ProjectSelectionAdapter(getContext(), projectList);
        listViewProjects.setAdapter(adapter);
        layoutListViewProjects.setVisibility(View.VISIBLE);

        listViewProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                project = ((ProjectSelectionAdapter)adapterView.getAdapter()).getItem(position);
                projectSelectionPresenter.selectProject(project);
            }
        });
    }


    private class ProjectSelectionAdapter<T> extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;

        private List<Project> items = new ArrayList<Project>();

        private ProjectSelectionAdapter(Context context, List<Project> items) {
            this.context = context;
            this.items = items;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.projectselection_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(Project item, ViewHolder holder) {
            holder.title.setText(item.getId() + DIVISOR + item.getDescription());
        }

        @Override
        public Project getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        private class ViewHolder {

            TextView title;

            private ViewHolder(View view) {
                title = view.findViewById(R.id.title);
            }
        }
    }


}
