package com.example.hongcheng.data.retrofit.response.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hongcheng.common.util.JsonUtils;

/**
 * Created by hongcheng on 17/5/7.
 */

public class FileOperate implements IModelConvert
{
	private String fileName;
	private String clientPath;
	private String serverPath;
	private long size;
	
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getClientPath() {
		return clientPath;
	}
	
	public void setClientPath(String clientPath) {
		this.clientPath = clientPath;
	}
	
	public String getServerPath() {
		return serverPath;
	}
	
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	public FileOperate(String fileName, String clientPath, String serverPath, long size) {
		this.fileName = fileName;
		this.clientPath = clientPath;
		this.serverPath = serverPath;
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "FileOperate{" +
				"fileName='" + fileName + '\'' +
				", clientPath='" + clientPath + '\'' +
				", serverPath='" + serverPath + '\'' +
				", size=" + size +
				'}';
	}
	
	public FileOperate(){}
	
	
	@Override
	public int describeContents() { return 0; }
	
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.fileName);
		dest.writeString(this.clientPath);
		dest.writeString(this.serverPath);
		dest.writeLong(this.size);
	}
	
	protected FileOperate(Parcel in)
	{
		this.fileName = in.readString();
		this.clientPath = in.readString();
		this.serverPath = in.readString();
		this.size = in.readLong();
	}
	
	public static final Parcelable.Creator<FileOperate> CREATOR = new Parcelable.Creator<FileOperate>()
	{
		@Override
		public FileOperate createFromParcel(Parcel source) {return new FileOperate(source);}
		
		@Override
		public FileOperate[] newArray(int size) {return new FileOperate[size];}
	};
	
	@Override
	public String toJsonStr()
	{
		return JsonUtils.toJsonStr(this);
	}
}
