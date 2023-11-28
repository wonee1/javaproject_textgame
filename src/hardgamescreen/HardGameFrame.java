
package hardgamescreen;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

public class HardGameFrame extends JFrame {
	private String id = "";

	public static Clip clip;

	// 전반적으로 엑세스 할 수 있게 하기 위해서 빼놓는다.
	private JMenuItem exitItem = new JMenuItem("게임 종료");
	private JMenuItem addTextItem = new JMenuItem("단어 추가");
	private JMenuItem informationItem = new JMenuItem("게임 정보");
	private JMenuItem audioStartItem = new JMenuItem("배경음악 키기");
	private JMenuItem audioStopItem = new JMenuItem("배경음악 끄기");
	private JMenuItem audioControlItem = new JMenuItem("음량 조절");
	private JMenuItem scoreItem = new JMenuItem("점수 기록");
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");

	private HardScorePanel scorePanel = new HardScorePanel();
	private EditPanel editPanel = new EditPanel();
	private HardGamePanel hardgamePanel = new HardGamePanel(scorePanel, editPanel);
	private HardMediatorPanel mediatorPanel;

	public HardGameFrame(String id) {
		mediatorPanel = new HardMediatorPanel();
		mediatorPanel.setGamePanelEasy(hardgamePanel);
		mediatorPanel.setScorePanel(scorePanel);
		hardgamePanel.setScorePanel(scorePanel);
		scorePanel.setGamePanel(mediatorPanel.getGamePanel_easy());

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");// 이모티콘
		setIconImage(img);

		this.id = id;
		editPanel.setId(id);
		setTitle("타이핑 게임");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 이창만꺼지기
		setSize(800, 600);

		splitPane(); // JSplitPane을 생성하여 컨테트팬에 CENTER에 부착.
		makeMenu();
		setLocationRelativeTo(null);
		setResizable(false); // 함부로 크기를 변경하지 않게.
		setVisible(true);
	}

	public HardGamePanel getGamePanel() {
		return hardgamePanel;
	}// easy게임 패널

	// ContentPane을 SplitPanne으로 나눌거임.

	private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		// ContentPane()은 BorderLayout이 기본.
		getContentPane().add(hPane, BorderLayout.CENTER);

		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(550);

		// 못움직이게 고정시키기. - 이건 모든 컴포넌트에 다 적용된다.
		hPane.setEnabled(false);

		// 왼쪽에 게임패널 넣기.
		hPane.setLeftComponent(hardgamePanel);

		// 왼쪽에 붙일 패널과 오른족에 붙일 패널을 만들어줘야된다.
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(300);
		pPane.setEnabled(false);

		// ScorePanel붙이기, id 붙이기
		pPane.setTopComponent(scorePanel);

		// EditPanel붙이기
		pPane.setBottomComponent(editPanel);

		// 오른쪽에 pPane 넣기.
		hPane.setRightComponent(pPane);
	}

	// 메뉴바를 만드는 함수.
	private void makeMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// 게임 관련 메뉴
		JMenu gameMenu = new JMenu("게임");
		gameMenu.add(exitItem);

		// 설정 관련 메뉴
		JMenu setMenu = new JMenu("설정");
		setMenu.add(addTextItem);

		// 소리 관련 메뉴
		JMenu soundMenu = new JMenu("볼륨");
		soundMenu.add(audioStartItem);
		soundMenu.add(audioStopItem);
		soundMenu.add(audioControlItem);

		// 개발자 정보관련 메뉴
		JMenu informationMenu = new JMenu("도움");
		informationMenu.add(informationItem);

		// 점수 관련 메뉴
		JMenu scoreMenu = new JMenu("점수");
		scoreMenu.add(scoreItem);

		menuBar.add(gameMenu);
		menuBar.add(setMenu);
		menuBar.add(soundMenu);
		menuBar.add(informationMenu);
		menuBar.add(scoreMenu);

		// 액션리스너
		exitItem.addActionListener(new ExitAction());
		addTextItem.addActionListener(new addWordAction());

		audioStopItem.addActionListener(new audioStopAction());
		audioStartItem.addActionListener(new audioStartAction());
		audioControlItem.addActionListener(new audioAction());
		informationItem.addActionListener(new InformationAction());
		scoreItem.addActionListener(new ScoreAction());

		audioStartItem.setEnabled(false);
	}

	// 액션 리스너 작성
	// 게임 종료
	private class ExitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	// 게임 단어추가
	private class addWordAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 새로운 창 추가.
			// 단어를 추가할 수 있는 창.
			AddTextFrame addTextFrame = new AddTextFrame();
		}
	}

	private class audioStopAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clip.stop();
			audioStopItem.setEnabled(false);
			audioStartItem.setEnabled(true);
		}
	}

	private class audioStartAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clip.loop(clip.LOOP_CONTINUOUSLY);
			audioStartItem.setEnabled(false);
			audioStopItem.setEnabled(true);
		}
	}

	private class audioAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ControlSoundFrame controlSoundFrame = new ControlSoundFrame();
		}
	}

	// 게임 정보
	private class InformationAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InformationFrame informationFrame = new InformationFrame();
		}
	}

	// 점수 정보
	private class ScoreAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			HardScoreFrame scoreFrame = new HardScoreFrame();// 점수 프레임 열리게
		}
	}
}
