package com.savior.android.entity;

import com.savior.android.base.BaseEntity;
import com.savior.android.util.FormatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 */
public class UserEntity extends BaseEntity {
    public long id;
    public String mobile, nick_name;
    public Date create_date;

    @Override
    public UserEntity parseJson(JSONObject json) throws Exception {
        try {
            UserEntity user = new UserEntity();
            user.id = json.getLong("id");
            user.mobile = json.getString("mobile");
            user.nick_name = json.getString("nick_name");
            user.create_date = FormatUtil.getDate(json.getString("create_date"), FormatUtil.STANDARD_FORMAT);
            return user;
        } catch (Exception e) {
        }
        return null;
    }
}
