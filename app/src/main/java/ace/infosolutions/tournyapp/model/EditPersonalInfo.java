package ace.infosolutions.tournyapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EditPersonalInfo implements Parcelable {
    public static final Creator<EditPersonalInfo> CREATOR = new Creator<EditPersonalInfo>() {
        @Override
        public EditPersonalInfo createFromParcel(Parcel in) {
            return new EditPersonalInfo(in);
        }

        @Override
        public EditPersonalInfo[] newArray(int size) {
            return new EditPersonalInfo[size];
        }
    };
    private String full_name;
    private String dob;
    private String location;

    protected EditPersonalInfo(Parcel in) {
        full_name = in.readString();
        dob = in.readString();
        location = in.readString();
    }

    public EditPersonalInfo(String full_name, String dob, String location) {
        this.full_name = full_name;
        this.dob = dob;
        this.location = location;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getDob() {
        return dob;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(full_name);
        parcel.writeString(dob);
        parcel.writeString(location);
    }
}
