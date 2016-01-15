package com.savior.android.entity;

import com.savior.android.base.BaseEntity;
import com.savior.android.util.FormatUtil;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/14.
 */
public class UserEntity extends BaseEntity {
    public long id;
    public String mobile, nick_name, last_token;
    public Date create_date;

    public static UserEntity parseJson(JSONObject json) throws Exception {
        try {
            UserEntity user = new UserEntity();
            user.id = json.getLong("id");
            user.mobile = json.has("mobile") ? json.getString("mobile") : null;
            user.nick_name = json.has("nick_name") ? json.getString("nick_name") : null;
            user.last_token = json.has("last_token") ? json.getString("last_token") : null;
            user.create_date = json.has("create_date") ? FormatUtil.getDate(json.getString("create_date"), FormatUtil.STANDARD_FORMAT) : null;
            return user;
        } catch (Exception e) {
        }
        return null;
    }
}
