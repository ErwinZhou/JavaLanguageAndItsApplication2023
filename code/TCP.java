package GoBangFinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;



public class TCP {
	private static TCP instance;//单例模式
	public static TCP getInstance() {//懒加载
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
		new Thread(){//为了防止当选中监听后棋盘卡死，将监听和界面定为两个不同的线程互不影响
			public void run() {
				try {
					ServerSocket ss=new ServerSocket(PORT);
					s=ss.accept();
					in=new BufferedReader(new InputStreamReader(s.getInputStream()));//输入流
					out=new PrintWriter(s.getOutputStream(),true);//输出流
					
					startReadThread();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	
	protected void startReadThread() {//从in里面读棋子
		new Thread(){
			public void run() {
				while(true){//死循环地读
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
		line=line.substring(9);//截取"PutChess:"往后的内容，调用substring函数
		String[] array=line.split(",");//以,进行分割，获得行和列两个数字的字符串
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
	
	public void sendChess(final int cur_row,final int cur_col){//发送棋子
		new Thread(){
			@Override
			public void run() {
				out.println("PutChess:"+cur_row+","+cur_col);//匿名内部类运行时局部变量失效,将row和col设为final
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
				out.println("Restart:"+curP2color+","+curP1color);//这里由于未来在对面传进来的curP1color实际上是需要接受对象的curP2color，这里注意调换
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
	
	public void startConnection(String ip) {//用于连接
		 try {
			s=new Socket(ip,PORT);
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));//输入流
			out=new PrintWriter(s.getOutputStream(),true);//输出流
			startReadThread();//客户端也要读线程
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
}//客户端不用调用服务器端监听部分的代码，客户端也需要in和out
