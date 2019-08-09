package com.springboot.camel.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class TransactionMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "TransactionMessage [id=" + id + ", message=" + message + "]";
	}

	
}
