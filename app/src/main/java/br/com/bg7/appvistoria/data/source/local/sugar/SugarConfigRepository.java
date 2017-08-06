package br.com.bg7.appvistoria.data.source.local.sugar;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;

/**
 * Created by: luciolucio
 * Date: 2017-07-28
 */

public class SugarConfigRepository extends SugarRepository<Config> implements ConfigRepository {
    public SugarConfigRepository() {
        super(Config.class);
    }
}
