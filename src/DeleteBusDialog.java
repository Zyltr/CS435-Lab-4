import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Thu May 17 17:27:44 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class DeleteBusDialog extends JDialog
{
    private Connection connection;
    private ArrayList< Properties > buses = new ArrayList<> ();

    DeleteBusDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        connection = databaseConnection;

        if ( connection != null )
        {
            final String query = "SELECT * FROM Bus";

            try ( Statement statement = connection.createStatement (); ResultSet resultSet = statement.executeQuery ( query ) )
            {
                DefaultComboBoxModel< Object > busIdModel = new DefaultComboBoxModel<> ();

                while ( resultSet.next () )
                {
                    Properties properties = new Properties ();
                    properties.setProperty ( Keys.BusID.toString (), resultSet.getString ( Keys.BusID.toString () ) );
                    properties.setProperty ( Keys.Model.toString (), resultSet.getString ( Keys.Model.toString () ) );
                    properties.setProperty ( Keys.Year.toString (), resultSet.getString ( Keys.Year.toString () ) );

                    busIdModel.addElement ( properties.getProperty ( Keys.BusID.toString () ) );

                    buses.add ( properties );
                }

                busIdComboBox.setModel ( busIdModel );
                busIdComboBox.setMaximumRowCount ( busIdModel.getSize () );
                modelDataLabel.setText ( buses.get ( busIdComboBox.getSelectedIndex () ).getProperty ( Keys.Model.toString () ) );
                yearDataLabel.setText ( buses.get ( busIdComboBox.getSelectedIndex () ).getProperty ( Keys.Year.toString () ) );
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();
            }
        }
        else
        {
            // Throw some kind of error
        }
    }

    private void busIdComboBoxItemStateChanged ()
    {
        int index = busIdComboBox.getSelectedIndex ();
        String model = buses.get ( index ).getProperty ( Keys.Model.toString () );
        String year = buses.get ( index ).getProperty ( Keys.Year.toString () );

        modelDataLabel.setText ( model );
        yearDataLabel.setText ( year );
    }

    private void deleteBusButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( deleteBusButton ) && connection != null )
        {
            final String busId =  Objects.requireNonNull ( busIdComboBox.getSelectedItem () ).toString ();

            final String query = "DELETE FROM Bus WHERE BusID=?";

            String model = buses.get ( busIdComboBox.getSelectedIndex () ).getProperty ( Keys.Model.toString () );
            String year = buses.get ( busIdComboBox.getSelectedIndex () ).getProperty ( Keys.Year.toString () );

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
                statement.setString ( 1, busId );

                statement.execute ();

                String deletedBus = "Deleted Bus ( " + ( new Date () ) + " )\n\tBus ID : " + busId + "\n\tModel : " + model + "\n\tYear : " + year;
                logTextArea.insert ( deletedBus + "\n\n", 0 );

                busIdComboBox.removeItemAt ( busIdComboBox.getSelectedIndex () );
                busIdComboBoxItemStateChanged ();
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();

                logTextArea.insert ( "Failed to delete Bus ( " + ( new Date () ) + " )\n\tBus ID : " + busId + "\n\tModel : " + model + "\n\tYear : " + year, 0 );
                JOptionPane.showMessageDialog ( this, "Failed to delete Bus", "Delete Bus", JOptionPane.ERROR_MESSAGE );
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
		busIdComboBox = new JComboBox();
		deleteBusButton = new JButton();
		JLabel logLabel = new JLabel();
		JScrollPane logScrollPane = new JScrollPane();
		logTextArea = new JTextArea();
		JButton doneButton = new JButton();
		modelDataLabel = new JLabel();
		yearDataLabel = new JLabel();

		//======== this ========
		setMinimumSize(new Dimension(460, 460));
		setName("dialog");
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

		//---- busIdComboBox ----
		busIdComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		busIdComboBox.addItemListener(e -> busIdComboBoxItemStateChanged());

		//---- deleteBusButton ----
		deleteBusButton.setText("Delete Bus");
		deleteBusButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		deleteBusButton.addActionListener(e -> deleteBusButtonActionPerformed(e));

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

		//---- modelDataLabel ----
		modelDataLabel.setText("-");
		modelDataLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- yearDataLabel ----
		yearDataLabel.setText("-");
		yearDataLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

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
								.addComponent(logScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
								.addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
									.addComponent(busIdLabel)
									.addGap(18, 18, 18)
									.addComponent(busIdComboBox, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
								.addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addComponent(yearLabel)
									.addGap(18, 18, 18)
									.addComponent(yearDataLabel, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(modelLabel)
									.addGap(19, 19, 19)
									.addComponent(modelDataLabel, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
								.addComponent(deleteBusButton, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
							.addGap(20, 20, 20))))
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap(362, Short.MAX_VALUE)
					.addComponent(doneButton)
					.addGap(19, 19, 19))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(busIdLabel)
						.addComponent(busIdComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(modelLabel)
						.addComponent(modelDataLabel))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(yearDataLabel)
						.addComponent(yearLabel))
					.addGap(18, 18, 18)
					.addComponent(deleteBusButton)
					.addGap(18, 18, 18)
					.addComponent(logLabel)
					.addGap(18, 18, 18)
					.addComponent(logScrollPane, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
					.addGap(18, 18, Short.MAX_VALUE)
					.addComponent(doneButton)
					.addGap(20, 20, 20))
		);
		pack();
		setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JComboBox busIdComboBox;
	private JButton deleteBusButton;
	private JTextArea logTextArea;
	private JLabel modelDataLabel;
	private JLabel yearDataLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
