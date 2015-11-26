package org.common.model;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.common.util.CommonUtil;

public class MyDialog {

	public JDialog jd;

	public MyDialog(String title, String text, JFrame parent){

		jd = new JDialog(parent,title,true);

		jd.setSize(300,200);

		jd.setLocation(CommonUtil.getCenterPointOnScreen(jd));

		Container c2=jd.getContentPane(); 

		c2.setLayout(null);  

		JLabel jl=new JLabel(text);

		jl.setFont(new Font("微软雅黑",Font.PLAIN,16));

		jl.setBounds(105,3,160,100);

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
