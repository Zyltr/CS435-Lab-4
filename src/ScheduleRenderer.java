import javax.swing.*;
import java.awt.*;
import java.util.Properties;
/*
 * Created by JFormDesigner on Mon May 14 20:03:12 PDT 2018
 */


/**
 * @author Erik Huerta
 */
public class ScheduleRenderer extends JPanel implements ListCellRenderer< Properties >
{
    ScheduleRenderer ()
    {
        initComponents ();
    }

    @Override
    public Component getListCellRendererComponent ( JList< ? extends Properties > list, Properties value, int index, boolean isSelected, boolean cellHasFocus )
    {
		startLocationName.setText ( value.getProperty ( Keys.StartLocationName.toString () ) );
		destinationName.setText ( value.getProperty (  Keys.DestinationName.toString () ) );
		date.setText ( value.getProperty (  Keys.Date.toString () ) );
		scheduledStartTime.setText ( value.getProperty (  Keys.ScheduledStartTime.toString () ) );
		scheduledArrivalTime.setText ( value.getProperty (  Keys.ScheduledArrivalTime.toString () ) );
		driverName.setText ( value.getProperty (  Keys.DriverName.toString () ) );
		busID.setText ( value.getProperty (  Keys.BusID.toString () ) );

		if ( isSelected )
		{
			setBackground ( list.getSelectionBackground () );
			setForeground ( list.getSelectionForeground () );
		}
		else
		{
			setBackground ( list.getBackground () );
			setForeground ( list.getForeground () );
		}

        return this;
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner non-commercial license
		JLabel startLocationNameLabel = new JLabel();
		JLabel destinationNameLabel = new JLabel();
		JLabel dateLabel = new JLabel();
		JLabel scheduleStartTimeLabel = new JLabel();
		JLabel scheduledArrivalTimeLabel = new JLabel();
		JLabel driverNameLabel = new JLabel();
		JLabel busIDLabel = new JLabel();
		startLocationName = new JLabel();
		destinationName = new JLabel();
		date = new JLabel();
		scheduledStartTime = new JLabel();
		scheduledArrivalTime = new JLabel();
		driverName = new JLabel();
		busID = new JLabel();
		JSeparator bottomSeparator = new JSeparator();

		//======== this ========
		setMinimumSize(new Dimension(415, 175));
		setPreferredSize(new Dimension(415, 175));
		setBackground(Color.white);

		//---- startLocationNameLabel ----
		startLocationNameLabel.setText("Start Location Name");
		startLocationNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- destinationNameLabel ----
		destinationNameLabel.setText("Destination Name");
		destinationNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- dateLabel ----
		dateLabel.setText("Date");
		dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- scheduleStartTimeLabel ----
		scheduleStartTimeLabel.setText("Scheduled Start Time");
		scheduleStartTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- scheduledArrivalTimeLabel ----
		scheduledArrivalTimeLabel.setText("Scheduled Arrival Time");
		scheduledArrivalTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- driverNameLabel ----
		driverNameLabel.setText("Driver Name");
		driverNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- busIDLabel ----
		busIDLabel.setText("Bus ID");
		busIDLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));

		//---- startLocationName ----
		startLocationName.setText("-");
		startLocationName.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- destinationName ----
		destinationName.setText("-");
		destinationName.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- date ----
		date.setText("-");
		date.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- scheduledStartTime ----
		scheduledStartTime.setText("-");
		scheduledStartTime.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- scheduledArrivalTime ----
		scheduledArrivalTime.setText("-");
		scheduledArrivalTime.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- driverName ----
		driverName.setText("-");
		driverName.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- busID ----
		busID.setText("-");
		busID.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(20, 20, 20)
					.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
							.addGap(60, 60, 60)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(busIDLabel)
								.addComponent(driverNameLabel)
								.addComponent(dateLabel))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup()
								.addComponent(busID, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(date, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(driverName, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
								.addComponent(scheduledArrivalTimeLabel)
								.addComponent(scheduleStartTimeLabel, GroupLayout.Alignment.TRAILING)
								.addComponent(startLocationNameLabel, GroupLayout.Alignment.TRAILING)
								.addComponent(destinationNameLabel, GroupLayout.Alignment.TRAILING))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup()
								.addComponent(scheduledStartTime, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(startLocationName, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
								.addComponent(scheduledArrivalTime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(destinationName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(bottomSeparator, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
					.addGap(20, 20, 20))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addGap(10, 10, 10)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(driverName)
						.addComponent(driverNameLabel))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(busID)
						.addComponent(busIDLabel))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(date)
						.addComponent(dateLabel))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(scheduledStartTime)
						.addComponent(scheduleStartTimeLabel))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(startLocationName)
						.addComponent(startLocationNameLabel))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(scheduledArrivalTimeLabel)
						.addComponent(scheduledArrivalTime))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(destinationName)
						.addComponent(destinationNameLabel))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(bottomSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1, Short.MAX_VALUE))
		);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JLabel startLocationName;
	private JLabel destinationName;
	private JLabel date;
	private JLabel scheduledStartTime;
	private JLabel scheduledArrivalTime;
	private JLabel driverName;
	private JLabel busID;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
