package david.example.com.productlisting;

import android.os.Parcel;
import android.os.Parcelable;

public class BrandListing implements Parcelable {
    private String name;
    private String logoURL;

    public BrandListing(String _name, String _logoUrl){
        name = _name;
        logoURL = _logoUrl;
    }

    public String getName() {
        return name;
    }

    public String getLogoURL() {
        return logoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.logoURL);
    }

    public final static Parcelable.Creator<BrandListing> CREATOR = new Parcelable.Creator<BrandListing>(){

        @Override
        public BrandListing createFromParcel(Parcel parcel) {
            return new BrandListing(parcel);
        }

        @Override
        public BrandListing[] newArray(int i) {
            return new BrandListing[i];
        }
    };

    private BrandListing(Parcel in){
        this.name = in.readString();
        this.logoURL = in.readString();
    }
}
