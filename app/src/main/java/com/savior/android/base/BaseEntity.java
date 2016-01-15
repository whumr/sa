package com.savior.android.base;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 */
public class BaseEntity implements Serializable {
    public static <E extends BaseEntity> E parseJson(JSONObject json) throws Exception {
        return null;
    }
}
