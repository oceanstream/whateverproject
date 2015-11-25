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
	private float count;
	
	
	
	public Seasoning() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seasoning(String name, float count) {
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

	public float getCount() {
		return count;
	}
	
	public void setCount(float count) {
		this.count = count;
	}
}
