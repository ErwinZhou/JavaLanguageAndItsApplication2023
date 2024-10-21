package GoBangFinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;



public class TCP {
	private static TCP instance;//����ģʽ
	public static TCP getInstance() {//������
		if(instance==null){
			instance=new TCP();
		}
		return instance;
	}
	
	private TCP(){
		
	};

	public static final int PORT=1206;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public void startListen(){
		new Thread(){//Ϊ�˷�ֹ��ѡ�м��������̿������������ͽ��涨Ϊ������ͬ���̻߳���Ӱ��
			public void run() {
				try {
					ServerSocket ss=new ServerSocket(PORT);
					s=ss.accept();
					in=new BufferedReader(new InputStreamReader(s.getInputStream()));//������
					out=new PrintWriter(s.getOutputStream(),true);//�����
					
					startReadThread();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	
	protected void startReadThread() {//��in���������
		new Thread(){
			public void run() {
				while(true){//��ѭ���ض�
						try {
							String line = in.readLine();
							if(line.startsWith("PutChess")){
								analyzeChessText(line);
							}
							else if(line.startsWith("Chat")){
								chatBegin();
							}
							else if(line.startsWith("Regret")){
								analyzeRegret();
							}
							else if(line.startsWith("Restart")){
								analyzeRestart(line);
							}else if(line.startsWith("RushMusic")){
								startRushMusic();
							}else if(line.startsWith("JoyMusic")){
								startJoyMusic();
							}else if(line.startsWith("NoMusic")){
								startNoMusic();
							}
						} catch (IOException e) {
							
							e.printStackTrace();
						}
				}
			}

			
		}.start();
		
	}

	public void analyzeChessText(String line) {
		line=line.substring(9);//��ȡ"PutChess:"��������ݣ�����substring����
		String[] array=line.split(",");//��,���зָ����к����������ֵ��ַ���
		int cur_row=Integer.parseInt(array[0]);
		int cur_col=Integer.parseInt(array[1]);
		Controllor.getInstance().putP2Chess(cur_row, cur_col);
	}

	public void chatBegin() throws IOException{
		Controllor.getInstance().P2JoinChatRoom();
	}
	public void analyzeRegret(){
		Controllor.getInstance().P2Regret();
	}
	public void analyzeRestart(String line){
		line=line.substring(8);
		String[] array=line.split(",");
		int curP1color=Integer.parseInt(array[0]);
		int curP2color=Integer.parseInt(array[1]);
		Controllor.getInstance().P2restart(curP1color,curP2color);
	}
	public void startRushMusic() {
		Controllor.getInstance().playRushMusic();
	}
	public void startJoyMusic() {
		Controllor.getInstance().playJoyMusic();
	}
    public void startNoMusic() {
		Controllor.getInstance().playNoMusic();
	}
	
	public void sendChess(final int cur_row,final int cur_col){//��������
		new Thread(){
			@Override
			public void run() {
				out.println("PutChess:"+cur_row+","+cur_col);//�����ڲ�������ʱ�ֲ�����ʧЧ,��row��col��Ϊfinal
			};
		}.start();
	}
	public void sendChat(){
		new Thread(){
			public void run() {
				out.println("Chat");
			};
		}.start();
	}
	
	public void regretOperation(){
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				out.println("Regret");
			};
		}.start();
	}
	public void restartOperation(final int curP1color,final int curP2color){
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				out.println("Restart:"+curP2color+","+curP1color);//��������δ���ڶ��洫������curP1colorʵ��������Ҫ���ܶ����curP2color������ע�����
			};
		}.start();
	}
	public void sendRushMusic(){
		new Thread(){
			public void run() {
				out.println("RushMusic");
			};
		}.start();
	}
	public void sendJoyMusic(){
		new Thread(){
			public void run() {
				out.println("JoyMusic");
			};
		}.start();
	}
	public void sendNoMusic(){
		new Thread(){
			public void run() {
				out.println("NoMusic");
			};
		}.start();
	}
	
	public void startConnection(String ip) {//��������
		 try {
			s=new Socket(ip,PORT);
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));//������
			out=new PrintWriter(s.getOutputStream(),true);//�����
			startReadThread();//�ͻ���ҲҪ���߳�
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
}//�ͻ��˲��õ��÷������˼������ֵĴ��룬�ͻ���Ҳ��Ҫin��out
