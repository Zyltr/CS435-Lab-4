import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
/*
 * Created by JFormDesigner on Mon May 14 18:23:29 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class DatabasePanel extends JPanel
{
    private final Connection connection;
    private final JFrame parentFrame = ( JFrame ) SwingUtilities.getWindowAncestor ( this );

    DatabasePanel ( Connection databaseConnection )
    {
        initComponents ();
        connection = databaseConnection;
    }

    private void displaySchedulesButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( displayScheduleButton ) )
        {
            // Schedule Dialog is begun here
            ( new ShowScheduleDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void editTripOfferingButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( editTripOfferingButton ) )
        {
            ( new ShowTripOfferingDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void displayStopButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( displayStopButton ) )
        {
            // Stop Dialog is begun here
            ( new ShowStopDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void addDriverButtonActionPerformed ( ActionEvent actionEvent )
    {
        // Show Driver Dialog here
        if ( actionEvent.getSource ().equals ( addDriverButton ) )
        {
            ( new AddDriverDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void addBusButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( addBusButton ) )
        {
            ( new AddBusDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void deleteBusButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( deleteBusButton ) )
        {
            ( new DeleteBusDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void displayWeeklyScheduleButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( displayWeeklyScheduleButton ) )
        {
            ( new ShowWeeklyScheduleDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void actualTripStopButtonActionPerformed ( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource ().equals ( actualTripStopButton ) )
        {
            ( new ShowActualTripStopInfoDialog ( parentFrame, connection ) ).setVisible ( true );
        }
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        displayScheduleButton = new JButton ();
        displayStopButton = new JButton ();
        addDriverButton = new JButton ();
        addBusButton = new JButton ();
        deleteBusButton = new JButton ();
        displayWeeklyScheduleButton = new JButton ();
        actualTripStopButton = new JButton ();
        editTripOfferingButton = new JButton ();

        //======== this ========
        setPreferredSize ( new Dimension ( 560, 280 ) );
        setMinimumSize ( new Dimension ( 560, 280 ) );

        //---- displayScheduleButton ----
        displayScheduleButton.setText ( "Display Schedules using a Location" );
        displayScheduleButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        displayScheduleButton.addActionListener ( e -> displaySchedulesButtonActionPerformed ( e ) );

        //---- displayStopButton ----
        displayStopButton.setText ( "Display Stops" );
        displayStopButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        displayStopButton.addActionListener ( e -> displayStopButtonActionPerformed ( e ) );

        //---- addDriverButton ----
        addDriverButton.setText ( "Add a Driver" );
        addDriverButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        addDriverButton.addActionListener ( e -> addDriverButtonActionPerformed ( e ) );

        //---- addBusButton ----
        addBusButton.setText ( "Add a Bus" );
        addBusButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        addBusButton.addActionListener ( e -> addBusButtonActionPerformed ( e ) );

        //---- deleteBusButton ----
        deleteBusButton.setText ( "Delete Bus" );
        deleteBusButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        deleteBusButton.addActionListener ( e -> deleteBusButtonActionPerformed ( e ) );

        //---- displayWeeklyScheduleButton ----
        displayWeeklyScheduleButton.setText ( "Display Week Schedules by Driver" );
        displayWeeklyScheduleButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        displayWeeklyScheduleButton.addActionListener ( e -> displayWeeklyScheduleButtonActionPerformed ( e ) );

        //---- actualTripStopButton ----
        actualTripStopButton.setText ( "Record Actual Trip Stop" );
        actualTripStopButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        actualTripStopButton.addActionListener ( e -> actualTripStopButtonActionPerformed ( e ) );

        //---- editTripOfferingButton ----
        editTripOfferingButton.setText ( "Edit Trip Offering" );
        editTripOfferingButton.setFont ( new Font ( "Lucida Grande", Font.BOLD, 12 ) );
        editTripOfferingButton.addActionListener ( e -> editTripOfferingButtonActionPerformed ( e ) );

        GroupLayout layout = new GroupLayout ( this );
        setLayout ( layout );
        layout.setHorizontalGroup ( layout.createParallelGroup ().addGroup ( GroupLayout.Alignment.TRAILING, layout.createSequentialGroup ().addGap ( 20, 20, 20 ).addGroup ( layout.createParallelGroup ( GroupLayout.Alignment.TRAILING ).addComponent ( editTripOfferingButton, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ).addComponent ( displayScheduleButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ).addComponent ( actualTripStopButton, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ).addComponent ( displayStopButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ).addComponent ( displayWeeklyScheduleButton, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ).addComponent ( deleteBusButton, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ).addComponent ( addBusButton, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ).addComponent ( addDriverButton, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE ) ).addGap ( 20, 20, 20 ) ) );
        layout.setVerticalGroup ( layout.createParallelGroup ().addGroup ( layout.createSequentialGroup ().addGap ( 20, 20, 20 ).addComponent ( displayScheduleButton ).addPreferredGap ( LayoutStyle.ComponentPlacement.RELATED ).addComponent ( editTripOfferingButton ).addPreferredGap ( LayoutStyle.ComponentPlacement.RELATED ).addComponent ( displayStopButton ).addPreferredGap ( LayoutStyle.ComponentPlacement.RELATED ).addComponent ( displayWeeklyScheduleButton ).addPreferredGap ( LayoutStyle.ComponentPlacement.RELATED ).addComponent ( addDriverButton ).addPreferredGap ( LayoutStyle.ComponentPlacement.RELATED ).addComponent ( addBusButton ).addPreferredGap ( LayoutStyle.ComponentPlacement.RELATED ).addComponent ( deleteBusButton ).addPreferredGap ( LayoutStyle.ComponentPlacement.RELATED ).addComponent ( actualTripStopButton ).addContainerGap ( 21, Short.MAX_VALUE ) ) );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JButton displayScheduleButton;
    private JButton displayStopButton;
    private JButton addDriverButton;
    private JButton addBusButton;
    private JButton deleteBusButton;
    private JButton displayWeeklyScheduleButton;
    private JButton actualTripStopButton;
    private JButton editTripOfferingButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
