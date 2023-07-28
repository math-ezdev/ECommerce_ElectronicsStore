package android.developer.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Product implements Parcelable,Serializable {

    //#PRODUCTS				ID - NAME - IMG_BANNER - DESCRIPTION - CONFIG - ORIGINAL_PRICE - PRICE - CATEGORIES_ID

    private int id;
    private String name;
    private String img_banner;
    private String description;
    private String config;
    private double original_price;
    private double price;
    private Category category;
    private boolean isSelected;

    //Constructor

    public Product(int id, String name, String img_banner, String description, String config, double original_price, double price, Category category) {
        this.id = id;
        this.name = name;
        this.img_banner = img_banner;
        this.description = description;
        this.config = config;
        this.original_price = original_price;
        this.price = price;
        this.category = category;
    }

    public Product(String name, String img_banner, String description, String config, double original_price, double price, Category category) {
        this.name = name;
        this.img_banner = img_banner;
        this.description = description;
        this.config = config;
        this.original_price = original_price;
        this.price = price;
        this.category = category;
    }

    public Product(int id) {
        this.id = id;
    }

    //Getter - Setter


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        img_banner = in.readString();
        description = in.readString();
        config = in.readString();
        original_price = in.readDouble();
        price = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_banner() {
        return img_banner;
    }

    public void setImg_banner(String img_banner) {
        this.img_banner = img_banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(img_banner);
        dest.writeString(description);
        dest.writeString(config);
        dest.writeDouble(original_price);
        dest.writeDouble(price);
    }
}
