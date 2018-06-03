import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.*;
import java.util.Date;
/*
 * Created by JFormDesigner on Tue May 22 17:03:59 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class ShowTripOfferingDialog extends JDialog
{
    private Connection connection;

    private ArrayList< Properties > tripOfferings = new ArrayList<> ();

    ShowTripOfferingDialog ( Window owner, final Connection databaseConnection )
    {
        super ( owner );
        initComponents ();

        DTOTripOfferingList.setCellRenderer ( new TripOfferingRenderer () );
        EDTripOfferingList.setCellRenderer ( new TripOfferingRenderer () );
        EBTripOfferingList.setCellRenderer ( new TripOfferingRenderer () );

        connection = databaseConnection;

        if ( connection != null )
        {
            final String query = "SELECT * FROM TripOffering";

            try ( Statement statement = connection.createStatement (); ResultSet resultSet = statement.executeQuery ( query ) )
            {
                while ( resultSet.next () )
                {
                    Properties properties = new Properties ();

                    properties.setProperty ( Keys.TripNumber.toString (), resultSet.getString ( Keys.TripNumber.toString () ) );
                    properties.setProperty ( Keys.Date.toString (), resultSet.getString ( Keys.Date.toString () ) );
                    properties.setProperty ( Keys.ScheduledStartTime.toString (), resultSet.getString ( Keys.ScheduledStartTime.toString () ) );
                    properties.setProperty ( Keys.ScheduledArrivalTime.toString (), resultSet.getString ( Keys.ScheduledArrivalTime.toString () ) );
                    properties.setProperty ( Keys.DriverName.toString (), resultSet.getString ( Keys.DriverName.toString () ) );
                    properties.setProperty ( Keys.BusID.toString (), resultSet.getString ( Keys.BusID.toString () ) );

                    tripOfferings.add ( properties );
                }

                tabbedPaneStateChanged ();
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();
            }
        }
    }

    private void tabbedPaneStateChanged ()
    {
        if ( connection == null )
        {
            return;
        }

        switch ( tabbedPane.getSelectedIndex () )
        {
            // Delete Trip Offering
            case 0:
            {
                DefaultListModel< Properties > tripOfferingsModel = new DefaultListModel<> ();

                for ( Properties properties : tripOfferings )
                {
                    tripOfferingsModel.addElement ( properties );
                }

                DTOTripOfferingList.setModel ( tripOfferingsModel );
                DTOTripOfferingList.setSelectedIndex ( 0 );

                break;
            }
            // Add Trip Offering
            case 1:
            {

                DefaultComboBoxModel< String > tripNumberModel = new DefaultComboBoxModel<> ();
                DefaultComboBoxModel< String > yearModel = new DefaultComboBoxModel<> ();
                DefaultComboBoxModel< String > busIDModel = new DefaultComboBoxModel<> ();

                // Populate Trip Numbers
                ArrayList< String > tempTripNumbers = new ArrayList<> ();
                for ( Properties tripOffering : tripOfferings )
                {
                    if ( !tempTripNumbers.contains ( tripOffering.getProperty ( Keys.TripNumber.toString () ) ) )
                    {
                        tempTripNumbers.add ( tripOffering.getProperty ( Keys.TripNumber.toString () ) );
                        tripNumberModel.addElement ( tripOffering.getProperty ( Keys.TripNumber.toString () ) );
                    }
                }

                ATOTripNumberComboBox.setModel ( tripNumberModel );

                // Populate Years
                int currentDay = Calendar.getInstance ().get ( Calendar.DAY_OF_MONTH );
                ATODateDaySpinner.setValue ( currentDay );

                int currentMonth = Calendar.getInstance ().get ( Calendar.MONTH );
                ATODateMonthSpinner.setValue ( currentMonth );

                int currentYear = Calendar.getInstance ().get ( Calendar.YEAR );
                int maxYear = currentYear + 10;

                while ( currentYear < maxYear )
                {
                    yearModel.addElement ( Integer.toString ( currentYear++ ) );
                }

                ATODateYearComboBox.setModel ( yearModel );

                // Populate Drivers
                final String driverQuery = "SELECT * FROM Driver";

                try ( Statement statement = connection.createStatement (); ResultSet resultSet = statement.executeQuery ( driverQuery ) )
                {
                    DefaultComboBoxModel< Object > driverModel = new DefaultComboBoxModel<> ();

                    while ( resultSet.next () )
                    {
                        driverModel.addElement ( resultSet.getString ( Keys.DriverName.toString () ) );
                    }

                    ATODriverNameComboBox.setModel ( driverModel );
                }
                catch ( SQLException exception )
                {
                    exception.printStackTrace ();
                }

                // Populate BusID
                final String busIDQuery = "SELECT * FROM Bus";

                try ( Statement statement = connection.createStatement (); ResultSet resultSet = statement.executeQuery ( busIDQuery ) )
                {
                    DefaultComboBoxModel< Object > busModel = new DefaultComboBoxModel<> ();

                    while ( resultSet.next () )
                    {
                        busModel.addElement ( resultSet.getString ( Keys.BusID.toString () ) );
                    }

                    ATOBusIDComboBox.setModel ( busModel );
                }
                catch ( SQLException exception )
                {
                    exception.printStackTrace ();
                }

                break;
            }
            // Edit Driver
            case 2:
            {
                DefaultListModel< Properties > tripOfferingsModel = new DefaultListModel<> ();

                for ( Properties properties : tripOfferings )
                {
                    tripOfferingsModel.addElement ( properties );
                }

                EDTripOfferingList.setModel ( tripOfferingsModel );
                EDTripOfferingList.setSelectedIndex ( 0 );

                final String query = "SELECT * FROM Driver";

                try ( Statement statement = connection.createStatement (); ResultSet resultSet = statement.executeQuery ( query ) )
                {
                    DefaultComboBoxModel< Object > driverModel = new DefaultComboBoxModel<> ();

                    while ( resultSet.next () )
                    {
                        driverModel.addElement ( resultSet.getString ( Keys.DriverName.toString () ) );
                    }

                    EDComboBox.setModel ( driverModel );
                    EDComboBox.setMaximumRowCount ( driverModel.getSize () );
                }
                catch ( SQLException exception )
                {
                    exception.printStackTrace ();
                }

                break;
            }
            // Edit Bus
            case 3:
            {
                DefaultListModel< Properties > tripOfferingsModel = new DefaultListModel<> ();

                for ( Properties properties : tripOfferings )
                {
                    tripOfferingsModel.addElement ( properties );
                }

                EBTripOfferingList.setModel ( tripOfferingsModel );
                EBTripOfferingList.setSelectedIndex ( 0 );

                final String query = "SELECT * FROM Bus";

                try ( Statement statement = connection.createStatement (); ResultSet resultSet = statement.executeQuery ( query ) )
                {
                    DefaultComboBoxModel< Object > busModel = new DefaultComboBoxModel<> ();

                    while ( resultSet.next () )
                    {
                        busModel.addElement ( resultSet.getString ( Keys.BusID.toString () ) );
                    }

                    EBComboBox.setModel ( busModel );
                    EBComboBox.setMaximumRowCount ( busModel.getSize () );
                }
                catch ( SQLException exception )
                {
                    exception.printStackTrace ();
                }

                break;
            }
            default:
            {
                break;
            }
        }
    }

    private void deleteTripOfferingButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( DTOButton ) )
        {
            final Properties properties = tripOfferings.get ( DTOTripOfferingList.getSelectedIndex () );

            final String tripNumber = properties.getProperty ( Keys.TripNumber.toString () );
            final String date = properties.getProperty ( Keys.Date.toString () );
            final String scheduledStartTime = properties.getProperty ( Keys.ScheduledStartTime.toString () );

            final String query = "DELETE FROM TripOffering WHERE TripNumber=? AND Date=? AND ScheduledStartTime=?";

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
                statement.setString ( 1, tripNumber );
                statement.setString ( 2, date );
                statement.setString ( 3, scheduledStartTime );

                statement.execute ();

                String deletedBus = "Deleted Trip Offering ( " + ( new Date () ) + " )\n\tTrip Number : " + tripNumber + "\n\tDate : " + date + "\n\tScheduled Start Time : " + scheduledStartTime;
                DTOLogTextArea.insert ( deletedBus + "\n\n", 0 );

                tripOfferings.remove ( properties );
                tabbedPaneStateChanged ();
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();

                DTOLogTextArea.insert ( "Failed to Delete Trip Offering ( " + ( new Date () ) + " )\n\tTrip Number : " + tripNumber + "\n\tDate : " + date + "\n\tScheduled Start Time : " + scheduledStartTime, 0 );
            }
        }
    }

    private void addTripOfferingButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( addTripOfferingButton ) )
        {
            String tripNumber = ATOTripNumberComboBox.getSelectedItem ().toString ();
            String date = ATODateYearComboBox.getSelectedItem ().toString () + "-" + ATODateMonthSpinner.getValue ().toString () + "-" + ATODateDaySpinner.getValue ().toString ();

            final boolean startTimeIsMorning = ATOStartTimeAMRadioButton.isSelected ();
            final Integer startTimeHour =  Integer.valueOf ( ATOStartTimeHourSpinner.getValue ().toString () ) + ( startTimeIsMorning ? 0 : 12 );
            String startTimeMinute = ATOStartTimeMinuteSpinner.getValue ().toString ();
            startTimeMinute = startTimeMinute.length () == 1 ? "0" + startTimeMinute : startTimeMinute;
            final String scheduledStartTime = startTimeHour + ":" + startTimeMinute + ":00 " + ( startTimeIsMorning ? "AM" : "PM" );

            final boolean arrivalTimeIsMorning = ATOArrivalTimeAMRadioButton.isSelected ();
            final Integer arrivalTimeHour =  Integer.valueOf ( ATOArrivalTimeHourSpinner.getValue ().toString () ) + ( arrivalTimeIsMorning ? 0 : 12 );
            String arrivalTimeMinute = ATOArrivalTimeMinuteSpinner.getValue ().toString ();
            arrivalTimeMinute = arrivalTimeMinute.length () == 1 ? "0" + arrivalTimeMinute : arrivalTimeMinute;
            final String scheduledArrivalTime = arrivalTimeHour + ":" + arrivalTimeMinute + ":00 " + ( arrivalTimeIsMorning ? "AM" : "PM" );

            String driverName = Objects.requireNonNull ( ATODriverNameComboBox.getSelectedItem () ).toString ();
            String busID = Objects.requireNonNull ( ATOBusIDComboBox.getSelectedItem () ).toString ();

            System.out.println ( "Using : " + tripNumber + ", " + date + ", " + scheduledStartTime + ", " + scheduledArrivalTime + ", " + driverName + ", " + busID );

            String query = "INSERT INTO TripOffering VALUES ( ?, ?, ?, ?, ?, ? )";

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
                statement.setString ( 1, tripNumber );
                statement.setString ( 2, date );
                statement.setString ( 3, scheduledStartTime );
                statement.setString ( 4, scheduledArrivalTime );
                statement.setString ( 5, driverName );
                statement.setString ( 6, busID );

                statement.execute ();

                String newTripOffering = "Added Trip Offering ( " + ( new java.util.Date () ) + " )";
                ATOLogTextArea.insert ( newTripOffering + "\n\nTrip Number : " + tripNumber + "\n\tDate : " + date + "\n\tScheduled Start Time : " + scheduledStartTime + "\n\tScheduled Arrival Time : " + scheduledArrivalTime + "\n\tDriver Name : " + driverName + "\n\tBus ID : " + busID, 0 );
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();

                ATOLogTextArea.insert ( "Failed to Add Trip Offering ( " + ( new Date () ) + " )\n\n", 0 );
                JOptionPane.showMessageDialog ( this, "Failed to Add", "Failed", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    private void changeDriverButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( changeDriverButton ) )
        {
            final Properties properties = tripOfferings.get ( DTOTripOfferingList.getSelectedIndex () );

            final String tripNumber = properties.getProperty ( Keys.TripNumber.toString () );
            final String date = properties.getProperty ( Keys.Date.toString () );
            final String scheduledStartTime = properties.getProperty ( Keys.ScheduledStartTime.toString () );
            final String driverName = Objects.requireNonNull ( EDComboBox.getSelectedItem () ).toString ();

            final String query = "UPDATE TripOffering SET DriverName=? WHERE TripNumber=? AND Date=? AND ScheduledStartTime=?";

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
                statement.setString ( 1, driverName );
                statement.setString ( 2, tripNumber );
                statement.setString ( 3, date );
                statement.setString ( 4, scheduledStartTime );

                statement.execute ();

                String updateDriver = "Update Trip Offering Driver ( " + ( new Date () ) + " )\n\tTrip Number : " + tripNumber + "\n\tDate : " + date + "\n\tScheduled Start Time : " + scheduledStartTime;
                EDLogTextArea.insert ( updateDriver + "\n\n", 0 );
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();

                EDLogTextArea.insert ( "Failed to update Trip Offering Driver ( " + ( new Date () ) + " )\n\tTrip Number : " + tripNumber + "\n\tDate : " + date + "\n\tScheduled Start Time : " + scheduledStartTime, 0 );
            }
        }
    }

    private void changeBusButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( changeBusButton ) )
        {
            final Properties properties = tripOfferings.get ( DTOTripOfferingList.getSelectedIndex () );

            final String tripNumber = properties.getProperty ( Keys.TripNumber.toString () );
            final String date = properties.getProperty ( Keys.Date.toString () );
            final String scheduledStartTime = properties.getProperty ( Keys.ScheduledStartTime.toString () );
            final String busID = Objects.requireNonNull ( EBComboBox.getSelectedItem () ).toString ();

            final String query = "UPDATE TripOffering SET BusID=? WHERE TripNumber=? AND Date=? AND ScheduledStartTime=?";

            try ( PreparedStatement statement = connection.prepareStatement ( query ) )
            {
                statement.setString ( 1, busID );
                statement.setString ( 2, tripNumber );
                statement.setString ( 3, date );
                statement.setString ( 4, scheduledStartTime );

                statement.execute ();

                String updateDriver = "Update Trip Offering Bus ID ( " + ( new Date () ) + " )\n\tTrip Number : " + tripNumber + "\n\tDate : " + date + "\n\tScheduled Start Time : " + scheduledStartTime;
                EBLogTextArea.insert ( updateDriver + "\n\n", 0 );
            }
            catch ( SQLException exception )
            {
                exception.printStackTrace ();

                EBLogTextArea.insert ( "Failed to update Trip Offering Bus ID ( " + ( new Date () ) + " )\n\tTrip Number : " + tripNumber + "\n\tDate : " + date + "\n\tScheduled Start Time : " + scheduledStartTime, 0 );
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
        tabbedPane = new JTabbedPane();
        JPanel deleteTripOfferingPanel = new JPanel();
        JLabel DTOTripNumberLabel = new JLabel();
        DTOButton = new JButton();
        JLabel DTOLogLabel = new JLabel();
        JScrollPane DTOLogScrollPane = new JScrollPane();
        DTOLogTextArea = new JTextArea();
        JScrollPane DTOTripOfferingScrollPane = new JScrollPane();
        DTOTripOfferingList = new JList();
        addTripOfferingPanel = new JPanel();
        JLabel ATOTripNumberLabel = new JLabel();
        JLabel ATODateLabel = new JLabel();
        JLabel ATOScheduledStartTimeLabel = new JLabel();
        JLabel ATOScheduledArrivalTimeLabel = new JLabel();
        JLabel ATODriverName = new JLabel();
        JLabel ATOBusIDLabel = new JLabel();
        ATOTripNumberComboBox = new JComboBox();
        ATODriverNameComboBox = new JComboBox();
        ATOBusIDComboBox = new JComboBox();
        ATODateMonthSpinner = new JSpinner();
        ATODateDaySpinner = new JSpinner();
        ATODateYearComboBox = new JComboBox<>();
        JLabel ATODateMonthLabel = new JLabel();
        JLabel ATODateDayLabel = new JLabel();
        JLabel ATODateYearLabel = new JLabel();
        ATOStartTimeHourSpinner = new JSpinner();
        ATOStartTimeMinuteSpinner = new JSpinner();
        ATOStartTimeAMRadioButton = new JRadioButton();
        JRadioButton ATOStartTimePMRadioButton = new JRadioButton();
        ATOArrivalTimeHourSpinner = new JSpinner();
        ATOArrivalTimeMinuteSpinner = new JSpinner();
        ATOArrivalTimeAMRadioButton = new JRadioButton();
        JRadioButton ATOArrivalTimePMRadioButton = new JRadioButton();
        addTripOfferingButton = new JButton();
        JLabel ATOLogLabel = new JLabel();
        JScrollPane ATOLogScrollPane = new JScrollPane();
        ATOLogTextArea = new JTextArea();
        JPanel editDrivePanel = new JPanel();
        JLabel EDTripOfferingLabel = new JLabel();
        JScrollPane EDTripOfferingScrollPane = new JScrollPane();
        EDTripOfferingList = new JList();
        JLabel EDNewDriverLabel = new JLabel();
        EDComboBox = new JComboBox();
        changeDriverButton = new JButton();
        JLabel EDLogLabel = new JLabel();
        JScrollPane EDLogScrollPane = new JScrollPane();
        EDLogTextArea = new JTextArea();
        JPanel editBusPanel = new JPanel();
        JLabel EBTripOfferingLabel = new JLabel();
        JLabel EBNewDriverLabel = new JLabel();
        EBComboBox = new JComboBox();
        changeBusButton = new JButton();
        JLabel EBLogLabel = new JLabel();
        EBLogTextArea = new JTextArea();
        EBScrollPane = new JScrollPane();
        EBTripOfferingList = new JList();
        JButton doneButton = new JButton();

        //======== this ========
        setTitle("Trip Offerings");
        Container contentPane = getContentPane();

        //======== tabbedPane ========
        {
            tabbedPane.addChangeListener(e -> tabbedPaneStateChanged());

            //======== deleteTripOfferingPanel ========
            {

                //---- DTOTripNumberLabel ----
                DTOTripNumberLabel.setText("Trip Offering");
                DTOTripNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                DTOTripNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- DTOButton ----
                DTOButton.setText("Delete Trip Offering");
                DTOButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
                DTOButton.addActionListener(e -> deleteTripOfferingButtonActionPerformed(e));

                //---- DTOLogLabel ----
                DTOLogLabel.setText("Log");
                DTOLogLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //======== DTOLogScrollPane ========
                {
                    DTOLogScrollPane.setBorder(new LineBorder(Color.lightGray));
                    DTOLogScrollPane.setViewportView(DTOLogTextArea);
                }

                //======== DTOTripOfferingScrollPane ========
                {
                    DTOTripOfferingScrollPane.setBorder(new LineBorder(Color.lightGray));

                    //---- DTOTripOfferingList ----
                    DTOTripOfferingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    DTOTripOfferingList.setCellRenderer(null);
                    DTOTripOfferingList.setSelectionBackground(Color.lightGray);
                    DTOTripOfferingScrollPane.setViewportView(DTOTripOfferingList);
                }

                GroupLayout deleteTripOfferingPanelLayout = new GroupLayout(deleteTripOfferingPanel);
                deleteTripOfferingPanel.setLayout(deleteTripOfferingPanelLayout);
                deleteTripOfferingPanelLayout.setHorizontalGroup(
                    deleteTripOfferingPanelLayout.createParallelGroup()
                        .addGroup(deleteTripOfferingPanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(deleteTripOfferingPanelLayout.createParallelGroup()
                                .addGroup(deleteTripOfferingPanelLayout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(DTOLogLabel)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(deleteTripOfferingPanelLayout.createSequentialGroup()
                                    .addGroup(deleteTripOfferingPanelLayout.createParallelGroup()
                                        .addComponent(DTOLogScrollPane)
                                        .addGroup(GroupLayout.Alignment.TRAILING, deleteTripOfferingPanelLayout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addGroup(deleteTripOfferingPanelLayout.createParallelGroup()
                                                .addComponent(DTOButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(GroupLayout.Alignment.TRAILING, deleteTripOfferingPanelLayout.createSequentialGroup()
                                                    .addComponent(DTOTripNumberLabel)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(DTOTripOfferingScrollPane, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)))))
                                    .addGap(20, 20, 20))))
                );
                deleteTripOfferingPanelLayout.setVerticalGroup(
                    deleteTripOfferingPanelLayout.createParallelGroup()
                        .addGroup(deleteTripOfferingPanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(deleteTripOfferingPanelLayout.createParallelGroup()
                                .addComponent(DTOTripNumberLabel)
                                .addComponent(DTOTripOfferingScrollPane, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(DTOButton)
                            .addGap(18, 18, 18)
                            .addComponent(DTOLogLabel)
                            .addGap(18, 18, 18)
                            .addComponent(DTOLogScrollPane, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addGap(20, 20, 20))
                );
            }
            tabbedPane.addTab("Delete Trip Offering", deleteTripOfferingPanel);

            //======== addTripOfferingPanel ========
            {

                //---- ATOTripNumberLabel ----
                ATOTripNumberLabel.setText("Trip Number");
                ATOTripNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                ATOTripNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATODateLabel ----
                ATODateLabel.setText("Date");
                ATODateLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                ATODateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATOScheduledStartTimeLabel ----
                ATOScheduledStartTimeLabel.setText("Scheduled Start Time");
                ATOScheduledStartTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                ATOScheduledStartTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATOScheduledArrivalTimeLabel ----
                ATOScheduledArrivalTimeLabel.setText("Scheduled Arrival Time");
                ATOScheduledArrivalTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                ATOScheduledArrivalTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATODriverName ----
                ATODriverName.setText("Driver Name");
                ATODriverName.setHorizontalAlignment(SwingConstants.TRAILING);
                ATODriverName.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATOBusIDLabel ----
                ATOBusIDLabel.setText("Bus ID");
                ATOBusIDLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                ATOBusIDLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATOTripNumberComboBox ----
                ATOTripNumberComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATODriverNameComboBox ----
                ATODriverNameComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATOBusIDComboBox ----
                ATOBusIDComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATODateMonthSpinner ----
                ATODateMonthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
                ATODateMonthSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATODateDaySpinner ----
                ATODateDaySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
                ATODateDaySpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATODateYearComboBox ----
                ATODateYearComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
                ATODateYearComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "1970"
                }));

                //---- ATODateMonthLabel ----
                ATODateMonthLabel.setText("M");
                ATODateMonthLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATODateDayLabel ----
                ATODateDayLabel.setText("D");
                ATODateDayLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATODateYearLabel ----
                ATODateYearLabel.setText("Y");
                ATODateYearLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- ATOStartTimeHourSpinner ----
                ATOStartTimeHourSpinner.setModel(new SpinnerNumberModel(12, 1, 12, 1));
                ATOStartTimeHourSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATOStartTimeMinuteSpinner ----
                ATOStartTimeMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
                ATOStartTimeMinuteSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATOStartTimeAMRadioButton ----
                ATOStartTimeAMRadioButton.setText("A.M");
                ATOStartTimeAMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
                ATOStartTimeAMRadioButton.setSelected(true);

                //---- ATOStartTimePMRadioButton ----
                ATOStartTimePMRadioButton.setText("P.M");
                ATOStartTimePMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));

                //---- ATOArrivalTimeHourSpinner ----
                ATOArrivalTimeHourSpinner.setModel(new SpinnerNumberModel(12, 1, 12, 1));
                ATOArrivalTimeHourSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATOArrivalTimeMinuteSpinner ----
                ATOArrivalTimeMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
                ATOArrivalTimeMinuteSpinner.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- ATOArrivalTimeAMRadioButton ----
                ATOArrivalTimeAMRadioButton.setText("A.M");
                ATOArrivalTimeAMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
                ATOArrivalTimeAMRadioButton.setSelected(true);

                //---- ATOArrivalTimePMRadioButton ----
                ATOArrivalTimePMRadioButton.setText("P.M");
                ATOArrivalTimePMRadioButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));

                //---- addTripOfferingButton ----
                addTripOfferingButton.setText("Add Trip Offering");
                addTripOfferingButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
                addTripOfferingButton.addActionListener(e -> addTripOfferingButtonActionPerformed(e));

                //---- ATOLogLabel ----
                ATOLogLabel.setText("Log");
                ATOLogLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //======== ATOLogScrollPane ========
                {
                    ATOLogScrollPane.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
                    ATOLogScrollPane.setBorder(new LineBorder(Color.lightGray));
                    ATOLogScrollPane.setViewportView(ATOLogTextArea);
                }

                GroupLayout addTripOfferingPanelLayout = new GroupLayout(addTripOfferingPanel);
                addTripOfferingPanel.setLayout(addTripOfferingPanelLayout);
                addTripOfferingPanelLayout.setHorizontalGroup(
                    addTripOfferingPanelLayout.createParallelGroup()
                        .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(addTripOfferingPanelLayout.createParallelGroup()
                                .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                                    .addComponent(ATODateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(ATODateMonthLabel)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ATODateMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ATODateDayLabel)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ATODateDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ATODateYearLabel)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(ATODateYearComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(83, Short.MAX_VALUE))
                                .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                                    .addGroup(addTripOfferingPanelLayout.createParallelGroup()
                                        .addComponent(ATOLogLabel)
                                        .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                                                .addComponent(ATOScheduledArrivalTimeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(ATOArrivalTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(ATOArrivalTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ATOArrivalTimeAMRadioButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ATOArrivalTimePMRadioButton))
                                            .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                                                .addComponent(ATOScheduledStartTimeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(ATOStartTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(ATOStartTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(ATOStartTimeAMRadioButton)
                                                .addGap(6, 6, 6)
                                                .addComponent(ATOStartTimePMRadioButton)))
                                        .addComponent(addTripOfferingButton, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                                        .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                                            .addComponent(ATOTripNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(ATOTripNumberComboBox, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
                                        .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                                            .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(ATODriverName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(ATOBusIDLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(18, 18, 18)
                                            .addGroup(addTripOfferingPanelLayout.createParallelGroup()
                                                .addComponent(ATOBusIDComboBox, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                                .addComponent(ATODriverNameComboBox, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))
                                        .addComponent(ATOLogScrollPane, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
                                    .addGap(20, 20, 20))))
                );
                addTripOfferingPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {ATOBusIDLabel, ATODateLabel, ATODriverName, ATOScheduledArrivalTimeLabel, ATOScheduledStartTimeLabel, ATOTripNumberLabel});
                addTripOfferingPanelLayout.setVerticalGroup(
                    addTripOfferingPanelLayout.createParallelGroup()
                        .addGroup(addTripOfferingPanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ATOTripNumberLabel)
                                .addComponent(ATOTripNumberComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ATODateLabel)
                                .addComponent(ATODateMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ATODateMonthLabel)
                                .addComponent(ATODateDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ATODateDayLabel)
                                .addComponent(ATODateYearComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ATODateYearLabel))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(addTripOfferingPanelLayout.createParallelGroup()
                                .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(ATOStartTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ATOScheduledStartTimeLabel))
                                .addComponent(ATOStartTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ATOStartTimeAMRadioButton)
                                .addComponent(ATOStartTimePMRadioButton))
                            .addGroup(addTripOfferingPanelLayout.createParallelGroup()
                                .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(ATOArrivalTimeHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ATOScheduledArrivalTimeLabel))
                                .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(ATOArrivalTimeMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ATOArrivalTimeAMRadioButton)
                                    .addComponent(ATOArrivalTimePMRadioButton)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ATODriverNameComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ATODriverName))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(addTripOfferingPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ATOBusIDComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ATOBusIDLabel))
                            .addGap(18, 18, 18)
                            .addComponent(addTripOfferingButton)
                            .addGap(18, 18, 18)
                            .addComponent(ATOLogLabel)
                            .addGap(18, 18, 18)
                            .addComponent(ATOLogScrollPane, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addGap(20, 20, 20))
                );
            }
            tabbedPane.addTab("Add Trip Offering", addTripOfferingPanel);

            //======== editDrivePanel ========
            {

                //---- EDTripOfferingLabel ----
                EDTripOfferingLabel.setText("Trip Offering");
                EDTripOfferingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //======== EDTripOfferingScrollPane ========
                {
                    EDTripOfferingScrollPane.setBorder(new LineBorder(Color.lightGray));

                    //---- EDTripOfferingList ----
                    EDTripOfferingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    EDTripOfferingList.setSelectionBackground(Color.lightGray);
                    EDTripOfferingList.setCellRenderer(null);
                    EDTripOfferingScrollPane.setViewportView(EDTripOfferingList);
                }

                //---- EDNewDriverLabel ----
                EDNewDriverLabel.setText("New Driver");
                EDNewDriverLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- EDComboBox ----
                EDComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- changeDriverButton ----
                changeDriverButton.setText("Change Driver");
                changeDriverButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
                changeDriverButton.addActionListener(e -> changeDriverButtonActionPerformed(e));

                //---- EDLogLabel ----
                EDLogLabel.setText("Log");
                EDLogLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //======== EDLogScrollPane ========
                {
                    EDLogScrollPane.setBorder(new LineBorder(Color.lightGray));
                    EDLogScrollPane.setViewportView(EDLogTextArea);
                }

                GroupLayout editDrivePanelLayout = new GroupLayout(editDrivePanel);
                editDrivePanel.setLayout(editDrivePanelLayout);
                editDrivePanelLayout.setHorizontalGroup(
                    editDrivePanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, editDrivePanelLayout.createSequentialGroup()
                            .addGroup(editDrivePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(GroupLayout.Alignment.LEADING, editDrivePanelLayout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addGroup(editDrivePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(EDNewDriverLabel)
                                        .addComponent(EDTripOfferingLabel))
                                    .addGap(18, 18, 18)
                                    .addGroup(editDrivePanelLayout.createParallelGroup()
                                        .addComponent(EDComboBox, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                                        .addComponent(EDTripOfferingScrollPane, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)))
                                .addGroup(GroupLayout.Alignment.LEADING, editDrivePanelLayout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addGroup(editDrivePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(changeDriverButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(EDLogScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.LEADING, editDrivePanelLayout.createSequentialGroup()
                                            .addComponent(EDLogLabel)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 424, Short.MAX_VALUE)))))
                            .addGap(20, 20, 20))
                );
                editDrivePanelLayout.setVerticalGroup(
                    editDrivePanelLayout.createParallelGroup()
                        .addGroup(editDrivePanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(editDrivePanelLayout.createParallelGroup()
                                .addComponent(EDTripOfferingScrollPane, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                .addComponent(EDTripOfferingLabel))
                            .addGap(18, 18, 18)
                            .addGroup(editDrivePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(EDComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(EDNewDriverLabel))
                            .addGap(18, 18, 18)
                            .addComponent(changeDriverButton)
                            .addGap(18, 18, 18)
                            .addComponent(EDLogLabel)
                            .addGap(18, 18, 18)
                            .addComponent(EDLogScrollPane, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addGap(20, 20, 20))
                );
            }
            tabbedPane.addTab("Edit Driver", editDrivePanel);

            //======== editBusPanel ========
            {

                //---- EBTripOfferingLabel ----
                EBTripOfferingLabel.setText("Trip Offering");
                EBTripOfferingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- EBNewDriverLabel ----
                EBNewDriverLabel.setText("New Bus ID");
                EBNewDriverLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- EBComboBox ----
                EBComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

                //---- changeBusButton ----
                changeBusButton.setText("Change Bus ID");
                changeBusButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
                changeBusButton.addActionListener(e -> changeBusButtonActionPerformed(e));

                //---- EBLogLabel ----
                EBLogLabel.setText("Log");
                EBLogLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

                //---- EBLogTextArea ----
                EBLogTextArea.setBorder(new LineBorder(Color.lightGray));

                //======== EBScrollPane ========
                {
                    EBScrollPane.setBorder(new LineBorder(Color.lightGray));

                    //---- EBTripOfferingList ----
                    EBTripOfferingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    EBTripOfferingList.setSelectionBackground(Color.lightGray);
                    EBTripOfferingList.setCellRenderer(null);
                    EBTripOfferingList.setBorder(new LineBorder(Color.lightGray));
                    EBScrollPane.setViewportView(EBTripOfferingList);
                }

                GroupLayout editBusPanelLayout = new GroupLayout(editBusPanel);
                editBusPanel.setLayout(editBusPanelLayout);
                editBusPanelLayout.setHorizontalGroup(
                    editBusPanelLayout.createParallelGroup()
                        .addGroup(editBusPanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(editBusPanelLayout.createParallelGroup()
                                .addGroup(editBusPanelLayout.createSequentialGroup()
                                    .addComponent(EBLogLabel)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING, editBusPanelLayout.createSequentialGroup()
                                    .addGroup(editBusPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(EBLogTextArea, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                                        .addComponent(changeBusButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                                        .addGroup(editBusPanelLayout.createSequentialGroup()
                                            .addGroup(editBusPanelLayout.createParallelGroup()
                                                .addGroup(editBusPanelLayout.createSequentialGroup()
                                                    .addGap(9, 9, 9)
                                                    .addComponent(EBNewDriverLabel)
                                                    .addGap(18, 18, Short.MAX_VALUE))
                                                .addGroup(GroupLayout.Alignment.TRAILING, editBusPanelLayout.createSequentialGroup()
                                                    .addGap(0, 0, Short.MAX_VALUE)
                                                    .addComponent(EBTripOfferingLabel)
                                                    .addGap(18, 18, 18)))
                                            .addGroup(editBusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(EBScrollPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                                                .addComponent(EBComboBox, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))))
                                    .addGap(20, 20, 20))))
                );
                editBusPanelLayout.setVerticalGroup(
                    editBusPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, editBusPanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(editBusPanelLayout.createParallelGroup()
                                .addComponent(EBTripOfferingLabel)
                                .addComponent(EBScrollPane, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(editBusPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(EBNewDriverLabel)
                                .addComponent(EBComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(changeBusButton)
                            .addGap(18, 18, 18)
                            .addComponent(EBLogLabel)
                            .addGap(18, 18, 18)
                            .addComponent(EBLogTextArea, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addGap(20, 20, 20))
                );
            }
            tabbedPane.addTab("Edit Bus", editBusPanel);
        }

        //---- doneButton ----
        doneButton.setText("Done");
        doneButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
        doneButton.addActionListener(e -> doneButtonActionPerformed());

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doneButton)
                    .addGap(20, 20, 20))
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(tabbedPane)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(doneButton)
                    .addGap(20, 20, 20))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //---- ATOStartTimeGroup ----
        ButtonGroup ATOStartTimeGroup = new ButtonGroup();
        ATOStartTimeGroup.add(ATOStartTimeAMRadioButton);
        ATOStartTimeGroup.add(ATOStartTimePMRadioButton);

        //---- ATOArrivalTimeGroup ----
        ButtonGroup ATOArrivalTimeGroup = new ButtonGroup();
        ATOArrivalTimeGroup.add(ATOArrivalTimeAMRadioButton);
        ATOArrivalTimeGroup.add(ATOArrivalTimePMRadioButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTabbedPane tabbedPane;
    private JButton DTOButton;
    private JTextArea DTOLogTextArea;
    private JList DTOTripOfferingList;
    private JPanel addTripOfferingPanel;
    private JComboBox ATOTripNumberComboBox;
    private JComboBox ATODriverNameComboBox;
    private JComboBox ATOBusIDComboBox;
    private JSpinner ATODateMonthSpinner;
    private JSpinner ATODateDaySpinner;
    private JComboBox<String> ATODateYearComboBox;
    private JSpinner ATOStartTimeHourSpinner;
    private JSpinner ATOStartTimeMinuteSpinner;
    private JRadioButton ATOStartTimeAMRadioButton;
    private JSpinner ATOArrivalTimeHourSpinner;
    private JSpinner ATOArrivalTimeMinuteSpinner;
    private JRadioButton ATOArrivalTimeAMRadioButton;
    private JButton addTripOfferingButton;
    private JTextArea ATOLogTextArea;
    private JList EDTripOfferingList;
    private JComboBox EDComboBox;
    private JButton changeDriverButton;
    private JTextArea EDLogTextArea;
    private JComboBox EBComboBox;
    private JButton changeBusButton;
    private JTextArea EBLogTextArea;
    private JScrollPane EBScrollPane;
    private JList EBTripOfferingList;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
