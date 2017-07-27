package br.com.bg7.appvistoria.data.source.local;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;

import br.com.bg7.appvistoria.BuildConfig;
import br.com.bg7.appvistoria.R;
import br.com.bg7.appvistoria.config.vo.Language;

/**
 * Created by: luciolucio
 * Date: 2017-07-21
 */

public class LanguageRepository {
    private Context context;

    public LanguageRepository(Context context) {
        this.context = context;
    }

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
}
