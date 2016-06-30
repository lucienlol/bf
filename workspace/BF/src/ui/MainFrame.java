package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import file.File;
import file.FileList;
import rmi.RemoteHelper;
import service.IOService;
import service.UserService;


public class MainFrame extends JFrame {
	private RemoteHelper remoteHelper;
	private IOService ioService;
	private UserService userService;
	private JPanel topPanel;
	private JTextArea textArea;
	private JPanel bottomPanel;
	private JTextArea inputArea;
	private JTextArea resultArea;
	private JLabel userLabel;
	private JLabel fileLabel;
	private JLabel verLabel;
	private JFrame frame;	
	private String user;
	private String fileName;
	private String version;
	private String code;
	private JMenu openMenu;
	
	public MainFrame() {
		// 创建窗体
		frame = new JFrame("BF Client");
		frame.getContentPane().setLayout(new BorderLayout());

		//菜单栏
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu runMenu = new JMenu("Run");
		JMenu versionMenu = new JMenu("Version");
		JMenu userMenu = new JMenu("Login");
		openMenu = new JMenu("Open");
		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		menuBar.add(versionMenu);
		menuBar.add(userMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenu);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		JMenuItem runMenuItem = new JMenuItem("Run");
		runMenu.add(runMenuItem);
		JMenuItem loginMenuItem = new JMenuItem("log in");
		userMenu.add(loginMenuItem);
		JMenuItem logoutMenuItem = new JMenuItem("log out");
		userMenu.add(logoutMenuItem);
		frame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		loginMenuItem.addActionListener(new MenuItemActionListener());
		logoutMenuItem.addActionListener(new MenuItemActionListener());
		
		//面板顶层panel，包括用户名、文件名以及文件版本号显示
		topPanel = new JPanel();
		FlowLayout topLayout = (FlowLayout) topPanel.getLayout();
		topLayout.setAlignment(FlowLayout.LEFT);
		userLabel = new JLabel("请登录！");
		fileLabel = new JLabel();
		verLabel = new JLabel();
		topPanel.add(userLabel);
		topPanel.add(fileLabel);
		topPanel.add(verLabel);
		frame.getContentPane().add(topPanel,BorderLayout.NORTH);

		//代码输入框
		textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(textArea, BorderLayout.CENTER);

		//面板底层panel，包括输入栏和输出栏
		bottomPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) bottomPanel.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		//输入参数
		inputArea = new JTextArea(7,20);
		inputArea.setText("input");
		inputArea.setBackground(Color.LIGHT_GRAY);
		bottomPanel.add(inputArea);
		
		// 显示结果
		resultArea = new JTextArea(7,20);
		resultArea.setText("result");
		inputArea.setBackground(Color.WHITE);
		bottomPanel.add(resultArea);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}

	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				
			} else if (cmd.equals("Run")) {
				resultArea.setText("Hello, result");
			} else if (cmd.equals("New")) {
				if(user == null) {
					JOptionPane.showMessageDialog(frame, "请先登录！", null, JOptionPane.INFORMATION_MESSAGE);
				} else {
					new fileDialog();
				}
			} else if (cmd.equals("log in")) {
				new loginDialog();
				if(user != null){					
					userLabel.setText("欢迎：" + user);
				}
			} else if (cmd.equals("log out")) {
				
			}
		}
	}

	/**
	 * save事件响应
	 */
	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String newCode = textArea.getText();
			if(!((code != null)&&(!(code.equals(newCode))))){
				try {
					ioService = RemoteHelper.getInstance().getIOService();
					ioService.writeFile(newCode, user, fileName);
					String file = ioService.readFile(user, fileName);
					System.out.println(file);
					version = new FileList(file).getLastVersion();
					verLabel.setText("当前版本：" + version);
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}			
			}
		}
	}	
	
	/**
	 * 登入对话框
	 */
	class loginDialog {
		private JDialog jdialog;
		private JLabel userLabel;
		private JLabel pasLabel;
		private JTextField userText;
		private JTextField pasText;
		private JButton loginButton;
		
		loginDialog(){
			jdialog = new JDialog(frame, "登陆窗口", true);
			userLabel = new JLabel("请输入用户名：");
			pasLabel = new JLabel("请输入密码：");
			userText = new JTextField(30);
			pasText = new JTextField(30);
			loginButton = new JButton("登入");
			loginButton.addActionListener(new LoginActionListener());
			jdialog.setSize(300,170);
			jdialog.setLayout(null);
			Container cp = jdialog.getContentPane();
			userLabel.setBounds(20, 10, 100, 30);
			userText.setBounds(130, 10, 150, 30);
			pasLabel.setBounds(20, 50, 100, 30);
			pasText.setBounds(130, 50, 150, 30);
			loginButton.setBounds(120, 100, 60, 30);
			cp.add(userLabel);
			cp.add(userText);
			cp.add(pasLabel);
			cp.add(pasText);
			cp.add(loginButton);
			jdialog.setVisible(true);
		}
		
		/**
		 * 登入按钮响应事件
		 */
		class LoginActionListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String userId = userText.getText();
				String password = pasText.getText();
				jdialog.dispose();
				remoteHelper = RemoteHelper.getInstance();
				userService = remoteHelper.getUserService();
				try {
					Boolean isLogined = userService.login(userId, password);
					if(isLogined) {
						JOptionPane.showMessageDialog(frame, "登陆成功", null, JOptionPane.INFORMATION_MESSAGE);
						user = userId;
						openFile();
					}
					else {
						JOptionPane.showMessageDialog(frame, "登陆失败", null, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
	}
	
	/**
	 * 新建文件对话框
	 */
	class fileDialog {
		private JDialog jDialog;
		private JLabel jLabel;
		private JTextField jText;
		private JButton jButton;
		
		fileDialog() {
			jDialog = new JDialog(frame, null, true);
			jLabel = new JLabel("新建文件名：");
			jText = new JTextField(30);
			jButton = new JButton("新建");
			jButton.addActionListener(new fileActionListener());
			jDialog.setSize(300,110);
			jDialog.setLayout(null);
			Container cp = jDialog.getContentPane();
			jLabel.setBounds(20, 10, 100, 25);
			jText.setBounds(130, 10, 150, 25);
			jButton.setBounds(120, 40, 60, 30);
			cp.add(jLabel);
			cp.add(jText);
			cp.add(jButton);
			jDialog.setVisible(true);
		}
		
		/**
		 * 新建文件按钮响应事件
		 */
		class fileActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileName = jText.getText();
				jDialog.dispose();
				newFile();
				System.out.println("fileAction");
			}
		}
	}
	
	/**
	 * 新建文件后续响应
	 */
	private void newFile(){
		textArea.setText("new");
		fileLabel.setText("文件名：" + fileName);
		System.out.println("new");
	}
	
	/**
	 * 登入后，根据用户名添加open菜单子按钮
	 */
	private void openFile() {
		ioService = RemoteHelper.getInstance().getIOService();
		
		try {
			ArrayList<String> fileArray = ioService.readFileList(user);
			for(int i = 0; i < fileArray.size(); i++) {
				JMenuItem item = new JMenuItem(fileArray.get(i));
				item.addActionListener(new loadFileListener());
				openMenu.add(item);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class loadFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fileName = e.getActionCommand();
			fileLabel.setText(fileName);
			ioService = RemoteHelper.getInstance().getIOService();
			try {				
				String file = ioService.readFile(user, fileName);
				FileList fl = new FileList(file);
				System.out.println(file);
				version = fl.getLastVersion();
				verLabel.setText("当前版本：" + version);
				textArea.setText(fl.getLastCode());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}

}
