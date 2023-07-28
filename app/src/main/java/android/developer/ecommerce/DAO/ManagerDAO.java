package android.developer.ecommerce.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.model.Manager;

import java.util.ArrayList;

public class ManagerDAO {
    private DbHelper dbHelper;
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;

    public ManagerDAO(Context context) {
        this.dbHelper = new DbHelper(context);
        //      Shared Preferences
        sharedPreferences = context.getSharedPreferences(MainActivity.PREFS_FILE,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
    }

    //#MANAGERS				ID - EMAIL - PASSWORD - ADMIN - NAME
    //	ADMIN
    //      0 - FALSE
    //      1 - TRUE

    //#get - one
    public Manager getItemDatabase(int id){
        Manager manager = null;

        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM MANAGERS WHERE ID = ?",new String[]{String.valueOf(id)});

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                manager =  new Manager
                        (
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getInt(3)==1,
                                cursor.getString(4)
                        );

            }
        }

        

        return manager;
    }

    //#get
    public ArrayList<Manager> getDatabase(){
        ArrayList<Manager> list = new ArrayList<>();

        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM MANAGERS",null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }

        

        return list;
    }

    //#insert
    public boolean insertDatabase(String newEmail, String newPassword, boolean checkAdmin, String newName) {
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL",newEmail);
        contentValues.put("PASSWORD",newPassword);
        contentValues.put("ADMIN",checkAdmin?1:0);
        contentValues.put("NAME",newName);


        long newRowID = database.insert("MANAGERS",null,contentValues);

        return newRowID != -1;
    }

    //#update
    public boolean updateDatabase(int id,String password){
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("PASSWORD",password);


        long newRowID = database.update("MANAGERS",contentValues,"ID = ?",new String[]{String.valueOf(id)});

        return newRowID != -1;
    }
    public boolean updateDatabase(String name,int id){
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);


        long newRowID = database.update("MANAGERS",contentValues,"ID = ?",new String[]{String.valueOf(id)});

        return newRowID != -1;
    }

    //#query
    public Manager queryDatabaseAvailable(String email, String password){
        Manager manager = null;

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM MANAGERS WHERE EMAIL = ? AND PASSWORD = ?",new String[]{email,password});

        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            manager = new Manager
                    (
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3)==1,
                            cursor.getString(4)
                    );

            saveSharedPreferences(manager);


        }

        return manager;
    }

    //
    public void saveSharedPreferences(Manager manager) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ACCOUNT_TYPE",true);
        editor.putInt("ID",manager.getId());
        editor.putString("NAME",manager.getName());

        editor.putString("EMAIL",manager.getEmail());
        editor.putBoolean("ADMIN",manager.isAdmin());
        editor.putString("PASSWORD",manager.getPassword());

        editor.putString("USERNAME","");
        editor.putString("PHONE_NUMBER","");
        editor.putString("ADDRESS","");
        editor.apply();
    }


}
