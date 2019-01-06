package com.example.hongcheng.common.view.searchview;

import android.database.Cursor;

/**
 * Created by Carson_Ho on 17/8/10.
 */

public interface ICallBack {
    void searchAction(String string);
    void queryResult(Cursor cursor);
}
