package GoBangFinal;


import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;



public class P2ChatDialog extends JDialog{
	private TextArea chatTextArea=null;
	private JButton sendButton=null;
	private TextArea chatHistoryArea=null;
	private JComboBox<String> chatListComboBox=null;
	private String[] chatList={"快点吧，我等的花都谢了!","和你合作真是太愉快了~","要不起!"};
	
	public TextArea getChatTextArea(){
		if(chatTextArea==null){
			chatTextArea=new TextArea(10,50);
			Font f=new Font("黑体",Font.PLAIN,20);
			chatTextArea.setFont(f);
			chatTextArea.setEditable(true);
			chatTextArea.setBounds(0,0,600, 150);
		}
		return chatTextArea;
	}
	public TextArea getChatHistoryArea(){
		if(chatHistoryArea==null){
			chatHistoryArea=new TextArea(10,50);
			Font f=new Font("黑体",Font.PLAIN,20);
			chatHistoryArea.setFont(f);
			chatHistoryArea.setEditable(false);
			chatHistoryArea.setBounds(0, 400, 600, 150);
		}
		return chatHistoryArea;
	}
	public JButton getSendButton(){
		if(sendButton==null){
			sendButton=new JButton("发送");	
			Font f=new Font("黑体",Font.BOLD,20);
			sendButton.setFont(f);
			sendButton.setBounds(250, 175, 100, 50);
		}
		return sendButton;
	}
	public JComboBox<String> getChatListComboBox(){
		if(chatListComboBox==null){
			chatListComboBox=new JComboBox<String>(chatList);
			Font f=new Font("黑体",Font.PLAIN,20);
			chatListComboBox.setFont(f);
			chatListComboBox.setBounds(150, 275, 300, 50);
			chatListComboBox.addItemListener(new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if(e.getStateChange()==ItemEvent.SELECTED){
						if(chatListComboBox.getSelectedIndex()==0){
							chatTextArea.setText("快点吧，我等的花都谢了!");
							TCP.getInstance().sendRushMusic();
						}
						else if(chatListComboBox.getSelectedIndex()==1){
							chatTextArea.setText("和你合作真是太愉快了~");
							TCP.getInstance().sendJoyMusic();
						}
						else if(chatListComboBox.getSelectedIndex()==2){
							chatTextArea.setText("要不起!");
							TCP.getInstance().sendNoMusic();
							
						}
					}
				}
			});
			
		}
		return chatListComboBox;
	}
	public P2ChatDialog() throws UnknownHostException, IOException{
		setSize(600,600);
		setTitle("聊天界面");
		setLayout(null);
		add(getChatTextArea());
		add(getSendButton());
		add(getChatListComboBox());
		add(getChatHistoryArea());
		Socket s=new Socket("192.168.124.3",6666);
		final BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream())); 
		final PrintWriter out =new PrintWriter(s.getOutputStream(), true); 
		new Thread(){
			public void run() {
				String line;
				try {
					while((line=in.readLine())!=null){
						getChatHistoryArea().append("对方对你说："+line+"\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			};
		}.start();
		getChatTextArea().addTextListener(new TextListener(){
			@Override
			public void textValueChanged(TextEvent e) {
				// TODO Auto-generated method stub
				getSendButton().setActionCommand(getChatTextArea().getText());
			}
		});
		getSendButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String messageText=e.getActionCommand();
				getChatHistoryArea().append("你对对方说："+messageText+"\n");
				out.println(messageText);
			}
		});
		setVisible(true);
	}
	}



