package kr.ac.sunmoon.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Artist implements IsSerializable {
	
	private String a_id;
	private String a_name;
	
	public String getA_id() {
		return a_id;
	}
	public void setA_id(String a_id) {
		this.a_id = a_id;
	}
	public String getA_name() {
		return a_name;
	}
	public void setA_name(String a_name) {
		this.a_name = a_name;
	}

}
