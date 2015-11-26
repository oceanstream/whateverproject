package org.common.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.common.dataobject.Dish;
import org.common.dataobject.Ingredient;
import org.common.dataobject.Order;
import org.common.dataobject.Seasoning;
import org.common.dataobject.SemiProduct;
import org.common.util.CommonUtil;

@SuppressWarnings("serial")
public class MyStatisticTableModel extends AbstractTableModel{

	private List<Dish> list_dish;
	
	private List<SemiProduct> list_semi;
	
	private List<Ingredient> list_ingre;
	
	private List<Seasoning> list_sea;
	
	@SuppressWarnings("unused")
	private Order order;
	
	public MyStatisticTableModel(Order order){
		this.order = order;
		this.list_dish = order.getList_dish();
		this.list_semi = order.getList_semi();
		this.list_ingre = order.getList_ingre();
		this.list_sea = order.getList_sea();
		
	}
	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return Math.max(Math.max(Math.max(list_dish.size(), list_semi.size()), list_ingre.size()), list_sea.size());
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		try{
			switch(columnIndex){
			case 0:
				return list_dish.get(rowIndex).getName() + "×" + list_dish.get(rowIndex).getCount();
			case 1:
				return list_semi.get(rowIndex).getName()+"×" + CommonUtil.formatFloat(list_semi.get(rowIndex).getCount());
			case 2:
				return list_ingre.get(rowIndex).getName() + "×" + CommonUtil.formatFloat(list_ingre.get(rowIndex).getCount());
			case 3:
				return list_sea.get(rowIndex).getName() + "×" + CommonUtil.formatFloat(list_sea.get(rowIndex).getCount());
			}		
		}catch(Exception e){
			return "";
		}
		return "";
	}

	
}
