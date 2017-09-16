package br.com.bg7.appvistoria.productselection;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by: elison
 * Date: 2017-09-04
 */
public class ProductSelectionActivity extends BaseActivity {

    private static final String EMPTY_SPACE = " ";

    private final ServiceLocator services = ServiceLocator.create(this, this);
    private ProductSelectionPresenter productSelectionPresenter;
    private Project project;
    private Location address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            project = (Project) intent.getSerializableExtra(INTENT_EXTRA_PROJECT_KEY);
            address = (Location) intent.getSerializableExtra(INTENT_EXTRA_LOCATION_KEY);
        }

        ProductSelectionView view = new ProductSelectionView(this);
        productSelectionPresenter = new ProductSelectionPresenter(project, address, services.getProductService(), services.getWorkOrderRepository(), view);

        if(getSupportActionBar() != null) {

            String actives = getString(R.string.actives) + EMPTY_SPACE + project.getId();

            View customView = getLayoutInflater().inflate(R.layout.action_bar, null);
            TextView title = customView.findViewById(R.id.title);
            ImageView btHome = customView.findViewById(R.id.bt_home);
            title.setText(actives);

            btHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productSelectionPresenter.backClicked();
                }
            });

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(customView);
        }


        setContentView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        productSelectionPresenter.start();
    }

}
