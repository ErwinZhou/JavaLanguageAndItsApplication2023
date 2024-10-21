package GoBangFinal;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;






public class Model {
	private static  Model instance;//����ģʽ
	public static Model getInstance() {//������
		if(instance==null){
			instance=new Model();
		}
		return instance;
	}
	private Model(){};//˽�е��޲ι��캯������֤�����޷����ⲿ����һ��Model���󣬸��õ�ʵ�ֵ���ģʽ
	//�ȶ���ģ�͵��������ԣ����ӡ����̡�����ļ�¼
	public static final int WHITE=1;
	public static final int BLACK=-1;//���ɫ����Ϊ�෴����Ŀ����Ϊ�˽�����ɫ��תʱ��ֻ�ý������ֵ�ȡ���������
	public static final int SPACE=0;//�����Ͷ����������͵�����
	
	public static final int WIDTH=29;//�涨���̵Ŀ��
	 
	private int[][] chessBoard=new int[WIDTH][WIDTH];//�ö�ά���������������
	
	
	public LinkedList<Chess> chessRecord=new LinkedList<Chess>();//����һ��LinkedList�����洢����ļ�¼
	
	public boolean putChess(int row,int col,int color){//���庯��
		if(row<0||row>=WIDTH||col<0||col>=WIDTH){
			JOptionPane.showMessageDialog(null, "�������λ�ò��Ϸ������������¡�","����λ�ô���",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(chessBoard[row][col]!=SPACE){
			JOptionPane.showMessageDialog(null, "�������λ���Ѿ�������ռ����~(������)~*�����������¡�","����λ�ô���",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		chessBoard[row][col]=color;
		Chess lastChess=new Chess(row,col,color);
		chessRecord.add(lastChess);//�����µ�������¼�����β��
		return true;
	}
	
	public int getChess(int row,int col){//���庯��
		if(row<0||row>=WIDTH||col<0||col>=WIDTH){
			return SPACE;
		}
		return chessBoard[row][col];
	}
	
	public int whoWin(){//�ж���Ӯ����
		int cur_count=1;
		int last_row=chessRecord.getLast().getRow();
		int last_col=chessRecord.getLast().getCol();
		int last_color=chessRecord.getLast().getColor();
		for(int i=last_row-1;i>=0;i--){//ͳ��ͬһ����û�����������ɫ����
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
		for(int i=last_col-1;i>=0;i--){//ͳ��ͬһ����û�����������ɫ����
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
		for(int i=last_row-1,j=last_col-1;i>=0&&j>=0;i--,j--){//ͳ�����Խ��ߣ������ϵ����£���û����������ɫ����
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
		for(int i=last_row-1,j=last_col+1;i>=0&&j<WIDTH;i--,j++){//ͳ�Ƹ��Խ��ߣ������ϵ����£���û����������ɫ����
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
		return SPACE;//��û���κ���ʤ���򷵻�һ��SPACE
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
		if(chessRecord.size()==0){//������û�¹���
			return false;
		}
		Chess lastChess=chessRecord.peekLast();
		chessBoard[lastChess.getRow()][lastChess.getCol()]=SPACE;
		ChessPanel.getInstance().repaint();
		return true;
	}
	
	
	

}
