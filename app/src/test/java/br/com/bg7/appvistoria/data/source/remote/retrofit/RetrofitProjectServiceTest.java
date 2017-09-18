package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import br.com.bg7.appvistoria.projectselection.vo.Project;
import okhttp3.mockwebserver.RecordedRequest;

import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.GET_COMMERCIAL_PROJECT_RESPONSE_JSON;

/**
 * Created by: luciolucio
 * Date: 2017-09-05
 */

public class RetrofitProjectServiceTest extends ServiceTest {

    private RetrofitProjectService projectService;

    @Before
    public void setUp() throws IOException {
        super.setUp();

        projectService = new RetrofitProjectService(url);
    }

    @Test
    public void shouldCallServiceWithTheCorrectParameters() throws InterruptedException {
        projectService.findByIdOrDescription("1234", new EmptyCallback<List<Project>>());

        RecordedRequest request = mockWebServer.takeRequest();

        Assert.assertEquals("/auction-lotting/commercial-project/?q=searchKey:1234", request.getPath());
        Assert.assertEquals("GET", request.getMethod());
    }

    @Test
    public void shouldConvertJsonToProjects() throws IOException, InterruptedException {
        setUpResponse(GET_COMMERCIAL_PROJECT_RESPONSE_JSON);

        projectService.findByIdOrDescription("blah", new VerifySuccessCallback<List<Project>>() {
            @Override
            protected void verify(List<Project> value) {
                Assert.assertEquals(3, value.size());

                Assert.assertEquals(74L, (long) value.get(0).getId());
                Assert.assertEquals("74 - Carros variados", value.get(0).getDescription());

                Assert.assertEquals(88L, (long) value.get(1).getId());
                Assert.assertEquals("88 - Carros e motos", value.get(1).getDescription());

                Assert.assertEquals(76L, (long) value.get(2).getId());
                Assert.assertEquals("76 - Apartamentos no Brooklin", value.get(2).getDescription());
            }
        });

        waitForServerToRespond();
    }

    @Test
    public void shouldThrowOnFailure() throws InterruptedException {
        setUpErrorResponse("ERROR");

        projectService.findByIdOrDescription("blah", new VerifyFailureCallback<List<Project>>());

        waitForServerToRespond();
    }
}
