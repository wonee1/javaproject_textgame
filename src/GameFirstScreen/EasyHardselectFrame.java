package GameFirstScreen;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import easygamescreen.LoginFrame_easy;
import hardgamescreen.LoginFrame_hard;
import java.awt.Font;
import java.awt.Font;

public class EasyHardselectFrame extends JFrame {

	public EasyHardselectFrame() {
		setTitle("게임 설정");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame on exit

		setLayout(null);
		Font malangFont = new Font("한컴 말랑말랑 Regular", Font.PLAIN, 14);
		JButton easyButton = new JButton("이지 모드");// 생명 5개, 속도 느림
		easyButton.setBounds(50, 50, 150, 50);
		easyButton.setFont(malangFont); // Set the font for the button
		add(easyButton);

		JButton hardButton = new JButton("하드 모드");// 생명 3개, 속도 빠름
		hardButton.setBounds(200, 50, 150, 50);
		hardButton.setFont(malangFont); // Set the font for the button
		add(hardButton);

		// Add action listeners to the buttons
		easyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Handle easy mode selection
				System.out.println("Easy mode selected");
				LoginFrame_easy loginFrame = new LoginFrame_easy();
				setVisible(false); // Hide the current frame

			}
		});

		hardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Handle hard mode selection
				System.out.println("Hard mode selected");
				LoginFrame_hard loginFrame = new LoginFrame_hard();
				setVisible(false); // Hide the current frame

			}
		});
		setLocationRelativeTo(null); // Center the frame on the screen
		setVisible(true);

		setVisible(true);
	}
}
