package hardgamescreen;

import javax.swing.JFrame;

public class HardMediatorPanel extends JFrame {
	private HardScorePanel scorePanel_hard;
	private HardGamePanel gamePanel_hard;

	public HardMediatorPanel() {
	}

	public HardMediatorPanel(HardScorePanel scorePanel, HardGamePanel gamePanel) {
		this.scorePanel_hard = scorePanel;
		this.gamePanel_hard = gamePanel;
	}

	public HardScorePanel getScorePanel() {
		return scorePanel_hard;
	}

	public HardGamePanel getGamePanel_easy() {
		return gamePanel_hard;
	}

	public void setScorePanel(HardScorePanel scorePanel) {
		this.scorePanel_hard = scorePanel;
	}

	public void setGamePanelEasy(HardGamePanel hardgamePanel) {
		this.gamePanel_hard = hardgamePanel;
	}

	public void setIncrease(int n) {
		this.scorePanel_hard.increase(n);
	}
}
