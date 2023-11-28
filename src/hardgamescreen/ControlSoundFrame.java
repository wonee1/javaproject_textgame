package hardgamescreen;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlSoundFrame extends JFrame {// 사운드 파일

	public ControlSoundFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");// 이모티콘
		setIconImage(img);
		setTitle("배경 음악 소리 조절");
		setSize(400, 146);
		setVisible(true);
		getContentPane().setLayout(null);

		JSlider slider = new JSlider();
		slider.setMinimum(-80);
		slider.setMaximum(6);
		slider.setFont(new Font("한컴 말랑말랑 Regular", Font.PLAIN, 14));
		slider.setPaintLabels(true);
		slider.setMinorTickSpacing(1);
		slider.setBounds(34, 46, 320, 45);

		getContentPane().add(slider);
		JLabel lblNewLabel = new JLabel("배경음악 볼륨 조절");
		lblNewLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(34, 10, 305, 26);
		getContentPane().add(lblNewLabel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		setResizable(false);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float value = (float) slider.getValue();
				FloatControl volume = (FloatControl) HardGameFrame.clip.getControl(FloatControl.Type.MASTER_GAIN);

				volume.setValue((float) value);
			}

		});
	}
}
