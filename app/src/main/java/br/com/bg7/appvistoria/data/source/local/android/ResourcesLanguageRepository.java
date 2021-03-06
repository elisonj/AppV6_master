package br.com.bg7.appvistoria.data.source.local.android;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.vo.Language;
import br.com.bg7.appvistoria.data.source.local.LanguageRepository;

/**
 * Created by: elison
 * Date: 2017-07-28
 */

public class ResourcesLanguageRepository implements LanguageRepository {
    private Context context;

    public ResourcesLanguageRepository(Context context) {
        this.context = context;
    }

    @Override
    public List<Language> getLanguages() {
        String languageNames[] = BuildConfig.LANGUAGE_NAMES;
        String displayNames[] = context.getResources().getStringArray(R.array.languageDisplayNames);

        if (languageNames.length != displayNames.length) {
            throw new ServiceConfigurationError("Language names and language display names do not match in size");
        }

        ArrayList<Language> languageList = new ArrayList<>();
        for (int i = 0; i < languageNames.length; i++) {
            languageList.add(new Language(languageNames[i], displayNames[i]));
        }

        return languageList;
    }

    @Override
    public Language getDefaultLanguage() {
        List<Language> languages = getLanguages();

        for (Language language : languages) {
            if (language.getName().equals(BuildConfig.DEFAULT_LANGUAGE_NAME)) {
                return language;
            }
        }

        throw new ServiceConfigurationError(
                String.format("Default language '%s' does not exist in LANGUAGE_NAMES", BuildConfig.DEFAULT_LANGUAGE_NAME)
        );
    }
}
