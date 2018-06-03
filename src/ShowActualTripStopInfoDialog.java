import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Mon May 21 14:20:27 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class ShowActualTripStopInfoDialog extends JDialog
{
    private final Connection connection;
    private final ArrayList< Properties > tripOfferings = new ArrayList<> (), stops = new ArrayList<> ();

    ShowActualTripStopInfoDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        connection = databaseConnection;
        tripOfferingList.setCellRenderer ( new TripOfferingRenderer () );

        if ( connection != null )
        {
            final String tripOfferingQuery = "SELECT * FROM TripOffering";

			try ( Statement statement = connection.createStatement ();
				  ResultSet resultSet = statement.executeQuery ( tripOfferingQuery ) )
			{
				DefaultListModel < Properties > tripOfferingModel = new DefaultListModel<> ();

				while ( resultSet.next () )
				{
					Properties properties = new Properties ();
					properties.setProperty ( Keys.TripNumber.toString (), resultSet.getString ( Keys.TripNumber.toString () ) );
					properties.setProperty ( Keys.Date.toString (), resultSet.getString ( Keys.Date.toString () ) );
					properties.setProperty ( Keys.ScheduledStartTime.toString (), resultSet.getString ( Keys.ScheduledStartTime.toString () ) );
					properties.setProperty ( Keys.ScheduledArrivalTime.toString (), resultSet.getString ( Keys.ScheduledArrivalTime.toString () ) );
					properties.setProperty ( Keys.DriverName.toString (), resultSet.getString ( Keys.DriverName.toString () ) );
					properties.setProperty ( Keys.BusID.toString (), resultSet.getString ( Keys.BusID.toString () ) );

					tripOfferingModel.addElement ( properties );
					tripOfferings.add ( properties );
				}

				tripOfferingList.setModel ( tripOfferingModel );
				tripOfferingList.setSelectedIndex ( 0 );
			}
			catch ( SQLException exception )
			{
				// Do Something
			}

			final String stopQuery = "SELECT * FROM Stop";

			try ( Statement statement = connection.createStatement ();
				  ResultSet resultSet = statement.executeQuery ( stopQuery ) )
			{
				DefaultComboBoxModel< Object > stopsModel = new DefaultComboBoxModel<> ();

				while ( resultSet.next () )
				{
					Properties properties = new Properties ();
					properties.setProperty ( Keys.StopNumber.toString (), resultSet.getString ( Keys.StopNumber.toString () ) );
					properties.setProperty ( Keys.StopAddress.toString (), resultSet.getString ( Keys.StopAddress.toString () ) );

					stopsModel.addElement ( resultSet.getString ( Keys.StopNumber.toString () ) );
					stops.add ( properties );
				}

				stopNumberComboBox.setModel ( stopsModel );
				stopNumberComboBox.setMaximumRowCount ( stopsModel.getSize () );
			}
			catch ( SQLException exception )
			{
				// Do Something
			}
        }
    }

    private void addButtonActionPerformed ( ActionEvent actionEvent )
    {
    	if ( actionEvent.getSource ().equals ( addButton ) )
		{
			final Properties tripOffering = tripOfferings.get ( tripOfferingList.getSelectedIndex () );

			final String tripNumber= tripOffering.getProperty ( Keys.TripNumber.toString () );
			final String date = tripOffering.getProperty ( Keys.Date.toString () );
			final String scheduledStartTime = tripOffering.getProperty ( Keys.ScheduledStartTime.toString () );

			final Properties stop = stops.get ( stopNumberComboBox.getSelectedIndex () );

			final String stopNumber = stop.getProperty ( Keys.StopNumber.toString () );
			final String scheduledArrivalTime = tripOffering.getProperty ( Keys.ScheduledArrivalTime.toString () );

			final boolean actualStartTimeIsMorning = actualStartAMRadioButton.isSelected ();
			final Integer actualStartTimeHour =  Integer.valueOf ( actualStartTimeHourSpinner.getValue ().toString () ) + ( actualStartTimeIsMorning ? 0 : 12 );

			String actualStartTimeMinute = actualStartTimeMinuteSpinner.getValue ().toString ();
			actualStartTimeMinute = actualStartTimeMinute.length () == 1 ? "0" + actualStartTimeMinute : actualStartTimeMinute;

			final String actualStartTime = actualStartTimeHour + ":" + actualStartTimeMinute + ":00 " + ( actualStartTimeIsMorning ? "AM" : "PM" );

			final boolean actualArrivalTimeIsMorning = actualArrivalAMRadioButton.isSelected ();
			final Integer actualArrivalTimeHour =  Integer.valueOf ( actualArrivalTimeHourSpinner.getValue ().toString () ) + ( actualArrivalTimeIsMorning ? 0 : 12 );

			String actualArrivalTimeMinute = actualArrivalTimeMinuteSpinner.getValue ().toString ();
			actualArrivalTimeMinute = actualArrivalTimeMinute.length () == 1 ? "0" + actualArrivalTimeMinute : actualArrivalTimeMinute;

			final String actualArrivalTime = actualArrivalTimeHour + ":" + actualArrivalTimeMinute + ":00 " + ( actualArrivalTimeIsMorning ? "AM" : "PM" );

			final String passengersIn = passengersInSpinner.getValue ().toString ();
			final String passengersOut = passengersOutSpinner.getValue ().toString ();

			String query = "INSERT INTO ActualTripStopInfo VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

			try ( PreparedStatement statement = connection.prepareStatement ( query ) )
			{
				statement.setString ( 1, tripNumber );
				statement.setString ( 2, date );
				statement.setString ( 3, scheduledStartTime );
				statement.setString ( 4, stopNumber );
				statement.setString ( 5, scheduledArrivalTime );
				statement.setString ( 6, actualStartTime );
				statement.setString ( 7, actualArrivalTime );
				statement.setString ( 8, passengersIn );
				statement.setString ( 9, passengersOut );

				statement.execute ();

				String newBus = "Added Actual Trip Stop Info ( " + ( new java.util.Date () ) + " )";
				logTextArea.insert ( newBus + "\n\n", 0 );
			}
			catch ( SQLException exception )
			{
				exception.printStackTrace ();

				logTextArea.insert ( "Failed to add Bus ( " + ( new Date () ) + " )\n\n", 0 );
				JOptionPane.showMessageDialog ( this, "Failed to Add", "Failed", JOptionPane.ERROR_MESSAGE );
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
		JLabel tripOfferingLabel = new JLabel();
		JLabel stopNumberLabel = new JLabel();
		JLabel actualStartTimeLabel = new JLabel();
		JLabel actualArrivalTimeLabel = new JLabel();
		JLabel numberPassengersInLabel = new JLabel();
		JLabel numberPassengersOutLabel = new JLabel();
		JScrollPane tripOfferingScrollPane = new JScrollPane();
		tripOfferingList = new JList();
		stopNumberComboBox = new JComboBox();
		actualStartTimeHourSpinner = new JSpinner();
		actualStartTimeMinuteSpinner = new JSpinner();
		actualArrivalTimeHourSpinner = new JSpinner();
		actualArrivalTimeMinuteSpinner = new JSpinner();
		passengersInSpinner = new JSpinner();
		passengersOutSpinner = new JSpinner();
		addButton = new JButton();
		JLabel logLabel = new JLabel();
		JScrollPane logScrollPane = new JScrollPane();
		logTextArea = new JTextArea();
		JButton doneButton = new JButton();
		actualStartAMRadioButton = new JRadioButton();
		JRadioButton actualStartPMRadioButton = new JRadioButton();
		actualArrivalAMRadioButton = new JRadioButton();
		JRadioButton actualArrivalPMRadioButton = new JRadioButton();

		//======== this ========
		setModal(true);
		setTitle("Actual Trip Stop Info");
		Container contentPane = getContentPane();

		//---- tripOfferingLabel ----
		tripOfferingLabel.setText("Trip Offering");
		tripOfferingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		tripOfferingLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- stopNumberLabel ----
		stopNumberLabel.setText("Stop Number");
		stopNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		stopNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- actualStartTimeLabel ----
		actualStartTimeLabel.setText("Actual Start Time");
		actualStartTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		actualStartTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- actualArrivalTimeLabel ----
		actualArrivalTimeLabel.setText("Actual Arrival Time");
		actualArrivalTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		actualArrivalTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- numberPassengersInLabel ----
		numberPassengersInLabel.setText("# of Passengers In");
		numberPassengersInLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		numberPassengersInLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- numberPassengersOutLabel ----
		numberPassengersOutLabel.setText("# of Passengers Out");
		numberPassengersOutLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		numberPassengersOutLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//======== tripOfferingScrollPane ========
		{
			tripOfferingScrollPane.setBorder(new LineBorder(Color.lightGray));
			tripOfferingScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			//---- tripOfferingList ----
			tripOfferingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tripOfferingList.setVisibleRowCount(6);
			tripOfferingList.setCellRenderer(null);
			tripOfferingList.setSelectionBackground(Color.lightGray);
			tripOfferingScrollPane.setViewportView(tripOfferingList);
		}

		//---- stopNumberComboBox ----
		stopNumberComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- actualStartTimeHourSpinner ----
		actualStartTimeHourSpinner.setModel(new SpinnerNumberModel(12, 1, 12, 1));
		actualStartTimeHourSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- actualStartTimeMinuteSpinner ----
		actualStartTimeMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		actualStartTimeMinuteSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- actualArrivalTimeHourSpinner ----
		actualArrivalTimeHourSpinner.setModel(new SpinnerNumberModel(12, 1, 12, 1));
		actualArrivalTimeHourSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- actualArrivalTimeMinuteSpinner ----
		actualArrivalTimeMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		actualArrivalTimeMinuteSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- passengersInSpinner ----
		passengersInSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		passengersInSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- passengersOutSpinner ----
		passengersOutSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		passengersOutSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- addButton ----
		addButton.setText("Add");
		addButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		addButton.addActionListener(e -> addButtonActionPerformed(e));

		//---- logLabel ----
		logLabel.setText("Log");
		logLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//======== logScrollPane ========
		{
			logScrollPane.setBorder(new LineBorder(Color.lightGray));
			logScrollPane.setViewportView(logTextArea);
		}

		//---- doneButton ----
		doneButton.setText("Done");
		doneButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		doneButton.addActionListener(e -> doneButtonActionPerformed());

		//---- actualStartAMRadioButton ----
		actualStartAMRadioButton.setText("A.M");
		actualStartAMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		actualStartAMRadioButton.setSelected(true);

		//---- actualStartPMRadioButton ----
		actualStartPMRadioButton.setText("P.M");
		actualStartPMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));

		//---- actualArrivalAMRadioButton ----
		actualArrivalAMRadioButton.setText("A.M");
		actualArrivalAMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		actualArrivalAMRadioButton.setSelected(true);

		//---- actualArrivalPMRadioButton ----
		actualArrivalPMRadioButton.setText("P.M");
		actualArrivalPMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
							.addGap(20, 20, 20)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(logScrollPane, GroupLayout.Alignment.TRAILING)
								.addComponent(addButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
										.addComponent(actualStartTimeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(stopNumberLabel, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
										.addComponent(numberPassengersInLabel)
										.addComponent(actualArrivalTimeLabel)
										.addComponent(numberPassengersOutLabel))
									.addGap(18, 18, 18)
									.addGroup(contentPaneLayout.createParallelGroup()
										.addComponent(stopNumberComboBox)
										.addGroup(contentPaneLayout.createSequentialGroup()
											.addGroup(contentPaneLayout.createParallelGroup()
												.addGroup(contentPaneLayout.createSequentialGroup()
													.addComponent(actualStartTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(actualStartTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(actualStartAMRadioButton)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(actualStartPMRadioButton))
												.addGroup(contentPaneLayout.createSequentialGroup()
													.addComponent(actualArrivalTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(actualArrivalTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(actualArrivalAMRadioButton)
													.addGap(6, 6, 6)
													.addComponent(actualArrivalPMRadioButton))
												.addComponent(passengersInSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(passengersOutSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGap(0, 60, Short.MAX_VALUE))))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(logLabel)
									.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(tripOfferingLabel)
									.addGap(18, 18, 18)
									.addComponent(tripOfferingScrollPane))))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addContainerGap(361, Short.MAX_VALUE)
							.addComponent(doneButton)))
					.addGap(20, 20, 20))
		);
		contentPaneLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {actualArrivalTimeLabel, actualStartTimeLabel, numberPassengersInLabel, numberPassengersOutLabel, tripOfferingLabel});
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(21, 21, 21)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(tripOfferingLabel)
							.addGap(0, 172, Short.MAX_VALUE))
						.addComponent(tripOfferingScrollPane, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(stopNumberLabel)
						.addComponent(stopNumberComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(actualStartTimeLabel)
						.addComponent(actualStartTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(actualStartTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(actualStartAMRadioButton)
						.addComponent(actualStartPMRadioButton))
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(contentPaneLayout.createParallelGroup()
							.addComponent(actualArrivalAMRadioButton)
							.addComponent(actualArrivalPMRadioButton))
						.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(actualArrivalTimeLabel)
							.addComponent(actualArrivalTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(actualArrivalTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(numberPassengersInLabel)
						.addComponent(passengersInSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(numberPassengersOutLabel)
						.addComponent(passengersOutSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24, 24, 24)
					.addComponent(addButton)
					.addGap(18, 18, 18)
					.addComponent(logLabel)
					.addGap(18, 18, 18)
					.addComponent(logScrollPane, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addComponent(doneButton)
					.addGap(20, 20, 20))
		);
		pack();
		setLocationRelativeTo(getOwner());

		//---- actualStartTimeGroup ----
		ButtonGroup actualStartTimeGroup = new ButtonGroup();
		actualStartTimeGroup.add(actualStartAMRadioButton);
		actualStartTimeGroup.add(actualStartPMRadioButton);

		//---- actualArrivalTimeGroup ----
		ButtonGroup actualArrivalTimeGroup = new ButtonGroup();
		actualArrivalTimeGroup.add(actualArrivalAMRadioButton);
		actualArrivalTimeGroup.add(actualArrivalPMRadioButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JList tripOfferingList;
	private JComboBox stopNumberComboBox;
	private JSpinner actualStartTimeHourSpinner;
	private JSpinner actualStartTimeMinuteSpinner;
	private JSpinner actualArrivalTimeHourSpinner;
	private JSpinner actualArrivalTimeMinuteSpinner;
	private JSpinner passengersInSpinner;
	private JSpinner passengersOutSpinner;
	private JButton addButton;
	private JTextArea logTextArea;
	private JRadioButton actualStartAMRadioButton;
	private JRadioButton actualArrivalAMRadioButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
