package hardgamescreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;

class Score {
	private String id;
	private int score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Score(String id, int score) {
		super();
		this.id = id;
		this.score = score;
	}

}

public class HardScoreFrame extends JFrame {

	private File file = new File("resource/record/record.txt");
	private Vector<Score> scores = new Vector<>();

	private void recordScore() {
		try {

			FileReader filereader = new FileReader(file);

			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";

			while ((line = bufReader.readLine()) != null) {
				String arr[] = line.split("/");
				String id = arr[0];
				String score = arr[1];

				Score scoretmp = new Score(id, Integer.parseInt(score));
				scores.add(scoretmp);
			}

			System.out.println("-----점수------");
			for (int i = 0; i < scores.size(); i++) {
				Score scoretmp = scores.get(i);
				String id = scoretmp.getId();
				int score = scoretmp.getScore();
				for (int j = i + 1; j < scores.size(); j++) {
					Score scoretmpRef = scores.get(j);
					String idRef = scoretmpRef.getId();
					int scoreRef = scoretmpRef.getScore();

					if (id.equals(idRef)) {

						if (score >= scoreRef) {

							scores.remove(scoretmpRef);
						} else {

							score = scoreRef;
							scoretmp.setScore(score);
							scores.remove(scoretmpRef);
							i--;
						}
					}
				}
			}
			for (int i = 0; i < scores.size(); i++) {
				System.out.println(i + "\t " + scores.get(i).getId() + "\t\t " + scores.get(i).getScore());
			}
		} catch (Exception e) {
			System.out.println("에러");
		}
	}

	public HardScoreFrame() {
		recordScore();

		setForeground(Color.CYAN);
		getContentPane().setForeground(Color.CYAN);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);
		setTitle("점수 기록");
		setSize(290, 448);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("아이디");
		lblNewLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.BOLD, 20));
		lblNewLabel.setBounds(41, 10, 98, 28);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setFont(new Font("한컴 말랑말랑 Regular", Font.BOLD, 20));
		lblNewLabel_1.setBounds(151, 10, 98, 28);
		getContentPane().add(lblNewLabel_1);

		System.out.println("--------------------------------------");

		JLabel[] tmp = new JLabel[scores.size()];
		String[] tmpId = new String[scores.size()];
		int[] tmpScore = new int[scores.size()];

		for (int i = 0; i < scores.size() - 1; i++) {
			Score scoretmp = scores.get(i);
			tmpId[i] = scoretmp.getId();
			tmpScore[i] = scoretmp.getScore();
		}

		for (int i = 0; i < scores.size() - 1; i++) {
			int indexMax = i;

			for (int j = i + 1; j < scores.size(); j++) {
				if (tmpScore[j] > tmpScore[i]) {
					indexMax = j;
				}
			}
			// swap
			int tmpInt = tmpScore[indexMax];
			tmpScore[indexMax] = tmpScore[i];
			tmpScore[i] = tmpInt;

			String tmpString = tmpId[indexMax];
			tmpId[indexMax] = tmpId[i];
			tmpId[i] = tmpString;
		}

		for (int i = 0; i < scores.size(); i++) {
			String id = tmpId[i];
			int score = tmpScore[i];

			JLabel idLabel = new JLabel(id);
			idLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.BOLD, 20));
			idLabel.setBounds(41, 10 + ((i + 1) * 27), 98, 28);
			getContentPane().add(idLabel);

			JLabel scoreLabel = new JLabel(Integer.toString(score));
			scoreLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.BOLD, 20));
			scoreLabel.setBounds(151, 10 + ((i + 1) * 27), 98, 28);
			getContentPane().add(scoreLabel);

			System.out.println(id + "\t:" + score);
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		// setResizable(false);
		setVisible(true);
	}
}
