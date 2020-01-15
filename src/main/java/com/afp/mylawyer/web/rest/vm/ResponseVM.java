package com.afp.mylawyer.web.rest.vm;

/**
 * View Model for general response
 */
public class ResponseVM {
	protected APIStatus apiStatus;

	public APIStatus getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(APIStatus apiStatus) {
		this.apiStatus = apiStatus;
	}
}
