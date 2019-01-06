package com.example.hongcheng.data.retrofit.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hongcheng.common.util.JsonUtils;
import com.example.hongcheng.common.util.SecurityUtils;
import com.example.hongcheng.data.retrofit.response.models.IModelConvert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongcheng on 17/5/1.
 */

public class BaseRequest<T extends IModelConvert> implements IModelConvert
{
	protected String token;
	protected T data;
	
	public BaseRequest(String token, T data)
	{
		this.token = token;
		this.data = data;
	}
	
	
	@Override
	public int describeContents() { return 0; }
	
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.token);
		dest.writeParcelable(this.data, flags);
	}
	
	protected BaseRequest(Parcel in)
	{
		this.token = in.readString();
		this.data = in.readParcelable(this.data.getClass().getClassLoader());
	}
	
	public static final Parcelable.Creator<BaseRequest> CREATOR = new Parcelable.Creator<BaseRequest>()
	{
		@Override
		public BaseRequest createFromParcel(Parcel source) {return new BaseRequest(source);}
		
		@Override
		public BaseRequest[] newArray(int size) {return new BaseRequest[size];}
	};
	
	@Override
	public String toJsonStr()
	{
		return JsonUtils.toJsonStr(this);
	}
	
	public Map<String, String> toMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", this.token);
		map.put("data", SecurityUtils.encodeBase64(this.data.toJsonStr()));
		return map;
	}
}
