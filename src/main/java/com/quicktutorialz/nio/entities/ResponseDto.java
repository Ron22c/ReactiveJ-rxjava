package com.quicktutorialz.nio.entities;

import java.util.Date;

/**
 * @author alessandroargentieri
 *
 * this pojo is used to wrap the HTTP generic response
 */
public class ResponseDto {
    private int status;
    private Object response;
    private Date datetime;

    public ResponseDto(){}

    public ResponseDto(int status, Object response) {
        this.status = status;
        this.response = response;
        this.datetime = new Date();
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
 
}
