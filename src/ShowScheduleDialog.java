import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Mon May 14 19:24:46 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class ShowScheduleDialog extends JDialog
{
    private final Connection connection;

    ShowScheduleDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        connection = databaseConnection;

        if ( connection != null )
		{
			final String query = "SELECT * FROM Trip T, TripOffering TT WHERE T.TripNumber=TT.TripNumber";

			try ( Statement statement = connection.createStatement ();
				  ResultSet resultSet = statement.executeQuery ( query ) )
			{
				DefaultComboBoxModel< String > startModel = new DefaultComboBoxModel<> ();
				DefaultComboBoxModel< String > destinationModel = new DefaultComboBoxModel<> ();
				DefaultComboBoxModel< String  > dateModel = new DefaultComboBoxModel<> ();

				while ( resultSet.next () )
				{
					startModel.addElement ( resultSet.getString ( Keys.StartLocationName.toString () ) );
					destinationModel.addElement ( resultSet.getString ( Keys.DestinationName.toString () ) );
					dateModel.addElement ( resultSet.getString ( Keys.Date.toString () ) );
				}

				startLocationComboBox.setModel ( startModel );
				startLocationComboBox.setMaximumRowCount ( startModel.getSize () );

				destinationNameComboBox.setModel ( destinationModel );
				destinationNameComboBox.setMaximumRowCount ( destinationModel.getSize () );

				dateComboBox.setModel ( dateModel );
				dateComboBox.setMaximumRowCount ( dateModel.getSize () );
			}
			catch ( SQLException exception )
			{
				exception.printStackTrace ();
			}
		}

        // Set custom Renderer
        scheduleList.setCellRenderer ( new ScheduleRenderer () );
    }

    private void findSchedulesButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( findSchedulesButton ) && connection != null )
        {
            // Queue Database here
            final String startLocationName = Objects.requireNonNull ( startLocationComboBox.getSelectedItem () ).toString ();
            final String destinationName = Objects.requireNonNull ( destinationNameComboBox.getSelectedItem () ).toString ();
            final String date = Objects.requireNonNull ( dateComboBox.getSelectedItem () ).toString ();

            if ( Debug.debug () )
			{
				System.out.println ( "ShowScheduleDialog : Search Parameters : " + startLocationName + ", " + destinationName + ", " + date );
			}

            // Precede WHERE parameters with a single quote on each side or else an error occurs
            final String select = "SELECT *";
			final String from = "FROM Trip T, TripOffering TT";
            final String where = "WHERE T.TripNumber=TT.TripNumber AND T.StartLocationName=? AND T.DestinationName=? AND TT.Date=?";
            final String query = select + ' ' + from + ' ' + where;

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
            	statement.setString ( 1, startLocationName );
            	statement.setString ( 2, destinationName );
				statement.setString ( 3, date );

				DefaultListModel< Properties > listModel = new DefaultListModel<> ();

            	try ( ResultSet resultSet = statement.executeQuery () )
				{
					while ( resultSet.next () )
					{
						Properties properties = new Properties ();
						properties.setProperty ( Keys.StartLocationName.toString (), resultSet.getString ( Keys.StartLocationName.toString () ) );
						properties.setProperty ( Keys.DestinationName.toString (), resultSet.getString ( Keys.DestinationName.toString () ) );
						properties.setProperty ( Keys.Date.toString (), resultSet.getString ( Keys.Date.toString () ) );
						properties.setProperty ( Keys.ScheduledStartTime.toString (), resultSet.getString ( Keys.ScheduledStartTime.toString () ) );
						properties.setProperty ( Keys.ScheduledArrivalTime.toString (), resultSet.getString ( Keys.ScheduledArrivalTime.toString () ) );
						properties.setProperty ( Keys.DriverName.toString (), resultSet.getString ( Keys.DriverName.toString () ) );
						properties.setProperty ( Keys.BusID.toString (), resultSet.getString ( Keys.BusID.toString () ) );

						if ( Debug.debug () )
						{
							System.out.println ( "ShowScheduleDialog : " + properties );
						}

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

    // Close Dialog
    private void doneButtonActionPerformed ()
    {
        this.dispose ();
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner non-commercial license
		JLabel startLocationNameLabel = new JLabel();
		JLabel dateLabel = new JLabel();
		JLabel destinationNameLabel = new JLabel();
		findSchedulesButton = new JButton();
		JButton doneButton = new JButton();
		JScrollPane scheduleScrollPane = new JScrollPane();
		scheduleList = new JList< Properties > ();
		startLocationComboBox = new JComboBox<> ();
		destinationNameComboBox = new JComboBox< String > ();
		dateComboBox = new JComboBox< String > ();

		//======== this ========
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(460, 500));
		setName("dialog");
		setTitle("Finding Schedules");
		Container contentPane = getContentPane();

		//---- startLocationNameLabel ----
		startLocationNameLabel.setText("Start Location Name");
		startLocationNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		startLocationNameLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- dateLabel ----
		dateLabel.setText("Date");
		dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		dateLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- destinationNameLabel ----
		destinationNameLabel.setText("Destination Name");
		destinationNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		destinationNameLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- findSchedulesButton ----
		findSchedulesButton.setText("Find Schedules");
		findSchedulesButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		findSchedulesButton.addActionListener(e -> findSchedulesButtonActionPerformed(e));

		//---- doneButton ----
		doneButton.setText("Done");
		doneButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		doneButton.addActionListener(e -> doneButtonActionPerformed());

		//======== scheduleScrollPane ========
		{
			scheduleScrollPane.setBorder(new EtchedBorder());

			//---- scheduleList ----
			scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scheduleList.setVisibleRowCount(0);
			scheduleList.setCellRenderer(null);
			scheduleList.setSelectionBackground(new Color(204, 204, 204));
			scheduleScrollPane.setViewportView(scheduleList);
		}

		//---- startLocationComboBox ----
		startLocationComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- destinationNameComboBox ----
		destinationNameComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- dateComboBox ----
		dateComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(20, 20, 20)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(startLocationNameLabel)
								.addComponent(destinationNameLabel)
								.addComponent(dateLabel))
							.addGap(18, 18, 18)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(startLocationComboBox)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGroup(contentPaneLayout.createParallelGroup()
										.addComponent(destinationNameComboBox, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)
										.addComponent(dateComboBox, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
									.addGap(0, 0, Short.MAX_VALUE))))
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addGap(0, 0, Short.MAX_VALUE)
							.addComponent(doneButton, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addComponent(findSchedulesButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scheduleScrollPane))
					.addGap(20, 20, 20))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(startLocationNameLabel)
						.addComponent(startLocationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(destinationNameLabel)
						.addComponent(destinationNameComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dateLabel)
						.addComponent(dateComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addComponent(findSchedulesButton)
					.addGap(18, 18, 18)
					.addComponent(scheduleScrollPane, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
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
	private JButton findSchedulesButton;
	private JList< Properties > scheduleList;
	private JComboBox< String > startLocationComboBox;
	private JComboBox< String > destinationNameComboBox;
	private JComboBox< String > dateComboBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
