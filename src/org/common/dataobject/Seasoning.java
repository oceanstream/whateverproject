package org.common.dataobject;

/**
 * 调料DO
 * @author Yone
 *
 */
public class Seasoning {
	
	//调料名称
	private String name;
	 //调味料质量
	private String count;
	
	
	
	public Seasoning() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seasoning(String name, String count) {
		super();
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
}
