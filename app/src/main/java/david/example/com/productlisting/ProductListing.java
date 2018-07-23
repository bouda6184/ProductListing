package david.example.com.productlisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David on 2018-03-26.
 */

public class ProductListing implements Parcelable {
    private String name;
    private String brand;
    private String description;
    private Double price;
    private String specURL;
    private String pdfManualTitle;
    private String websiteURL;

    public ProductListing(String _name, String _brand, String desc, Double _price,
                          String _specURL, String manTitle, String url ){
        name = _name;
        brand = _brand;
        description = desc;
        price = _price;
        specURL = _specURL;
        pdfManualTitle = manTitle;
        websiteURL = url;
    }

    public String getName(){
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getSpecURL() {
        return specURL;
    }

    public String getPdfManualTitle() {
        return pdfManualTitle;
    }

    public String getWebsiteURL() { return websiteURL; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.brand);
        parcel.writeString(this.description);
        parcel.writeDouble(this.price);
        parcel.writeString(this.specURL);
        parcel.writeString(this.pdfManualTitle);
        parcel.writeString(this.websiteURL);

    }

    public static final Parcelable.Creator<ProductListing> CREATOR = new Parcelable.Creator<ProductListing>(){

        @Override
        public ProductListing createFromParcel(Parcel parcel) {
            return new ProductListing(parcel);
        }

        @Override
        public ProductListing[] newArray(int i) {
            return new ProductListing[i];
        }
    };

    private ProductListing(Parcel in) {
        this.name = in.readString();
        this.brand = in.readString();
        this.description = in.readString();
        this.price = in.readDouble();
        this.specURL = in.readString();
        this.pdfManualTitle = in.readString();
        this.websiteURL = in.readString();

    }
}
