package br.com.bg7.appvistoria.projectselection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by: elison
 * Date: 2017-09-01
 */
public class ProjectSelectionPresenterTest {

    private ArrayList<Project> allProjects = new ArrayList<Project>() {{
        add(new Project(1L, "Projeto 1"));
        add(new Project(2L, "Projeto 2"));
        add(new Project(3L, "Projeto 3"));
        add(new Project(4L, "Projeto 4"));
        add(new Project(5L, "Projeto 5"));
    }};

    private ArrayList<String> allAddresses = new ArrayList<String>() {{
        add("Endereço 1");
        add("Endereço 2");
        add("Endereço 3");
    }};

    @Mock
    ProjectSelectionContract.View projectSelectionView;

    @Mock
    ProjectService projectService;

    private ProjectSelectionPresenter projectSelectionPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        projectSelectionPresenter = new ProjectSelectionPresenter(projectService, projectSelectionView);

        when(projectService.findByIdOrDescription(anyString())).thenReturn(allProjects);
        when(projectService.findAddressesForProject(any(Project.class))).thenReturn(allAddresses);
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
    public void shouldNotSearchOnInvalidString() {
        projectSelectionPresenter.search("");
        projectSelectionPresenter.search("   ");
        projectSelectionPresenter.search(null);

        verify(projectService, never()).findByIdOrDescription(anyString());
        verify(projectSelectionView, never()).showProjectResults(any(List.class));
    }

    @Test
    public void shouldShowListProjectsWhenSearch() {
        projectSelectionPresenter.search("xyz");

        verifyLoadingShowAndHide();

        verify(projectService).findByIdOrDescription("xyz");
        verify(projectSelectionView).showProjectResults(allProjects);
    }

    @Test
    public void shouldShowSelectedProjectWhenSelectProject() {
        Project project = allProjects.get(0);

        projectSelectionPresenter.selectProject(project);

        verifyLoadingShowAndHide();
        verify(projectService).findAddressesForProject(project);
        verify(projectSelectionView).showSelectedProject(project, allAddresses);
    }

    @Test
    public void shouldShowSelectProjectWhenAddressClicked() {
        Project project = allProjects.get(0);
        String address = allAddresses.get(0);
        projectSelectionPresenter.selectProject(project);

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

    private void verifyLoadingShowAndHide() {
        verify(projectSelectionView).showLoading();
        verify(projectSelectionView).hideLoading();
    }
}
