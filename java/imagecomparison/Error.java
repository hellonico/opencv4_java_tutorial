package imagecomparison;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Error extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	public Error() {
		setBounds(100, 100, 312, 148);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(104, 75, 89, 23);
		contentPanel.add(btnOk);
	}

	public void dis_error(String error) {
		JLabel lblNewLabel = new JLabel("ERROR: " + error);
		lblNewLabel.setBounds(10, 42, 276, 14);
		contentPanel.add(lblNewLabel);

	}
}
