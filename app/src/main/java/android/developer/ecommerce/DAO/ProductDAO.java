package android.developer.ecommerce.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.model.Product;

import java.util.ArrayList;

public class ProductDAO {
    private DbHelper dbHelper;
    //      DAO
    private CategoryDAO categoryDAO;

    public ProductDAO(Context context) {
        this.dbHelper = new DbHelper(context);
        //      DAO
        categoryDAO = new CategoryDAO(context);
    }

    //#PRODUCTS				ID - NAME - IMG_BANNER - DESCRIPTION - CONFIG - ORIGINAL_PRICE - PRICE - CATEGORIES_ID

    //#get
    public ArrayList<Product> getDatabaseByCategory(int categories_id){
        ArrayList<Product> list = new ArrayList<>();

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCTS WHERE CATEGORIES_ID = ? ORDER BY ID DESC",new String[]{String.valueOf(categories_id)});

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }

        

        return list;
    }
    public ArrayList<Product> getDatabaseByCategory(int categories_id,int priceMin  ,int priceMax){
        ArrayList<Product> list = new ArrayList<>();

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCTS WHERE CATEGORIES_ID = ? AND PRICE BETWEEN ? AND ? ORDER BY PRICE ASC",new String[]{String.valueOf(categories_id),String.valueOf(priceMin),String.valueOf(priceMax)});

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }



        return list;
    }
    public ArrayList<Product> getDatabaseHidden(){
        ArrayList<Product> list = new ArrayList<>();

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCTS WHERE CATEGORIES_ID IN (10,20,30)  ORDER BY ID DESC",null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }



        return list;
    }


    //#get - one
    public Product getItemDatabase(int id){
        Product product = null;

        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCTS WHERE ID = ? ",new String[]{String.valueOf(id)});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

                product = new Product
                        (
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getDouble(5),
                                cursor.getDouble(6),
                                categoryDAO.getItemDatabase(cursor.getInt(7))
                        );
        }


        return product;
    }


    //#insert
    public boolean insertDatabase(Product product){
        //Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",product.getName());
        contentValues.put("IMG_BANNER",product.getImg_banner());
        contentValues.put("DESCRIPTION",product.getDescription());
        contentValues.put("CONFIG",product.getConfig());
        contentValues.put("ORIGINAL_PRICE",product.getOriginal_price());
        contentValues.put("PRICE",product.getPrice());
        contentValues.put("CATEGORIES_ID",product.getCategory().getId());

        long newRowID = database.insert("PRODUCTS",null,contentValues);

        return newRowID != -1;
    }

    //#update
    public boolean updateDatabase(Product product){
        //Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",product.getName());
        contentValues.put("IMG_BANNER",product.getImg_banner());
        contentValues.put("DESCRIPTION",product.getDescription());
        contentValues.put("CONFIG",product.getConfig());
        contentValues.put("ORIGINAL_PRICE",product.getOriginal_price());
        contentValues.put("PRICE",product.getPrice());
        contentValues.put("CATEGORIES_ID",product.getCategory().getId());

        long newRowID = database.update("PRODUCTS",contentValues," ID = ?",new String[]{String.valueOf(product.getId())});

        return newRowID != -1;
    }
    public boolean updateDatabase(int id, int category_id){
        //Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("CATEGORIES_ID",category_id);

        long newRowID = database.update("PRODUCTS",contentValues," ID = ?",new String[]{String.valueOf(id)});

        return newRowID != -1;
    }
}
