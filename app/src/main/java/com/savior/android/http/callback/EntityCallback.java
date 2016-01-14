package com.savior.android.http.callback;

public interface EntityCallback<E> extends Callback {
	public void succeed(E entity);
}
