package org.common.dataobject;

import java.util.List;
/**
 * 菜品DO
 * @author Yone
 *
 */
public class Dish {
	//菜品名字
	private String name;
	//所需原材料
	private List<SemiProduct> list_semis;	
	//所需调味料
	private List<Seasoning> list_seasonings;
	//点菜数量
	private int count;
	
	public Dish() {
		super();
	}

	public Dish(String name, List<SemiProduct> list_semis,
			List<Seasoning> list_seasonings) {
		super();
		this.name = name;
		this.list_semis = list_semis;
		this.list_seasonings = list_seasonings;
	}

	
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SemiProduct> getList_semis() {
		return list_semis;
	}

	public void setList_semis(List<SemiProduct> list_semis) {
		this.list_semis = list_semis;
	}

	public List<Seasoning> getList_seasonings() {
		return list_seasonings;
	}

	public void setList_seasonings(List<Seasoning> list_seasonings) {
		this.list_seasonings = list_seasonings;
	}
	
}
