package android.developer.ecommerce.model;

public class OrderDetail {

    //#ORDER_DETAILS		ID - QUANTITY - TOTAL - PRODUCTS_ID - ORDERS_ID

    private int id;
    private int quantity;
    private double total;
    private Product product;
    private Order order;

    private boolean isSelected;

    //Constructor

    public OrderDetail(int id, int quantity, double total, Product product, Order order) {
        this.id = id;
        this.quantity = quantity;
        this.total = total;
        this.product = product;
        this.order = order;
    }

    //Getter - Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
