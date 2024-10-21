package GoBangFinal;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;






public class Model {
	private static  Model instance;//单例模式
	public static Model getInstance() {//懒加载
		if(instance==null){
			instance=new Model();
		}
		return instance;
	}
	private Model(){};//私有的无参构造函数，保证别人无法在外部构造一个Model对象，更好地实现单例模式
	//先定义模型的三个属性：棋子、棋盘、下棋的记录
	public static final int WHITE=1;
	public static final int BLACK=-1;//与白色设置为相反数的目的是为了进行颜色反转时候只用进行数字的取反，运算简单
	public static final int SPACE=0;//用整型定义三种类型的棋子
	
	public static final int WIDTH=29;//规定棋盘的宽度
	 
	private int[][] chessBoard=new int[WIDTH][WIDTH];//用二维整型数组代表棋盘
	
	
	public LinkedList<Chess> chessRecord=new LinkedList<Chess>();//定义一个LinkedList用来存储下棋的记录
	
	public boolean putChess(int row,int col,int color){//下棋函数
		if(row<0||row>=WIDTH||col<0||col>=WIDTH){
			JOptionPane.showMessageDialog(null, "您下棋的位置不合法捏，请重新再下。","下棋位置错误",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(chessBoard[row][col]!=SPACE){
			JOptionPane.showMessageDialog(null, "您下棋的位置已经被别人占了捏~(￣▽￣)~*，请重新再下。","下棋位置错误",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		chessBoard[row][col]=color;
		Chess lastChess=new Chess(row,col,color);
		chessRecord.add(lastChess);//将刚下的棋放入记录链表的尾部
		return true;
	}
	
	public int getChess(int row,int col){//读棋函数
		if(row<0||row>=WIDTH||col<0||col>=WIDTH){
			return SPACE;
		}
		return chessBoard[row][col];
	}
	
	public int whoWin(){//判断输赢函数
		int cur_count=1;
		int last_row=chessRecord.getLast().getRow();
		int last_col=chessRecord.getLast().getCol();
		int last_color=chessRecord.getLast().getColor();
		for(int i=last_row-1;i>=0;i--){//统计同一列有没有五个相连颜色棋子
			if(getChess(i,last_col)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}	
			}
			else
				break;
		}
		for(int i=last_row+1;i<WIDTH;i++){
			if(getChess(i,last_col)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}
			}
			else 
				break;
		}
		cur_count=1;
		for(int i=last_col-1;i>=0;i--){//统计同一行有没有五个相连颜色棋子
			if(getChess(last_row,i)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}
			}
			else
				break;
		}
		for(int i=last_col+1;i<WIDTH;i++){
			if(getChess(last_row,i)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}
			}
			else
				break;
		}
		cur_count=1;
		for(int i=last_row-1,j=last_col-1;i>=0&&j>=0;i--,j--){//统计正对角线（即左上到右下）有没有相连的颜色棋子
			if(getChess(i,j)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}
			}
			else
				break;
		}
		for(int i=last_row+1,j=last_col+1;i<WIDTH&&j<WIDTH;i++,j++){
			if(getChess(i,j)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}
			}
			else
				break;
		}
		cur_count=1;
		for(int i=last_row-1,j=last_col+1;i>=0&&j<WIDTH;i--,j++){//统计副对角线（即右上到左下）有没有相连的颜色棋子
			if(getChess(i,j)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}
			}
			else
				break;
		}
		for(int i=last_row+1,j=last_col-1;i<WIDTH&&j>=0;i++,j--){
			if(getChess(i,j)==last_color){
				cur_count++;
				if(cur_count==5){
					return last_color;
				}
			}
			else
				break;
		}
		return SPACE;//若没有任何人胜出则返回一个SPACE
	}
	
	public void clear(){
		for(int i=0;i<WIDTH;i++){
			for(int j=0;j<WIDTH;j++){
				chessBoard[i][j]=SPACE;
			}
		}
		ChessPanel.getInstance().repaint();
		chessRecord.clear();
	}
	public boolean regret() {
		if(chessRecord.size()==0){//即若还没下过棋
			return false;
		}
		Chess lastChess=chessRecord.peekLast();
		chessBoard[lastChess.getRow()][lastChess.getCol()]=SPACE;
		ChessPanel.getInstance().repaint();
		return true;
	}
	
	
	

}
