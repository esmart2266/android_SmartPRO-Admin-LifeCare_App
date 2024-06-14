package in.esmartsolution.shree.proadmin.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Visit implements Parcelable, Comparable<Visit> {

    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("middleName")
    @Expose
    public String middleName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile1")
    @Expose
    public String mobile1;
    @SerializedName("mobile2")
    @Expose
    public String mobile2;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("doj")
    @Expose
    public String doj;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("visitId")
    @Expose
    public String visitId;
    @SerializedName("doctorName")
    @Expose
    public String doctorName;
    @SerializedName("speciality")
    @Expose
    public String speciality;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("landline")
    @Expose
    public String landline;
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("remarks")
    @Expose
    public String remarks;
    @SerializedName("geoAddress")
    @Expose
    public String geoAddress;
    @SerializedName("visitDateTime")
    @Expose
    public String visitDateTime;
    @SerializedName("birthDate")
    @Expose
    public String birthDate;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    boolean isSelected;
    public String distance = "";

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGeoAddress() {
        return geoAddress;
    }

    public void setGeoAddress(String geoAddress) {
        this.geoAddress = geoAddress;
    }

    public String getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(String visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public Visit() {
    }

    @Override
    public int compareTo(@NonNull Visit o) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(getVisitDateTime()).compareTo(dateFormat.parse(o.getVisitDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.mobile1);
        dest.writeString(this.mobile2);
        dest.writeString(this.dob);
        dest.writeString(this.doj);
        dest.writeString(this.address);
        dest.writeString(this.password);
        dest.writeString(this.isActive);
        dest.writeString(this.visitId);
        dest.writeString(this.doctorName);
        dest.writeString(this.speciality);
        dest.writeString(this.mobile);
        dest.writeString(this.landline);
        dest.writeString(this.area);
        dest.writeString(this.city);
        dest.writeString(this.remarks);
        dest.writeString(this.geoAddress);
        dest.writeString(this.visitDateTime);
        dest.writeString(this.birthDate);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected Visit(Parcel in) {
        this.userId = in.readString();
        this.firstName = in.readString();
        this.middleName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.mobile1 = in.readString();
        this.mobile2 = in.readString();
        this.dob = in.readString();
        this.doj = in.readString();
        this.address = in.readString();
        this.password = in.readString();
        this.isActive = in.readString();
        this.visitId = in.readString();
        this.doctorName = in.readString();
        this.speciality = in.readString();
        this.mobile = in.readString();
        this.landline = in.readString();
        this.area = in.readString();
        this.city = in.readString();
        this.remarks = in.readString();
        this.geoAddress = in.readString();
        this.visitDateTime = in.readString();
        this.birthDate = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel source) {
            return new Visit(source);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };
}