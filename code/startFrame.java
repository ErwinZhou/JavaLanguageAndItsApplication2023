package GoBangFinal;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class startFrame extends JFrame {
	private JButton startButton=null;
	private JButton exitButton=null;
	private JLabel welcomeLabel=new JLabel("欢迎来到五子棋最终版本！");
	public JButton getStartButton(){
		if(startButton==null){
			startButton=new JButton("开始游戏");
			Font f=new Font("黑体",Font.BOLD,20);
			startButton.setFont(f);
			startButton.setBounds(50,400, 150, 150);
			startButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JDialog Dialog=new choseModeDialog();
					dispose();
				}
			});
		}
		return startButton;
	}
	public JButton getExitButton(){
		if(exitButton==null){
			exitButton=new JButton("退出游戏");
			Font f=new Font("黑体",Font.BOLD,20);
			exitButton.setFont(f);
			exitButton.setBounds(400, 400, 150, 150);
			exitButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
		}
		return exitButton;
	}
	startFrame(){
		setSize(600,600);
		setLayout(null);
		setTitle("开始界面");
		setLocationRelativeTo(null);//把界面设置在中间
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeLabel.setBounds(180,100, 300,100);
		Font f=new Font("黑体",Font.PLAIN,20);
		welcomeLabel.setFont(f);
		add(welcomeLabel);
		add(getStartButton());
		add(getExitButton());
		setVisible(true);
		
		
		
	}

}
