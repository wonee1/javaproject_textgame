package GameFirstScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Font;

public class FirstPanel extends JPanel {
	private JLabel titleLabel = new JLabel("수룡이 타자 게임");

	private JLabel GameStartLabel = new JLabel("게임시작");
	private JLabel GameInfo = new JLabel("게임설명");
	private JLabel copyRightLabel = new JLabel("2023 Sungshin univ");

	private Font titleFont = new Font("한컴 말랑말랑 Regular", Font.BOLD, 50);
	private Font firstFont = new Font("한컴 말랑말랑 Regular", Font.BOLD, 20);
	private Font changeFont = new Font("한컴 말랑말랑 Regular", Font.BOLD, 22);
	private Font secondFont = new Font("한컴 말랑말랑 Regular", Font.BOLD, 10);
	private ImageIcon backgroundIcon = new ImageIcon("resource/img/FirstBackground.jpg");// 첫번째 화면 이미지 첨가

	public void paintComponent(Graphics g) {
		Image originImg = backgroundIcon.getImage();
		g.drawImage(originImg, 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}

	public FirstPanel(Clip clip) {
		setLayout(null);
		titleLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.BOLD, 47));

		titleLabel.setSize(400, 100);
		titleLabel.setLocation(330, 98);
		add(titleLabel);

		GameStartLabel.setForeground(Color.BLACK);

		GameStartLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.BOLD, 20));
		GameStartLabel.setSize(200, 50);
		GameStartLabel.setLocation(450, 260);
		add(GameStartLabel); //
		GameInfo.setForeground(Color.BLACK);

		GameInfo.setFont(firstFont);
		GameInfo.setSize(200, 50);
		GameInfo.setLocation(450, 312);
		add(GameInfo);

		copyRightLabel.setFont(secondFont);
		copyRightLabel.setSize(220, 50);
		copyRightLabel.setLocation(440, 372);
		add(copyRightLabel);

		GameStartLabel.addMouseListener(new MouseAdapter() {// 게임 시작 라벨 누르면 이지하드모드 선택으로 이동
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("게임시작 클릭");

				EasyHardselectFrame settingframe = new EasyHardselectFrame();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				GameStartLabel.setFont(changeFont);
				GameStartLabel.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				GameStartLabel.setFont(firstFont);
				GameStartLabel.setForeground(Color.BLACK);
			}
		});

		GameInfo.addMouseListener(new MouseAdapter() {// 게임설명 클릭시 informationFrame으로 이동
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("게임 설명 클릭");
				InformationFrame info = new InformationFrame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				GameInfo.setFont(changeFont);
				GameInfo.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				GameInfo.setFont(firstFont);
				GameInfo.setForeground(Color.BLACK);
			}

		});
	}
}
