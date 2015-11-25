package org.common.model;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.common.ui.MainUI;


@SuppressWarnings("serial")
public class MyButtonEditor extends DefaultCellEditor{

	private JPanel panel;  

	private JButton button;  
	
	private MainUI jf;
	
	private JTable jtb;

	public MyButtonEditor(MainUI jf,JTable table) {
		super(new JTextField());

		this.jf = jf;
		
		this.jtb = table;
		
		this.setClickCountToStart(1);  

		this.initButton();  

		this.initPanel();  

		this.panel.add(this.button);  

	}

	private void initButton() {
		// TODO Auto-generated method stub
		this.button = new JButton();  
		this.button.setBounds(6, 6, 80, 40);
		this.button.setFont(new Font("微软雅黑",Font.PLAIN,16));
		this.button.addActionListener(new ActionListener() {

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
		this.panel.setLayout(null);  

	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		this.button.setText(value == null ? "" : String.valueOf(value));  
		return this.panel;  
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return this.button.getText();  
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


}
