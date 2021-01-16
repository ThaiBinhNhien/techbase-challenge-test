package test.challenge.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PagingResponse <I> extends SingleResponse<I> {

//	public final I DATA_NONE = null;

	long total = 0;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(this).replaceAll("<", " ").replaceAll(">", " ");
	}

}
