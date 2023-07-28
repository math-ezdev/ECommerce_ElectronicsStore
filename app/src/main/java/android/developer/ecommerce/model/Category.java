package android.developer.ecommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Category implements Parcelable, Serializable {

    //#CATEGORIES			ID - NAME - IMG_BANNER - DESCRIPTION

    private int id;
    private String name;
    private String img_banner;
    private String description;

    //Constructor

    public Category(int id, String name, String img_banner, String description) {
        this.id = id;
        this.name = name;
        this.img_banner = img_banner;
        this.description = description;
    }

    public Category(int id) {
        this.id = id;
    }

    //Getter - Setter

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
        img_banner = in.readString();
        description = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
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
    }
}
