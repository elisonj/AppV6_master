package br.com.bg7.appvistoria.productselection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.bg7.appvistoria.BaseActivity;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.data.servicelocator.ServiceLocator;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static br.com.bg7.appvistoria.Constants.INTENT_EXTRA_LOCATION_KEY;
import static br.com.bg7.appvistoria.Constants.INTENT_EXTRA_PROJECT_KEY;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class ProductSelectionActivity extends BaseActivity {

    private final ServiceLocator services = ServiceLocator.create(this, this);
    private ProductSelectionPresenter productSelectionPresenter;

    ImageView btBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = checkNotNull(getIntent());
        Project project = (Project) checkNotNull(intent.getSerializableExtra(INTENT_EXTRA_PROJECT_KEY));
        Location location = (Location) checkNotNull(intent.getSerializableExtra(INTENT_EXTRA_LOCATION_KEY));

        ProductSelectionView view = new ProductSelectionView(this);
        productSelectionPresenter = new ProductSelectionPresenter(project, location, services.getProductService(), services.getWorkOrderRepository(), view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            initializeViewElements(project.getId(), actionBar);

            initializeListeners();
        }

        setContentView(view);
    }

    private void initializeViewElements(Long projectId, ActionBar actionBar) {
        String productSelectionTitle = getString(R.string.product_selection_title_format, projectId);

        View actionBarView = getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView title = actionBarView.findViewById(R.id.title);
        btBack = actionBarView.findViewById(R.id.bt_back);
        title.setText(productSelectionTitle);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarView);
    }

    private void initializeListeners() {
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelectionPresenter.backClicked();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productSelectionPresenter.start();
    }
}
