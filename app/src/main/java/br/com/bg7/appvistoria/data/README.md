How to add a new ormlite persisted entity
-----------------------------------------

1. Increment the database version by one in `build.gradle`
1. Create the class under `br.com.bg7.appvistoria.data`
1. Annotate it using `ormlite` annotations
1. Set it up in `data.source.local.ormlite.DatabaseHelper`:
    * Declare a private member variable for its DAO - *maintain alphabetical order*
    * Create a getter for the DAO. It should call `getDao` with the appropriate parameters - *maintain alphabetical order*
    * Make sure it is set to `null` during `close()` - *maintain alphabetical order*
    * During the ALPHA or BETA stages, add `createTable` statements under **onCreate** - *maintain alphabetical order*
    * Otherwise, change `onUpgrade` so that it applies your changes between the previous version and now
        * **WARNING** DO NOT EVER CHANGE **onCreate** if there is at least one user in PRODUCTION
1. Add a convenience DAO getter method to `br.com.bg7.appvistoria.BaseActivity` - *maintain alphabetical order*
1. Generate the config file (see `ORMLITE.md` at the root dir of the project)
1. You can now use it in any activity that subclasses `BaseActivity`

### More info

See the [ormlite documentation](http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Using)
 for details on `ormlite` annotations and more
