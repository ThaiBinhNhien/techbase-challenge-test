package test.challenge.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SingleResponse<I> extends BaseResponse {

	//	public final I DATA_NONE = null;

	I data;

	public SingleResponse() {
		super();
	}

	public I getData() {
		return data == null ? (I) "" : data;
	}

	public SingleResponse<I> setData(I data) {
		this.data = data;
		return this;
	}
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(this).replaceAll("<", " ").replaceAll(">", " ");
	}
}
