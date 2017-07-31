package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import okhttp3.MediaType;
import okio.Buffer;


/**
 * Created by: elison
 * Date: 2017-07-31
 */
public class ProgressRequestBodyTest {

    private static final String FILE_URI = "file.txt";
    private static final MediaType MEDIA_TYPE = MediaType.parse("image/*");
    private static final int BUFFER_SIZE = 16224;
    private File file = getFileFromPath(this, FILE_URI);
    private Buffer buffer = new Buffer();
    private ProgressRequestBody body;
    private boolean showProgress = false;

    @Before
    public void setUp() {
        body = new ProgressRequestBody(file, BUFFER_SIZE, listener);
    }

    @Test
    public void shouldSaveDataToBuffer() throws IOException {
        write();
        Assert.assertTrue(buffer.size() > 0);
    }

    @Test
    public void shouldFileSizeEqualsBuffer() throws IOException {
        write();
        Assert.assertTrue(buffer.size() == file.length());
    }

    @Test
    public void shouldFileTypeEquals() throws IOException {
        write();
        Assert.assertTrue(MEDIA_TYPE.type().equals(body.contentType().type()));
    }

    @Test
    public void shouldShowOnProgressUpdate() throws IOException {
        showProgress = true;
        write();
    }

    private void write() throws IOException {
        body.writeTo(buffer);
    }

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    private HttpProgressCallback listener = new HttpProgressCallback() {
        @Override
        public void onProgressUpdated(double percentage) {
                if(showProgress) System.out.println("onProgressUpdated: "+percentage);
        }

        @Override
        public void onResponse(HttpResponse httpResponse) { }

        @Override
        public void onFailure(Throwable t) { }
    };

}
