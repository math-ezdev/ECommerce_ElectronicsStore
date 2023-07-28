package android.developer.ecommerce.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.model.OrderDetail;

import java.util.ArrayList;

public class OrderDetailDAO {
    private DbHelper dbHelper;
    //      DAO
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public OrderDetailDAO(Context context) {
        this.dbHelper = new DbHelper(context);
        //      DAO
        productDAO = new ProductDAO(context);
        orderDAO = new OrderDAO(context);
    }

    //#ORDER_DETAILS		ID - QUANTITY - TOTAL - PRODUCTS_ID - ORDERS_ID

    //#get - one
    public OrderDetail getItemDatabase(int id) {
        OrderDetail orderDetail = null;

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM ORDER_DETAILS WHERE ID = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            orderDetail = new OrderDetail
                    (
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getDouble(2),
                            productDAO.getItemDatabase(cursor.getInt(3)),
                            orderDAO.getItemDatabase(cursor.getInt(4))
                    );


        }

        

        return orderDetail;
    }

    //#get
    public ArrayList<OrderDetail> getDatabase() {
        ArrayList<OrderDetail> list = new ArrayList<>();

        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM ORDER_DETAILS", null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }

        

        return list;
    }
    public ArrayList<OrderDetail> getDatabase(int order_id) {
        ArrayList<OrderDetail> list = new ArrayList<>();

        //Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM ORDER_DETAILS WHERE ORDERS_ID = ?", new String[]{String.valueOf(order_id)});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }



        return list;
    }
    //      get from a customer
    //      (contain all item cart)
    public ArrayList<OrderDetail> getDatabaseOfCustomer(int customer_id) {
        ArrayList<OrderDetail> list = new ArrayList<>();

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT ORDER_DETAILS.* FROM ORDER_DETAILS,ORDERS,CUSTOMERS " +
                "WHERE ORDER_DETAILS.ORDERS_ID = ORDERS.ID AND ORDERS.CUSTOMERS_ID = CUSTOMERS.ID " +
                "AND  CUSTOMERS.ID = ? AND ORDER_DETAILS.ORDERS_ID = ? " +
                        "ORDER BY ORDER_DETAILS.ID DESC"
                , new String[]{String.valueOf(customer_id),String.valueOf(orderDAO.queryDatabaseGetID(customer_id))});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }

        

        return list;
    }
    //      get in 'a order'
    public ArrayList<OrderDetail> getDatabaseOfCustomerByOrderID(int order_id) {
        ArrayList<OrderDetail> list = new ArrayList<>();

        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT ORDER_DETAILS.* FROM ORDER_DETAILS,ORDERS,CUSTOMERS " +
                "WHERE ORDER_DETAILS.ORDERS_ID = ORDERS.ID AND ORDERS.CUSTOMERS_ID = CUSTOMERS.ID " +
                "AND ORDER_DETAILS.ORDERS_ID = ?", new String[]{String.valueOf(order_id)});

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                list.add(getItemDatabase(cursor.getInt(0)));

            }
        }

        

        return list;
    }




    //#query
    //      check Product in Cart
    public boolean queryDatabaseProductAvailable(int product_id, int customer_id) {
        //      Readable
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT ORDER_DETAILS.* FROM ORDER_DETAILS,ORDERS,CUSTOMERS " +
                "WHERE ORDER_DETAILS.ORDERS_ID = ORDERS.ID AND ORDERS.CUSTOMERS_ID = CUSTOMERS.ID " +
                "AND ORDER_DETAILS.PRODUCTS_ID = ? AND CUSTOMERS.ID = ? AND ORDERS.ID = ?", new String[]{String.valueOf(product_id), String.valueOf(customer_id),String.valueOf(orderDAO.queryDatabaseGetID(customer_id))});

        return cursor.getCount() > 0;
    }

    //#insert
    public boolean insertDatabase(int quantity, double total, int product_id, int order_id) {
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("QUANTITY", quantity);
        contentValues.put("TOTAL", total);
        contentValues.put("PRODUCTS_ID", product_id);
        contentValues.put("ORDERS_ID", order_id);

        long newRowID = database.insert("ORDER_DETAILS", null, contentValues);

        return newRowID != -1;
    }

    //#update
    public boolean updateDatabaseOrderID(OrderDetail orderDetail, int order_id) {
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ORDERS_ID", order_id);
        contentValues.put("QUANTITY", orderDetail.getQuantity());
        contentValues.put("TOTAL", orderDetail.getTotal());

        long newRowID = database.update("ORDER_DETAILS", contentValues,"ID = ?",new String[]{String.valueOf(orderDetail.getId())});

        return newRowID != -1;
    }

    //#delete
    public boolean deleteDatabase(int id) {
        //      Writable
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long newRowID = database.delete("ORDER_DETAILS","ID = ?",new String[]{String.valueOf(id)});

        return newRowID != -1;
    }

}
