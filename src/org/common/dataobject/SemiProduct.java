package org.common.dataobject;

/**
 * 
 * 半成品DO
 * @author Yone
 *
 */
public class SemiProduct {
	//半成品唯一名字
	private String name;
	//半成品数量
	private String count;
	//半成品原材料
	private String ingredient;
	//半成品与原材料比例
	private String scale;
	
	public SemiProduct(){
		super();
	}

	public SemiProduct(String name, String count, String ingredient,
			String scale) {
		super();
		this.name = name;
		this.count = count;
		this.ingredient = ingredient;
		this.scale = scale;
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

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
	
}
