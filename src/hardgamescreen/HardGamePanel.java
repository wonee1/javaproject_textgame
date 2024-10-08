package hardgamescreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HardGamePanel extends JPanel {

	// Audio 관련 클립
	private Clip effectSoundClip; // 정답
	private Clip effectSoundClip2; // 오답.
	private Clip gameOverClip; // 게임오버
	private Clip lifeMinusClip; // 생명 감소

	private JTextField input = new JTextField(25);
	private JLabel timeLabel = new JLabel("Time: 02:00");
	private ImageIcon gameBackGround = new ImageIcon("resource/img/GameBackground.jpg");

	private Vector<TextObject> textArr = new Vector<TextObject>(50);
	private HardScorePanel scorePanel = null;
	private EditPanel editPanel = null;
	private TextSource textSource = new TextSource(); // 단어 백터 생성.
	private int number = 0;

	private int life = 3; // hard 초기 라이프

	private Vector<LifeIcon> lifeIcons = new Vector<LifeIcon>();
	private Vector<LifePanel> lifePanels = new Vector<LifePanel>();

	public int pixel = 10;
	public int speed = 20;
	public int gameLevel = 1;

	// Thread 재시작 하기 위해서 Vector를 사용.
	private int threadCount = 0;
	private Vector<RainThread> rainThreads = new Vector<RainThread>();
	private Vector<TimeThread> timeThreads = new Vector<TimeThread>();
	private Vector<AudioThread> audioThreads = new Vector<AudioThread>();
	private int threadStateFlag = 2; // set ..
	private AudioThread audioThread = new AudioThread();
	private EndGameFrame endGameFrame = new EndGameFrame();

	public void setScorePanel(HardScorePanel scorePanel) {
		this.scorePanel = scorePanel;
		System.out.println(scorePanel.getName() + " ScorePanel 만들어짐." + scorePanel.getScore());
	}

	private void saveRecord() {
		int scoreTmp = rainThreads.get(threadCount).getScore();
		String idTmp = editPanel.getId();

		System.out.println(scoreTmp + " " + idTmp);
		endGameFrame = new EndGameFrame(scoreTmp, idTmp);
	}

	private void makeThreads() {
		RainThread rainThread = new RainThread();
		TimeThread timeThread = new TimeThread();
		AudioThread audioThread = new AudioThread();

		rainThreads.add(rainThread);
		timeThreads.add(timeThread);
		audioThreads.add(audioThread);
	}

	private RainThread getRainThread() {
		return rainThreads.get(threadCount);
	}

	public TimeThread getTimeThread() {
		return timeThreads.get(threadCount);
	}

	private AudioThread getAudioThread() {
		return audioThreads.get(threadCount);
	}

	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		g.drawImage(gameBackGround.getImage(), 0, 0, d.width, d.height, null);
	}

	private String setTime(int sec) {// 시간설정
		int min;
		min = sec / 60;
		sec = sec % 60;
		min = min % 60;
		return "Time:" + min + ":" + sec;
	}

	class RainThread extends Thread { // 산성비 메소드 thread 상속
		private int score = 0;

		public int getScore() {
			return score;
		}

		synchronized public void run() {
			try {
				while (true) {
					// ****************************Game over ************************//
					if (Thread.interrupted()) {
						break;
					}
					if (life <= 0) {// 생명이 0이되면 게임종료
						System.out.println("Game over");
						resetGame();// 리셋
						break;
					}
					score = scorePanel.getScore();
					if (score > 100 && score <= 200) {
						setLevel(2);
					} else if (score > 200 && score <= 300) {
						setLevel(3);
					} else if (score > 300 && score <= 400) {
						setLevel(4);
					} else if (score > 400 && score <= 500) {
						setLevel(5);
					} else if (score > 500 && score <= 600) {
						setLevel(6);
					} else if (score > 600 && score <= 700) {
						setLevel(7);
					} else if (score > 700 && score <= 800) {
						setLevel(8);
					} else if (score > 800 && score <= 900) {
						setLevel(9);
					} else if (score > 900 && score <= 1000) {
						setLevel(10);
					}
					// System.out.println("RainThread start");
					setText();
					changePosition();
					HardGamePanel.this.repaint();
					// 능력사용 - 속도를 변경 한다.

					if (threadStateFlag >= 200) { // Blue Ability
						System.out.println("** blue text ability run ***");
						Thread.sleep(3000);
						threadStateFlag = 0;
					} else if (threadStateFlag < 0) {
						Thread.sleep(500);
					} else {
						threadStateFlag = 0;
						Thread.sleep(1000);
					}
					System.out.println("ability protocol :" + threadStateFlag);
					threadStateFlag++;
				}
			} catch (InterruptedException e) {
				System.out.println("Thread stop");
				e.printStackTrace();
			}
		}
	}

	class TimeThread extends Thread {
		private int time = 120; // 게임 시간 2분

		public int getTime() {
			return time;
		}

		synchronized public void run() {
			while (true) {
				try {
					if (Thread.interrupted()) {
						break;
					} else if (time <= 0) {
						System.out.println("Game over");
						resetGame(); // 리셋
						break;
					}

					time--;
					timeLabel.setText(setTime(time));
					Thread.sleep(1000);

				} catch (InterruptedException e) {
					timeLabel.setText(setTime(0));
					System.out.println("Kill TimeThread");
					e.printStackTrace();
					break;
				}
			}
		}
	}

	// Score를 기준으로 Level이 변경된다.
	private void setLevel(int level) {// 레벨이 올라갈수록 속도를 증가시킨다
		switch (level) {
		case 1:
			scorePanel.setLevel(1);
			this.speed = 10;
			break;
		case 2:
			scorePanel.setLevel(2);
			this.speed = 15;
			break;
		case 3:
			scorePanel.setLevel(3);
			this.speed = 20;
			break;
		case 4:
			scorePanel.setLevel(4);
			this.speed = 25;
			break;
		case 5:
			scorePanel.setLevel(5);
			this.speed = 30;
			break;
		case 6:
			scorePanel.setLevel(6);
			this.speed = 33;
			break;
		case 7:
			scorePanel.setLevel(7);
			this.speed = 35;
			break;
		case 8:
			scorePanel.setLevel(8);
			this.speed = 37;
			break;
		case 9:
			scorePanel.setLevel(9);
			this.speed = 40;
			break;
		case 10:
			scorePanel.setLevel(10);
			this.speed = 45;
			break;
		}
	}

	private void setText() {
		// System.out.println("SetText");
		String newWord = textSource.get();
		TextObject textTmp = new TextObject(newWord, number);
		// System.out.println(textTmp.getText() + " 단어 출력.");

		for (int i = 0; i < textArr.size(); i++) {
			TextObject tmp = (TextObject) textArr.get(i);
			tmp.setSize(100, 30);

			// 색 설정
			switch (tmp.getAbility()) {
			case 0:
				tmp.setForeground(Color.BLACK);
				break;
			case 1:
				tmp.setForeground(new Color(137, 7, 255));// 보라색
				break;
			case 2:
				tmp.setForeground(new Color(238, 49, 53));// 빨간색
				break;
			case 3:
				tmp.setForeground(new Color(15, 169, 93));// 초록색
				break;
			}
			tmp.setLocation(tmp.getX(), tmp.getY());
			add(tmp);
		}
		textArr.add(textTmp);
		number++;
	}

	public void resetGame() {
		scorePanel.setEnabledStartButton();
		// life객체 지우기
		LifePanel lifePanel = lifePanels.get(threadCount);
		lifePanel.removeAll();
		remove(lifePanel);

		number = 0;
		life = 5;
		pixel = 10;
		speed = 10;
		gameLevel = 1;
		for (int i = 0; i < textArr.size(); i++) {
			TextObject textTmp = (TextObject) textArr.get(i);
			textTmp.setText("");
		}
		getRainThread().interrupt();
		getAudioThread().interrupt();

		scorePanel.resetLevelAndPoint();
		textArr.removeAllElements();
		HardGameFrame.clip.stop();
		loadGameOverEffectAudio();
		saveRecord();
		threadCount++;
	}

	public void startGame() {

		LifePanel lifePanel = new LifePanel(); // 생명 패널
		lifePanels.add(lifePanel);
		add(lifePanel);

		makeThreads();
		getTimeThread().start();
		getRainThread().start();
		System.out.println((threadCount + 1) + "번째 시도 시작.(threadMake)");

		// 배경음악 시작
		loadAudio("resource/sound/bgm.wav");
		getAudioThread().start();
	}

	private synchronized void changePosition() {
		for (int i = 0; i < textArr.size(); i++) {
			TextObject textTmp = (TextObject) textArr.get(i);
			int y = textTmp.getY();

			// 생명 감소
			if (y > 500) {
				// 입력을 못한 상황
				textArr.remove(i);
				textTmp.setText("");
				HardGamePanel.this.repaint();

				LifeIcon icon = (LifeIcon) lifeIcons.get(life - 1);
				icon.setText("");
				lifeIcons.remove(icon);

				System.out.println("life 감소.");
				life--;
				loadLifeEffectAudio();
			}
			textTmp.setY(y + speed);
		}
	}

	// 생명 아이콘
	class LifeIcon extends JLabel {
		private ImageIcon lifeIcon = new ImageIcon("resource/img/life.png");// 생명 이미지
		private Image lifeImg = lifeIcon.getImage();

		public void paintComponent(Graphics g) {
			setOpaque(false);
			super.paintComponent(g);
			g.drawImage(lifeImg, 0, 0, this.getWidth(), this.getHeight(), null);
		}

		public LifeIcon() {
			this.setSize(40, 30);
			this.setForeground(new Color(136, 194, 234));
			this.setText("000");
			System.out.println("그림 아이콘그림");
		}
	}

	class LifePanel extends JPanel {

		public void makeIcon() {
			JLabel tmp = new JLabel();
			tmp.setSize(40, 30);
		}

		public LifePanel() {
			this.setBounds(162, 0, 400, 30);
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			this.setBackground(new Color(255, 0, 0, 0)); // 투명

			for (int i = 0; i < life; i++) {// 생명 수 만큼 그림을 그림
				LifeIcon lifeIcon = new LifeIcon();
				lifeIcons.add(lifeIcon);
				add(lifeIcon);
			}

		}// 생성자

	}

	/*
	 * * * 오디오 효과 * *
	 */

	// 맞출때 효과
	private void loadAnswerEffectAudio() {
		try {
			effectSoundClip = AudioSystem.getClip();
			File soundFile = new File("resource/sound/correctEffect.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			effectSoundClip.open(audioStream);
			effectSoundClip.start();
			System.out.println("맞춤");
		} catch (Exception e) {
			System.out.println("오디오 에러");
		}
	}

	// 틀릴때 효과
	private void loadWrongAnswerEffectAudio() {
		try {
			effectSoundClip2 = AudioSystem.getClip();
			File soundFile = new File("resource/sound/wrongAnswerEffect.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			effectSoundClip2.open(audioStream);
			effectSoundClip2.start();
		} catch (Exception e) {
			System.out.println("오디오 에러");
		}
	}

	// 라이프 감소 효과
	private void loadLifeEffectAudio() {
		try {
			lifeMinusClip = AudioSystem.getClip();
			File soundFile = new File("resource/sound/lifeEffect.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			lifeMinusClip.open(audioStream);
			lifeMinusClip.start();
			// System.out.println("라이프 감소 사운드");
		} catch (Exception e) {
			System.out.println("오디오 에러");
		}
	}

	// 게임오버 효과
	private void loadGameOverEffectAudio() {
		try {
			gameOverClip = AudioSystem.getClip();
			File soundFile = new File("resource/sound/gameOverEffect.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			gameOverClip.open(audioStream);
			gameOverClip.start();
			// System.out.println("게임오버 사운드");
		} catch (Exception e) {
			System.out.println("오디오 에러");
		}
	}

	// 배경 오디오 올려놓기
	private void loadAudio(String pathName) {
		try {
			HardGameFrame.clip = AudioSystem.getClip();
			File soundFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			HardGameFrame.clip.open(audioStream);
			// System.out.println("배경음악 start");
		} catch (Exception e) {
			System.out.println("오디오 에러");
		}
	}

	// 배경 오디오 관련 쓰레드
	class AudioThread extends Thread {
		@Override
		synchronized public void run() {
			try {
				HardGameFrame.clip.loop(Clip.LOOP_CONTINUOUSLY); // 무한재생
			} catch (Exception e) {
				timeLabel.setText(setTime(0));
				System.out.println("Kill AudioThread");
				e.printStackTrace();
			}
		}
	}

	public HardGamePanel() {

	}

	public HardGamePanel(HardScorePanel scorePanel, EditPanel editPanel) {
		this.scorePanel = scorePanel;
		this.editPanel = editPanel;

		setLayout(null);

		input.setBounds(216, 481, 116, 21);
		add(input);
		input.setColumns(10);
		timeLabel.setFont(new Font("Eras Bold ITC", Font.PLAIN, 15));
		timeLabel.setSize(127, 24);
		timeLabel.setLocation(0, 0);
		add(timeLabel);

		input.addActionListener(new ActionListener() {
			int flag = 0;

			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) e.getSource();
				String inWord = t.getText();
				boolean correctAnswer = false; // 정답 여부를 나타내는 플래그
				for (int i = 0; i < textArr.size(); i++) {
					TextObject textTmp = (TextObject) textArr.get(i);
					if (textTmp.getText().equals(inWord)) {// 정답 맞췄을때
						loadAnswerEffectAudio(); // 정답 맞추면 싸운드
						Dimension d = editPanel.getSize();
						editPanel.setCharImage(editPanel.getGraphics(), d, true);
						switch (textTmp.getAbility()) {
						case 0: // Black 검은색 점수+10
							scorePanel.increase(10);
							textTmp.setText("");
							textArr.remove(i); // 지우기
							repaint();
							t.setText("");
							// System.out.println("black ability");
							break;

						case 1: // 보라색
							threadStateFlag = 200; // 3초간 정지, 점수+20
							scorePanel.increase(20);
							textTmp.setText("");
							textArr.remove(i); // 지우기
							repaint();
							t.setText("");
							// System.out.println("blue ability");
							break;

						case 2: // 빨간색
							scorePanel.increase(50);
							textTmp.setText("");
							// 모든 내용 지우기.
							for (int j = 0; j < textArr.size(); j++) {
								TextObject tmp = (TextObject) textArr.get(j);
								tmp.setText("");
							}
							t.setText("");
							textArr.removeAllElements();
							repaint();
							// System.out.println("Green ability");
							break;

						case 3: // 초록색
							threadStateFlag = -10; // 5초간 빨라짐.
							scorePanel.increase(10);
							textTmp.setText("");
							textArr.remove(i); // 지우기
							repaint();
							t.setText("");
							// System.out.println("Red ability");
							break;
						}
						flag = -1; // 정답이 있었다.
						break;
					}
					t.setText(""); // 맞추든 틀리든 지워준다.
				} // End for
					// 틀린경우
				if (flag == 0) { // flag가 그냥 나온 경우 -> 틀렸다.
					loadWrongAnswerEffectAudio();
					Dimension d = editPanel.getSize();
					editPanel.setCharImage(editPanel.getGraphics(), d, false);

					HardGamePanel.this.repaint();
					LifeIcon icon = (LifeIcon) lifeIcons.get(life - 1);
					icon.setText("");
					lifeIcons.remove(icon);
					System.out.println("life 감소.");
					life--;
					loadLifeEffectAudio();
				}
				t.setText("");
				flag = 0;// 다시 0으로
			}
		});
	}
}