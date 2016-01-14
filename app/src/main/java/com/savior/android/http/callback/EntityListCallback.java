package com.savior.android.http.callback;

import java.util.List;

public interface EntityListCallback<E> extends Callback {
	public void succeed(List<E> list);
}
