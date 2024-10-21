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
	private static PVPanel instance;//单例模式
	public static PVPanel getInstance() {//懒加载
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
	};//私有的无参构造函数，保证别人无法在外部构造一个PVPanel对象，更好地实现单例模式
	
	private JButton startButton=null;
	private JButton conButton=null;
	private JTextField IP=null;
	
	
	
	public JButton  getStartButton(){//懒加载
		if(startButton==null){
			startButton=new JButton("开始监听");
			startButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Controllor.getInstance().beginListen();
					startButton.setEnabled(false);//防止进行监听后再点端口已经被占用而产生错误，因此将按钮设为失效
					}
			});
		}
		return startButton;
	}
	
	public JButton  getConButton(){//懒加载
		if(conButton==null){
			conButton=new JButton("连接服务器");
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
	public JTextField getIP(){//懒加载
		if(IP==null){
			IP=new JTextField(20);
			IP.setText("localhost");
		}
		return IP;
	}
	

}
