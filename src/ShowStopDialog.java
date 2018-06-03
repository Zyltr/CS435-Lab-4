import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Properties;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Tue May 15 17:24:31 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class ShowStopDialog extends JDialog
{
    private final Connection connection;

    ShowStopDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        connection = databaseConnection;

		// Set custom Renderer
		stopList.setCellRenderer ( new StopRenderer () );

        if ( connection != null )
		{
			// Populate JComboBox with Trip Numbers from Database
			final String query = "SELECT TripNumber FROM Trip";

			try ( Statement statement = connection.createStatement ();
				  ResultSet resultSet = statement.executeQuery ( query ) )
			{
				DefaultComboBoxModel <Integer> comboBoxModel = new DefaultComboBoxModel<> ();

				while ( resultSet.next () )
				{
					comboBoxModel.addElement ( resultSet.getInt ( Keys.TripNumber.toString () ) );
				}

				tripNumberComboBox.setModel ( comboBoxModel );
				tripNumberComboBox.setMaximumRowCount ( comboBoxModel.getSize () );
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

    private void findStopButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( findStopButton ) && connection != null )
        {
			final int selectedValue =  ( int ) tripNumberComboBox.getSelectedItem ();

        	final String select = "SELECT S.StopNumber, S.StopAddress, TSI.SequenceNumber, TSI.DrivingTime";
        	final String from = "FROM Trip T, Stop S, TripStopInfo TSI";
        	final String where = "WHERE T.TripNumber=TSI.TripNumber AND TSI.StopNumber=S.StopNumber AND T.TripNumber=?";
        	final String query = select + " " + from + " " + where;

			try ( PreparedStatement statement = connection.prepareStatement ( query ) )
			{
				statement.setInt ( 1, selectedValue );

				DefaultListModel< Properties > listModel = new DefaultListModel<> ();

				try ( ResultSet resultSet = statement.executeQuery () )
				{
					while ( resultSet.next () )
					{
						Properties properties = new Properties ();

						properties.setProperty ( Keys.StopNumber.toString (), resultSet.getString ( Keys.StopNumber.toString () ) );
						properties.setProperty ( Keys.StopAddress.toString (), resultSet.getString ( Keys.StopAddress.toString () ) );
						properties.setProperty ( Keys.SequenceNumber.toString (), resultSet.getString ( Keys.SequenceNumber.toString () ) );
						properties.setProperty ( Keys.DrivingTime.toString (), resultSet.getString ( Keys.DrivingTime.toString () ) );

						listModel.addElement ( properties );
					}
				}

				stopList.setModel ( listModel );
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
		JLabel tripLabel = new JLabel();
		tripNumberComboBox = new JComboBox();
		findStopButton = new JButton();
		JScrollPane scrollPane = new JScrollPane();
		stopList = new JList();
		JButton doneButton = new JButton();

		//======== this ========
		setMinimumSize(new Dimension(460, 500));
		setModal(true);
		setTitle("Finding Stops");
		Container contentPane = getContentPane();

		//---- tripLabel ----
		tripLabel.setText("Trip Number");
		tripLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- tripNumberComboBox ----
		tripNumberComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		tripNumberComboBox.setMaximumRowCount(0);
		tripNumberComboBox.setSelectedIndex(-1);

		//---- findStopButton ----
		findStopButton.setText("Find Stops");
		findStopButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		findStopButton.addActionListener(e -> findStopButtonActionPerformed(e));

		//======== scrollPane ========
		{
			scrollPane.setBorder(new EtchedBorder());

			//---- stopList ----
			stopList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			stopList.setVisibleRowCount(1);
			stopList.setCellRenderer(null);
			stopList.setSelectionBackground(new Color(204, 204, 204));
			scrollPane.setViewportView(stopList);
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
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(findStopButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(tripLabel)
							.addGap(18, 18, 18)
							.addComponent(tripNumberComboBox))
						.addComponent(scrollPane))
					.addGap(17, 17, 17))
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap(361, Short.MAX_VALUE)
					.addComponent(doneButton)
					.addGap(20, 20, 20))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(20, 20, 20)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(tripLabel)
						.addComponent(tripNumberComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addComponent(findStopButton)
					.addGap(18, 18, 18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
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
	private JComboBox tripNumberComboBox;
	private JButton findStopButton;
	private JList stopList;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
