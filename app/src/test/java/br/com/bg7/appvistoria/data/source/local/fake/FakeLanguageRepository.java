package br.com.bg7.appvistoria.data.source.local.fake;

import java.util.Arrays;
import java.util.List;

import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;

/**
 * Created by: luciolucio
 * Date: 2017-08-22
 */

public class FakeLanguageRepository implements LanguageRepository {
    @Override
    public List<Language> getLanguages() {
        return Arrays.asList(
                new Language("pt", null),
                new Language("en", null)
        );
    }
}
