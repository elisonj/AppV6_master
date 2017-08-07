package br.com.bg7.appvistoria.data.source.local.fake;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.UserRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeConfigRepository extends FakeRepository<String, Config> implements ConfigRepository {
    @Override
    public Config findByUsername(String username) {
        return ENTITIES_BY_KEY.get(username);
    }

    @Override
    String getKey(Config entity) {
        return entity.getUsername();
    }
}
