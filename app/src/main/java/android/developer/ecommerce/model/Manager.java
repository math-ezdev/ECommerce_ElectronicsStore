package android.developer.ecommerce.model;

public class Manager {

    //#MANAGERS				ID - EMAIL - PASSWORD - ADMIN - NAME
    //	ADMIN
    //      0 - FALSE
    //      1 - TRUE

    private int id;
    private String email;
    private String password;
    private boolean admin;
    private String name;

    //Constructor

    public Manager(int id, String email, String password, boolean admin, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.name = name;
    }


    //Getter - Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
