package easygamescreen;


import javax.swing.JFrame;

public class EasyMediatorPanel extends JFrame {
	private EasyScorePanel scorePanel_easy;
	private EasyGamePanel gamePanel_easy;

	public EasyMediatorPanel() {
	}

	public EasyMediatorPanel(EasyScorePanel scorePanel, EasyGamePanel gamePanel) {
		this.scorePanel_easy = scorePanel;
		this.gamePanel_easy = gamePanel;
	}

	public EasyScorePanel getScorePanel() {
		return scorePanel_easy;
	}

	public EasyGamePanel getGamePanel_easy() {
		return gamePanel_easy;
	}

	public void setScorePanel(EasyScorePanel scorePanel) {
		this.scorePanel_easy = scorePanel;
	}

	public void setGamePanelEasy(EasyGamePanel easygamePanel) {
		this.gamePanel_easy = easygamePanel;
	}

	public void setIncrease(int n) {
		this.scorePanel_easy.increase(n);
	}
}
