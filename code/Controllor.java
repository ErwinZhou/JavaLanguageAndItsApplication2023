package GoBangFinal;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;

public class Controllor {
	private static Controllor instance;//单例模式
	public static Controllor getInstance() {//懒加载
		if(instance==null){
			instance=new Controllor();
		}
		return instance;
	}
	
	private Controllor(){};//私有的构造函数

    private boolean PVP_Mode=true;//PVP网络对战模式，默认为否
    
    private boolean P1_setLocked=false;//当P1已经下好棋后，在对方P2下棋前不允许再下，进行锁定操作
	
	
	private int curP1Color=Model.BLACK;//PVE模式当前棋子颜色,也就是该哪个颜色下了，同时也是PVP模式当前P1棋子颜色
	private int curP2Color=Model.WHITE;//PVP模式当前P2棋子颜色
	
	public void setP1Color(int color){
		curP1Color=color;
	}
	public void setP2Color(int color){
		curP2Color=color;
	}
	public int getP1Color(){
		return curP1Color;
	}
	public int getP2Color(){
		return curP2Color;
	}
	public void PVPWhoGoesFirst(int player){//PVP模式谁先先手下棋
		if(player==1){//若为P1先手，则P1先不锁定
			ifP1_setLocked(false);
		}
		else if(player==2){//若为P2先手，则P1锁定
			ifP1_setLocked(true);
		}
	}
	
	
	public void if_PVP_Mode(boolean choice){//由用户选择的是否进行PVP模式
		PVP_Mode=choice;
	}
	public boolean getPVP_Mode(){
		return PVP_Mode;
	}

	public void ifP1_setLocked(boolean turn){
		P1_setLocked=turn;
	}
	
	public void PVEWhoGoFirst(int color){//PVE模式即本地下棋中，决定谁先开始的函数
		if(color==Model.BLACK){
			curP1Color=Model.BLACK;
		}
		else
			if(color==Model.WHITE){
				curP1Color=Model.WHITE;
			}
			else {//若即不为黑棋也不为白棋
				JOptionPane.showMessageDialog(null, "哥们咱玩的是五子棋，就黑白两色（shai)😓","本地先手选择错误",JOptionPane.ERROR_MESSAGE);
			}
		
	}
	public void putChess(int cur_row, int cur_col) {
		if(PVP_Mode){//PVP模式
		putP1Chess(cur_row, cur_col);
		
	}else{//如果不为PVP模式，即PVE的单机模式
		putPVEChess(cur_row,cur_col);
		}
	}

	private void putP1Chess(int cur_row, int cur_col) {
		if(!P1_setLocked){//若没锁定，证明该我方下棋了
		boolean if_success=Model.getInstance().putChess(cur_row, cur_col, curP1Color);
		if(if_success){
			ChessPanel.getInstance().repaint();//刷新界面
			ChoicePanel.getInstance().getRegretButton().setEnabled(true);
			TCP.getInstance().sendChess(cur_row, cur_col);//ChessPanel中通过鼠标算出来的棋子横纵坐标发送给TCP
			P1_setLocked=true;//下完棋后进行锁定
			int Winner=Model.getInstance().whoWin();
			if(Winner==Model.BLACK){
				JOptionPane.showMessageDialog(null, "PVP:恭喜你黑方胜出！对面输的彻彻底底~");//若本方下完棋有人胜出，一定是本方胜出，所以本方播放胜利隐喻
				new VictoryMusic().start();
			}
			else if(Winner==Model.WHITE){
				JOptionPane.showMessageDialog(null, "PVP:恭喜你白方胜出！对面输的彻彻底底~");
				new VictoryMusic().start();
			}
			
		}
	}
		else{//若锁定,则对操作者进行提示
			JOptionPane.showMessageDialog(null, "哥们别偷偷下啊，我这可都能看到","下棋操作错误",JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
	
	public void putP2Chess(int cur_row,int cur_col){
		boolean if_success=Model.getInstance().putChess(cur_row, cur_col, curP2Color);
		if(if_success){
			ChessPanel.getInstance().repaint();//刷新界面
			P1_setLocked=false;//这时候不再锁定
			int Winner=Model.getInstance().whoWin();
			if(Winner==Model.BLACK){
				JOptionPane.showMessageDialog(null, "PVP:恭喜对面黑方胜出！你输的彻彻底底~");
				new FailMusic().start();//若刚下完对面的棋便有人胜出，一定是对面胜出，则本方播放失败音乐
			}
			else if(Winner==Model.WHITE){
				JOptionPane.showMessageDialog(null, "PVP:恭喜对面白方胜出！你输的彻彻底底~");
				new FailMusic().start();//若刚下完对面的棋便有人胜出，一定是对面胜出，则本方播放失败音乐
			}
		}
		
	}
	private void putPVEChess(int cur_row, int cur_col) {
		boolean if_success=Model.getInstance().putChess(cur_row, cur_col, curP1Color);
		if(if_success){
			curP1Color=-curP1Color;//颜色翻转，该另一种颜色下棋了
			ChessPanel.getInstance().repaint();//刷新界面
			int Winner=Model.getInstance().whoWin();
			if(Winner==Model.BLACK){
				JOptionPane.showMessageDialog(null, "PVE:恭喜黑方胜出！");
				new VictoryMusic().start();
			}
			else if(Winner==Model.WHITE){
				JOptionPane.showMessageDialog(null, "PVE:恭喜白方胜出！");
				new VictoryMusic().start();
			}
		}
	}
	public void PVERegret(){
		boolean if_success=Model.getInstance().regret();
		if(if_success){
			curP1Color=-curP1Color;
		}
		else{
			JOptionPane.showMessageDialog(null, "哥们悔棋时候你也得先下过棋才行吧","悔棋操作错误",JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
	public void P1Regret(){
		boolean if_success=Model.getInstance().regret();
		if(if_success){
			P1_setLocked=false;
			TCP.getInstance().regretOperation();
		}
		else{
			JOptionPane.showMessageDialog(null, "哥们悔棋时候你也得先下过棋才行吧","悔棋操作错误",JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
	public void P2Regret(){
		boolean if_success=Model.getInstance().regret();
		if(if_success){
			P1_setLocked=true;
		}
		else{
		JOptionPane.showMessageDialog(null, "哥们悔棋时候你也得先下过棋才行吧","悔棋操作错误",JOptionPane.WARNING_MESSAGE);
		return;
	}
}
	public void beginListen() {
		TCP.getInstance().startListen();
		
	}

	public void startConnection(String ip) {
		curP1Color=Model.WHITE;
		curP2Color=Model.BLACK;
		P1_setLocked=true;
		TCP.getInstance().startConnection(ip);
	}

	public void playMusic() {
		new BackgroundMusic().start();
	}

	public void stopMusic() {
		BackgroundMusic.clip.stop();
	}

	public void PVErestart(){
		Model.getInstance().clear();
		new choseColorDialog();
	}
	public void P1restart() {
		Model.getInstance().clear();
		new choseColorDialog();	
		
	}
	public void P2restart(int curP1Color,int curP2Color){
		Model.getInstance().clear();
		setP1Color(curP1Color);
		setP2Color(curP2Color);
		if(curP1Color==Model.BLACK)
		{
			JOptionPane.showMessageDialog(null, "对面已经重新开局，你的颜色是黑色");
		}
		else{
			JOptionPane.showMessageDialog(null, "对面已经重新开局，你的颜色是白色");
		}
	}

	public void startChat() throws IOException {
	new Thread(){
		public void run() {
			try {
				new ChatDialog();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}.start();
	TCP.getInstance().sendChat();
	     
	}

	public void P2JoinChatRoom() throws IOException {
		new Thread(){
			public void run() {
				try {
					new P2ChatDialog();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	public void playRushMusic() {
		new RushMusic().start();
	}

	public void playJoyMusic() {
		new JoyMusic().start();
	}

	public void playNoMusic() {
		new NoMusic().start();
	}
	
}
