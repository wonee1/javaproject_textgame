package hardgamescreen;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EditPanel extends JPanel {
    private ImageIcon normalChar = new ImageIcon("resource/img/char_normal.jpg");
    private ImageIcon heartChar = new ImageIcon("resource/img/char_heart.jpg");
    private ImageIcon cryChar = new ImageIcon("resource/img/char_cry.jpg");
    private ImageIcon angryChar = new ImageIcon("resource/img/char_angry.jpg");
    private ImageIcon surpriseChar = new ImageIcon("resource/img/char_surprise.jpg");
    private JLabel textLabel = new JLabel("");
    private String id = " ";

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 부모의 paintComponent 호출
        Dimension d = getSize();
        g.drawImage(normalChar.getImage(), 0, 0, d.width, d.height, null);
    }

    public void setCharImage(Graphics g, Dimension d, boolean isCorrectAnswer) {
        if (isCorrectAnswer) {
            // 답이 맞았을 때
            // char_heart 이미지로 설정
            g.drawImage(heartChar.getImage(), 0, 0, d.width, d.height, null);
        } else {
            // 답이 틀렸을 때
            // 랜덤으로 char_cry, angry, surprise 중 하나를 선택하여 설정
            Random rand = new Random();
            int randomIndex = rand.nextInt(3); // 0, 1, 2 중에서 랜덤 선택
            switch (randomIndex) {
                case 0:
                    g.drawImage(cryChar.getImage(), 0, 0, d.width, d.height, null);
                    break;
                case 1:
                    g.drawImage(angryChar.getImage(), 0, 0, d.width, d.height, null);
                    break;
                case 2:
                    g.drawImage(surpriseChar.getImage(), 0, 0, d.width, d.height, null);
                    break;
            }
        }
    }

    public EditPanel() {
        this.setLayout(null);
        textLabel = new JLabel("아이디 자리");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.PLAIN, 17));
        textLabel.setBounds(52, 173, 109, 37);
        add(textLabel);
    }

    public void setId(String id) {
        this.id = id;
        textLabel.setText(id);
    }

    public String getId() {
        return id;
    }
}
