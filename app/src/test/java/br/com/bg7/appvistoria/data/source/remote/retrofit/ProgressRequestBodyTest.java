package br.com.bg7.appvistoria.data.source.remote.retrofit;

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import br.com.bg7.appvistoria.data.source.remote.TestHttpProgressCallback;
import okhttp3.MediaType;
import okio.Buffer;

import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.FILE_0_TXT;
import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.FILE_1000_TXT;
import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.FILE_1_TXT;
import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.FILE_2048_TXT;
import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.FILE_3800_TXT;
import static br.com.bg7.appvistoria.data.source.remote.retrofit.Resources.FILE_4096_TXT;


/**
 * Created by: elison
 * Date: 2017-07-31
 */
public class ProgressRequestBodyTest {

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final File DEFAULT_FILE = FILE_2048_TXT;

    private int index = 0;
    private static final TestHttpProgressCallback EMPTY_LISTENER = new TestHttpProgressCallback() {
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
    public void shouldCallbackOnProgressUpdates() throws IOException {
        ArrayList<ProgressTestCase> testCases = new ArrayList<>();

        testCases.add(new ProgressTestCase(FILE_0_TXT, new double[]{100.0}));
        testCases.add(new ProgressTestCase(FILE_1_TXT, new double[]{0,100.0}));
        testCases.add(new ProgressTestCase(FILE_1000_TXT, new double[]{0, 100.0}));
        testCases.add(new ProgressTestCase(FILE_2048_TXT, new double[]{0, 50.0, 100.0}));
        testCases.add(new ProgressTestCase(FILE_4096_TXT, new double[]{0, 25.0, 50.0, 75.0, 100.0}));
        testCases.add(new ProgressTestCase(FILE_3800_TXT, new double[]{0, 26.95, 53.90, 80.84, 100.0}));

        for (ProgressTestCase testCase : testCases) {
            testFile(testCase.file, testCase.expected);
        }
    }

    private void testFile(File file, final double[] expectedPercentages) throws IOException {
        index = 0;

        ProgressRequestBody body = new ProgressRequestBody(file, DEFAULT_BUFFER_SIZE, new TestHttpProgressCallback() {
            @Override
            public void onProgressUpdated(double percentage) {
                Assert.assertEquals(expectedPercentages[index], percentage, 1e-2);
                index++;
            }
        });

        body.writeTo(new Buffer());
    }

    private class ProgressTestCase {
        double[] expected;
        File file;

        ProgressTestCase(File file, double[] expected) {
            this.expected = expected;
            this.file = this.file;
        }
    }
}
