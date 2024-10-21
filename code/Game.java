package GoBangFinal;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game {
	public static void main(String[] args) {
		startFrame startjf=new startFrame();
		GameFrame.getInstance().setTitle("GoBang FinalVersion");
		GameFrame.getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameFrame.getInstance().getContentPane().setLayout(new BorderLayout());
		GameFrame.getInstance().getContentPane().add(PVPanel.getInstance(),BorderLayout.NORTH);
		GameFrame.getInstance().getContentPane().add(ChessPanel.getInstance(),BorderLayout.CENTER);
		GameFrame.getInstance().getContentPane().add(ChoicePanel.getInstance(),BorderLayout.EAST);
		GameFrame.getInstance().setSize(1000, 1000);
		GameFrame.getInstance().setLocationRelativeTo(null);
		}
		

}

