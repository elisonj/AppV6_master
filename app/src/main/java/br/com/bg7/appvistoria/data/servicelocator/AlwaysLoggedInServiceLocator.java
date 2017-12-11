package br.com.bg7.appvistoria.data.servicelocator;

import java.io.IOException;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.data.Config;
import br.com.bg7.appvistoria.data.User;
import br.com.bg7.appvistoria.data.source.local.AuthRepository;
import br.com.bg7.appvistoria.data.source.local.ConfigRepository;
import br.com.bg7.appvistoria.data.source.local.UserRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeAuthRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeConfigRepository;
import br.com.bg7.appvistoria.data.source.local.fake.FakeUserRepository;

/**
 * Created by: elison
 * Date: 2017-09-03
 */

class AlwaysLoggedInServiceLocator extends ReleaseServiceLocator {
    private final static User USER = new User("username", "token", "hash");

    @Override
    public UserRepository getUserRepository() {
        FakeUserRepository userRepository = new FakeUserRepository();
        userRepository.save(USER);
        return userRepository;
    }

    @Override
    public ConfigRepository getConfigRepository() {
        FakeConfigRepository configRepository = new FakeConfigRepository();
        configRepository.save(new Config(BuildConfig.DEFAULT_LANGUAGE_NAME, USER));
        return configRepository;
    }

    @Override
    public AuthRepository getAuthRepository() {
        try {
            AuthRepository repository = new FakeAuthRepository();
            repository.save(USER, "token");

            return repository;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
