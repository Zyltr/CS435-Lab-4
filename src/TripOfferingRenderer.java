import java.awt.*;
import java.util.Properties;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Mon May 21 16:29:02 PDT 2018
 */


/**
 * @author Erik Huerta
 */
public class TripOfferingRenderer extends JPanel implements ListCellRenderer< Properties >
{
    TripOfferingRenderer ()
    {
        initComponents ();
    }

    @Override
    public Component getListCellRendererComponent ( JList< ? extends Properties > list, Properties value, int index, boolean isSelected, boolean cellHasFocus )
    {
		tripNumber.setText ( value.getProperty ( Keys.TripNumber.toString () ) );
		date.setText ( value.getProperty (  Keys.Date.toString () ) );
		scheduledStartTime.setText ( value.getProperty (  Keys.ScheduledStartTime.toString () ) );
		scheduledArrivalTime.setText ( value.getProperty (  Keys.ScheduledArrivalTime.toString () ) );

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
		JLabel tripNumberLabel = new JLabel();
		JLabel dateLabel = new JLabel();
		JLabel scheduledStartTimeLabel = new JLabel();
		JLabel scheduledArrivalTimeLabel = new JLabel();
		tripNumber = new JLabel();
		date = new JLabel();
		scheduledStartTime = new JLabel();
		scheduledArrivalTime = new JLabel();

		//======== this ========

		//---- tripNumberLabel ----
		tripNumberLabel.setText("Trip Number");
		tripNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		tripNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- dateLabel ----
		dateLabel.setText("Date");
		dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		dateLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- scheduledStartTimeLabel ----
		scheduledStartTimeLabel.setText("Scheduled Start Time");
		scheduledStartTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		scheduledStartTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- scheduledArrivalTimeLabel ----
		scheduledArrivalTimeLabel.setText("Scheduled Arrival Time");
		scheduledArrivalTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		scheduledArrivalTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- tripNumber ----
		tripNumber.setText("-");
		tripNumber.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- date ----
		date.setText("-");
		date.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- scheduledStartTime ----
		scheduledStartTime.setText("-");
		scheduledStartTime.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		//---- scheduledArrivalTime ----
		scheduledArrivalTime.setText("-");
		scheduledArrivalTime.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addGap(10, 10, 10)
							.addGroup(layout.createParallelGroup()
								.addComponent(scheduledArrivalTimeLabel, GroupLayout.Alignment.TRAILING)
								.addComponent(scheduledStartTimeLabel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup()
								.addComponent(scheduledStartTime, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addComponent(scheduledArrivalTime, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
						.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(dateLabel, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
								.addComponent(tripNumberLabel, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup()
								.addComponent(tripNumber, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addComponent(date, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))))
					.addGap(10, 10, 10))
		);
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {scheduledArrivalTimeLabel, scheduledStartTimeLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {date, scheduledArrivalTime, scheduledStartTime, tripNumber});
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(10, 10, 10)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(tripNumberLabel)
						.addComponent(tripNumber))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dateLabel)
						.addComponent(date))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(scheduledStartTimeLabel)
						.addComponent(scheduledStartTime))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(scheduledArrivalTimeLabel)
						.addComponent(scheduledArrivalTime))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		layout.linkSize(SwingConstants.VERTICAL, new Component[] {dateLabel, scheduledArrivalTimeLabel, scheduledStartTimeLabel, tripNumberLabel});
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JLabel tripNumber;
	private JLabel date;
	private JLabel scheduledStartTime;
	private JLabel scheduledArrivalTime;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
