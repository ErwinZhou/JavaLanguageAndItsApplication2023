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
	private static  ChessPanel instance;//����ģʽ
	public static ChessPanel getInstance() {//������
		if(instance==null){
			instance=new ChessPanel();
		}
		return instance;
	}
	
	private int blankGap=50;//��������������Ŀ��
	private int grid=10;//ÿ����λ��Ŀ��
	private int px=5;//���̵����Ͻǵ�x����
	private int py=5;//���̵����Ͻǵ�y����
	
	private Image blackChess=(Image)new ImageIcon("C:\\Users\\zyc13\\workspace\\ADT\\src\\GoBangFinal\\blackChess1.png").getImage();//����ͼƬ
	private Image whiteChess=(Image)new ImageIcon("C:\\Users\\zyc13\\workspace\\ADT\\src\\GoBangFinal\\whiteChess1.png").getImage();//����ͼƬ
	private Image background=(Image)new ImageIcon("C:\\Users\\zyc13\\workspace\\ADT\\src\\GoBangFinal\\backgroundColor.png").getImage();//����ͼƬ
	
	private ChessPanel(){
		addComponentListener(new ComponentAdapter(){//���ƿ��Լ�������ı��С
			@Override
			public void componentResized(ComponentEvent e) {//ʹ�ÿ���ͨ��������������Ӧ�ظı䴰���е����̵�ÿһ����Ĵ�С��ʹ���̿�����Զ����
				super.componentResized(e);
				int width=getWidth();
				int height=getHeight();
				int length=width<height?width:height;//�������㣬ѡ��������н�С��һ��
			    grid=(length-2*blankGap)/(Model.WIDTH-1);//�õ�ÿ����λ��Ŀ��
				px=(width-(Model.WIDTH-1)*grid)/2;
				py=(height-(Model.WIDTH-1)*grid)/2;
				repaint();//ÿ�θı䴰���Сt�󣬵���repaint�ػ�����
				
			}
		});
	    addMouseListener(new MouseAdapter(){
	    	@Override
	    	public void mousePressed(MouseEvent e) {//ʵ�ֵ��λ�ý�������
	    		new HesitatingMusic().start();
	    		int cur_row,cur_col;
	    		if((e.getX()-px)%grid>=0.5*grid){//�������Ķ�Ӧ�����,��λ�ô��ڵ�Ԫ���ȵ�һ�룬Ӧ����������ͽ�
	    			cur_col=(e.getX()-px)/grid+1;
	    		}
	    		else{
	    			cur_col=(e.getX()-px)/grid;
	    		}
	    		if((e.getY()-py)%grid>=0.5*grid){//�������Ķ�Ӧ�����,��λ�ô��ڵ�Ԫ���ȵ�һ�룬Ӧ����������ͽ�
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
	};//˽�е��޲ι��캯������֤�����޷����ⲿ����һ��ChessPanel���󣬸��õ�ʵ�ֵ���ģʽ
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(background,px,py,grid*(Model.WIDTH-1),grid*(Model.WIDTH-1),this);
		drawPanel(g);//ʹpaintComponent������Ӿ۽������Ӽ��
		try {
			drawChess(g);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	private void drawChess(Graphics g)throws Exception {
		Model model=Model.getInstance();//����ģʽ�ļ�
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
	private void drawPanel(Graphics g) {//���ƽ���
		g.setColor(Color.black);
		for(int i=0;i<Model.WIDTH;i++){
			g.drawLine(px, py+grid*i, px+grid*(Model.WIDTH-1), py+grid*i);
			g.drawLine(px+grid*i, py, px+grid*i, py+grid*(Model.WIDTH-1));
			
		}

		
	}

}
