package david.example.com.productlisting;

/**
 * Created by David on 2018-03-26.
 */

class DBAdapter {
    private static final DBAdapter mInstance = new DBAdapter();

    static DBAdapter getInstance() {
        return mInstance;
    }

    private DBAdapter() {

    }
}
