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

    private static final String FILE_1000_URI = "file1000.txt";
    private static final String FILE_2048_URI = "file2048.txt";
    private static final MediaType MEDIA_TYPE = MediaType.parse("image/*");
    private static final int FILE_SIZE = 2048;
    private static final int BUFFER_SIZE = 1024;
    private static final int BUFFER_SIZE_EMPTY = 0;
    private static final int BUFFER_SIZE_NEGATIVE = -1;
    private File file = getFileFromPath(this, FILE_2048_URI);
    private Buffer buffer = new Buffer();
    private ProgressRequestBody body;
    private int index = 0;
    private double[] arrayExpectedValues = {0, 50.0, 100.0};
    private double minMaxValue = 0.0;

    @Before
    public void setUp() {
        createNewBody(file);
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
        createNewBody(new HttpProgressCallbackTest() {
            @Override
            public void onProgressUpdated(double percentage) {
                Assert.assertEquals(arrayExpectedValues[index], percentage);
                index++;
            }
        });
        write();
    }

    @Test
    public void shouldShowOnProgressSmallerThanBuffer() throws IOException {
        file = getFileFromPath(this, FILE_1000_URI);
        createNewBody(new HttpProgressCallbackTest() {
            @Override
            public void onProgressUpdated(double percentage) {
                Assert.assertEquals(minMaxValue, percentage);
                minMaxValue = 100;
            }
        });
        write();
    }

    @Test(expected = NullPointerException.class)
    public void shouldErrorWhenNullFile() throws IOException {
        file = null;
        setUpNewBodyAndWrite();
    }

    @Test(expected = NullPointerException.class)
    public void shouldErrorWhenNullListener() throws IOException {
        listener = null;
        setUpNewBodyAndWrite();
    }

    @SuppressWarnings("all")
    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorWhenNegativeBuffer() throws IOException {
        body = new ProgressRequestBody(file, BUFFER_SIZE_NEGATIVE, listener);
        write();
    }

    @SuppressWarnings("all")
    @Test(expected = IllegalArgumentException.class)
    public void shouldErrorWhenEmptyBufferSize() throws IOException {
        body = new ProgressRequestBody(file, BUFFER_SIZE_EMPTY, listener);
        write();
    }

    @Test(expected = NullPointerException.class)
    public void shouldErrorWhenNullBuffer() throws IOException {
        buffer = null;
        setUpNewBodyAndWrite();
    }

    private void setUpNewBodyAndWrite() throws IOException {
        createNewBody(file);
        write();
    }
    private void createNewBody(File file) {
        body = new ProgressRequestBody(file, BUFFER_SIZE, listener);
    }

    private void createNewBody(HttpProgressCallbackTest listener) {
        body = new ProgressRequestBody(file, BUFFER_SIZE, listener);
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
