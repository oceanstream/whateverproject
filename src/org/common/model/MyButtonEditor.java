package org.common.model;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.common.dataobject.Dish;
import org.common.dataobject.Order;
import org.common.ui.MainUI;
import org.common.util.CommonUtil;


@SuppressWarnings("serial")
public class MyButtonEditor extends DefaultCellEditor{

	private JPanel panel;  

	private JButton btn_detail;
	
	private JButton btn_delete;  
	
	private MyDetailTableJF mdtjf;
	
	private MainUI jf;
	
	private JTable jtb;
	
	private HashMap<String,Dish> ALL_DISHES;

	public MyButtonEditor(MainUI jf,JTable table,HashMap<String,Dish> all_dishes) {
		super(new JTextField());

		
		this.jf = jf;
		
		this.jtb = table;

		this.ALL_DISHES = all_dishes;
		
		this.setClickCountToStart(1);  

		this.initButton();  

		this.initPanel();  

		this.panel.add(btn_detail);
		
		this.panel.add(this.btn_delete);  

		
	}

	private void initButton() {
		// TODO Auto-generated method stub
		this.btn_detail = new JButton();  
		this.btn_detail.setBounds(5, 5, 60, 20);
		this.btn_detail.setFont(new Font("微软雅黑",Font.PLAIN,12));
		this.btn_detail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MyButtonEditor.this.fireEditingCanceled(); 
				//查看详情
				
				if(mdtjf!=null){
					mdtjf.dispose();
				}
				
					MyTableModel model = (MyTableModel) jtb.getModel();
					
					Vector<List<String>> data = model.getTableData();
					
					Vector<List<String>> newData = new Vector<List<String>>();
					
					List<String> list = data.get(jtb.getSelectedRow());
					
					String name = list.get(0).split("×")[0];
					
					List<String> newList = new ArrayList<String>();
					
					//添加菜名
					newList.add(name);
					//添加份数为1份，即查看每份所需材料，Order中已对数据进行处理，故无需管后面几项
					//Order中处理只根据菜名和份数，即可算出所需的所有东西
					//这里直接添加菜名+份数1份，利用order可查看1份菜品所需的材料
					newList.add("1");
					
					newData.add(newList);
					
					MyTableModel newModel = new MyTableModel(jf);
					newModel.setTableData(newData);
					
					Order order = new Order(jf, newModel, ALL_DISHES);
					
					mdtjf = new MyDetailTableJF(order);
					mdtjf.setVisible(true);
					
			}
		});

		this.btn_delete = new JButton();  
		this.btn_delete.setBounds(70, 5, 60, 20);
		this.btn_delete.setFont(new Font("微软雅黑",Font.PLAIN,12));
		this.btn_delete.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MyButtonEditor.this.fireEditingCanceled(); 
				new WarningDialog(jtb).jd.show();
			}
		});

	}



	private void initPanel() {
		// TODO Auto-generated method stub
		this.panel = new JPanel();
		this.panel.setBounds(5, 5, 130, 20);
		this.panel.setLayout(null);  

	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		this.btn_detail.setText(value == null ? "" : String.valueOf(value).split(";")[0]); 
		this.btn_delete.setText(value == null ? "" : String.valueOf(value).split(";")[1]);  
		return this.panel;  
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return this.btn_delete.getText();  
	}


	class WarningDialog{
		
        JDialog jd=new JDialog(jf,"警告",true);  
        WarningDialog(final JTable table){
            jd.setSize(300,200); 
            int width = Toolkit.getDefaultToolkit().getScreenSize().width;
            int height = Toolkit.getDefaultToolkit().getScreenSize().height;
            jd.setLocation((width-300)/2,(height-200)/2);
            Container c2=jd.getContentPane();  
            c2.setLayout(null);  
            JLabel jl=new JLabel("是否删除这道菜？");
            jl.setFont(new Font("微软雅黑",Font.PLAIN,16));
            jl.setBounds(78,3,160,100);  
            JLabel jl1 = new JLabel("(修改数量请双击数量所在单元格)");
            jl1.setFont(new Font("微软雅黑",Font.PLAIN,14));
            jl1.setBounds(40,28,220,100);  
            JButton jbb=new JButton("是"); 
            jbb.setFont(new Font("微软雅黑",Font.PLAIN,16));
            jbb.setBounds(50,102,80,30);  
            
            JButton jbb1=new JButton("否"); 
            jbb1.setFont(new Font("微软雅黑",Font.PLAIN,16));
            jbb1.setBounds(150,102,80,30);  
            
            c2.add(jl);
            c2.add(jl1);
            c2.add(jbb);
            c2.add(jbb1);
            
            jbb.addActionListener(new ActionListener(){  
                @SuppressWarnings("static-access")
				@Override  
                public void actionPerformed(ActionEvent e) {  
                    
                	MyTableModel myModel = (MyTableModel) table.getModel();
                	int row = table.getSelectedRow();
                	myModel.getTableData().remove(row);
                	jf.setChange_data(true);
                	myModel.fireTableDataChanged();
                	jd.dispose();
                }  
            });
            
            jbb1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					jd.dispose();  
				}
			});
            
            jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  

        }  
	}


	class MyDetailTableJF extends JFrame{
		
		Order order;
		
		public MyDetailTableJF(Order order){
			this.order = order;
			this.setSize(new Dimension(1000,600));
			this.setLocation(CommonUtil.getCenterPointOnScreen(this));



			final MyDetailTableModel model = new MyDetailTableModel(order);

			final JTable table  = new JTable(model){
				//设置JTable填充满Jscrollpane
				@Override
				public boolean getScrollableTracksViewportWidth() {
					// TODO Auto-generated method stub
					if (autoResizeMode == AUTO_RESIZE_OFF) //
						if (this.getParent() instanceof JViewport) //
							return (((JViewport) getParent()).getWidth() > getPreferredSize().width);
					return true;
				}
			} ;

			table.setFont(new Font("微软雅黑",Font.PLAIN, 16));
			//设置不可改变表格大小
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//设置表格行不可选中
			table.setRowSelectionAllowed(false);
			//表头不可拖动
			table.getTableHeader().setReorderingAllowed(false);
			//列大小不可改变
			table.getTableHeader().setResizingAllowed(false);
			//设置列高30
			table.setRowHeight(30);

			table.getColumnModel().getColumn(0).setHeaderValue("菜名");
			table.getColumnModel().getColumn(0).setPreferredWidth(120);

			table.getColumnModel().getColumn(1).setHeaderValue("半成品每份(g)");
			table.getColumnModel().getColumn(1).setPreferredWidth(190);

			table.getColumnModel().getColumn(2).setHeaderValue("原材料每份(g)");
			table.getColumnModel().getColumn(2).setPreferredWidth(190);

			table.getColumnModel().getColumn(3).setHeaderValue("转化率");
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			
			table.getColumnModel().getColumn(4).setHeaderValue("调味料每份(g)");
			table.getColumnModel().getColumn(4).setPreferredWidth(190);
			
			

			table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));

			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			
			table.setDefaultRenderer(Object.class, render);


			JScrollPane jsp = new JScrollPane(table);

			JPanel jpanel = new JPanel();
			JButton jbt = new JButton("确定");
			
			jbt.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(mdtjf!=null){
						mdtjf.dispose();
					}
				}
			});
			jpanel.add(jbt);

			this.getContentPane().add(jsp);
			this.getContentPane().add(jpanel);



			GridBagLayout layout = new GridBagLayout();
			this.getContentPane().setLayout(layout);


			GridBagConstraints s= new GridBagConstraints();

			s.fill = GridBagConstraints.BOTH;

			s.gridx= 0;
			s.gridy =0;
			s.gridwidth = 1;
			s.gridheight = 1;
			s.weightx = 1;
			s.weighty = 96;
			s.ipadx = 600;
			s.ipady = 550;

			layout.setConstraints(jsp, s);


			s.gridx= 0;
			s.gridy =1;
			s.gridwidth = 1;
			s.gridheight = 1;
			s.weightx = 1;
			s.weighty = 4;
			s.ipadx = 600;
			s.ipady = 50;

			layout.setConstraints(jpanel, s);
		}
		
		
	}
	
}
