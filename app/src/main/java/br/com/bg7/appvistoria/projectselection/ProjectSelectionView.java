package br.com.bg7.appvistoria.projectselection;


import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
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

import br.com.bg7.appvistoria.ProgressDialog;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static br.com.bg7.appvistoria.Constants.FONT_NAME_NUNITO_REGULAR;
import static br.com.bg7.appvistoria.Constants.FONT_NAME_ROBOTO_REGULAR;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionView extends ConstraintLayout implements  ProjectSelectionContract.View {

    private static final String SEPARATOR = " | ";

    private ProjectSelectionContract.Presenter projectSelectionPresenter;
    private ListView listViewProjects;
    private ListView listViewAddress;
    private LinearLayout layoutListViewProjects;
    private LinearLayout layoutListViewAddress;
    private EditText editIdProject;
    private EditText editAddress;
    private Project project;
    private String address;
    private AddressSelectionAdapter adapterAddress = null;
    private ProgressDialog progress;


    public ProjectSelectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_project_selection, this);
        progress = new ProgressDialog(getContext());

        initializeViewElements();

        initializeListeners();
    }

    private void initializeViewElements() {
        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), FONT_NAME_ROBOTO_REGULAR);
        Typeface nunitoRegular = Typeface.createFromAsset(getContext().getAssets(), FONT_NAME_NUNITO_REGULAR);

        editIdProject = findViewById(R.id.editText_idproject);
        editAddress = findViewById(R.id.editText_address);

        TextView title = findViewById(R.id.title);
        TextView subtitle = findViewById(R.id.subtitle);
        subtitle.setTypeface(roboto);
        title.setTypeface(nunitoRegular);
        listViewProjects = findViewById(R.id.listView_project);
        layoutListViewProjects = findViewById(R.id.list_layout);
        listViewAddress = findViewById(R.id.listView_address);
        layoutListViewAddress = findViewById(R.id.list_layout_address);
    }

    private void initializeListeners() {

        editIdProject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                projectSelectionPresenter.projectFieldClicked();
            }
        });

        editAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                projectSelectionPresenter.addressFieldClicked();
            }
        });

        editIdProject.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    projectSelectionPresenter.search(editIdProject.getText().toString());
                }
            }
        });
    }

    @Override
    public void showSelectedProject(Project projectSelected, List<String> addresses) {
        editIdProject.setText(projectSelected.getId() + SEPARATOR + projectSelected.getDescription());
        layoutListViewProjects.setVisibility(View.GONE);

        adapterAddress = new AddressSelectionAdapter(addresses);
        listViewAddress.setAdapter(adapterAddress);
        listViewAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String address = ((AddressSelectionAdapter)adapterView.getAdapter()).getItem(position);
                projectSelectionPresenter.selectAddress(address);
            }
        });
        layoutListViewAddress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.hide();
    }

    @Override
    public void showLoading() {
        progress.show();
    }

    @Override
    public void clearProjectField() {
        editIdProject.setText("");
        editAddress.setText("");
        project = null;
        adapterAddress = null;
    }

    @Override
    public void clearAddressField() {
        editAddress.setText("");

        if(project != null && adapterAddress != null) {
            listViewAddress.setAdapter(adapterAddress);
            layoutListViewAddress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProductSelection(Long projectId, String address) {
        this.address = address;
        editAddress.setText(address);
        layoutListViewAddress.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(ProjectSelectionContract.Presenter presenter) {
        projectSelectionPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showProjectResults(List<Project> projectList) {
        ProjectSelectionAdapter adapter = new ProjectSelectionAdapter(projectList);
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

    private class ProjectSelectionAdapter extends BaseAdapter {

        private List<Project> items = new ArrayList<>();

        private ProjectSelectionAdapter(List<Project> items) {
            this.items = items;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.projectselection_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(Project item, ViewHolder holder) {
            holder.title.setText(item.getId() + SEPARATOR + item.getDescription());
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

    private class AddressSelectionAdapter extends BaseAdapter {

        private List<String> items = new ArrayList<>();

        private AddressSelectionAdapter(List<String> items) {
            this.items = items;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.projectselection_item, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(String item, ViewHolder holder) {
            holder.title.setText(item);
        }

        @Override
        public String getItem(int position) {
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
