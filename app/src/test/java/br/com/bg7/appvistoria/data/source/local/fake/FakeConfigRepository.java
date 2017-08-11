package br.com.bg7.appvistoria.data.source.local.fake;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-31
 */

public class FakeConfigRepository extends FakeRepository<String, Config> implements ConfigRepository {
    @Override
    public Config findByUser(User user) {
        return ENTITIES_BY_KEY.get(user.getUsername());
    }

    @Override
    String getKey(Config entity) {
        return entity.getUser().getUsername();
    }
}
