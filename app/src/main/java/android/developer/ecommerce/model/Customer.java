package android.developer.ecommerce.model;

public class Customer {

    //#CUSTOMERS			ID - USERNAME - PASSWORD - NAME - PHONE_NUMBER - ADDRESS

    private int id;
    private String username;
    private String password;
    private String name;
    private String phone_number;
    private String address;

    //Constructor

    public Customer(int id, String username, String password, String name, String phone_number, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
    }

    public Customer(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    //Getter - Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
