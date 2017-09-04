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

    public static final Language PT_LANGUAGE = new Language("pt", "Portugues");
    public static final Language EN_LANGUAGE = new Language("en", "English");

    @Override
    public List<Language> getLanguages() {
        return Arrays.asList(
                PT_LANGUAGE,
                EN_LANGUAGE
        );
    }

    @Override
    public Language getDefaultLanguage() {
        return PT_LANGUAGE;
    }
}
