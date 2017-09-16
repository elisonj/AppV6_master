package br.com.bg7.appvistoria.projectselection;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.List;

import br.com.bg7.appvistoria.ProgressDialog;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.productselection.ProductSelectionActivity;
import br.com.bg7.appvistoria.projectselection.adapter.AddressSelectionAdapter;
import br.com.bg7.appvistoria.projectselection.adapter.ProjectSelectionAdapter;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static br.com.bg7.appvistoria.Constants.FONT_NAME_NUNITO_REGULAR;
import static br.com.bg7.appvistoria.Constants.FONT_NAME_ROBOTO_REGULAR;
import static br.com.bg7.appvistoria.Constants.INTENT_EXTRA_LOCATION_KEY;
import static br.com.bg7.appvistoria.Constants.INTENT_EXTRA_PROJECT_KEY;

/**
 * Created by: elison
 * Date: 2017-08-30
 */
public class ProjectSelectionView extends ConstraintLayout implements ProjectSelectionContract.View {

    private ProjectSelectionContract.Presenter projectSelectionPresenter;
    private ListView listViewProjects;
    private ListView listViewAddress;
    private LinearLayout layoutListViewProjects;
    private LinearLayout layoutListViewAddress;
    private EditText editIdProject;
    private EditText editAddress;
    private AddressSelectionAdapter adapterAddress = null;
    private ProgressDialog progress;
    private Button buttonNext;

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
        buttonNext = findViewById(R.id.button_next);
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

        editIdProject.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    projectSelectionPresenter.search(editIdProject.getText().toString());
                    return true;
                }
                return false;
            }
        });

        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                projectSelectionPresenter.nextClicked();
            }
        });
    }

    @Override
    public void showSelectedProject(Project project) {
        editIdProject.setText(project.getDescription());
        layoutListViewProjects.setVisibility(View.GONE);
    }

    @Override
    public void showAddresses(List<Location> addresses) {
        adapterAddress = new AddressSelectionAdapter(addresses, getContext());
        listViewAddress.setAdapter(adapterAddress);
        listViewAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Location address = ((AddressSelectionAdapter) adapterView.getAdapter()).getItem(position);
                projectSelectionPresenter.selectAddress(address);
            }
        });
        layoutListViewAddress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSelectedAddress(Long projectId, Location address) {
        editAddress.setText(address.getAddress());
        layoutListViewAddress.setVisibility(View.GONE);
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
    public void showProductSelectionScreen(Project project, Location address) {
        Intent intent = new Intent(getContext(), ProductSelectionActivity.class);
        intent.putExtra(INTENT_EXTRA_PROJECT_KEY, project);
        intent.putExtra(INTENT_EXTRA_LOCATION_KEY, address);
        getContext().startActivity(intent);
    }

    @Override
    public void showLoadErrorMessage() {
        // TODO: Implementar mensagem de 'Nao foi possivel carregar os dados. Tente novamente.'
    }

    @Override
    public void clearProjectField() {
        editIdProject.setText("");
        editAddress.setText("");
        adapterAddress = null;
    }

    @Override
    public void clearAddressField() {
        editAddress.setText("");

        if (adapterAddress != null) {
            listViewAddress.setAdapter(adapterAddress);
            layoutListViewAddress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setPresenter(ProjectSelectionContract.Presenter presenter) {
        projectSelectionPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showProjectResults(List<Project> projectList) {
        ProjectSelectionAdapter adapter = new ProjectSelectionAdapter(projectList, getContext());
        listViewProjects.setAdapter(adapter);
        layoutListViewProjects.setVisibility(View.VISIBLE);

        listViewProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Project clicked = ((ProjectSelectionAdapter) adapterView.getAdapter()).getItem(position);
                projectSelectionPresenter.selectProject(clicked);
            }
        });
    }
}
