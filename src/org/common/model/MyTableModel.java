package org.common.model;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import org.common.util.CommonUtil;

@SuppressWarnings("serial")
public class MyTableModel extends AbstractTableModel{

	
	//表格数据
	private Vector<List<String>> tableData;
	//表格列标题
	private Vector<String> tableTitle;
	//JFrame
	private JFrame jf;
	
	public MyTableModel(JFrame jf){
		this.jf = jf;
		tableData = new Vector<List<String>>();
		tableTitle= new Vector<String>();
		
		tableTitle.add("第一列");
		tableTitle.add("第二列");
		tableTitle.add("第三列");
		tableTitle.add("第四列");
		tableTitle.add("第五列");
		tableTitle.add("第六列");
		
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return tableTitle.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return tableData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
        List<String> LineTemp = (List<String>)this.tableData.get(rowIndex);
        return LineTemp.get(columnIndex);
	}
	
	

	@SuppressWarnings("deprecation")
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex!=5){
			if(CommonUtil.isInteger(aValue.toString())){
				tableData.get(rowIndex).set(columnIndex, (String) aValue);
				fireTableCellUpdated(rowIndex, columnIndex);
			}else{
				new WarningDialog().jd.show();
			}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex == 1||columnIndex==5){
			return true;
		}
		return false;
	}

	public Vector<List<String>> getTableData() {
		return tableData;
	}

	public void setTableData(Vector<List<String>> tableData) {
		this.tableData = tableData;
	}

	public Vector<String> getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(Vector<String> tableTitle) {
		this.tableTitle = tableTitle;
	}
	
	class WarningDialog{
        JDialog jd=new JDialog(jf,"警告",true);  
        WarningDialog(){ 
            jd.setSize(300,200); 
            jd.setLocation(CommonUtil.getCenterPointOnScreen(jd));
            Container c2=jd.getContentPane();  
            c2.setLayout(null);  
            JLabel jl=new JLabel("请输入正确的数量！");
            jl.setFont(new Font("微软雅黑",Font.PLAIN,16));
            jl.setBounds(70,3,160,100);  
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
