package GameFirstScreen;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import easygamescreen.InformationFrame;

public class FirstFrame extends JFrame {

	private Clip clip;
	private FirstPanel firstPanel = new FirstPanel(clip);

	public FirstFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");// 이모티콘
		setIconImage(img);

		setTitle("수룡이 타자 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 700);

		setLocationRelativeTo(null);
		getContentPane().add(firstPanel);
		makeMenu();
		setResizable(false);
		setVisible(true);
	}

	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
	}

	private class InformationAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InformationFrame informationFrame = new InformationFrame();
		}// 게임 설명
	}
}
