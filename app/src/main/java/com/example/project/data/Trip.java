package com.example.project.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.ParcelField;
import androidx.versionedparcelable.VersionedParcelize;

import java.util.Date;

@Entity(tableName = "trip_table")

public class Trip implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int image;
    private String name;
    private String destination;
    private String type;
    private int price;
    private float rating;
    private String startDate;
    private String endDate;
    private int isFavourite;

    public Trip (){
        this.image = -1;
        this.name = "";
        this.destination = "";
        this.type = "";
        this.price = 0;
        this.rating = 0;
        startDate = "";
        endDate = "";
        isFavourite = 0;
    }

    public Trip(int id, int image, String name, String destination, String type, int price, float rating, String startDate, String endDate, int isFavourite) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.rating = rating;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFavourite = isFavourite;
    }

    protected Trip(Parcel in) {
        id = in.readInt();
        image = in.readInt();
        name = in.readString();
        destination = in.readString();
        type = in.readString();
        price = in.readInt();
        rating = in.readFloat();
        startDate = in.readString();
        endDate = in.readString();
        isFavourite = in.readInt();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getIsFavourite() {
        return isFavourite;
    }
    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(image);
        parcel.writeString(name);
        parcel.writeString(destination);
        parcel.writeString(type);
        parcel.writeInt(price);
        parcel.writeFloat(rating);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeInt(isFavourite);
    }

    @NonNull
    @Override
    public String toString() {
        return "NAME: " + this.name + "; DESTINATION: " + this.destination + "isFav ? " + this.isFavourite;
    }
}
