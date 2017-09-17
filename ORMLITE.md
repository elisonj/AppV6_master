How to generate ormlite_config.txt
----------------------------------

The file `ormlite_config.txt` makes the initialization of ormlite DAOs much faster. Refer
 to the
 [ormlite docs](http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_4.html#Config-Optimization)
 for more info.

This file **must** be generated **manually** every time there are new entities for ormlite
 to persist, or changes to existing ones.

1. In Android Studio, create a new run configuration by going to `Run` > `Edit Configurations...`
2. Create a new run configuration with the following properties;
    * Main class: `br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteConfigGenerator`
    * Working directory: `$MODULE_DIR$/src/main`
    * JRE: A regular Java JRE, i.e. **not the Android JRE**
3. Save and execute the run configuration
4. Once you see output similar to the lines below, you are done

> `Writing configurations to app/src/main/./res/raw/ormlite-config.txt`
>
> `Wrote config for class br.com.bg7.appvistoria.data.Config`
>
> `Wrote config for class br.com.bg7.appvistoria.data.Inspection`
>
> `Wrote config for class br.com.bg7.appvistoria.data.Picture`
>
> `Wrote config for class br.com.bg7.appvistoria.data.User`
>
> `Done.`
