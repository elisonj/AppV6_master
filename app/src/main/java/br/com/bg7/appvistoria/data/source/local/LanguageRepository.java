package br.com.bg7.appvistoria.data.source.local;

import java.util.List;

import br.com.bg7.appvistoria.config.vo.Language;

/**
 * Created by: elison
 * Date: 2017-07-21
 */

public interface LanguageRepository {
    List<Language> getLanguages();

    Language getDefaultLanguage();
}
