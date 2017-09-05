package br.com.bg7.appvistoria.projectselection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.fake.ProjectServiceFake;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static org.mockito.Mockito.verify;

/**
 * Created by: elison
 * Date: 2017-09-01
 */
public class ProjectSelectionPresenterTest {

    private static final String SEARCH = "Pr";

    @Mock
    ProjectSelectionContract.View projectSelectionView;
    private ProjectServiceFake projectService;
    private ProjectSelectionPresenter projectSelectionPresenter;
    private List<Project> projectList;
    private List<String> addressList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        projectService = new ProjectServiceFake();
        projectSelectionPresenter = new ProjectSelectionPresenter(projectService, projectSelectionView);

        projectList =  projectService.findByIdOrDescription(SEARCH);
    }

    @Test
    public void shouldInitializePresenter()
    {
        verify(projectSelectionView).setPresenter(projectSelectionPresenter);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullProjectService() {
        new ProjectSelectionPresenter(null, projectSelectionView);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullView() {
        new ProjectSelectionPresenter(projectService, null);
    }

    @Test
    public void shouldShowListProjectsWhenSearch() {
        projectSelectionPresenter.search(SEARCH);
        verifyLoadingStartAndEnd();

        verify(projectSelectionView).showProjectResults(ArgumentMatchers.eq(projectList));
    }

    @Test
    public void shouldShowSelectedProjectWhenSelectProject() {
        Project project = projectList.get(0);
        getAddresListByProject(project);
        verifyLoadingStartAndEnd();

        verify(projectSelectionView).showSelectedProject(ArgumentMatchers.eq(project), ArgumentMatchers.eq(addressList));
    }

    @Test
    public void shouldShowSelectProjectWhenAddressClicked() {
        Project project = projectList.get(0);
        getAddresListByProject(project);
        String address = addressList.get(0);
        projectSelectionPresenter.selectAddress(address);

        verify(projectSelectionView).showProductSelection(project.getId(), address);
    }

    @Test
    public void shouldCleanAddressFieldWhenAddresFieldClicked() {
        projectSelectionPresenter.addressFieldClicked();
        verify(projectSelectionView).clearAddressField();
    }

    @Test
    public void shouldShowClearProjectFieldWhenProjectClicked() {
        projectSelectionPresenter.projectFieldClicked();

        verify(projectSelectionView).clearProjectField();
    }

    private void getAddresListByProject(Project project) {
        projectSelectionPresenter.selectProject(project);
        addressList = projectService.findAddressesForProject(project);
    }

    private void verifyLoadingStartAndEnd() {
        verify(projectSelectionView).showLoading();
        verify(projectSelectionView).hideLoading();
    }
}
