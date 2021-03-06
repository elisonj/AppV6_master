package br.com.bg7.appvistoria.projectselection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.bg7.appvistoria.data.source.remote.ProjectService;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Location;
import br.com.bg7.appvistoria.projectselection.vo.Project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;
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

    private ArrayList<Location> allLocations = new ArrayList<Location>() {{
        add(new Location(1L, "Endereço 1"));
        add(new Location(2L, "Endereço 2"));
        add(new Location(3L, "Endereço 3"));
    }};

    @Mock
    ProjectSelectionContract.View projectSelectionView;

    @Mock
    ProjectService projectService;

    @Mock
    HttpResponse<List<Project>> projectResponse;

    @Mock
    HttpResponse<List<Location>> locationResponse;

    @Captor
    ArgumentCaptor<HttpCallback<List<Project>>> projectCallBackCaptor;

    @Captor
    ArgumentCaptor<HttpCallback<List<Location>>> locationCallBackCaptor;

    private ProjectSelectionPresenter projectSelectionPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        projectSelectionPresenter = new ProjectSelectionPresenter(projectService, projectSelectionView);

        when(projectResponse.body()).thenReturn(allProjects);
        when(locationResponse.body()).thenReturn(allLocations);
        when(projectResponse.isSuccessful()).thenReturn(true);
        when(locationResponse.isSuccessful()).thenReturn(true);
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

        verify(projectService, never()).findByIdOrDescription(anyString(), projectCallBackCaptor.capture());
        verify(projectSelectionView, never()).showProjectResults(any(List.class));
    }

    @Test
    public void shouldNotShowListWhenSearchResultsIsEmpty() {
        when(projectResponse.body()).thenReturn(null);

        projectSelectionPresenter.search("xis");

        verify(projectSelectionView, never()).showProjectResults(any(List.class));
    }

    @Test
    public void shouldShowListProjectsWhenSearch() {
        projectSelectionPresenter.search("xyz");

        verify(projectService).findByIdOrDescription(matches("xyz"), projectCallBackCaptor.capture());
        projectCallBackCaptor.getValue().onResponse(projectResponse);

        verifyLoadingShowAndHide();

        verify(projectSelectionView).showProjectResults(allProjects);
    }

    @Test
    public void shouldShowSelectedProjectWhenSelectProject() {
        Project project = allProjects.get(0);

        projectSelectionPresenter.selectProject(project);

        verify(projectService).findLocationsForProject(eq(project), locationCallBackCaptor.capture());
        locationCallBackCaptor.getValue().onResponse(locationResponse);

        verifyLoadingShowAndHide();
        verify(projectSelectionView).showSelectedProject(project);
    }

    @Test
    public void shouldShowSelectProjectWhenLocationClicked() {
        Project project = allProjects.get(0);
        Location location = allLocations.get(0);
        projectSelectionPresenter.selectProject(project);

        projectSelectionPresenter.selectLocation(location);

        verify(projectSelectionView).showSelectedLocation(project.getId(), location);
    }

    @Test
    public void shouldCleanLocationFieldWhenAddresFieldClicked() {
        projectSelectionPresenter.locationFieldClicked();
        verify(projectSelectionView).clearLocationField();
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
