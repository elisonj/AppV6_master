package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import br.com.bg7.appvistoria.projectselection.vo.Project;

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
    public void shouldCallService() throws IOException, InterruptedException {
        setUpResponse(GET_COMMERCIAL_PROJECT_RESPONSE_JSON);

        projectService.findByIdOrDescription("blah", new VerifyCallback<List<Project>>() {
            @Override
            protected void verify(List<Project> value) {
                Assert.assertEquals(3, value.size());
                Assert.assertEquals(74L, (long) value.get(0).getId());
            }
        });

        waitForServerToRespond();
    }
}
