package br.com.bg7.appvistoria.data.source.remote.retrofit;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by: luciolucio
 * Date: 2017-09-05
 */

public class Resources {
    private static File getFileFromPath(String filename) {
        ClassLoader classLoader = ProgressRequestBodyTest.class.getClassLoader();
        URL resource = classLoader.getResource(filename);
        return new File(resource.getPath());
    }

    private static String getFileContents(String filename) {
        File file = getFileFromPath(filename);
        try {
            List<String> lines = Files.readLines(file, Charsets.UTF_8);
            return StringUtils.join(lines, "\n");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final static File FILE_0_TXT = getFileFromPath("file0.txt");
    public final static File FILE_1_TXT = getFileFromPath("file1.txt");
    public final static File FILE_1000_TXT = getFileFromPath("file1000.txt");
    public final static File FILE_2048_TXT = getFileFromPath("file2048.txt");
    public final static File FILE_4096_TXT = getFileFromPath("file4096.txt");
    public final static File FILE_3800_TXT = getFileFromPath("file3800.txt");
    public final static String GET_TOKEN_RESPONSE_JSON = getFileContents("tokenService/getTokenResponse.json");
}
