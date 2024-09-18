import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class StudentForm extends JFrame implements ActionListener {
    JTextField nameField, emailField;
    JButton submitButton;

    StudentForm() {
        setTitle("Student Form");
        setLayout(new FlowLayout());
        setSize(400, 150);

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        submitButton = new JButton("Submit");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(submitButton);

        submitButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
    
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in both fields.");
            return;
        }
    
        Connection con = null;
        PreparedStatement pstmt = null;
    
        try {

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/studentdb", "root", "Ekt@bh@16ti");
    
            // Prepare the SQL query
            String query = "INSERT INTO students (name, email) VALUES (?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
    
            // Execute the insert operation
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Data inserted successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting data: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
        public static void main(String[] args) {
        new StudentForm();
    }
}
