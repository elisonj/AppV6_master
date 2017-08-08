package br.com.bg7.appvistoria.data.source.local.sugar;

import java.util.List;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-28
 */

public class SugarConfigRepository extends SugarRepository<Config> implements ConfigRepository {
    public SugarConfigRepository() {
        super(Config.class);
    }

    @Override
    public Config findByUser(User user) {
        if (user.getId() == null) {
            return null;
        }

        String[] params = new String[]{user.getId().toString()};
        List<Config> configList = Config.find(Config.class, "user = ?", params);

        if (configList.size() <= 0) {
            return null;
        }

        return configList.get(0);
    }
}
