import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon May 14 16:04:44 PDT 2018
 */


/**
 * @author Erik Huerta
 */
public class MainFrame extends JFrame
{
    public static void main ( String[] args )
    {
        SwingUtilities.invokeLater ( () -> new MainFrame ().setVisible ( true ) );
    }

    private MainFrame ()
    {
        initComponents ();

        // If tabbedPane is not NULL, add JPanels for Login and Database
        if ( tabbedPane != null )
        {
            // Add the Login JPanel here
            tabbedPane.add ( "Login", new LoginPanel ( tabbedPane ) );
        }
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner non-commercial license
		tabbedPane = new JTabbedPane();

		//======== this ========
		setName("frame");
		setTitle("Pomona Transit System");
		setMinimumSize(new Dimension(600, 400));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();

		//======== tabbedPane ========
		{
			tabbedPane.setPreferredSize(new Dimension(586, 280));
			tabbedPane.setMinimumSize(new Dimension(586, 280));
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JTabbedPane tabbedPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
