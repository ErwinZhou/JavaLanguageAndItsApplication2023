package GoBangFinal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PVPanel extends JPanel{
	private static PVPanel instance;//����ģʽ
	public static PVPanel getInstance() {//������
		if(instance==null){
			instance=new PVPanel();
		}
		return instance;
	}
	
	private PVPanel(){
		setLayout(new FlowLayout());
		add(getStartButton());
		add(getIP());
		add(getConButton());
	};//˽�е��޲ι��캯������֤�����޷����ⲿ����һ��PVPanel���󣬸��õ�ʵ�ֵ���ģʽ
	
	private JButton startButton=null;
	private JButton conButton=null;
	private JTextField IP=null;
	
	
	
	public JButton  getStartButton(){//������
		if(startButton==null){
			startButton=new JButton("��ʼ����");
			startButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Controllor.getInstance().beginListen();
					startButton.setEnabled(false);//��ֹ���м������ٵ�˿��Ѿ���ռ�ö�����������˽���ť��ΪʧЧ
					}
			});
		}
		return startButton;
	}
	
	public JButton  getConButton(){//������
		if(conButton==null){
			conButton=new JButton("���ӷ�����");
			conButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String ip=IP.getText();
					Controllor.getInstance().startConnection(ip);
				}
			});
		}
		return conButton;
	}
	public JTextField getIP(){//������
		if(IP==null){
			IP=new JTextField(20);
			IP.setText("localhost");
		}
		return IP;
	}
	

}
