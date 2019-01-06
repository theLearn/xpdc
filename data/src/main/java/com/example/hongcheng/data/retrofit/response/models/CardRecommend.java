package com.example.hongcheng.data.retrofit.response.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hongcheng.common.util.JsonUtils;

/**
 * Created by hongcheng on 16/9/15.
 */
public class CardRecommend implements IModelConvert{
    private long id;
    private String imageUrl;
    private String description;
    private String type;
    private String content;
    private String infoId;
    private String date;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public CardRecommend(String imageUrl, String description, String type, String content, String infoId, String date)
    {
        this.imageUrl = imageUrl;
        this.description = description;
        this.type = type;
        this.content = content;
        this.infoId = infoId;
        this.date = date;
    }
    
    @Override
    public int describeContents()
    {
        return 0;
    }

    public CardRecommend()
    {
    }

    public CardRecommend(Parcel in)
    {
        super();
        this.id = in.readLong();
        this.imageUrl = in.readString();
        this.content = in.readString();
        this.type = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.infoId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(id);
        dest.writeString(imageUrl);
        dest.writeString(content);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(date);
        dest.writeString(infoId);
    }

    public static final Parcelable.Creator<CardRecommend> CREATOR = new Parcelable.Creator<CardRecommend>()
    {
        public CardRecommend createFromParcel(Parcel in)
        {
            return new CardRecommend(in);
        }

        public CardRecommend[] newArray(int size)
        {
            return new CardRecommend[size];
        }
    };
    
    @Override
    public String toString()
    {
        return "CardRecommend{" + "id=" + id + ", imageUrl='" + imageUrl + '\'' + ", content='" + content + '\'' + ", description='" + description + '\'' + ", date='" + date + '\'' + ", infoId='" + infoId + '\'' + '}';
    }
    
    @Override
    public String toJsonStr()
    {
        return JsonUtils.toJsonStr(this);
    }
}
