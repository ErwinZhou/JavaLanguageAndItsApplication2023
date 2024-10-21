package GoBangFinal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class choseColorDialog extends JDialog {
	private JLabel jl=new JLabel("您想选择黑色还是白色？");
	private JButton white=null;
	private JButton black=null;
	public JButton getWhite(){
		if(white==null){
			white=new JButton("白色");
			white.setBounds(50, 150, 100, 50);
			white.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(!Controllor.getInstance().getPVP_Mode()){
					Controllor.getInstance().setP1Color(Model.WHITE);
					}
					else{
						Controllor.getInstance().setP1Color(Model.WHITE);
						Controllor.getInstance().setP2Color(Model.BLACK);
						TCP.getInstance().restartOperation(Model.WHITE,Model.BLACK);	
					}
					dispose();
				
				}
			});
		}
		return white;
	}
	public JButton getBlack(){
		if(black==null){
			black=new JButton("黑色");
			black.setBounds(250, 150, 100, 50);
			black.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(!Controllor.getInstance().getPVP_Mode()){
					Controllor.getInstance().setP1Color(Model.BLACK);
					}
					else{
						Controllor.getInstance().setP1Color(Model.BLACK);
						Controllor.getInstance().setP2Color(Model.WHITE);
						TCP.getInstance().restartOperation(Model.BLACK,Model.WHITE);	
					}
					dispose();
				}
			});
		}
		return black;
	}
	
	public choseColorDialog(){
		setSize(400,300);
		setLayout(null);
		jl.setBounds(135, 50,250, 50);
		add(jl);
		add(getWhite());
		add(getBlack());
		setTitle("颜色选择界面");
		setVisible(true);
	}
	

}
