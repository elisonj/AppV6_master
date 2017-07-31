package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import br.com.bg7.appvistoria.data.source.remote.HttpResponse;
import okio.Buffer;


/**
 * Created by: elison
 * Date: 2017-07-31
 */
public class ProgressRequestBodyTest {

    private static final String FILE_URI = "android.jpg";
    private static final int BUFFER_SIZE = 2048;
    private File file = getFileFromPath(this, FILE_URI);
    private ProgressRequestBody body = new ProgressRequestBody(file, listener, BUFFER_SIZE);
    private Buffer buffer = new Buffer();

    @Test
    public void shouldBufferHasContentWhenCallWriteTo() {
        try {
            Assert.assertTrue(body.contentLength() > 0);
            Assert.assertNotNull(body.contentType());

            body.writeTo(buffer);

            Assert.assertTrue(buffer.size() > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    static HttpProgressCallback listener = new HttpProgressCallback() {
        @Override
        public void onProgressUpdated(double percentage) {  }

        @Override
        public void onResponse(HttpResponse httpResponse) { }

        @Override
        public void onFailure(Throwable t) { }
    };
}
