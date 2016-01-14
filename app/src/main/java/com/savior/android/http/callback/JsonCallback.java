package com.savior.android.http.callback;

import org.json.JSONObject;

public interface JsonCallback extends Callback {
	public void succeed(JSONObject json);
}
