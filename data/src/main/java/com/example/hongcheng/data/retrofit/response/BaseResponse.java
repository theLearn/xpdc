package com.example.hongcheng.data.retrofit.response;


import com.example.hongcheng.common.constant.BaseConstants;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseResponse<T>{
    protected int status;
    protected String description;
    protected T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return BaseConstants.STATUS_SUCCESS == status;
    }
    
    @Override
    public String toString()
    {
        return "BaseResponse{" + "status=" + status + ", description='" + description + '\'' + ", data=" + data + '}';
    }
}
