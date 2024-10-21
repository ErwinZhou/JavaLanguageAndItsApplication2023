package GoBangFinal;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class ChessSound extends Thread{
	public static Clip clip;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 try
	        {
	            File musicPath = new File("C:\\Users\\zyc13\\workspace\\ADT\\src\\GoBangFinal\\下棋.wav");
	                if(musicPath.exists())
	                {
	                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
	                    clip = AudioSystem.getClip();
	                    clip.open(audioInput);
	                    FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	                    gainControl.setValue(-20.0f);//设置音量，范围为 -60.0f 到 6.0f
	                    clip.start();
	                }
	        }
	        catch(Exception ex)
	        {
	            ex.printStackTrace();
	        }

	}

}
