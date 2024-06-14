package in.esmartsolution.shree.proadmin.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import in.esmartsolution.shree.proadmin.widget.expandablerecyclerview.model.Parent;

public class VisitMain implements Parcelable, Parent<Visit>, Comparable<VisitMain> {
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    ArrayList<Visit> visitList = new ArrayList<>();
    String maxVisitDateTime;

    public String getMaxVisitDateTime() {
        return maxVisitDateTime;
    }

    public void setMaxVisitDateTime(String maxVisitDateTime) {
        this.maxVisitDateTime = maxVisitDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(ArrayList<Visit> visitList) {
        this.visitList = visitList;
    }

    @Override
    public List<Visit> getChildList() {
        return getVisitList();
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public VisitMain() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeTypedList(this.visitList);
        dest.writeString(this.maxVisitDateTime);
    }

    protected VisitMain(Parcel in) {
        this.userId = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.visitList = in.createTypedArrayList(Visit.CREATOR);
        this.maxVisitDateTime = in.readString();
    }

    public static final Creator<VisitMain> CREATOR = new Creator<VisitMain>() {
        @Override
        public VisitMain createFromParcel(Parcel source) {
            return new VisitMain(source);
        }

        @Override
        public VisitMain[] newArray(int size) {
            return new VisitMain[size];
        }
    };

    @Override
    public int compareTo(@NonNull VisitMain o) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(getMaxVisitDateTime()).compareTo(dateFormat.parse(o.getMaxVisitDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}