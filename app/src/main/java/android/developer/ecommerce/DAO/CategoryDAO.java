package android.developer.ecommerce.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.model.Category;

import java.util.ArrayList;

public class CategoryDAO {
    private DbHelper dbHelper;

    public CategoryDAO(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    //#CATEGORIES			ID - NAME - IMG_BANNER - DESCRIPTION

    //#get - one
    public Category getItemDatabase(int id){
        Category category = null;

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIES WHERE ID = ?",new String[]{String.valueOf(id)});

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                category =  new Category
                        (
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3)
                        );

            }

            

        }


        return category;
    }

    //#get all
    public ArrayList<Category> getDatabase(){
        ArrayList<Category> list = new ArrayList<>();

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM CATEGORIES",null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }

        

        return list;
    }



}
