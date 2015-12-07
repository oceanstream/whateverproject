package org.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.common.model.MyStatisticTableModel;
import org.common.model.MyTableModel;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * 将订单转为Excel表格
 * 
 * @author Yone
 *
 */

public class MyExcelWriter {

	public static void writeExcel(MyStatisticTableModel model){

		String[] title = {"所有的菜(份)", "半成品统计(g)", "原材料统计(g)", "调味料统计(g)" };

		//		String filePath = ".//resources/data/test.xls";

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		String filePath="C:/Users/Yone/Desktop/"+df.format(new Date())+".xls";

		WritableWorkbook wwb;

		OutputStream os;

		try {

			os = new FileOutputStream(filePath);

			wwb=Workbook.createWorkbook(os); 

			WritableSheet sheet = wwb.createSheet("统计清单", 0);  

			for(int i=0;i<title.length;i++){
				WritableFont wfont = new WritableFont(WritableFont.createFont("微软雅黑"),12,WritableFont.BOLD);   
				WritableCellFormat font = new WritableCellFormat(wfont); 
				font.setAlignment(Alignment.CENTRE);
				sheet.setColumnView( i, 30);

				Label label = new Label(i, 0, title[i],font);
				sheet.addCell(label);
			}

			WritableFont wfont = new WritableFont(WritableFont.createFont("微软雅黑"), 12 ,WritableFont.NO_BOLD);   
			WritableCellFormat font = new WritableCellFormat(wfont); 
			font.setAlignment(Alignment.CENTRE);

			for(int i=1;i<=model.getRowCount();i++){

				Label label = new Label(0,i,model.getValueAt(i-1, 0).toString(),font);
				sheet.addCell(label);

				Label label1 = new Label(1,i,model.getValueAt(i-1, 1).toString(),font);
				sheet.addCell(label1);

				Label label2 = new Label(2,i,model.getValueAt(i-1, 2).toString(),font);
				sheet.addCell(label2);

				Label label3 = new Label(3,i,model.getValueAt(i-1, 3).toString(),font);
				sheet.addCell(label3);
			}



			// 写入数据   
			wwb.write();   
			// 关闭文件   
			wwb.close();   

			os.flush();

			os.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}

	public static void writeExcel(FileOutputStream out,MyStatisticTableModel model){

		String[] title = {"所有的菜(份)", "半成品统计(g)", "原材料统计(g)", "调味料统计(g)" };

		WritableWorkbook wwb;

		try {
			wwb=Workbook.createWorkbook(out); 
			WritableSheet sheet = wwb.createSheet("统计清单", 0);  
			for(int i=0;i<title.length;i++){
				WritableFont wfont = new WritableFont(WritableFont.createFont("微软雅黑"),12,WritableFont.BOLD);   
				WritableCellFormat font = new WritableCellFormat(wfont); 
				font.setAlignment(Alignment.CENTRE);
				sheet.setColumnView( i, 30);
				Label label = new Label(i, 0, title[i],font);
				sheet.addCell(label);
			}

			WritableFont wfont = new WritableFont(WritableFont.createFont("微软雅黑"), 12 ,WritableFont.NO_BOLD);   
			WritableCellFormat font = new WritableCellFormat(wfont); 
			font.setAlignment(Alignment.CENTRE);

			for(int i=1;i<=model.getRowCount();i++){
				Label label = new Label(0,i,model.getValueAt(i-1, 0).toString(),font);
				sheet.addCell(label);

				Label label1 = new Label(1,i,model.getValueAt(i-1, 1).toString(),font);
				sheet.addCell(label1);

				Label label2 = new Label(2,i,model.getValueAt(i-1, 2).toString(),font);
				sheet.addCell(label2);

				Label label3 = new Label(3,i,model.getValueAt(i-1, 3).toString(),font);
				sheet.addCell(label3);
			}



			// 写入数据   
			wwb.write();   
			// 关闭文件   
			wwb.close();   

			out.flush();
			out.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}

	public static boolean writeExcel(FileOutputStream out, MyTableModel tm,MyStatisticTableModel sm){

		String[] title1 = {"菜名","数量","每份所需半成品(g)","每份所需调味品(g)"};
		String[] title2 = {"所有的菜(份)", "半成品统计(g)", "原材料统计(g)", "调味料统计(g)" };


		WritableWorkbook wwb;

		try {
			wwb=Workbook.createWorkbook(out); 

			WritableSheet sheet = wwb.createSheet("点菜清单", 0);

			WritableSheet sheet2 = wwb.createSheet("统计清单", 1);

			WritableFont wfont_title = new WritableFont(WritableFont.createFont("微软雅黑"),12,WritableFont.BOLD);   
			WritableCellFormat font_title = new WritableCellFormat(wfont_title); 

			WritableFont wfont_content = new WritableFont(WritableFont.createFont("微软雅黑"), 12 ,WritableFont.NO_BOLD);   
			WritableCellFormat font_content = new WritableCellFormat(wfont_content); 

			font_title.setAlignment(Alignment.CENTRE);
			font_content.setAlignment(Alignment.CENTRE);

			//写点菜清单
			for(int i=0;i<title1.length;i++){
				//				if(i!=1){
				//					sheet.setColumnView(i, 50);
				//				}else{
				//					sheet.setColumnView(i, 10);
				//				}

				switch(i){
				case 0:
					sheet.setColumnView(i, 30);
					break;
				case 1:
					sheet.setColumnView(i, 10);
					break;
				case 2:
					sheet.setColumnView(i, 50);
					break;
				case 3:
					sheet.setColumnView(i, 50);
					break;
				default:
					sheet.setColumnView(i, 30);
					break;
				}
				Label label = new Label(i, 0, title1[i],font_title);
				sheet.addCell(label);
			}

			for(int i=0;i<tm.getTableData().size();i++){

				Label label = new Label(0,i+1,tm.getValueAt(i, 0).toString(),font_content);
				sheet.addCell(label);

				Label label1 = new Label(1,i+1,tm.getValueAt(i, 1).toString(),font_content);
				sheet.addCell(label1);

				Label label2 = new Label(2,i+1,tm.getValueAt(i, 2).toString(),font_content);
				sheet.addCell(label2);

				Label label3 = new Label(3,i+1,tm.getValueAt(i, 3).toString(),font_content);
				sheet.addCell(label3);

				//				Label label4 = new Label(4,i+1,tm.getValueAt(i, 4).toString(),font_content);
				//				sheet.addCell(label4);
			}














			//写统计清单
			for(int i=0;i<title2.length;i++){
				sheet2.setColumnView( i, 30);
				Label label = new Label(i, 0, title2[i],font_title);
				sheet2.addCell(label);
			}


			for(int i=1;i<=sm.getRowCount();i++){
				Label label = new Label(0,i,sm.getValueAt(i-1, 0).toString(),font_content);
				sheet2.addCell(label);

				Label label1 = new Label(1,i,sm.getValueAt(i-1, 1).toString(),font_content);
				sheet2.addCell(label1);

				Label label2 = new Label(2,i,sm.getValueAt(i-1, 2).toString(),font_content);
				sheet2.addCell(label2);

				Label label3 = new Label(3,i,sm.getValueAt(i-1, 3).toString(),font_content);
				sheet2.addCell(label3);
			}

			wwb.write();   
			wwb.close();   
			out.flush();
			out.close();

			return true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}   
	}
}
