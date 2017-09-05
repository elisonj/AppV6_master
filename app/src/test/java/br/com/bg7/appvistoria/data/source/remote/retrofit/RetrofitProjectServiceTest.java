package br.com.bg7.appvistoria.data.source.remote.retrofit;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import br.com.bg7.appvistoria.UserLoggedInTest;
import br.com.bg7.appvistoria.data.source.remote.http.HttpCallback;
import br.com.bg7.appvistoria.data.source.remote.http.HttpResponse;
import br.com.bg7.appvistoria.projectselection.vo.Project;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.GET_COMMERCIAL_PROJECT_RESPONSE_JSON;

/**
 * Created by: luciolucio
 * Date: 2017-09-05
 */

public class RetrofitProjectServiceTest extends UserLoggedInTest {

    private RetrofitProjectService projectService;
    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws IOException {
        super.setUp();

        mockWebServer = new MockWebServer();

        projectService = new RetrofitProjectService(mockWebServer.url("").toString());
    }

    @Test
    public void shouldCallService() throws IOException {
        List<String> lines = Files.readLines(GET_COMMERCIAL_PROJECT_RESPONSE_JSON, Charsets.UTF_8);
        String body = StringUtils.join(lines, "\n");
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(body)
        );

        projectService.findByIdOrDescription("blah", new HttpCallback<List<Project>>() {
            @Override
            public void onResponse(HttpResponse<List<Project>> httpResponse) {
                List<Project> projects = httpResponse.body();

                Assert.assertNotNull(projects);
                Assert.assertEquals(3, projects.size());
                Assert.assertEquals(74L, (long) projects.get(0).getId());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
