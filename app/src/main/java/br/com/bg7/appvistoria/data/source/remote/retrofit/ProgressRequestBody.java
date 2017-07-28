package br.com.bg7.appvistoria.data.source.remote.retrofit;


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
public class ProgressRequestBody extends RequestBody {

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    private File file;
    private HttpProgressCallback listener;

    public ProgressRequestBody(final File file,
                               final HttpProgressCallback listener) {
        this.file = file;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("image/*");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                listener.onProgressUpdated((int)(100 * uploaded / fileLength));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }
}
