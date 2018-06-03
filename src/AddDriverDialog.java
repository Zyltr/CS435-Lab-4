import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Wed May 16 17:09:30 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class AddDriverDialog extends JDialog
{
    private final Connection connection;

    AddDriverDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        connection = databaseConnection;
    }

    private void addDriverButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( addDriverButton ) && connection != null )
        {
            final String driverName = driverNameTextField.getText ();
            final String driverTelephone = driverTelephoneTextField.getText ();

            // Check to see that parameters are not empty
            if ( driverName.trim ().isEmpty () )
            {
                JOptionPane.showMessageDialog ( this, "Enter a name", "Name Error", JOptionPane.ERROR_MESSAGE );
                return;
            }

            String query = "INSERT INTO Driver VALUES ( ?, ? )";

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
                statement.setString ( 1, driverName );
                statement.setString ( 2, driverTelephone );

                statement.execute ();

                String newDriver = "Added New Driver ( " + ( new Date () ) + " )\n\tDriver Name : " + driverName + "\n\tDriver Telephone : " + driverTelephone;
                logTextArea.insert ( newDriver + "\n\n", 0 );
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();

                logTextArea.insert ( "Failed to add Driver ( " + ( new Date () ) + " )\n\n", 0 );
                JOptionPane.showMessageDialog ( this, "Failed to add Driver", "Add Driver", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    private void doneButtonActionPerformed ()
    {
        this.dispose ();
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner non-commercial license
		JLabel driverNameLabel = new JLabel();
		JLabel driverTelephoneLabel = new JLabel();
		driverNameTextField = new JTextField();
		driverTelephoneTextField = new JTextField();
		addDriverButton = new JButton();
		JButton doneButton = new JButton();
		JLabel logLabel = new JLabel();
		JScrollPane logScrollPane = new JScrollPane();
		logTextArea = new JTextArea();

		//======== this ========
		setTitle("Add Driver");
		setModal(true);
		Container contentPane = getContentPane();

		//---- driverNameLabel ----
		driverNameLabel.setText("Driver Name");
		driverNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		driverNameLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- driverTelephoneLabel ----
		driverTelephoneLabel.setText("Driver Telephone");
		driverTelephoneLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		driverTelephoneLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- driverNameTextField ----
		driverNameTextField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- driverTelephoneTextField ----
		driverTelephoneTextField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- addDriverButton ----
		addDriverButton.setText("Add Driver");
		addDriverButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		addDriverButton.addActionListener(e -> addDriverButtonActionPerformed(e));

		//---- doneButton ----
		doneButton.setText("Done");
		doneButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		doneButton.addActionListener(e -> doneButtonActionPerformed());

		//---- logLabel ----
		logLabel.setText("Log");
		logLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		logLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//======== logScrollPane ========
		{
			logScrollPane.setBorder(null);

			//---- logTextArea ----
			logTextArea.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
			logTextArea.setEditable(false);
			logTextArea.setBorder(new LineBorder(Color.lightGray));
			logScrollPane.setViewportView(logTextArea);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addContainerGap(361, Short.MAX_VALUE)
							.addComponent(doneButton))
						.addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
							.addGap(20, 20, 20)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(logScrollPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
											.addComponent(driverNameLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addGap(0, 1, Short.MAX_VALUE))
										.addComponent(driverTelephoneLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(contentPaneLayout.createParallelGroup()
										.addComponent(driverNameTextField, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
										.addComponent(driverTelephoneTextField, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)))
								.addComponent(addDriverButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(logLabel)
									.addGap(0, 0, Short.MAX_VALUE)))))
					.addGap(20, 20, 20))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(17, 17, 17)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(driverNameLabel)
						.addComponent(driverNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(driverTelephoneTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(driverTelephoneLabel))
					.addGap(18, 18, 18)
					.addComponent(addDriverButton)
					.addGap(18, 18, 18)
					.addComponent(logLabel)
					.addGap(18, 18, 18)
					.addComponent(logScrollPane, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
					.addGap(18, 18, 18)
					.addComponent(doneButton)
					.addGap(20, 20, 20))
		);
		pack();
		setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JTextField driverNameTextField;
	private JTextField driverTelephoneTextField;
	private JButton addDriverButton;
	private JTextArea logTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
