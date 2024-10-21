package GoBangFinal;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChoicePanel extends JPanel{
	private static ChoicePanel instance;//单例模式
	public static ChoicePanel getInstance() {//懒加载
		if(instance==null){
			instance=new ChoicePanel();
		}
		return instance;
	}
	private ChoicePanel(){
		setSize(20,30);
		setLayout(new GridLayout(8,1,0,50));
		add(getRestartButton());
		add(getRegretButton());

		add(getPlayMusicButton());
		add(getStopMusicButton());
		add(getChatStartButton());
	}

	private JButton restartButton=null;
	private JButton regretButton=null;
	private JButton playMusicButton=null;
	private JButton stopMusicButton=null;
	private JButton chatStartButton=null;
	
	public JButton getRestartButton(){
		if(restartButton==null){
			restartButton=new JButton("重开");
			restartButton.setPreferredSize(new Dimension(100,10));
			restartButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(Controllor.getInstance().getPVP_Mode())
					{
						Controllor.getInstance().P1restart();
					}
					else{
						Controllor.getInstance().PVErestart();
					}
					
				}
			
		});
		}
		return restartButton;
	}
	public JButton getRegretButton(){
		if(regretButton==null){
			regretButton=new JButton("悔棋");
			regretButton.setPreferredSize(new Dimension(100,10));
			regretButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(Controllor.getInstance().getPVP_Mode()){
						Controllor.getInstance().P1Regret();
						regretButton.setEnabled(false);
					}
					else{
						Controllor.getInstance().PVERegret();
					}
				}
			});
		}
		return regretButton;
	}
	public JButton getPlayMusicButton(){
		if(playMusicButton==null){
			playMusicButton=new JButton("播放音乐");
			playMusicButton.setPreferredSize(new Dimension(100,10));
			playMusicButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Controllor.getInstance().playMusic();
				}
			});
		}
		return playMusicButton;
	}
	public JButton getStopMusicButton(){
		if(stopMusicButton==null){
			stopMusicButton=new JButton("停止音乐");
			stopMusicButton.setPreferredSize(new Dimension(100,10));
			stopMusicButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
				Controllor.getInstance().stopMusic();	
				}
			});
			
		}
		return stopMusicButton;
	}
	public JButton getChatStartButton(){
		if(chatStartButton==null){
			chatStartButton=new JButton("开始聊天");
			chatStartButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(Controllor.getInstance().getPVP_Mode()){
						try {
							Controllor.getInstance().startChat();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "哥们咱单身一个人儿就别自己和自己聊天找没快了 ","聊天操作错误",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			});
		}
		return chatStartButton;
	}
}
