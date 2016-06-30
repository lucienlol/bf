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
import service.ExecuteService;
import service.IOService;
import service.UserService;


public class MainFrame extends JFrame {
	//服务
	private IOService ioService;
	private UserService userService;
	private ExecuteService executeService;
	
	//组件
	private JFrame frame;	
	private JPanel topPanel;
	private JLabel userLabel;
	private JLabel fileLabel;
	private JLabel verLabel;
	private JTextArea textArea;
	private JPanel bottomPanel;
	private JTextArea inputArea;
	private JTextArea resultArea;
	private JMenu openMenu;
	private JMenu versionMenu;
	
	//成员变量
	private String user;
	private String fileName;
	private String version;
	private String code;
	private String input;
	private String result;
	
	public MainFrame() {
		// 创建窗体
		frame = new JFrame("BF Client");
		frame.getContentPane().setLayout(new BorderLayout());

		//菜单栏
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu runMenu = new JMenu("Run");
		versionMenu = new JMenu("Version");
		JMenu userMenu = new JMenu("Login");
		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		menuBar.add(versionMenu);
		menuBar.add(userMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		openMenu = new JMenu("Open");
		fileMenu.add(openMenu);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		JMenuItem executeMenuItem = new JMenuItem("Execute");
		runMenu.add(executeMenuItem);
		JMenuItem loginMenuItem = new JMenuItem("Log in");
		userMenu.add(loginMenuItem);
		JMenuItem logoutMenuItem = new JMenuItem("Log out");
		userMenu.add(logoutMenuItem);
		frame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new NewfileActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		executeMenuItem.addActionListener(new ExecuteActionListener());
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
			if (cmd.equals("Exit")) {
				frame.dispose();
			} else if (cmd.equals("Log in")) {
				new LoginDialog();
				if(user != null){					
					userLabel.setText("欢迎：" + user);
				}
			} else if (cmd.equals("Log out")) {
				clean();
				userLabel.setText("请登录！");
			}
		}
	}

	/**
	 * new事件相应
	 */
	class NewfileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(user == null) {
				JOptionPane.showMessageDialog(frame, "请先登录！", null, JOptionPane.INFORMATION_MESSAGE);
			} else {
				new FileDialog();
			}
		}		
	}
	
	/**
	 * 新建文件对话框
	 */
	class FileDialog {
		private JDialog jDialog;
		private JLabel jLabel;
		private JTextField jText;
		private JButton jButton;
		
		FileDialog() {
			jDialog = new JDialog(frame, null, true);
			jLabel = new JLabel("新建文件名：");
			jText = new JTextField(30);
			jButton = new JButton("新建");
			jButton.addActionListener(new FileActionListener());
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
		class FileActionListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileName = jText.getText();
				jDialog.dispose();
				fileLabel.setText("文件名：" + fileName);
				verLabel.setText("");
				textArea.setText("");
				code = null;
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
			try {
				ioService = RemoteHelper.getInstance().getIOService();
				if(code == null) {
					ioService.writeFile(newCode, user, fileName);
					String file = ioService.readFile(user, fileName);
					version = new FileList(file).getLastVersion();
					verLabel.setText("当前版本：" + version);
					
					JMenuItem fileItem = new JMenuItem(fileName);
					fileItem.addActionListener(new LoadFileListener());
					openMenu.add(fileItem);
					
					JMenuItem versionItem = new JMenuItem(version);
					versionItem.addActionListener(new LoadVersionListener());
					versionMenu.add(versionItem);
				} else if(!(code.equals(newCode))) {
					ioService.writeFile(newCode, user, fileName);
					String file = ioService.readFile(user, fileName);
					version = new FileList(file).getLastVersion();
					verLabel.setText("当前版本：" + version);
					
					JMenuItem versionItem = new JMenuItem(version);
					versionItem.addActionListener(new LoadVersionListener());
					versionMenu.add(versionItem);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}			
		}
	}	
	
	/**
	 * 登入对话框
	 */
	class LoginDialog {
		private JDialog jdialog;
		private JLabel userLabel;
		private JLabel pasLabel;
		private JTextField userText;
		private JTextField pasText;
		private JButton loginButton;
		
		LoginDialog(){
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
				userService = RemoteHelper.getInstance().getUserService();
				try {
					Boolean isLogined = userService.login(userId, password);
					if(isLogined) {
						JOptionPane.showMessageDialog(frame, "登陆成功", null, JOptionPane.INFORMATION_MESSAGE);
						clean();
						user = userId;
						ioService = RemoteHelper.getInstance().getIOService();
						ArrayList<String> fileArray = ioService.readFileList(user);
						for(int i = 0; i < fileArray.size(); i++) {
							JMenuItem item = new JMenuItem(fileArray.get(i));
							item.addActionListener(new LoadFileListener());
							openMenu.add(item);
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "登陆失败", null, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
	/**
	 * open菜单子文件菜单项响应事件
	 */
	class LoadFileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fileName = e.getActionCommand();
			fileLabel.setText("文件名：" + fileName);
			ioService = RemoteHelper.getInstance().getIOService();
			try {				
				String file = ioService.readFile(user, fileName);
				FileList fl = new FileList(file);
				System.out.println(file);
				version = fl.getLastVersion();
				verLabel.setText("当前版本：" + version);
				code = fl.getLastCode();
				textArea.setText(code);
				versionMenu.removeAll();
				ArrayList<String> versions = fl.getVersions();
				for(int i = 0; i < versions.size(); i++) {
					JMenuItem item = new JMenuItem(versions.get(i));
					item.addActionListener(new LoadVersionListener());
					versionMenu.add(item);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
		}	
	}

	/**
	 * version菜单子版本菜单项响应事件
	 */
	class LoadVersionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			version = e.getActionCommand();
			verLabel.setText("当前版本：" + version);
			String file;
			ioService = RemoteHelper.getInstance().getIOService();
			try {
				file = ioService.readFile(user, fileName);
				FileList fl = new FileList(file);
				code = fl.getCode(version);
				textArea.setText(code);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Execute事件响应
	 */
	class ExecuteActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			input = inputArea.getText();
			executeService = RemoteHelper.getInstance().getExecuteService();
			try {
				result = executeService.execute(code, input);
				resultArea.setText(result);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 清空函数，用于清空成员变量及菜单
	 */
	private void clean(){
		user = null;
		fileName = null;
		version = null;
		code = null;
		input = null;
		result = null;
		userLabel.setText("");
		fileLabel.setText("");
		verLabel.setText("");
		textArea.setText("");
		inputArea.setText("");
		resultArea.setText("");
		
		openMenu.removeAll();
		versionMenu.removeAll();
	}
}
