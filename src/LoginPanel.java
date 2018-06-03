import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Created by JFormDesigner on Mon May 14 15:03:42 PDT 2018
 */


/**
 * @author Erik Huerta
 */
class LoginPanel extends JPanel
{
    private final JTabbedPane tabbedPane;

    LoginPanel ( final JTabbedPane tabbedPane )
    {
        initComponents ();
        this.tabbedPane = tabbedPane;
    }

    private void loginButtonActionPerformed ( ActionEvent event )
    {
        if ( event.getSource ().equals ( loginButton ) )
        {
            // Create Properties that will be passed used to load
            Properties properties = new Properties ();

            // Get Hosts
            String hosts = hostsTextField.getText ();

            // Get Database
            String database = ( String ) databaseComboBox.getSelectedItem ();

            // Get Username
            String user = userTextField.getText ();

            // Get Password
            String password = new String ( passwordField.getPassword () );

            // Get SSL Option
            String useSSL = sslCheckBox.isSelected () ? "true" : "false";

            // Store Properties
            properties.setProperty ( "hosts", hosts );
            properties.setProperty ( "database", database );
            properties.setProperty ( "user", user );
            properties.setProperty ( "password", password );
            properties.setProperty ( "useSSL", useSSL );

            if ( Debug.debug () )
            {
                System.out.println ( "LoginPanel : Login Button Pressed" );
                System.out.println ( "LoginPanel : " + properties );
            }

            final Connection connection = new LoadDriver ().loadDriverUsingProperties ( properties );

            if ( connection != null )
            {

                if ( Debug.debug () )
                {
                    System.out.println ( "LoginPanel : Have Connection" );

                    try
					{
						System.out.println ( "LoginPanel : Database being Used -> " + connection.getCatalog () );
					}
					catch ( SQLException exception )
					{
						exception.printStackTrace ();
					}
                }

                // Now that we have a valid connection, disable further login attempts
                hostsTextField.setEnabled ( false );
                databaseComboBox.setEnabled ( false );
                userTextField.setEnabled ( false );
                passwordField.setEnabled ( false );
                sslCheckBox.setEnabled ( false );
                loginButton.setEnabled ( false );

                // Load Database JPanel and pass Connection as parameter
                if ( tabbedPane != null )
                {
                    tabbedPane.add ( database + " Database", new DatabasePanel ( connection ) );
                    tabbedPane.setSelectedIndex ( tabbedPane.getTabCount () - 1 );
                }
            }
            else
			{
				JOptionPane.showMessageDialog ( this, "Failed to connect to database",null, JOptionPane.ERROR_MESSAGE );
			}
        }
    }

    private void initComponents ()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner non-commercial license
		JLabel loginLabel = new JLabel();
		JLabel userLabel = new JLabel();
		JLabel passwordLabel = new JLabel();
		JLabel sslLabel = new JLabel();
		userTextField = new JTextField();
		passwordField = new JPasswordField();
		sslCheckBox = new JCheckBox();
		loginButton = new JButton();
		JLabel hostsLabel = new JLabel();
		hostsTextField = new JTextField();
		JLabel databaseLabel = new JLabel();
		databaseComboBox = new JComboBox<>();

		//======== this ========
		setMinimumSize(new Dimension(560, 280));
		setPreferredSize(new Dimension(560, 280));

		//---- loginLabel ----
		loginLabel.setText("Pomona Transit System");
		loginLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

		//---- userLabel ----
		userLabel.setText("User");
		userLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		userLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- passwordLabel ----
		passwordLabel.setText("Password");
		passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		passwordLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- sslLabel ----
		sslLabel.setText("Use SSL");
		sslLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		sslLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- userTextField ----
		userTextField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		userTextField.setText("root");

		//---- passwordField ----
		passwordField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		passwordField.setText("MySQLDatabase");

		//---- loginButton ----
		loginButton.setText("Login");
		loginButton.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		loginButton.addActionListener(e -> loginButtonActionPerformed(e));

		//---- hostsLabel ----
		hostsLabel.setText("Host");
		hostsLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		hostsLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- hostsTextField ----
		hostsTextField.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		hostsTextField.setText("localhost:3306");

		//---- databaseLabel ----
		databaseLabel.setText("Database");
		databaseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		databaseLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		//---- databaseComboBox ----
		databaseComboBox.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		databaseComboBox.setMaximumRowCount(1);
		databaseComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
			"CS435"
		}));

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(20, 20, 20)
					.addGroup(layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addComponent(databaseLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(hostsLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(userLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(passwordLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(sslLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
									.addComponent(sslCheckBox)
									.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(userTextField)
										.addComponent(passwordField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
										.addComponent(hostsTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
										.addComponent(databaseComboBox, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))
									.addGap(20, 20, 20))))
						.addGroup(layout.createSequentialGroup()
							.addComponent(loginLabel)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup()
							.addComponent(loginButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(20, 20, 20))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(20, 20, 20)
					.addComponent(loginLabel)
					.addGap(18, 18, 18)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(hostsLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(hostsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(databaseLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(databaseComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(userTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(sslCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(sslLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18, 24, Short.MAX_VALUE)
					.addComponent(loginButton)
					.addGap(20, 20, 20))
		);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner non-commercial license
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JCheckBox sslCheckBox;
	private JButton loginButton;
	private JTextField hostsTextField;
	private JComboBox<String> databaseComboBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
