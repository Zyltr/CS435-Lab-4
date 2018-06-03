import java.awt.*;
import java.util.Properties;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Tue May 15 17:43:57 PDT 2018
 */


/**
 * @author Erik Huerta
 */
public class StopRenderer extends JPanel implements ListCellRenderer< Properties >
{
    StopRenderer ()
    {
        initComponents ();
    }

    @Override
    public Component getListCellRendererComponent ( JList< ? extends Properties > list, Properties value, int index, boolean isSelected, boolean cellHasFocus )
    {
        stopNumber.setText ( value.getProperty ( Keys.StopNumber.toString () ) );
        stopAddress.setText ( value.getProperty ( Keys.StopAddress.toString () ) );
        sequenceLabel.setText ( value.getProperty ( Keys.SequenceNumber.toString ( ) ) );
        drivingTime.setText ( value.getProperty ( Keys.DrivingTime.toString ( ) ) );

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
        JSeparator separator = new JSeparator();
        JLabel stopNumberLabel = new JLabel();
        JLabel stopAddressLabel = new JLabel();
        stopNumber = new JLabel();
        stopAddress = new JLabel();
        JLabel sequenceNumberLabel = new JLabel();
        sequenceLabel = new JLabel();
        JLabel drivingTimeLabel = new JLabel();
        drivingTime = new JLabel();

        //======== this ========
        setPreferredSize(new Dimension(415, 110));
        setMinimumSize(new Dimension(415, 110));
        setBackground(Color.white);

        //---- stopNumberLabel ----
        stopNumberLabel.setText("Stop Number");
        stopNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        stopNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);

        //---- stopAddressLabel ----
        stopAddressLabel.setText("Stop Address");
        stopAddressLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        stopAddressLabel.setHorizontalAlignment(SwingConstants.TRAILING);

        //---- stopNumber ----
        stopNumber.setText("-");
        stopNumber.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

        //---- stopAddress ----
        stopAddress.setText("-");
        stopAddress.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

        //---- sequenceNumberLabel ----
        sequenceNumberLabel.setText("Seq Number");
        sequenceNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        sequenceNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);

        //---- sequenceLabel ----
        sequenceLabel.setText("-");
        sequenceLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

        //---- drivingTimeLabel ----
        drivingTimeLabel.setText("Driving Time");
        drivingTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        drivingTimeLabel.setHorizontalAlignment(SwingConstants.TRAILING);

        //---- drivingTime ----
        drivingTime.setText("-");
        drivingTime.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(stopNumberLabel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(stopNumber, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(sequenceNumberLabel)
                                .addComponent(stopAddressLabel)
                                .addComponent(drivingTimeLabel))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(stopAddress, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup()
                                        .addComponent(sequenceLabel, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(drivingTime, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addComponent(separator, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                    .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(stopNumberLabel)
                        .addComponent(stopNumber))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(stopAddressLabel)
                        .addComponent(stopAddress))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(sequenceNumberLabel)
                        .addComponent(sequenceLabel))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(drivingTime)
                        .addComponent(drivingTimeLabel))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JLabel stopNumber;
    private JLabel stopAddress;
    private JLabel sequenceLabel;
    private JLabel drivingTime;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
