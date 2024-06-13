import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private int count = 0;
    private JFrame frame;
    private JPanel panel;

    private JLabel userLabel;
    private JTextField userText;
    private JLabel passwordLabel;
    private  JPasswordField passwordField;
    private JButton button;
    private JLabel success;
    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setTitle("GUI");
        panel.setLayout(null);
//        JButton button = new JButton("CLick me");
//        button.addActionListener(this);
//        label = new JLabel("Number of clicks: 0");

//        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
//        panel.setLayout(new GridLayout(0, 1));
//        panel.add(button);
//        panel.add(label);

        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100,50,165,25);
        panel.add(passwordField);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String pw = passwordField.getText();
                success.setText("OK");
                System.out.println(">>" + user);
                System.out.println(">>" + pw);
            }
        });

        panel.add(button);

         success = new JLabel("");
        success.setBounds(10, 110,300, 25);
        panel.add(success);
//        success.setText();

        frame.setVisible(true);

    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        count++;
//        label.setText("Number of clicks: " + count);
//    }
}
