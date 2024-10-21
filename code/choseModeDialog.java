package GoBangFinal;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class choseModeDialog extends JDialog{
	private JButton PVEButton=null;
	private JButton PVPButton=null;
	private JLabel choseLabel=new JLabel("��ѡ������Ҫ������ģʽ��");
	public JButton getPVEButton(){
		if(PVEButton==null){
			PVEButton=new JButton("PVEģʽ");
			Font f=new Font("����",Font.BOLD,20);
			PVEButton.setFont(f);
			PVEButton.setBounds(50,400, 150, 150);
			PVEButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Controllor.getInstance().if_PVP_Mode(false);
					GameFrame.getInstance().setVisible(true);
					dispose();
				}
			});
		}
		return PVEButton;
	}
	public JButton getPVPButton(){
		if(PVPButton==null){
			PVPButton=new JButton("PVPģʽ");
			Font f=new Font("����",Font.BOLD,20);
			PVPButton.setFont(f);
			PVPButton.setBounds(400, 400, 150, 150);
			PVPButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Controllor.getInstance().if_PVP_Mode(true);
					GameFrame.getInstance().setVisible(true);
					dispose();
				}
			});
		}
		return PVPButton;
	}
	public choseModeDialog(){
		setLayout(null);
		setSize(600,600);
		setTitle("ģʽѡ��");
		setLocationRelativeTo(null);//�ѽ����������м�
		choseLabel.setBounds(185,100, 300, 100);
		Font f=new Font("����",Font.PLAIN,20);
		choseLabel.setFont(f);
		add(choseLabel);
		add(getPVEButton());
		add(getPVPButton());
		setVisible(true);
	}

}
