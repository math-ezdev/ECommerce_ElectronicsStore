package android.developer.ecommerce.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderDAO {
    private DbHelper dbHelper;
    //      DAO
    private CustomerDAO customerDAO;
    private ManagerDAO managerDAO;
    private ProductDAO productDAO;

    public OrderDAO(Context context) {
        this.dbHelper = new DbHelper(context);
        //      DAO
        managerDAO = new ManagerDAO(context);
        customerDAO = new CustomerDAO(context);
        productDAO = new ProductDAO(context);
    }

    //#ORDERS				ID - ORDER_DATE - RECEIVED_DATE - TOTAL - STATUS - CUSTOMERS_ID - MANAGERS_ID
    //  STATUS
    //      -1 - CHƯA ĐẶT HÀNG
    //      0 - CHỜ XÁC NHẬN
    //		1 - ĐANG GIAO
    //      10 - ĐÃ GIAO
    //      99 - ĐÃ HỦY

    //#get - one
    public Order getItemDatabase(int id) {
        Order order = null;

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM ORDERS WHERE ID = ? ", new String[]{String.valueOf(id)});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            order = new Order
                    (
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3),
                            cursor.getInt(4),
                            customerDAO.getItemDatabase(cursor.getInt(5)),
                            managerDAO.getItemDatabase(cursor.getInt(6))
                    );

        }

        return order;
    }


    //#get
    public ArrayList<Order> getDatabase(int status) {
        ArrayList<Order> list = new ArrayList<>();

        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM ORDERS WHERE STATUS = ? ORDER BY ID DESC", new String[]{String.valueOf(status)});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                list.add(getItemDatabase(cursor.getInt(0)));

            }

            
        }



        return list;
    }
    //      from 'a customer'
    public ArrayList<Order> getDatabaseOfCustomer(int customer_id,int status) {
        ArrayList<Order> list = new ArrayList<>();

        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM ORDERS WHERE CUSTOMERS_ID = ? AND STATUS = ? ORDER BY ID DESC", new String[]{String.valueOf(customer_id),String.valueOf(status)});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                list.add(getItemDatabase(cursor.getInt(0)));

            }

            
        }



        return list;
    }

    //#query
    public int queryDatabaseGetID(int customer_id){
        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT  ID FROM ORDERS WHERE CUSTOMERS_ID = ? ORDER BY ID ASC LIMIT 1",new String[]{String.valueOf(customer_id)});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            return cursor.getInt(0);
        }

        return 0;
    }

    //#insert
    public boolean insertDatabase(int customer_id) {
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("CUSTOMERS_ID", customer_id);

        long newRowID = database.insert("ORDERS", null, contentValues);

        return newRowID != -1;
    }

    public int insertDatabase(double total, int customer_id) {
        //      'order date' == time when customer order
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String order_date = simpleDateFormat.format(calendar.getTime());

        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("ORDER_DATE", order_date);
        contentValues.put("TOTAL", total);
        contentValues.put("CUSTOMERS_ID", customer_id);

        long newRowID = database.insert("ORDERS", null, contentValues);

        return (int) newRowID;
    }

    //#update
    public boolean updateDatabase(int id, int status) {
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("STATUS", status);

        if(status == 10){
            //      'received date' == time when customer order
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String received_date = simpleDateFormat.format(calendar.getTime());
            contentValues.put("RECEIVED_DATE", received_date);
        }


        long newRowID = database.update("ORDERS",contentValues, "ID = ?", new String[]{String.valueOf(id)});

        return newRowID != -1;
    }

    public boolean updateDatabase(int id, String estimated_date, int status, int manager_id) {
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("STATUS", status);
        contentValues.put("RECEIVED_DATE", estimated_date);
        contentValues.put("MANAGERS_ID",manager_id);
        if(status == 10){
            //      'received date' == time when customer order
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String received_date = simpleDateFormat.format(calendar.getTime());
            contentValues.put("RECEIVED_DATE", received_date);
        }


        long newRowID = database.update("ORDERS",contentValues, "ID = ?", new String[]{String.valueOf(id)});

        return newRowID != -1;
    }



}
