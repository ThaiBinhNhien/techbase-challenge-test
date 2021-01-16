package test.challenge.response;

import lombok.Data;

@Data
public class BaseResponse {

	public static final Integer RESPONSE_OK = 200;

	public static final String MSG_NONE = "";
	
	private Boolean success = Boolean.FALSE;
	private Integer code = RESPONSE_OK;
	private String message;
	
}
