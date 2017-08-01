package br.com.bg7.appvistoria.data.source.remote.retrofit;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
class ProgressRequestBody extends RequestBody {

    private static final int MAX_PERCENTAGE = 100;
    private static final MediaType MEDIA_TYPE = MediaType.parse("image/*");
    private int bufferSize;

    private File file;
    private HttpProgressCallback listener;

    ProgressRequestBody(final File file, int bufferSize,
                        final HttpProgressCallback listener) throws IllegalArgumentException {
        this.file = checkNotNull(file, "file cannot be null");
        this.listener = checkNotNull(listener, "listener cannot be null");
        if(bufferSize <= 0) {
            throw new IllegalArgumentException();
        }
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

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = file.length();
        byte[] buffer = new byte[bufferSize];
        long uploaded = 0;

        try (FileInputStream in = new FileInputStream(file)) {
            int read;
            while ((read = in.read(buffer)) != -1) {
                listener.onProgressUpdated(100 * uploaded / fileLength);
                uploaded += read;
                sink.write(buffer, 0, read);
            }
            listener.onProgressUpdated(MAX_PERCENTAGE);
        }
    }
}
