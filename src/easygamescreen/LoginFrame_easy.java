package easygamescreen;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginFrame_easy extends JFrame {// 로그인 프레임
	private JTextField textField;

	public LoginFrame_easy() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("resource/img/favcion_bugi.png");
		setIconImage(img);

		setTitle("아이디 입력");
		setSize(233, 131);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 가운데 출력시키기
		textField = new JTextField();
		textField.setBounds(87, 10, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton registerButton = new JButton("등록");// 등록
		registerButton.setFont(new Font("한컴 말랑말랑 Regular", Font.PLAIN, 16));

		registerButton.addActionListener(new ActionListener() {
			String id = (String) textField.getText();

			public void actionPerformed(ActionEvent e) {

				String id = textField.getText();
				System.out.println("id : " + id + " 입니다.");
				EasyGameFrame gameframe = new EasyGameFrame(id);
				setVisible(false);
			}
		});

		registerButton.setBounds(24, 41, 179, 35);
		getContentPane().add(registerButton);

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					System.out.println("click enter");
					String id = textField.getText();
					System.out.println("id : " + id + " 입니다.");
					EasyGameFrame gameframe = new EasyGameFrame(id);// 이지 게임프레임으로 전환
					setVisible(false);
				}
			}
		});

		textField.requestFocus();

		JLabel registerLabel = new JLabel("아이디:");
		registerLabel.setFont(new Font("한컴 말랑말랑 Regular", Font.PLAIN, 12));
		registerLabel.setBounds(24, 13, 81, 15);

		getContentPane().add(registerLabel);
		setResizable(false);
		setVisible(true);

	}

	private class RegisterAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = textField.getText();
			System.out.println("id : " + id + " 입니다.");
			EasyGameFrame gameframe = new EasyGameFrame(id);
			setVisible(false);

		}
	}
}
