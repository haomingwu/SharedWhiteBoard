import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Manager extends ClientServiceImpl implements MouseListener, MouseMotionListener, ActionListener {

	private static final long serialVersionUID = 1L;
	protected ServerService server;
	private JFrame frame;
	private JMenuBar menu;
	private JMenu fileMenu;
	private JMenu colorMenu;
	private JMenuItem newFile;
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem colorChooser;
	private JToolBar toolBar;

	private String tools[] = { "pen", "eraser", "line", "circle", "oval", "rect", "roundrect", "word" };
	private String tip[] = { "Pencil", "Eraser", "Line", "Circle", "Oval", "Rectangle", "RoundRect", "Word" };
	private JComboBox<Integer> strokeType = new JComboBox<Integer>(
			new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20 });
	private JComboBox<String> fontlist;
	private Icon icons[];
	private JButton button[];
	private JMenuItem exit;
	protected Component whiteboard;
	protected DrawArea drawArea;
	private ImageIcon fileImg;
	private ImageIcon colorImg;
	private ImageIcon newImg;
	private ImageIcon saveImg;
	private ImageIcon openImg;
	private ImageIcon exitImg;
	private ImageIcon colorPaletteImg;
	private int toolChoice = 0;
	protected Image buffer;
	private Color color = Color.black;
	private int R = 0, G = 0, B = 0;
	private int x1, y1;
	private int x2, y2;
	private JPanel rightpanel;
	private JPanel rightdown;
	private JTextArea messageTxt;
	private JTextArea messageArea;
	private JTextArea memberArea;
	private JButton send;
	private JScrollPane memberSP;
	private JScrollPane messageSP;
	private JScrollPane txtSP;
	private String username;
	private JMenu groupMenu;
	private ImageIcon groupImg;
	private ImageIcon kickImg;
	private JMenuItem kick;
	private String[] font;
	private JLabel editLb;
	private JCheckBox fillchoose;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	protected Manager(ServerService server) throws RemoteException {
		super();
		this.username = "Manager";
		this.server = server;

	}

	public void initUI() {
		frame = new JFrame("White Board for Manager");

		// initialize menu bar
		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		fileImg = new ImageIcon(getClass().getResource("/icon/file.png"));
		fileMenu.setIcon(fileImg);
		colorMenu = new JMenu("Color");
		colorImg = new ImageIcon(getClass().getResource("/icon/colorPlate.png"));
		colorMenu.setIcon(colorImg);
		groupMenu = new JMenu("Group");
		groupImg = new ImageIcon(getClass().getResource("/icon/group.png"));
		groupMenu.setIcon(groupImg);

		menu.add(fileMenu);
		menu.add(colorMenu);
		menu.add(groupMenu);
		menu.setBackground(null);

		frame.setJMenuBar(menu);

		// initialize menu item File
		newImg = new ImageIcon(getClass().getResource("/icon/newfile.png"));
		saveImg = new ImageIcon(getClass().getResource("/icon/savefile.png"));
		openImg = new ImageIcon(getClass().getResource("/icon/openfile.png"));
		exitImg = new ImageIcon(getClass().getResource("/icon/Exit.png"));
		newFile = new JMenuItem("New", newImg);
		openFile = new JMenuItem("Open", openImg);
		saveFile = new JMenuItem("Save", saveImg);
		exit = new JMenuItem("Exit", exitImg);
		newFile.addActionListener(this);
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
		exit.addActionListener(this);

		fileMenu.add(newFile);
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(exit);

		// initialize menu item Color Chooser
		colorPaletteImg = new ImageIcon(getClass().getResource("/icon/colorPalette.png"));
		colorChooser = new JMenuItem("Color Chooser", colorPaletteImg);
		colorChooser.addActionListener(this);
		colorMenu.add(colorChooser);

		kickImg = new ImageIcon(getClass().getResource("/icon/kick.png"));
		kick = new JMenuItem("Kick", kickImg);
		kick.addActionListener(this);
		groupMenu.add(kick);

		rightpanel = new JPanel();
		rightpanel.setPreferredSize(new Dimension(300, 300));
		frame.getContentPane().add(rightpanel, BorderLayout.EAST);
		rightpanel.setLayout(new BorderLayout(0, 0));
		rightpanel.setBorder(new TitledBorder("WeChat"));

		rightdown = new JPanel();
		rightdown.setPreferredSize(new Dimension(300, 50));
		rightpanel.add(rightdown, BorderLayout.SOUTH);
		rightdown.setLayout(new BorderLayout(0, 0));

		send = new JButton("Send");
		send.addActionListener(this);
		send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rightdown.add(send, BorderLayout.EAST);

		txtSP = new JScrollPane();
		messageTxt = new JTextArea();
		messageTxt.setLineWrap(true);
		messageTxt.setWrapStyleWord(true);
		messageTxt.setBorder(new EmptyBorder(5, 5, 5, 5));
		// rightdown.add(messageTxt, BorderLayout.CENTER);
		rightdown.add(txtSP, BorderLayout.CENTER);
		txtSP.setViewportView(messageTxt);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		rightpanel.add(splitPane, BorderLayout.CENTER);

		messageSP = new JScrollPane();
		messageArea = new JTextArea();
		messageSP.setBorder(new TitledBorder("Message"));
		messageArea.setEditable(false);
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		splitPane.setRightComponent(messageSP);
		messageSP.setViewportView(messageArea);
		try {
			for (String message : server.getMessageList()) {
				messageArea.append(message + "\n");
			}
		} catch (Exception e) {
		}

		memberSP = new JScrollPane();
		memberArea = new JTextArea();
		memberSP.setBorder(new TitledBorder("Member"));
		memberArea.setLineWrap(true);
		memberArea.setWrapStyleWord(true);
		memberArea.setEditable(false);
		splitPane.setLeftComponent(memberSP);
		memberSP.setViewportView(memberArea);
		try {
			for (Entry<String, ClientService> entry : server.getClientsList().entrySet()) {
				memberArea.append(entry.getKey() + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// initialize Tool Bar
		toolBar = new JToolBar(JToolBar.HORIZONTAL);
		toolBar.setBorder(new TitledBorder("Tools"));
		toolBar.setFloatable(false);

		icons = new ImageIcon[tools.length];
		button = new JButton[tools.length];
		for (int i = 0; i < tools.length; i++) {
			icons[i] = new ImageIcon(getClass().getResource("/icon/" + tools[i] + ".png"));
			button[i] = new JButton("", icons[i]);
			button[i].setToolTipText(tip[i]);
			toolBar.add(button[i]);
			button[i].setBackground(Color.white);
			button[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			button[i].addActionListener(this);
		}
		frame.getContentPane().add(toolBar, BorderLayout.SOUTH);

		JLabel fillLb = new JLabel("  Fill:");
		toolBar.add(fillLb);
		
		fillchoose = new JCheckBox();
		toolBar.add(fillchoose);
		
		JLabel strokeLb = new JLabel(" Stroke:");
		toolBar.add(strokeLb);

		strokeType.setMaximumSize(new Dimension(80, 50));
		strokeType.setMinimumSize(new Dimension(80, 40));
		toolBar.add(strokeType);

		JLabel fontlistLb = new JLabel(" Font:");
		toolBar.add(fontlistLb);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		font = ge.getAvailableFontFamilyNames();
		fontlist = new JComboBox<String>(font);
		fontlist.setMaximumSize(new Dimension(200, 50));
		fontlist.setMinimumSize(new Dimension(100, 40));
		toolBar.add(fontlist);

		editLb = new JLabel();
		toolBar.add(editLb);

		drawArea = new DrawArea();
		drawArea.setBackground(Color.white);
		frame.getContentPane().add(drawArea, BorderLayout.CENTER);
		whiteboard = drawArea;

		// initialize frame
		Toolkit tool = frame.getToolkit();
		Dimension dim = tool.getScreenSize();
		frame.setBounds(40, 40, dim.width - 300, dim.height - 200);
		// frame.setBounds(40, 40, 800, 600);
		frame.setVisible(true);
		frame.validate();
		// frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					server.managerLeave();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		whiteboard = drawArea;
		whiteboard.addMouseListener(this);
		whiteboard.addMouseMotionListener(this);

	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newFile) { // new file
			try {
				server.newFile();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == openFile) {// open file

			JFileChooser filechooser = new JFileChooser();
			filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = filechooser.showOpenDialog(whiteboard);

			if (returnVal == JFileChooser.CANCEL_OPTION) {
				return;
			}
			File fileName = filechooser.getSelectedFile();
			fileName.canRead();
			if (fileName == null || fileName.getName().equals("")) {
				JOptionPane.showMessageDialog(filechooser, "File name", "plz input file name!",
						JOptionPane.ERROR_MESSAGE);
			}

			else {

				try {
					FileInputStream ifs = new FileInputStream(fileName);
					ObjectInputStream input = new ObjectInputStream(ifs);
					Vector<Shape> list = (Vector<Shape>) input.readObject();
					server.openFile(list);
					input.close();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(whiteboard, "File Open Error!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}

			}

		} else if (e.getSource() == saveFile) {// save file
			JFileChooser filechooser = new JFileChooser();
			filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = filechooser.showSaveDialog(whiteboard);
			if (result == JFileChooser.CANCEL_OPTION) {
				return;
			}
			File fileName = filechooser.getSelectedFile();
			fileName.canWrite();
			if (fileName == null || fileName.getName().equals("")) {
				JOptionPane.showMessageDialog(filechooser, "File name", "plz input file name!",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					fileName.delete();
					FileOutputStream fos = new FileOutputStream(fileName + ".xxh");
					ObjectOutputStream output = new ObjectOutputStream(fos);
					Vector<Shape> list = server.getShapeList();
					output.writeObject(list);
					output.close();
					fos.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		} else if (e.getSource() == exit) {// exit
			try {
				// server.leave(username);
				server.managerLeave();
				System.exit(0);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		} else if (e.getSource() == colorChooser) {// get color chooser
			colorPallet();
		} else if (e.getSource() == kick) {
			new Thread() {
				@Override
				public void run() {

					Object user = JOptionPane.showInputDialog(whiteboard, "Plz input the user name you want to kick:\n",
							"Kick", JOptionPane.PLAIN_MESSAGE, null, null, null);
					String username = (String) user;
					try {
						if (username.equals("Manager") || username.equals(null) || username.equals("")) {
							JOptionPane.showMessageDialog(whiteboard, "Plz input the right username", "Attention",
									JOptionPane.WARNING_MESSAGE);
						} else {
							if (server.kickUser(username)) {

							} else {
								JOptionPane.showMessageDialog(null, "Sorry! No such user", "Attention",
										JOptionPane.WARNING_MESSAGE);
							}
						}
					} catch (Exception e) {
					}

				}
			}.start();

		} else if (e.getSource() == send) {
			String s = messageTxt.getText();
			String txt = s;
			try {
				if (s.equals(null) || s.equals("")) {

				} else {
					Date d = new Date(); 
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
			        String dateNowStr = sdf.format(d); 
					String message = dateNowStr+" "+username + ": " + txt;
					server.boradcastMessage(message);
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			messageTxt.setText(null);
		}

		for (int i = 0; i < tools.length; i++) {
			if (e.getSource() == button[i]) {
				toolChoice = i;
			}
		}
	}

	public void colorPallet() {
		color = JColorChooser.showDialog(null, "Choose your color", color);
		try {
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
		} catch (Exception e) {
			R = 0;
			G = 0;
			B = 0;
		}

	}

	public void redraw() throws RemoteException {
		drawArea.repaint();
	}

	class DrawArea extends Canvas {

		private static final long serialVersionUID = 1L;

		public DrawArea() {
		}

		public void paint(Graphics g) {
			try {
				Vector<Shape> shapeList = server.getShapeList();
				for (Shape s : shapeList) {
					drawTask(s);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean notifyTask(Shape s) throws RemoteException {

		new Thread() {
			@Override
			public void run() {
				drawTask(s);
			}
		}.start();
		return true;
	}

	public void notifyNewFile() throws RemoteException {
		redraw();

	}

	public void notifyManagerLeave() throws RemoteException {

	}

	public void notifyOpenFile() throws RemoteException {
		redraw();
	}

	public void notifyClientLeave(String s) throws RemoteException {
		new Thread() {
			@Override
			public void run() {
				Date d = new Date(); 
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
		        String dateNowStr = sdf.format(d);  
				messageArea.append(dateNowStr+" Manager: " + s + " has left.\n");
				resetMemberboard();
			}
		}.start();

	}

	public void notifyMessage(String txtmessage) throws RemoteException {
		new Thread() {
			@Override
			public void run() {
				messageArea.append(txtmessage + "\n");
			}
		}.start();

	}

	public void notifyKickMessage(String message) throws RemoteException {
		new Thread() {
			@Override
			public void run() {
				messageArea.append(message);
				resetMemberboard();
			}
		}.start();
	}

	public void notifyClient(String s) throws RemoteException {
		new Thread() {
			@Override
			public void run() {
				if (!s.equals(username)) {
					Date d = new Date(); 
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
			        String dateNowStr = sdf.format(d); 
					messageArea.append(dateNowStr+" Manager: Welcom " + s + ".\n");
					resetMemberboard();
				}
			}
		}.start();
	}
	
	public boolean notifyApproval(String username) throws RemoteException {
		int n = JOptionPane.showConfirmDialog(null, username+" wants to join the board.", "Approval",JOptionPane.YES_NO_OPTION);//i=0/1  
		if(n==1){
			return false;
		}
		return true;
	}

	public void resetMemberboard() {
		memberArea.setText(null);
		try {
			for (Entry<String, ClientService> entry : server.getClientsList().entrySet()) {
				memberArea.append(entry.getKey() + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawTask(Shape s) {

		int x1 = s.getX1();
		int x2 = s.getX2();
		int y1 = s.getY1();
		int y2 = s.getY2();
		int R = s.getR();
		int G = s.getG();
		int B = s.getB();
		String user = s.getUsername();

		editLb.setText("  " + user + " is editing...");
		int stroke = s.getStroke();
		Graphics2D gr = (Graphics2D) whiteboard.getGraphics();
		if (s.getType() == 0) {
			gr.setPaint(new Color(R, G, B));
			gr.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			gr.drawLine(x1, y1, x2, y2);

		} else if (s.getType() == 1) {
			gr.setPaint(Color.WHITE);
			gr.setStroke(new BasicStroke(stroke));
			gr.drawLine(x1, y1, x2, y2);
		} else if (s.getType() == 2) {
			gr.setPaint(new Color(R, G, B));
			gr.setStroke(new BasicStroke(stroke));
			gr.drawLine(x1, y1, x2, y2);
		} else if (s.getType() == 3) {
			gr.setPaint(new Color(R, G, B));
			gr.setStroke(new BasicStroke(stroke));
			if(s.isFill()){
				gr.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
						Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
			}else{
				gr.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
						Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
			}
		} else if (s.getType() == 4) {
			gr.setPaint(new Color(R, G, B));
			gr.setStroke(new BasicStroke(stroke));
			if(s.isFill()){
				gr.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
			}else{
				gr.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
			}
		} else if (s.getType() == 5) {
			gr.setPaint(new Color(R, G, B));
			gr.setStroke(new BasicStroke(stroke));
			if(s.isFill()){
				gr.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
			}else{
				gr.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
			}
		} else if (s.getType() == 6) {
			gr.setPaint(new Color(R, G, B));
			gr.setStroke(new BasicStroke(stroke));
			if(s.isFill()){
				gr.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
			}else{
				gr.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 35);
			}
		} else if (s.getType() == 7) {
			gr.setPaint(new Color(R, G, B));
			gr.setFont(new Font(s.getFont(), x2 + y2, ((int) stroke) * 18));
			gr.drawString(s.getTxt(), x1, y1);
		}

	}


	@Override
	public void mouseDragged(MouseEvent ev) {
		// TODO Auto-generated method stub

		x2 = ev.getX();
		y2 = ev.getY();
		if (toolChoice < 2) {
			x2 = ev.getX();
			y2 = ev.getY();
			int stroke = Integer.parseInt(strokeType.getSelectedItem().toString());
			Shape shape = new Shape(toolChoice, x1, x2, y1, y2, stroke, R, G, B, username);
			try {
				server.broadcast(shape);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x1 = x2;
			y1 = y2;

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent ev) {

	}

	@Override
	public void mousePressed(MouseEvent ev) {
		// TODO Auto-generated method stub

		x1 = ev.getX();
		y1 = ev.getY();
		x2 = x1;
		y2 = y1;
		if (toolChoice < 2) {
			int stroke = Integer.parseInt(strokeType.getSelectedItem().toString());
			Shape shape = new Shape(toolChoice, x1, x2, y1, y2, stroke, R, G, B, username);
			try {
				server.broadcast(shape);
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(whiteboard, "Drawing error", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (toolChoice == 7) {
			int stroke = Integer.parseInt(strokeType.getSelectedItem().toString());
			String fonttype = fontlist.getSelectedItem().toString();
			Shape shape = new Shape(toolChoice, x1, x2, y1, y2, stroke, R, G, B, username);
			try {
				String word = JOptionPane.showInputDialog(whiteboard, "Plz input your text", "Kick",
						JOptionPane.PLAIN_MESSAGE, null, null, null).toString();
				if (word.equals(null) || word.equals("")) {
					JOptionPane.showMessageDialog(whiteboard, "Plz input something", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					shape.setTxt(word);
					shape.setFont(fonttype);
					server.broadcast(shape);
				}
			} catch (Exception e) {

			}

		}
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		// TODO Auto-generated method stub

		if (toolChoice < 2) {
			x1 = ev.getX();
			y1 = ev.getY();
		}
		x2 = ev.getX();
		y2 = ev.getY();

		int stroke = Integer.parseInt(strokeType.getSelectedItem().toString());
		Shape shape = new Shape(toolChoice, x1, x2, y1, y2, stroke, R, G, B, username);
		if (fillchoose.isSelected()) {
			shape.setFill(true);
		}else{
			shape.setFill(false);
		}
		try {
			server.broadcast(shape);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent ev) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent ev) {
		// TODO Auto-generated method stub

	}

}
