package org.common.dataobject;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.common.model.MyTableModel;
import org.common.util.CommonUtil;

public class Order {

	//创建时间，唯一
	private String time;
	
	//点菜数量
	private List<Dish> list_dish;
	
	//所有的半成品
	private List<SemiProduct> list_semi;
	
	//所有的原材料
	private List<Ingredient> list_ingre;
	
	//所有的调味料
	private List<Seasoning> list_sea;
	
	private MyTableModel model;

	@SuppressWarnings("unused")
	private HashMap<String,Dish> ALL_DISHES ;

	public WarningDialog dialog;
	
	private JFrame jf;
	
	
	
	
	
	public WarningDialog getDialog() {
		return dialog;
	}


	public void setDialog(WarningDialog dialog) {
		this.dialog = dialog;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public List<Dish> getList_dish() {
		return list_dish;
	}


	public void setList_dish(List<Dish> list_dish) {
		this.list_dish = list_dish;
	}


	public List<SemiProduct> getList_semi() {
		return list_semi;
	}


	public void setList_semi(List<SemiProduct> list_semi) {
		this.list_semi = list_semi;
	}


	public List<Ingredient> getList_ingre() {
		return list_ingre;
	}


	public void setList_ingre(List<Ingredient> list_ingre) {
		this.list_ingre = list_ingre;
	}


	public List<Seasoning> getList_sea() {
		return list_sea;
	}


	public void setList_sea(List<Seasoning> list_sea) {
		this.list_sea = list_sea;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public Order(JFrame jf,MyTableModel model, HashMap<String,Dish> ALL_DISHES){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.time = df.format(new Date());
		this.jf = jf;
		this.model = model;
		this.ALL_DISHES = ALL_DISHES;
		
		Vector<List<String>> data = this.model.getTableData();
	
		list_dish = new ArrayList<Dish>();
		list_semi = new ArrayList<SemiProduct>();
		list_ingre = new ArrayList<Ingredient>();
		list_sea = new ArrayList<Seasoning>();
		
		if(data.size()!=0){
			//表中有数据
			for(int i=0;i<data.size();i++){
				List<String> list = data.get(i);
				Dish dish = new Dish();
				dish.setName(list.get(0));
				dish.setCount(Integer.parseInt(list.get(1)));
				list_dish.add(dish);
			}
			for(Dish d : list_dish){
				Dish target = ALL_DISHES.get(d.getName());
				List<SemiProduct> list_1 = target.getList_semis();
				List<Seasoning> list_2 = target.getList_seasonings();
	 			for(int i=0;i<list_1.size();i++){
					int k = CommonUtil.SemiContainedInTheList(list_semi, list_1.get(i).getName());
	 				if(k!=-1){
						//list_semi中已经有这份半成品了，则将数量相加
						float count = list_semi.get(k).getCount() + (list_1.get(i).getCount())*(d.getCount());
						list_semi.get(i).setCount(count);
						//原材料数量相加
						float count4 = list_ingre.get(i).getCount() + (list_1.get(i).getCount()/list_1.get(i).getScale())*d.getCount();
						list_ingre.get(i).setCount(count4);
					}else{
						//list_semi中没有这份半成品，添加
						SemiProduct semi = new SemiProduct();
						semi.setName(list_1.get(i).getName());
						float count2 = list_1.get(i).getCount()*d.getCount();
						semi.setCount(count2);
						
						//转化率
						semi.setScale(list_1.get(i).getScale());
						
						list_semi.add(semi);
						
						/**
						 * 一份原材料可能做成多份半成品，所以添加了不同的半成品还是要检测原材料是否已添加
						 * 
						 */
						int j = CommonUtil.IngreContainedInTheList(list_ingre, list_1.get(i).getIngredient());
						if(j!=-1){
								float count = list_ingre.get(j).getCount() + list_1.get(i).getCount()/list_1.get(i).getScale() * d.getCount();
								list_ingre.get(j).setCount(count);
						}else{
							Ingredient ingre = new Ingredient();
							ingre.setName(list_1.get(i).getIngredient());
							float count3 = (list_1.get(i).getCount()/list_1.get(i).getScale())*d.getCount();
							ingre.setCount(count3);
							list_ingre.add(ingre);
							
						}
					}
				}
	 			
	 			for(int i=0;i<list_2.size();i++){
	 				int result = CommonUtil.SeaContainedInTheList(list_sea, list_2.get(i).getName());
	 				if(result !=-1){
	 					//list_sea中已包含该调味料，则修改数量
	 					float count2 = list_sea.get(result).getCount() + list_2.get(i).getCount()*d.getCount();
	 					list_sea.get(i).setCount(count2);
	 				}else{
	 					Seasoning sea = new Seasoning();
	 					sea.setName(list_2.get(i).getName());
	 					float count = list_2.get(i).getCount()*d.getCount();
	 					sea.setCount(count);
	 					list_sea.add(sea);
	 				}
	 			}
			}
		}else{
			//表中无数据
			dialog = new WarningDialog();
			dialog.jd.show();
		}		
	}
	
	public String printDish(){
		String s = "";
		for(int i=0;i<list_dish.size();i++){
			s = s + list_dish.get(i).getName() + "×" +list_dish.get(i).getCount()+"\n";
		}
		return s;
	}
	
	public String printSemi(){
		String s ="";
		for(int i=0;i<list_semi.size();i++){
			s = s + list_semi.get(i).getName() + "×" +CommonUtil.formatFloat(list_semi.get(i).getCount())+"\n";
		}
		return s;
	}
	
	public String printIngre(){
		String s ="";
		for(int i=0;i<list_ingre.size();i++){
			s = s + list_ingre.get(i).getName() + "×" +CommonUtil.formatFloat(list_ingre.get(i).getCount())+"\n";
		}
		return s;
	}
	
	public String printSea(){
		String s ="";
		for(int i=0;i<list_sea.size();i++){
			s = s + list_sea.get(i).getName() + "×" +CommonUtil.formatFloat(list_sea.get(i).getCount())+"\n";
		}
		return s;
	}	
	
	class WarningDialog{
		
		JDialog jd=new JDialog(jf,"警告",true);  
	 
		WarningDialog(){ 
	        jd.setSize(300,200); 
	        jd.setLocation(CommonUtil.getCenterPointOnScreen(jd));
	        Container c2=jd.getContentPane();  
	        c2.setLayout(null);  
	        JLabel jl=new JLabel("请双击左侧菜单添加数据！");
	        jl.setFont(new Font("微软雅黑",Font.PLAIN,16));
	        jl.setBounds(60,3,200,120);  
	        JButton jbb=new JButton("确定"); 
	        jbb.setFont(new Font("微软雅黑",Font.PLAIN,16));
	        jbb.setBounds(100,102,80,30);  
	        c2.add(jl);  
	        c2.add(jbb);  
	        jbb.addActionListener(new ActionListener(){  
	            @Override  
	            public void actionPerformed(ActionEvent e) {  
	                jd.dispose();  
	            }  
	        });  
	        jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
	    }  
	}
	
}


