package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallbackTest;
import okhttp3.MediaType;
import okio.Buffer;


/**
 * Created by: elison
 * Date: 2017-07-31
 */
public class ProgressRequestBodyTest {

    private static final String FILE_URI = "file.txt";
    private static final String FILE_2048_URI = "file2048.txt";
    private static final MediaType MEDIA_TYPE = MediaType.parse("image/*");
    private static final int BUFFER_SIZE = 1024;
    private static final int FILE_SIZE = 2048;
    private File file = getFileFromPath(this, FILE_2048_URI);
    private Buffer buffer = new Buffer();
    private ProgressRequestBody body;
    private int index = 0;
    private double[] arrayExpectedValues = {0, 50.0, 100.0};


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
        Assert.assertEquals(buffer.size(), FILE_SIZE);
    }

    @Test
    public void shouldFileTypeEquals() throws IOException {
        write();
        Assert.assertEquals(MEDIA_TYPE, body.contentType());
    }

    @Test
    public void shouldShowOnProgressUpdate() throws IOException {

        index = 0;
        body = new ProgressRequestBody(file, BUFFER_SIZE, new HttpProgressCallbackTest() {
            @Override
            public void onProgressUpdated(double percentage) {
                Assert.assertEquals(arrayExpectedValues[index], percentage);
                index++;
            }
        });

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

    private HttpProgressCallbackTest listener = new HttpProgressCallbackTest() {
        @Override
        public void onProgressUpdated(double percentage) {
        }
    };

}
