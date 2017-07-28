package br.com.bg7.appvistoria.data.source.remote.retrofit;


import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import br.com.bg7.appvistoria.data.source.remote.HttpProgressCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by: elison
 * Date: 2017-07-27
 */
class ProgressRequestBody extends RequestBody {

    private static final String MEDIA_TYPE = "image/*";
    private int defaultBufferSize;

    private File file;
    private HttpProgressCallback listener;

    ProgressRequestBody(final File file,
                               final HttpProgressCallback listener, int defaultBufferSize) {
        this.file = file;
        this.listener = listener;
        this.defaultBufferSize = defaultBufferSize;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(MEDIA_TYPE);
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        long fileLength = file.length();
        byte[] buffer = new byte[defaultBufferSize];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                listener.onProgressUpdated((double) (100 * uploaded / fileLength));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }
}
