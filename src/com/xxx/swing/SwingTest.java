package com.xxx.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
 
public class SwingTest extends JFrame{
	
	
	public SwingTest(){
		
	}
	
	public void init(){
		
		
		this.setTitle("zipper app");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(500,500,1000,500);
		JPanel contentPane=new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		this.setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		JLabel label=new JLabel("文件目录:");
		contentPane.add(label);
		JComboBox comboBox=new JComboBox();
		comboBox.addItem("d:/u04/");
		comboBox.addItem("c:/u04/");
		comboBox.addItem("e:/u04/");
		contentPane.add(comboBox);

		
	     JButton process = new JButton("Process");
	     process.setBounds(10, 80, 80, 25);

	     JButton cancelButton = new JButton("Cancel");
	     cancelButton.setBounds(10, 100, 80, 25);
	     
	     
	     contentPane.add(process);
	     contentPane.add(cancelButton);
	     
	     
	     final JTextArea jta = new JTextArea(10,5);
	     jta.setBounds(10, 160, 160, 80);
	     JScrollPane jsp = new JScrollPane(jta);
	     jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	     contentPane.add(jsp, BorderLayout.CENTER);
	     
	     final Thread startThread = new Thread(new ZipCompress("xxx.zip",comboBox.getSelectedItem().toString(),jta));
	     
	     process.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	             
	            	try {
	            		startThread.start();
					} catch (Exception e2) {
						System.out.println("文件压缩异常"+e2);
					}
	            	
	            	
	            }
	        });
	     
	     
	     cancelButton.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("用户取消");
					startThread.interrupt();
				} catch (Exception e2) {
					System.out.println("线程中断异常！"+e2);
				}
				
			}
		});
		this.setVisible(true);
	}
	
	
	
    public static void main(String[]args){
    	
    	SwingTest example=new SwingTest();
    	example.init();
    }
}
