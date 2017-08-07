package br.com.bg7.appvistoria.data.source.remote.retrofit;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import br.com.bg7.appvistoria.data.source.remote.http.HttpProgressCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
class ProgressRequestBody extends RequestBody {

    private static final double MAX_PERCENTAGE = 100.0;
    private static final MediaType MEDIA_TYPE = MediaType.parse("image/*");
    private int bufferSize;

    private File file;
    private HttpProgressCallback callback;

    ProgressRequestBody(File file, int bufferSize, HttpProgressCallback callback) {
        if(bufferSize <= 0) {
            throw new IllegalArgumentException("buffer size must be positive");
        }

        this.file = checkNotNull(file, "file cannot be null");
        this.callback = checkNotNull(callback, "callback cannot be null");
        this.bufferSize = bufferSize ;
    }

    @Override
    public MediaType contentType() {
        return MEDIA_TYPE ;
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @ParametersAreNonnullByDefault
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = contentLength();
        byte[] buffer = new byte[bufferSize];
        long uploaded = 0;

        try (FileInputStream in = new FileInputStream(file)) {
            int read;
            while ((read = in.read(buffer)) != -1) {
                callback.onProgressUpdated(MAX_PERCENTAGE * uploaded / fileLength);
                uploaded += read;
                sink.write(buffer, 0, read);
            }

            callback.onProgressUpdated(MAX_PERCENTAGE);
        }
    }
}
