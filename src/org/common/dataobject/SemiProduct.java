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
	private float count;
	//半成品原材料
	private String ingredient;
	//半成品与原材料比例
	private float scale;
	
	public SemiProduct(){
		super();
	}

	public SemiProduct(String name, float count, String ingredient,
			float scale) {
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

	public float getCount() {
		return count;
	}

	public void setCount(float count) {
		this.count = count;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
