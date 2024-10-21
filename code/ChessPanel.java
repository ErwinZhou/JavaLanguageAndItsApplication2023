package GoBangFinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ChessPanel extends JPanel{
	private static  ChessPanel instance;//单例模式
	public static ChessPanel getInstance() {//懒加载
		if(instance==null){
			instance=new ChessPanel();
		}
		return instance;
	}
	
	private int blankGap=50;//定义棋盘与两侧的宽度
	private int grid=10;//每个单位格的宽度
	private int px=5;//棋盘的左上角的x坐标
	private int py=5;//棋盘的左上角的y坐标
	
	private Image blackChess=(Image)new ImageIcon("C:\\Users\\zyc13\\workspace\\ADT\\src\\GoBangFinal\\blackChess1.png").getImage();//黑棋图片
	private Image whiteChess=(Image)new ImageIcon("C:\\Users\\zyc13\\workspace\\ADT\\src\\GoBangFinal\\whiteChess1.png").getImage();//白棋图片
	private Image background=(Image)new ImageIcon("C:\\Users\\zyc13\\workspace\\ADT\\src\\GoBangFinal\\backgroundColor.png").getImage();//背景图片
	
	private ChessPanel(){
		addComponentListener(new ComponentAdapter(){//控制可以监听窗体改变大小
			@Override
			public void componentResized(ComponentEvent e) {//使得可以通过鼠标放缩可以相应地改变窗体中的棋盘的每一个格的大小，使棋盘可以永远居中
				super.componentResized(e);
				int width=getWidth();
				int height=getHeight();
				int length=width<height?width:height;//条件运算，选出长与宽中较小的一个
			    grid=(length-2*blankGap)/(Model.WIDTH-1);//得到每个单位格的宽度
				px=(width-(Model.WIDTH-1)*grid)/2;
				py=(height-(Model.WIDTH-1)*grid)/2;
				repaint();//每次改变窗体大小t后，调用repaint重画界面
				
			}
		});
	    addMouseListener(new MouseAdapter(){
	    	@Override
	    	public void mousePressed(MouseEvent e) {//实现点击位置进行下棋
	    		new HesitatingMusic().start();
	    		int cur_row,cur_col;
	    		if((e.getX()-px)%grid>=0.5*grid){//计算点击的对应格的列,若位置大于单元格宽度的一半，应该四舍五入就近
	    			cur_col=(e.getX()-px)/grid+1;
	    		}
	    		else{
	    			cur_col=(e.getX()-px)/grid;
	    		}
	    		if((e.getY()-py)%grid>=0.5*grid){//计算点击的对应格的行,若位置大于单元格宽度的一半，应该四舍五入就近
	    			cur_row=(e.getY()-py)/grid+1;
	    		}else{
	    			cur_row=(e.getY()-py)/grid;
	    		}
	    		
	    		Controllor.getInstance().putChess(cur_row,cur_col);
	    	}
	    	@Override
	    	public void mouseReleased(MouseEvent e) {
	    		HesitatingMusic.clip.stop();
	    		new ChessSound().start();
	    		
	    	}
	    });
	};//私有的无参构造函数，保证别人无法在外部构造一个ChessPanel对象，更好地实现单例模式
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(background,px,py,grid*(Model.WIDTH-1),grid*(Model.WIDTH-1),this);
		drawPanel(g);//使paintComponent任务更加聚焦，更加简短
		try {
			drawChess(g);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	private void drawChess(Graphics g)throws Exception {
		Model model=Model.getInstance();//单例模式的简化
		for(int row=0;row<Model.WIDTH;row++){
			for(int col=0;col<Model.WIDTH;col++){
				int color=model.getChess(row, col);
				if(color==Model.BLACK){
					g.setColor(Color.black);
					g.drawImage(blackChess,px+grid*col-grid/2, py+grid*row-grid/2, grid, grid, this);
					
				}else if(color==Model.WHITE){
					g.setColor(Color.white);
					g.drawImage(whiteChess,px+grid*col-grid/2, py+grid*row-grid/2, grid, grid, this);
				}
				
			}
		}
		
		
	}
	private void drawPanel(Graphics g) {//绘制界面
		g.setColor(Color.black);
		for(int i=0;i<Model.WIDTH;i++){
			g.drawLine(px, py+grid*i, px+grid*(Model.WIDTH-1), py+grid*i);
			g.drawLine(px+grid*i, py, px+grid*i, py+grid*(Model.WIDTH-1));
			
		}

		
	}

}
