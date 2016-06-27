package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rmi.RemoteHelper;


public class MainFrame extends JFrame {
	private JPanel topPanel;
	private JTextArea textArea;
	private JPanel bottomPanel;
	private JTextArea inputArea;
	private JTextArea resultArea;
	private JLabel userLabel;

	public MainFrame() {
		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		frame.getContentPane().setLayout(new BorderLayout());
		
		//面板顶层panel，包括菜单栏和用户名显示
		topPanel = new JPanel();
		FlowLayout topLayout = (FlowLayout) topPanel.getLayout();
		topLayout.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(topPanel,BorderLayout.NORTH);

		//菜单栏
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu runMenu = new JMenu("Run");
		JMenu versionMenu = new JMenu("Version");
		JMenu userMenu = new JMenu("Login");
		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		menuBar.add(versionMenu);
		menuBar.add(userMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
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
		//frame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		loginMenuItem.addActionListener(new MenuItemActionListener());
		logoutMenuItem.addActionListener(new MenuItemActionListener());
		topPanel.add(menuBar);
		
		//用户名显示
		userLabel = new JLabel("请登录！");
		topPanel.add(userLabel);		

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
				textArea.setText("Open");
			} else if (cmd.equals("Save")) {
				textArea.setText("Save");
			} else if (cmd.equals("Run")) {
				resultArea.setText("Hello, result");
			} else if (cmd.equals("New")) {
				
			} else if (cmd.equals("log in")) {
				new loginDialog(this);
			} else if (cmd.equals("log out")) {
				
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = textArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	class loginDialog extends Dialog {
		public loginDialog(Dialog owner) {
			super(owner);
			// TODO Auto-generated constructor stub
		}
		JLabel userLabel = new JLabel("请输入用户名：");
		JLabel pasLabel = new JLabel("请输入密码：");
		JTextField userText = new JTextField(50);
		JTextField pasText = new JTextField(50);
		
		
	}
}
