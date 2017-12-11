package br.com.bg7.appvistoria.data.source.local;

import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;

/**
 * Created by: elison
 * Date: 2017-07-21
 */

public interface ConfigRepository extends Repository<Config> {
    Config findByUser(User user);
}
