package org.common.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.common.dataobject.Dish;
import org.common.dataobject.Seasoning;
import org.common.dataobject.SemiProduct;
import org.common.model.MyButtonEditor;
import org.common.model.MyTableModel;
import org.common.util.CommonUtil;
import org.common.util.XmlUtil;



@SuppressWarnings("serial")
public class MainUI extends JFrame{

	private JMenuBar mmb;

	private JMenu jm1,jm2,jm3;

	//全局配置文件
	private Properties props;

	//存储全部菜单数据的列表
	private HashMap<String,Dish> ALL_DISHES ;

	//全部菜单名称
	private List<String> dish_names ;


	public static boolean change_data = false;

	private JTable jtb;

	private String selection="";


	
	
	
	public static boolean getChange_data() {
		return change_data;
	}

	public static void setChange_data(boolean change_data) {
		MainUI.change_data = change_data;
	}

	private void InitData(){

		//从菜单文件读取
		File file = new File(".//resources/xmls/Dishes.xml");
		try {

			//获取所有菜单信息
			ALL_DISHES = XmlUtil.getDishes(file);

			dish_names = new LinkedList<String>();

			//获取菜单列表，从所有数据中遍历得到
			Iterator<Entry<String,Dish>> it = ALL_DISHES.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,Dish> entry = it.next();
				Dish dish = entry.getValue();
				dish_names.add(dish.getName());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dish_names.add("数据初始化出错！");
		}

		props = new Properties();

		try {
			//读取配置
			FileInputStream fis = new FileInputStream(".//resources/props/settings.properties");

			//加载配置文件
			props.load(fis);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  







	}

	private void InitComponent(){

		Container contentPane = this.getContentPane();
		//		contentPane.setLayout(new BorderLayout(0, 0));

		GridBagLayout layout = new GridBagLayout();
		contentPane.setLayout(layout);





		//初始化菜单栏
		initMenuBar();


		DefaultListModel<String> dlm = new DefaultListModel<String>();
		for(String s : dish_names){
			dlm.addElement(s);
		}
		final JList<String> jl = new JList<String>(dlm);


		jl.setFont(new Font("微软雅黑",Font.PLAIN, 14));
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
			}
		};
		dlcr.setPreferredSize(new Dimension(120, 50));
		dlcr.setHorizontalAlignment(SwingConstants.CENTER);
		jl.setCellRenderer(dlcr);


		//设置List中Item的监听
		jl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				if(jl.getSelectedIndex() != -1) {					
					if(e.getClickCount()==1){
						//单击事件
						change_data = false;
					}

					if(e.getClickCount() == 2){
						MyTableModel myModel = (MyTableModel) jtb.getModel();
						Vector<List<String>> data = myModel.getTableData();
						String temp = jl.getSelectedValue();

						//第一条数据必定要添加
						System.out.println(data.size());
						if(data.size()==0){
							change_data = true;
						}

						selection = temp;


						for(int i=0;i<data.size();i++){
							if(data.get(i).get(0).equals(selection)){
								int count = Integer.parseInt(data.get(i).get(1));
								data.get(i).set(1, count+1+"");
								change_data = false;
								myModel.fireTableDataChanged();
								break;
							}else{
								change_data = true;
							}
						}						

						if(change_data){
							//更新表中数据
							List<String> addData = new ArrayList<String>();
							if(selection!=""){
								Dish dish = ALL_DISHES.get(selection);
								addData.add(dish.getName());
								addData.add("1");

								//半成品
								List<SemiProduct> list = dish.getList_semis();
								String str = "";
								for(SemiProduct s : list){
									str = str+s.getName()+"（"+s.getCount()+"g）\n";
								}
								str = str.substring(0,str.length()-1);
								addData.add(str);

								//原材料
								String ingre="";
								for(SemiProduct s1 : list){
									double scale = Double.parseDouble(s1.getScale());
									double result = Double.parseDouble(s1.getCount())/scale;

									ingre = ingre+s1.getIngredient()+"（"+CommonUtil.formatDouble(result)+"g）\n";

								}
								ingre = ingre.substring(0,ingre.length()-1);

								addData.add(ingre);

								//调料
								List<Seasoning> list1 = dish.getList_seasonings();
								String sea = "";
								for(Seasoning s : list1){
									sea = sea+s.getName()+"（"+s.getCount()+"g）\n";
								}
								sea = sea.substring(0,sea.length()-1);
								addData.add(sea);

								addData.add("删除");
								data.add(addData);
								myModel.fireTableDataChanged();
							}
						}


					}
				}


			}
		});

		JScrollPane scrollpane = new JScrollPane(jl);

		TableModel tableModel = new MyTableModel(this);

		jtb = new JTable(tableModel){

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
		jtb.setFont(new Font("微软雅黑",Font.PLAIN, 12));

		//设置不可改变表格大小
		jtb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		jtb.setRowSelectionAllowed(false);
		//表头不可拖动
		jtb.getTableHeader().setReorderingAllowed(false);
		//列大小不可改变
		jtb.getTableHeader().setResizingAllowed(false);
		//设置列高50
		jtb.setRowHeight(100);

		jtb.getColumnModel().getColumn(0).setHeaderValue("菜名");
		jtb.getColumnModel().getColumn(0).setPreferredWidth(95);

		jtb.getColumnModel().getColumn(1).setHeaderValue("数量");
		jtb.getColumnModel().getColumn(1).setPreferredWidth(50);

		jtb.getColumnModel().getColumn(2).setHeaderValue("半成品(每份)");
		jtb.getColumnModel().getColumn(2).setPreferredWidth(160);

		jtb.getColumnModel().getColumn(3).setHeaderValue("原材料(每份)");
		jtb.getColumnModel().getColumn(3).setPreferredWidth(160);

		jtb.getColumnModel().getColumn(4).setHeaderValue("调味品(每份)");
		jtb.getColumnModel().getColumn(4).setPreferredWidth(160);

		jtb.getColumnModel().getColumn(5).setHeaderValue("操作");
		jtb.getColumnModel().getColumn(5).setPreferredWidth(80);

		jtb.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));

		//		DefaultTableCellRenderer render = new DefaultTableCellRenderer();

		TableCellTextAreaRenderer render = new TableCellTextAreaRenderer();

		//		jtb.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor());  

		jtb.getColumnModel().getColumn(0).setCellRenderer(render);
		jtb.getColumnModel().getColumn(1).setCellRenderer(render);
		jtb.getColumnModel().getColumn(2).setCellRenderer(render);
		jtb.getColumnModel().getColumn(3).setCellRenderer(render);
		jtb.getColumnModel().getColumn(4).setCellRenderer(render);
		jtb.getColumnModel().getColumn(5).setCellRenderer(render);


		jtb.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor(this,jtb));



		JScrollPane scrollpane1 = new JScrollPane(jtb);

		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		JButton jb = new JButton("统计");
		jpanel.add(jb,BorderLayout.CENTER);


		contentPane.add(scrollpane);
		contentPane.add(scrollpane1);
		contentPane.add(jpanel);


		GridBagConstraints s= new GridBagConstraints();

		s.fill = GridBagConstraints.BOTH;

		s.gridx= 0;
		s.gridy =0;
		s.gridwidth = 1;
		s.gridheight = 2;
		s.weightx = 2;
		s.weighty = 0;
		s.ipadx = 140;
		s.ipady = 600;

		layout.setConstraints(scrollpane, s);

		s.gridx = 1;
		s.gridy = 0;
		s.gridwidth = 1;
		s.gridheight = 1;
		s.weightx = 8;
		s.weighty = 9;
		s.ipadx = 650;
		s.ipady = 580;

		layout.setConstraints(scrollpane1, s);

		s.gridx = 1;
		s.gridy =1;
		s.gridwidth = 1;
		s.gridheight = 1;
		s.weightx = 0;
		s.weighty = 1;
		s.ipadx = 645;
		s.ipady = 20;

		layout.setConstraints(jpanel, s);
	}

	private void initMenuBar() {
		// TODO Auto-generated method stub
		mmb = new JMenuBar();

		Font font = new Font("微软雅黑", Font.PLAIN, 12);

		jm1 = new JMenu("文件");
		jm1.setFont(font);

		JMenuItem m1 = new JMenuItem("打开");
		m1.setFont(font);
		m1.setHorizontalAlignment(SwingConstants.CENTER);
		JMenuItem m2 = new JMenuItem("保存");
		m2.setFont(font);
		m2.setHorizontalAlignment(SwingConstants.CENTER);
		JMenuItem m3 = new JMenuItem("另存为");
		m3.setFont(font);
		m3.setHorizontalAlignment(SwingConstants.CENTER);

		jm1.add(m1);
		jm1.add(m2);
		jm1.add(m3);

		jm2 = new JMenu("选项");
		jm2.setFont(font);

		JMenuItem m4 = new JMenuItem("修改菜单");
		m4.setFont(font);
		m4.setHorizontalAlignment(SwingConstants.CENTER);
		JMenuItem m5 = new JMenuItem("修改菜谱");
		m5.setFont(font);
		m5.setHorizontalAlignment(SwingConstants.CENTER);
		JMenuItem m6 = new JMenuItem("修改调料表");
		m6.setFont(font);
		m6.setHorizontalAlignment(SwingConstants.CENTER);

		jm2.add(m4);
		jm2.add(m5);
		jm2.add(m6);

		jm3 = new JMenu("帮助");
		jm3.setFont(font);

		JMenuItem m7 = new JMenuItem("使用说明");
		m7.setFont(font);
		m7.setHorizontalAlignment(SwingConstants.CENTER);
		JMenuItem m8 = new JMenuItem("关于...");	
		m8.setFont(font);
		m8.setHorizontalAlignment(SwingConstants.CENTER);

		jm3.add(m7);
		jm3.add(m8);

		mmb.add(jm1);
		mmb.add(jm2);
		mmb.add(jm3);

	}

	public MainUI(){
		//初始化数据
		InitData();
		//初始化UI
		InitComponent();
		this.setJMenuBar(mmb);
		setTitle("原材料统计"+" v"+props.getProperty("version"));
		setSize(1000, 600);
		//设置不可改变大小
		//		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		MainUI mainUI = new MainUI();
		mainUI.setVisible(true);
	}

	class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {


		private JPanel panel;

		private JButton button;


		public TableCellTextAreaRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			this.initButton();
			this.initPanel();
			this.panel.add(this.button);
		}



		private void initPanel() {
			// TODO Auto-generated method stub
			  this.panel = new JPanel();
			  this.panel.setLayout(null);
		}



		private void initButton() {
			// TODO Auto-generated method stub
			this.button = new JButton();
			this.button.setBounds(6, 6, 80, 40);
			this.button.setFont(new Font("微软雅黑",Font.PLAIN,16));

		}



		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			// 计算当下行的最佳高度
			int maxPreferredHeight = 0;
			for (int i = 0; i < table.getColumnCount(); i++) {
				setText("" + table.getValueAt(row, i));
				setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
				maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
			}

			if (table.getRowHeight(row) != maxPreferredHeight)  // 少了这行则处理器瞎忙
				table.setRowHeight(row, maxPreferredHeight);

			setText(value == null ? "" : value.toString());
			setFont(new Font("微软雅黑", Font.PLAIN, 16));

			setMargin(new Insets(6, 6, 6, 0));

			if(column == 5){
				this.button.setText(value == null ? "" : String.valueOf(value));
				return this.panel;
			}
			return this;
		}
	}

}
