package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

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

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final File DEFAULT_FILE = getFileFromPath("file2048.txt");

    private int index = 0;
    private static final HttpProgressCallbackTest EMPTY_LISTENER = new HttpProgressCallbackTest() {
        @Override
        public void onProgressUpdated(double percentage) {
        }
    };

    @Test
    public void shouldSaveDataToBuffer() throws IOException {
        ProgressRequestBody body = new ProgressRequestBody(DEFAULT_FILE, DEFAULT_BUFFER_SIZE, EMPTY_LISTENER);
        Buffer buffer = new Buffer();

        body.writeTo(buffer);

        Assert.assertTrue(buffer.size() > 0);
        Assert.assertEquals(2048, buffer.size());
    }

    @Test
    public void shouldAlwaysReturnCorrectMediaType() throws IOException {
        ProgressRequestBody body = new ProgressRequestBody(DEFAULT_FILE, DEFAULT_BUFFER_SIZE, EMPTY_LISTENER);

        Assert.assertEquals(MediaType.parse("image/*"), body.contentType());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullFile() throws IOException {
        new ProgressRequestBody(null, DEFAULT_BUFFER_SIZE, EMPTY_LISTENER);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullListener() throws IOException {
        new ProgressRequestBody(DEFAULT_FILE, DEFAULT_BUFFER_SIZE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNegativeBufferSize() throws IOException {
        new ProgressRequestBody(DEFAULT_FILE, -1, EMPTY_LISTENER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptZeroBufferSize() throws IOException {
        new ProgressRequestBody(DEFAULT_FILE, 0, EMPTY_LISTENER);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotAcceptNullBuffer() throws IOException {
        ProgressRequestBody body = new ProgressRequestBody(DEFAULT_FILE, DEFAULT_BUFFER_SIZE, EMPTY_LISTENER);
        body.writeTo(null);
    }

    @Test
    public void shouldCallbackOnProgressUpdates1000() throws IOException {
        final double[] expected = {0, 100.0};
        testFile("file1000.txt", expected);
    }

    @Test
    public void shouldCallbackOnProgressUpdates2048() throws IOException {
        final double[] expected = {0, 50.0, 100.0};
        testFile("file2048.txt", expected);
    }

    @Test
    public void shouldCallbackOnProgressUpdates4096() throws IOException {
        final double[] expected = {0, 25.0, 50.0, 75.0, 100.0};
        testFile("file4096.txt", expected);
    }

    private static File getFileFromPath(String fileName) {
        ClassLoader classLoader = ProgressRequestBodyTest.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    private void testFile(String fileName, final double[] expectedPercentages) throws IOException {
        index = 0;

        ProgressRequestBody body = new ProgressRequestBody(getFileFromPath(fileName), DEFAULT_BUFFER_SIZE, new HttpProgressCallbackTest() {
            @Override
            public void onProgressUpdated(double percentage) {
                Assert.assertEquals(expectedPercentages[index], percentage);
                index++;
            }
        });

        body.writeTo(new Buffer());
    }
}
