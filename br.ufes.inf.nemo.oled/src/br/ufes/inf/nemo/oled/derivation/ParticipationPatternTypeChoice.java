package br.ufes.inf.nemo.oled.derivation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import br.ufes.inf.nemo.oled.DiagramManager;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.Font;

public class ParticipationPatternTypeChoice extends JDialog {



	private final JPanel contentPanel = new JPanel();
	JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("The type is derived by a PART-WHOLE relation (ex: Team Member, Team)");
	JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("The derivation is BIDIRECTIONAL (ex: Husband, Wife)");
	JRadioButton rdbtnNewRadioButton = new JRadioButton("The derivation is UNIDIRECTIONAL (ex: Manager, Department)");
	private DiagramManager dman;
	Point2D.Double location = new Point2D.Double();
	/**
	 * Launch the application.
	 */
	
	

	/**
	 * Create the dialog.
	 */
	public ParticipationPatternTypeChoice(DiagramManager dm) {
		dman=dm;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ParticipationPatternTypeChoice.class.getResource("/resources/icons/x16/sitemap.png")));
		setTitle("Participation Options");
		setBounds(100, 100, 486, 230);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(245, 245, 245));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		ButtonGroup bt = new ButtonGroup();
		rdbtnNewRadioButton.setSelected(true);
		
		
		rdbtnNewRadioButton.setBackground(new Color(245, 245, 245));
		bt.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1.setBackground(new Color(245, 245, 245));
		bt.add(rdbtnNewRadioButton_1);
		
		
		rdbtnNewRadioButton_2.setBackground(new Color(245, 245, 245));
		bt.add(rdbtnNewRadioButton_2);
		
		JTextArea txtrWhatsTheType = new JTextArea();
		txtrWhatsTheType.setBackground(new Color(245, 245, 245));
		txtrWhatsTheType.setEditable(false);
		txtrWhatsTheType.setForeground(Color.DARK_GRAY);
		txtrWhatsTheType.setText("What's the type of the derivation by participation?\r\n");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(27)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnNewRadioButton_2)
								.addComponent(rdbtnNewRadioButton_1)
								.addComponent(rdbtnNewRadioButton)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(98)
							.addComponent(txtrWhatsTheType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtrWhatsTheType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(rdbtnNewRadioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNewRadioButton_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNewRadioButton_2)
					.addGap(56))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(245, 245, 245));
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						JDialog dialog = null;
						
						if(rdbtnNewRadioButton.isSelected()){
							dialog = new UnidirectionalParticipationPattern(dman);
							dman.setCenterDialog(dialog);
							((UnidirectionalParticipationPattern) dialog).setPosition(new Point2D.Double(location.x, location.y));
							dialog.setModal(true);
							dialog.setVisible(true);
						}else if(rdbtnNewRadioButton_1.isSelected()){
							dialog = new BidirectionalParticipationPattern(dman);
							dman.setCenterDialog(dialog);
							((BidirectionalParticipationPattern) dialog).setPosition(new Point2D.Double(location.x, location.y));
							dialog.setModal(true);
							dialog.setVisible(true);
						}else if(rdbtnNewRadioButton_2.isSelected()){
							dialog = new CompositionParticipationPattern(dman);
							dman.setCenterDialog(dialog);
							((CompositionParticipationPattern) dialog).setPosition(new Point2D.Double(location.x, location.y));
							dialog.setModal(true);
							dialog.setVisible(true);
						}
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public void setPosition(double x, double y) {
		// TODO Auto-generated method stub
		location.x= x;
		location.y= y;
		
	}
}