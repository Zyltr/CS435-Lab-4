import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Thu May 17 17:04:29 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class AddBusDialog extends JDialog
{
    private Connection connection;

    AddBusDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        connection = databaseConnection;
    }

    private void addBusButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( addBusButton ) && connection != null )
        {
            final String busID = busIdTextField.getText ();
            final String model = modelTextField.getText ();
            final String year = yearTextField.getText ();

            if ( busID.trim ().isEmpty () )
            {
                JOptionPane.showMessageDialog ( this, "Enter a Bus ID", "Bus ID Error", JOptionPane.ERROR_MESSAGE );
                return;
            }

            String query = "INSERT INTO Bus VALUES ( ?, ?, ? )";

			try ( PreparedStatement statement = connection.prepareStatement ( query ) )
			{
				statement.setString ( 1, busID );
				statement.setString ( 2, model );
				statement.setString ( 3, year );

				statement.execute ();

				String newBus = "Added New Bus ( " + ( new Date () ) + " )\n\tBus ID : " + busID + "\n\tModel : " + model + "\n\tYear : " + year;
				logTextArea.insert ( newBus + "\n\n", 0 );
			}
			catch ( SQLException exception )
			{
				exception.printStackTrace ();

				logTextArea.insert ( "Failed to add Bus ( " + ( new Date () ) + " )\n\n", 0 );
				JOptionPane.showMessageDialog ( this, "Failed to add Bus", "Add Bus", JOptionPane.ERROR_MESSAGE );
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
		JLabel busIdLabel = new JLabel();
		JLabel modelLabel = new JLabel();
		JLabel yearLabel = new JLabel();
		busIdTextField = new JTextField();
		modelTextField = new JTextField();
		yearTextField = new JTextField();
		addBusButton = new JButton();
		JLabel logLabel = new JLabel();
		JScrollPane logScrollPane = new JScrollPane();
		logTextArea = new JTextArea();
		JButton doneButton = new JButton();

		//======== this ========
		setMinimumSize(new Dimension(460, 22));
		setTitle("Add Bus");
		Container contentPane = getContentPane();

		//---- busIdLabel ----
		busIdLabel.setText("Bus ID");
		busIdLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- modelLabel ----
		modelLabel.setText("Model");
		modelLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- yearLabel ----
		yearLabel.setText("Year");
		yearLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- busIdTextField ----
		busIdTextField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- modelTextField ----
		modelTextField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- yearTextField ----
		yearTextField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- addBusButton ----
		addBusButton.setText("Add Bus");
		addBusButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		addBusButton.addActionListener(e -> addBusButtonActionPerformed(e));

		//---- logLabel ----
		logLabel.setText("Log");
		logLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//======== logScrollPane ========
		{
			logScrollPane.setBorder(new LineBorder(Color.lightGray));

			//---- logTextArea ----
			logTextArea.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
			logScrollPane.setViewportView(logTextArea);
		}

		//---- doneButton ----
		doneButton.setText("Done");
		doneButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		doneButton.addActionListener(e -> doneButtonActionPerformed());

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(20, 20, 20)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(logLabel)
							.addContainerGap(418, Short.MAX_VALUE))
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGap(0, 341, Short.MAX_VALUE)
									.addComponent(doneButton))
								.addComponent(addBusButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(busIdLabel)
										.addComponent(modelLabel)
										.addComponent(yearLabel))
									.addGap(18, 18, 18)
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(yearTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
										.addComponent(busIdTextField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
										.addComponent(modelTextField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)))
								.addComponent(logScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
							.addGap(20, 20, 20))))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(17, 17, 17)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(busIdLabel)
						.addComponent(busIdTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(modelLabel)
						.addComponent(modelTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(yearTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(yearLabel))
					.addGap(18, 18, 18)
					.addComponent(addBusButton)
					.addGap(18, 18, 18)
					.addComponent(logLabel)
					.addGap(18, 18, 18)
					.addComponent(logScrollPane, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addComponent(doneButton)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		pack();
		setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JTextField busIdTextField;
	private JTextField modelTextField;
	private JTextField yearTextField;
	private JButton addBusButton;
	private JTextArea logTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
