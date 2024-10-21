package GoBangFinal;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	private static GameFrame instance;
	public static GameFrame getInstance() {
		if(instance==null){
			instance=new GameFrame();
		}
		return instance;
	}

}
