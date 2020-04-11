package museumWin;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Execute {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setBounds(100, 100, 700, 300);
					frame.setTitle("National Museum of Georgia");
//					frame.pack();
					frame.setLocationByPlatform(true);
					frame.setVisible(true);
					DbWork.tableMetaData();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
