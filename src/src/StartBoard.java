import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class StartBoard {

	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		StartBoard start = new StartBoard();
		start.loginUI();
	}

	private JFrame firstframe;
	private JPanel contentPane;
	private JComponent connectPane;
	protected ServerService server;
	private JPanel usernamePane;
	private JLabel feedback;
	private JPanel managerPane;
	private JTextField mportTxt;
	private JTextField boardTxt;
	private JTextField userTxt;

	public void loginUI() {
		firstframe = new JFrame();
		firstframe.setBounds(100, 100, 450, 300);
		firstframe.setResizable(false);
		firstframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstframe.setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton managerBt = new JButton("Manager");
		managerBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				managerPane();
			}
		});
		managerBt.setBounds(161, 89, 179, 29);
		contentPane.add(managerBt);

		JButton clientBt = new JButton("Client");
		clientBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				clientConnectPane();
			}
		});
		clientBt.setBounds(161, 148, 179, 29);
		contentPane.add(clientBt);

		JButton exitBt = new JButton("Exit");
		exitBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBt.setBounds(161, 210, 179, 29);
		contentPane.add(exitBt);

		JLabel welcomeLb = new JLabel("Welcome to White Board！");
		welcomeLb.setFont(new Font("Hannotate TC", Font.PLAIN, 20));
		welcomeLb.setBounds(115, 32, 252, 16);
		contentPane.add(welcomeLb);

		JLabel managerLb = new JLabel("");
		ImageIcon image = new ImageIcon(getClass().getResource("/icon/manager.png"));
		managerLb.setIcon(image);
		managerLb.setBounds(101, 78, 48, 50);
		contentPane.add(managerLb);

		JLabel clientLb = new JLabel("");
		ImageIcon image1 = new ImageIcon(getClass().getResource("/icon/user.png"));
		clientLb.setIcon(image1);
		clientLb.setBounds(101, 136, 48, 50);
		contentPane.add(clientLb);

		JLabel exitLb = new JLabel("Exit");
		ImageIcon image2 = new ImageIcon(getClass().getResource("/icon/Exit.png"));
		exitLb.setIcon(image2);
		exitLb.setBounds(101, 198, 48, 50);
		contentPane.add(exitLb);

		firstframe.setVisible(true);
	}

	public void clientConnectPane() {

		connectPane = new JPanel();
		connectPane.setBackground(new Color(250, 240, 230));
		connectPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstframe.setContentPane(connectPane);
		connectPane.setLayout(null);

		JLabel hostLb = new JLabel("Host");
		hostLb.setBounds(50, 48, 48, 50);
		connectPane.add(hostLb);

		JLabel portLb = new JLabel("Port");
		portLb.setBounds(50, 84, 48, 50);
		connectPane.add(portLb);

		JLabel serverLb = new JLabel("Server Name");
		serverLb.setBounds(50, 125, 85, 50);
		connectPane.add(serverLb, BorderLayout.WEST);

		JTextField hostTxt = new JTextField();
		hostTxt.setBounds(147, 59, 170, 29);
		connectPane.add(hostTxt);

		JTextField portTxt = new JTextField();
		portTxt.setBounds(147, 95, 170, 29);
		connectPane.add(portTxt);

		JTextField serverTxt = new JTextField();
		serverTxt.setBounds(147, 136, 170, 29);
		connectPane.add(serverTxt);


		JButton connect = new JButton("connect");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String host = hostTxt.getText();
				String port = portTxt.getText();
				String servername = serverTxt.getText();
				if (host.equals(null) || port.equals(null) || servername.equals(null) || host.equals("")
						|| port.equals("") || servername.equals("")) {
					JOptionPane.showMessageDialog(firstframe, "Plz input something", "ERROR",
							JOptionPane.WARNING_MESSAGE);
					hostTxt.setText(null);
					portTxt.setText(null);
					serverTxt.setText(null);
				} else {
					String address = "//" + host + ":" + port + "/" + servername;
					try {
						server = (ServerService) Naming.lookup(address);
						connectPane.setVisible(false);
						usernamePane();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(firstframe, "1.Wrong Address 2.Wrong input 3.Server is not up",
								"ERROR", JOptionPane.WARNING_MESSAGE);
						hostTxt.setText(null);
						portTxt.setText(null);
						serverTxt.setText(null);
					}
				}
			}
		});
		connect.setBounds(147, 209, 170, 29);
		connectPane.add(connect);
	}

	public void usernamePane() {
		usernamePane = new JPanel();
		usernamePane.setBackground(new Color(250, 240, 230));
		usernamePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstframe.setContentPane(usernamePane);
		usernamePane.setLayout(null);

		JLabel portLb = new JLabel("User Name");
		portLb.setBounds(50, 84, 85, 50);
		usernamePane.add(portLb);

		userTxt = new JTextField();
		userTxt.setBounds(147, 95, 170, 29);
		usernamePane.add(userTxt);

		feedback = new JLabel("");
		feedback.setBounds(147, 139, 170, 16);
		usernamePane.add(feedback);

		JButton startpaint = new JButton("register");
		startpaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userTxt.getText();
				if (username.equals(null) || username.equals("")) {
					JOptionPane.showMessageDialog(firstframe, "Plz input something", "ERROR",
							JOptionPane.WARNING_MESSAGE);
					userTxt.setText(null);
				} else {
					try {
						Client client = new Client(server);
						if(server.checkUserName(username)){
							JOptionPane.showMessageDialog(firstframe, "Great! You can have this name.\nwaiting fo approval...", "Congratulations",
									JOptionPane.PLAIN_MESSAGE);
							if(server.approval(username)){
								client.setUsername(username);
								server.register(username, client);
								JOptionPane.showMessageDialog(firstframe, "Welcome, you are approved.");
								firstframe.setVisible(false);
								server.boardcastClient(username);
								client.initUI();
							}else{
								JOptionPane.showMessageDialog(firstframe, "Sorry,you didn't get the approval from Manager.");
								System.exit(0);
							}
						}else{
							JOptionPane.showMessageDialog(firstframe, "Sorry, this user name has been used.");
							userTxt.setText(null);
						}
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(firstframe, "ERROR", "ERROR", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		startpaint.setBounds(147, 178, 170, 29);
		usernamePane.add(startpaint);

	}

	public void managerPane() {
		managerPane = new JPanel();
		managerPane.setBackground(new Color(250, 240, 230));
		managerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstframe.setContentPane(managerPane);
		managerPane.setLayout(null);

		JLabel portLb = new JLabel("port");
		portLb.setBounds(60, 49, 85, 50);
		managerPane.add(portLb);

		boardTxt = new JTextField();
		boardTxt.setBounds(147, 114, 170, 29);
		managerPane.add(boardTxt);

		mportTxt = new JTextField();
		mportTxt.setBounds(147, 61, 170, 26);
		managerPane.add(mportTxt);
		mportTxt.setColumns(10);

		JLabel boardNameLb = new JLabel("Board Name");
		boardNameLb.setBounds(60, 120, 85, 16);
		managerPane.add(boardNameLb);

		JButton setup = new JButton("setup");
		setup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String port = mportTxt.getText();
				String boardname = boardTxt.getText();
				if (port.equals(null) || port.equals("") || boardname.equals(null) || boardname.equals("")) {
					JOptionPane.showMessageDialog(firstframe, "Plz input something", "ERROR",
							JOptionPane.WARNING_MESSAGE);
					mportTxt.setText(null);
					boardTxt.setText(null);
				} else {
					try {
						String address = "//localhost:" + port + "/" + boardname;
						StartServer ss = new StartServer();
						ss.start(port, address);
						server = (ServerService) Naming.lookup(address);
						Manager manager = new Manager(server);
						server.register("Manager", manager);
						firstframe.setVisible(false);
						manager.initUI();
					}catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(firstframe, "Server set up error", "ERROR",
								JOptionPane.WARNING_MESSAGE);
						mportTxt.setText(null);
						boardTxt.setText(null);
					}	
				}
			}
		});
		setup.setBounds(147, 178, 170, 29);
		managerPane.add(setup);
	}

}
