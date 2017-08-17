How to add a new ormlite persisted entity
-----------------------------------------

1. Increment the database version by one in `build.gradle`
1. Create the class under `br.com.bg7.appvistoria.data`
1. Annotate it using `ormlite` annotations
1. Set it up in `data.source.local.ormlite.DatabaseHelper`:
    * Declare a private member variable for its DAO 
    * Create a getter for the DAO. It should call `getDao` with the appropriate parameters
    * Make sure it is set to `null` during `close()`
    * Change `onUpgrade` so that it applies your changes between the previous version and now
1. Add a convenience DAO getter method to `br.com.bg7.appvistoria.BaseActivity`
1. Add a `createTable` statement to `DatabaseHelper`
1. Generate the config file (see `ORMLITE.md` at the root dir of the project)
1. You can now use it in any activity that subclasses `BaseActivity`

### More info

See the [ormlite documentation](http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Using)
 for details on `ormlite` annotations and more
