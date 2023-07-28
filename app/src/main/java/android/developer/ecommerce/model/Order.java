package android.developer.ecommerce.model;

import java.util.ArrayList;

public class Order {

    //#ORDERS				ID - ORDER_DATE - RECEIVED_DATE - TOTAL - STATUS - CUSTOMERS_ID - MANAGERS_ID
    //  STATUS
    //      0 - CHỜ XÁC NHẬN
    //		1 - ĐANG GIAO
    //      10 - ĐÃ GIAO
    //      99 - ĐÃ HỦY

    private int id;
    private String order_date;
    private String received_date;
    private double total;
    private int status;
    private Customer customer;
    private Manager manager;

    private ArrayList<OrderDetail> list;

    //Constructor

    public Order(int id, String order_date, String received_date, double total, int status, Customer customer, Manager manager) {
        this.id = id;
        this.order_date = order_date;
        this.received_date = received_date;
        this.total = total;
        this.status = status;
        this.customer = customer;
        this.manager = manager;
    }

    public Order(int id, String order_date, String received_date, double total, int status, Customer customer, Manager manager, ArrayList<OrderDetail> list) {
        this.id = id;
        this.order_date = order_date;
        this.received_date = received_date;
        this.total = total;
        this.status = status;
        this.customer = customer;
        this.manager = manager;
        this.list = list;
    }

    //Getter - Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getReceived_date() {
        return received_date;
    }

    public void setReceived_date(String received_date) {
        this.received_date = received_date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ArrayList<OrderDetail> getList() {
        return list;
    }

    public void setList(ArrayList<OrderDetail> list) {
        this.list = list;
    }
}
