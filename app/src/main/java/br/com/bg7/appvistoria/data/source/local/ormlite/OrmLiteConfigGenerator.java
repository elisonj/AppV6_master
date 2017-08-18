package br.com.bg7.appvistoria.data.source.local.ormlite;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by: luciolucio
 * Date: 2017-08-17
 */

public class OrmLiteConfigGenerator extends OrmLiteConfigUtil {
    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt");
    }
}
