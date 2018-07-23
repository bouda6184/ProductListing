package david.example.com.productlisting;

/**
 * Created by David on 2018-03-27.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "cafeunion.db";
    private static final int DB_VERSION = 1;
    private static String DB_PATH = "";
    private final Context cont;
    private static SQLiteDatabase sqlDB;
    private boolean needUpdate = false;


    public DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.cont = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";

        copyDataBase();
        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (needUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            needUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("Erreur de copie de la base de données");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = cont.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public static boolean openDataBase() throws SQLException {
        sqlDB = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return sqlDB != null;
    }

    @Override
    public synchronized void close() {
        if(sqlDB != null){
            sqlDB.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            needUpdate = true;
        }
    }

    public ArrayList<ProductListing> getProductsByBrand(String brand_name){
        String sqlReq = String.format("SELECT P.name, B.name AS brandname, type, price, specsURL, manURL, websiteURL FROM Product P, Brand B WHERE P.brandID = B.rowid AND B.name ='" + brand_name + "' ORDER BY P.name;");
        return createProductList(sqlReq);
    }

    public ArrayList<ProductListing> getSemiAutoMachines(){
        String sqlReq = String.format("SELECT P.name, B.name AS brandname, type, price, specsURL, manURL, websiteURL FROM Product P, Brand B WHERE P.brandID = B.rowid AND type='Semi-Auto' ORDER BY B.name, P.name;");
        return createProductList(sqlReq);
    }

    public ArrayList<ProductListing> getSuperAutoMachines(){
        String sqlReq = String.format("SELECT P.name, B.name AS brandname, type, price, specsURL, manURL, websiteURL FROM Product P, Brand B WHERE P.brandID = B.rowid AND type='Super-Auto' ORDER BY B.name, P.name;");
        return createProductList(sqlReq);
    }

    public ArrayList<ProductListing> getGrinders(){
        String sqlReq = String.format("SELECT P.name, B.name AS brandname, type, price, specsURL, manURL, websiteURL FROM Product P, Brand B WHERE P.brandID = B.rowid AND type='Moulin' ORDER BY B.name, P.name;");
        return createProductList(sqlReq);
    }

    public static byte[] getBlobByProductName(String prodName){
        byte[] blob = null;
        String sqlReq = String.format("SELECT image FROM Product WHERE name='" + prodName + "'LIMIT 1;");
        openDataBase();
        Cursor cur = sqlDB.rawQuery(sqlReq, null);
        if(cur.moveToFirst()){
            do {
                blob = cur.getBlob(cur.getColumnIndex("image"));
            } while (cur.moveToNext());
        }
        cur.close();
        sqlDB.close();
        return blob;
    }


    public ArrayList<BrandListing> getAllBrands(){
        String sqlReq = "SELECT name, logoURL FROM Brand;";
        ArrayList<BrandListing> brandList = new ArrayList<>();
        openDataBase();
        Cursor cur = sqlDB.rawQuery(sqlReq, null);
        if(cur.moveToFirst()){
            do {
                String _name = cur.getString(cur.getColumnIndex("name"));
                String _logoUrl = cur.getString(cur.getColumnIndex("logoURL"));
                BrandListing brandListing = new BrandListing(_name, _logoUrl);
                brandList.add(brandListing);
            } while(cur.moveToNext());
        }
        cur.close();
        sqlDB.close();

        return brandList;
    }


    private ArrayList<ProductListing> createProductList(String req){
        ArrayList<ProductListing> prodList = new ArrayList<>();
        openDataBase();
        Cursor cur = sqlDB.rawQuery(req, null);
        if(cur.moveToFirst()){
            do{
                String _name = cur.getString(cur.getColumnIndex("name"));
                String _brand = cur.getString(cur.getColumnIndex("brandname"));
                String _type = cur.getString(cur.getColumnIndex("type"));
                Double _price = cur.getDouble(cur.getColumnIndex("price"));
                String _specsUrl = cur.getString(cur.getColumnIndex("specsURL"));
                String _manUrl = cur.getString(cur.getColumnIndex("manURL"));
                String _url = cur.getString(cur.getColumnIndex("websiteURL"));
                ProductListing prodListing = new ProductListing(_name, _brand, _type, _price, _specsUrl, _manUrl, _url);
                prodList.add(prodListing);
            } while(cur.moveToNext());
        }
        cur.close();
        sqlDB.close();

        return prodList;
    }
}
