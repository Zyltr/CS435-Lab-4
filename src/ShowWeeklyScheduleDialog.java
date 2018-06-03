import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Thu May 17 21:32:58 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class ShowWeeklyScheduleDialog extends JDialog
{
    private Connection connection;

    ShowWeeklyScheduleDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        scheduleList.setCellRenderer ( new ScheduleRenderer () );

        connection = databaseConnection;

        if ( connection != null )
        {
            final String query = "SELECT DriverName FROM Driver";

            try ( Statement statement = connection.createStatement ();
                  ResultSet resultSet = statement.executeQuery ( query ) )
            {
                DefaultComboBoxModel <String> driverNameModel = new DefaultComboBoxModel<> ();

                while ( resultSet.next () )
                {
                    driverNameModel.addElement ( resultSet.getString ( Keys.DriverName.toString () ) );
                }

                driverNameComboBox.setModel ( driverNameModel );
                driverNameComboBox.setMaximumRowCount ( driverNameModel.getSize () );
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();
            }
        }
    }

    private void findButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( findButton ) && connection != null )
        {
            final String driverName = Objects.requireNonNull ( driverNameComboBox.getSelectedItem () ).toString ();

            final String referenceDateString = Utilities.sqlDateFormat.format ( dateSpinner.getValue () );
            final Date referenceDate = Date.valueOf ( referenceDateString );

            final String query = "SELECT * FROM Trip, TripOffering WHERE Trip.TripNumber=TripOffering.TripNumber AND DriverName=?";

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
                statement.setString ( 1, driverName );

                DefaultListModel< Properties > listModel = new DefaultListModel<> ();

                try ( ResultSet resultSet = statement.executeQuery () )
                {
                    while ( resultSet.next () )
                    {
                    	Date resultDate = Date.valueOf ( resultSet.getString ( Keys.Date.toString () ) );

                        if ( ! Utilities.isDateInCurrentWeek ( resultDate, referenceDate ) )
                        {
                            continue;
                        }

                        Properties properties = new Properties ();

                        properties.setProperty ( Keys.TripNumber.toString (), resultSet.getString ( Keys.TripNumber.toString () ) );
                        properties.setProperty ( Keys.Date.toString (), resultSet.getString ( Keys.Date.toString () ) );
                        properties.setProperty ( Keys.ScheduledStartTime.toString (), resultSet.getString ( Keys.ScheduledStartTime.toString () ) );
                        properties.setProperty ( Keys.ScheduledArrivalTime.toString (), resultSet.getString ( Keys.ScheduledArrivalTime.toString () ) );
                        properties.setProperty ( Keys.DriverName.toString (), resultSet.getString ( Keys.DriverName.toString () ) );
                        properties.setProperty ( Keys.BusID.toString (), resultSet.getString ( Keys.BusID.toString () ) );
                        properties.setProperty ( Keys.StartLocationName.toString (), resultSet.getString ( Keys.StartLocationName.toString () ) );
                        properties.setProperty ( Keys.DestinationName.toString (), resultSet.getString ( Keys.DestinationName.toString () ) );

                        listModel.addElement ( properties );
                    }
                }

                scheduleList.setModel ( listModel );
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();
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
		JLabel dateLabel = new JLabel();
		driverNameComboBox = new JComboBox();
		dateSpinner = new JSpinner();
		findButton = new JButton();
		JScrollPane scheduleScrollPane = new JScrollPane();
		scheduleList = new JList();
		JButton doneButton = new JButton();

		//======== this ========
		Container contentPane = getContentPane();

		//---- driverNameLabel ----
		driverNameLabel.setText("Driver Name");
		driverNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- dateLabel ----
		dateLabel.setText("Date");
		dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- driverNameComboBox ----
		driverNameComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		driverNameComboBox.setSelectedIndex(-1);

		//---- dateSpinner ----
		dateSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		dateSpinner.setModel(new SpinnerDateModel(new java.util.Date((System.currentTimeMillis()/60000)*60000), null, null, java.util.Calendar.DAY_OF_WEEK));

		//---- findButton ----
		findButton.setText("Find Weekly Schedules");
		findButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		findButton.addActionListener(e -> findButtonActionPerformed(e));

		//======== scheduleScrollPane ========
		{
			scheduleScrollPane.setBorder(new LineBorder(Color.lightGray));

			//---- scheduleList ----
			scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scheduleList.setCellRenderer(null);
			scheduleScrollPane.setViewportView(scheduleList);
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
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(driverNameLabel)
								.addComponent(dateLabel))
							.addGap(18, 18, 18)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(driverNameComboBox, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
									.addGap(20, 20, 20))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(dateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addContainerGap(211, Short.MAX_VALUE))))
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGap(0, 341, Short.MAX_VALUE)
									.addComponent(doneButton))
								.addComponent(findButton, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
								.addComponent(scheduleScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
							.addGap(20, 20, 20))))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(driverNameLabel)
						.addComponent(driverNameComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dateLabel)
						.addComponent(dateSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addComponent(findButton)
					.addGap(18, 18, 18)
					.addComponent(scheduleScrollPane, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
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
	private JComboBox driverNameComboBox;
	private JSpinner dateSpinner;
	private JButton findButton;
	private JList scheduleList;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
