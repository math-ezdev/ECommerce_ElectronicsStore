package android.developer.ecommerce.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.model.Customer;

public class CustomerDAO {
    private DbHelper dbHelper;
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;

    public CustomerDAO(Context context) {
        this.dbHelper = new DbHelper(context);
        //      Shared Preferences
        sharedPreferences = context.getSharedPreferences(MainActivity.PREFS_FILE,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
    }

    //#CUSTOMERS			ID - USERNAME - PASSWORD - NAME - PHONE_NUMBER - ADDRESS

    //#get - one
    public Customer getItemDatabase(int id){
        Customer customer = null;

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMERS WHERE ID = ?",new String[]{String.valueOf(id)});

        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            customer = new Customer
                    (
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    );

        }

        

        return customer;
    }

    //#query
    public Customer queryDatabaseAvailable(String username, String password){
        Customer customer = null;

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMERS WHERE USERNAME = ? AND PASSWORD = ?",new String[]{username,password});

        if (cursor.getCount() > 0){
            cursor.moveToFirst();

                customer = new Customer
                        (
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5)
                        );

            saveSharedPreferences(customer);


        }

        

        return customer;
    }
    //      query - get id by username
    public int queryDatabaseID(String username){
        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT ID FROM CUSTOMERS WHERE USERNAME = ?",new String[]{username});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            return cursor.getInt(0);
        }

        return 0;
    }

    //#insert
    public boolean insertDatabase(String username,String password,String name){
        if(queryDatabaseAvailable(username, password) != null){
            return false;
        }

        //Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("USERNAME",username);
        contentValues.put("PASSWORD",password);

        long newRowID = database.insert("CUSTOMERS",null,contentValues);

        return newRowID != -1;
    }

    //#update
    public boolean updateDatabase(int id,String phoneNumber,String address){
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ADDRESS",address);
        contentValues.put("PHONE_NUMBER",phoneNumber);

        long newRowID = database.update("CUSTOMERS",contentValues,"ID = ?",new String[]{String.valueOf(id)});

        return newRowID != -1;
    }
    public boolean updateDatabase(int id,String name,String phoneNumber,String address){
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("ADDRESS",address);
        contentValues.put("PHONE_NUMBER",phoneNumber);


        long newRowID = database.update("CUSTOMERS",contentValues,"ID = ?",new String[]{String.valueOf(id)});

        return newRowID != -1;
    }
    public boolean updateDatabase(int id,String password){
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("PASSWORD",password);


        long newRowID = database.update("CUSTOMERS",contentValues,"ID = ?",new String[]{String.valueOf(id)});

        return newRowID != -1;
    }


    //
    public void saveSharedPreferences(Customer customer) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ACCOUNT_TYPE",false);
        editor.putInt("ID",customer.getId());
        editor.putString("NAME",customer.getName());

        editor.putString("EMAIL","");
        editor.putBoolean("ADMIN",false);

        editor.putString("USERNAME",customer.getUsername());
        editor.putString("PASSWORD",customer.getPassword());
        editor.putString("PHONE_NUMBER",customer.getPhone_number());
        editor.putString("ADDRESS",customer.getAddress());
        editor.apply();
    }

}
