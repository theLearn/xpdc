package com.example.hongcheng.data.retrofit.response.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hongcheng.common.util.JsonUtils;

/**
 * Created by hongcheng on 16/9/11.
 */
public class Card implements IModelConvert{
    private String name;
    private String imageUrl;
    private String description;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public Card()
    {
    }

    public Card(Parcel in)
    {
        super();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.description = in.readString();
        this.type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>()
    {
        public Card createFromParcel(Parcel in)
        {
            return new Card(in);
        }

        public Card[] newArray(int size)
        {
            return new Card[size];
        }
    };
    
    @Override
    public String toString()
    {
        return "Card{" + "name='" + name + '\'' + ", imageUrl='" + imageUrl + '\'' + ", description='" + description + '\'' + ", type='" + type + '\'' + '}';
    }
    
    @Override
    public String toJsonStr()
    {
        return JsonUtils.toJsonStr(this);
    }
}
