import java.sql.*;
import java.util.EventObject;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class sqldemo extends JFrame implements ActionListener{
    private JLabel nameLabel, brandLabel, mfgLabel, expLabel, qtyLabel,priceLabel,medicineLabel;
    private JTextField nameField, brandField, mfgField, expField, qtyField,priceField,idfield;
    private JButton submitButton,deleteButton;
    private JComboBox<String> medicineComboBox;



    public void insertMedicineData() throws SQLException {
        double price=0.00;
        setTitle("Enter Medicine Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 20);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 20);
        add(nameField);

        brandLabel = new JLabel("Brand:");
        brandLabel.setBounds(50, 80, 100, 20);
        add(brandLabel);

        brandField = new JTextField();
        brandField.setBounds(150, 80, 200, 20);
        add(brandField);

        mfgLabel = new JLabel("Manufactured Date:");
        mfgLabel.setBounds(50, 110, 100, 20);
        add(mfgLabel);

        mfgField = new JTextField();
        mfgField.setBounds(150, 110, 200, 20);
        add(mfgField);

        expLabel = new JLabel("Expiry Date:");
        expLabel.setBounds(50, 140, 100, 20);
        add(expLabel);

        expField = new JTextField();
        expField.setBounds(150, 140, 200, 20);
        add(expField);

        qtyLabel = new JLabel("Quantity:");
        qtyLabel.setBounds(50, 170, 100, 20);
        add(qtyLabel);

        qtyField = new JTextField();
        qtyField.setBounds(150, 170, 200, 20);
        add(qtyField);

        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 200, 100, 30);
        submitButton.addActionListener(this);
        add(submitButton);

        setVisible(true);


        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();
                String brand = brandField.getText();
                String mfg = mfgField.getText();
                String exp = expField.getText();
                int qty = Integer.parseInt(qtyField.getText());

        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
            String sql = "INSERT INTO medicine (name, brand, mfg, exp, qty, price) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, brand);
            pstmt.setString(3, mfg);
            pstmt.setString(4, exp);
            pstmt.setInt(5, qty);
            pstmt.setDouble(6, price);
            pstmt.executeUpdate();
            System.out.println("Medicine data inserted successfully.");
            sqldemo s=new sqldemo();
            s.MedicalDashboard();
            setVisible(false);
            
        } catch (SQLException ex) {
            System.out.println("Error inserting medicine data: " + ex.getMessage());
        }

    }
});
    }


    public void updateMedicineData() throws SQLException {
        setTitle("Update Medicine Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    
        // Create a JComboBox to display the medicine names
        medicineComboBox = new JComboBox<String>();
    
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
            Statement stmt = conn.createStatement();
            String sql = "SELECT name FROM medicine";
            ResultSet rs = stmt.executeQuery(sql);
    
            while (rs.next()) {
                String name = rs.getString("name");
                medicineComboBox.addItem(name);
            }
    
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error retrieving medicine data: " + ex.getMessage());
        }
    
        // Add an ActionListener to the JComboBox to update the fields when a medicine is selected
        medicineComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
                    String sql = "SELECT * FROM medicine WHERE name=?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, (String)medicineComboBox.getSelectedItem());
                    ResultSet rs = pstmt.executeQuery();
    
                    if (rs.next()) {
                        idfield.setText(rs.getString("id"));
                        nameField.setText(rs.getString("name"));
                        brandField.setText(rs.getString("brand"));
                        mfgField.setText(rs.getString("mfg"));
                        expField.setText(rs.getString("exp"));
                        qtyField.setText(Integer.toString(rs.getInt("qty")));
                        priceField.setText(Double.toString(rs.getDouble("price")));
                    } else {
                        System.out.println("No medicine found with name " + medicineComboBox.getSelectedItem());
                    }
    
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Error retrieving medicine data: " + ex.getMessage());
                }
            }
        });
    
        medicineComboBox.setBounds(150, 20, 200, 20);
        add(medicineComboBox);
    
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 20);
        add(nameLabel);

        idfield = new JTextField();
        idfield.setBounds(150, 50, 200, 20);
        add(idfield);
    
        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 20);
        add(nameField);

        
    
        brandLabel = new JLabel("Brand:");
        brandLabel.setBounds(50, 80, 100, 20);
        add(brandLabel);
    
        brandField = new JTextField();
        brandField.setBounds(150, 80, 200, 20);
        add(brandField);
    
        mfgLabel = new JLabel("Manufactured Date:");
        mfgLabel.setBounds(50, 110, 100, 20);
        add(mfgLabel);
    
        mfgField = new JTextField();
        mfgField.setBounds(150, 110, 200, 20);
        add(mfgField);
    
        expLabel = new JLabel("Expiry Date:");
        expLabel.setBounds(50, 140, 100, 20);
        add(expLabel);
    
        expField = new JTextField();
        expField.setBounds(150, 140, 200, 20);
        add(expField);
    
        qtyLabel = new JLabel("Quantity:");
        qtyLabel.setBounds(50, 170, 100, 20);
        add(qtyLabel);
    
        qtyField = new JTextField();
        qtyField.setBounds(150, 170, 200, 20);
        add(qtyField);
    
        priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 200, 100, 20);
        add(priceLabel);
    
        priceField = new JTextField();
        priceField.setBounds(150, 200, 200, 20);
        add(priceField);
    
        submitButton = new JButton("Update");
        submitButton.setBounds(150, 230, 100, 30);
        submitButton.addActionListener(this);
        add(submitButton);
    
        setVisible(true);
    
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            String name = nameField.getText();
            String brand = brandField.getText();
            String mfg = mfgField.getText();
            String exp = expField.getText();
            String id = idfield.getText();
            int x=Integer.parseInt(id);
            int qty = Integer.parseInt(qtyField.getText());
            double price = Double.parseDouble(priceField.getText());

            conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
            String sql = "UPDATE medicine SET name=?, brand=?, mfg=?, exp=?, qty=?, price=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, brand);
            pstmt.setString(3, mfg);
            pstmt.setString(4, exp);
            pstmt.setInt(5, qty);
            pstmt.setDouble(6, price);
            pstmt.setInt(7, x);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medicine data updated successfully.");
                sqldemo s=new sqldemo();
                s.MedicalDashboard();
                setVisible(false);
            } else {
                System.out.println("No medicine data was updated.");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error updating medicine data: " + ex.getMessage());
        }
    }
});
    }

    public void deleteMedicineData() {
        setTitle("Delete Medicine Details");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    
        medicineComboBox = new JComboBox<String>();
    
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
            Statement stmt = conn.createStatement();
            String sql = "SELECT id, name FROM medicine";
            ResultSet rs = stmt.executeQuery(sql);
    
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                medicineComboBox.addItem(id + " - " + name);
            }
    
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error retrieving medicine data: " + ex.getMessage());
        }
    
        medicineLabel = new JLabel("Select a medicine:");
        medicineLabel.setBounds(50, 50, 100, 20);
        add(medicineLabel);
    
        medicineComboBox.setBounds(150, 50, 200, 20);
        add(medicineComboBox);
    
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(150, 80, 100, 30);
        deleteButton.addActionListener(this);
        add(deleteButton);
    
        setVisible(true);
    
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                PreparedStatement pstmt = null;
    
                try {
                    String selectedItem = medicineComboBox.getSelectedItem().toString();
                    int id = Integer.parseInt(selectedItem.substring(0, selectedItem.indexOf(" - ")));
    
                    conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
                    String sql = "DELETE FROM medicine WHERE id=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, id);
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Medicine data deleted successfully.");
                    } else {
                        System.out.println("No medicine data was deleted.");
                    }
    
                } catch (SQLException ex) {
                    System.out.println("Error deleting medicine data: " + ex.getMessage());
                }
            }
        });
    }


    public void MedicalDashboard() {
        setTitle("Medical Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 700, 500);
        panel.setBackground(new Color(248, 248, 248));
        panel.setLayout(null);
        add(panel);
        
        JLabel titleLabel = new JLabel("Medical Management System");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setBounds(50, 30, 400, 40);
        panel.add(titleLabel);
        
        JButton displaybtn = new JButton("Display Medicine");
        displaybtn.setBounds(150, 100, 200, 50);
        panel.add(displaybtn);
        
        JButton insertButton = new JButton("Insert Medicine");
        insertButton.setBounds(150, 160, 200, 50);
        panel.add(insertButton);
        
        JButton updateButton = new JButton("Update Medicine");
        updateButton.setBounds(150, 220, 200, 50);
        panel.add(updateButton);
        
        deleteButton = new JButton("Delete Medicine");
        deleteButton.setBounds(150, 270, 200, 50);
        panel.add(deleteButton);
        
        setVisible(true);

        displaybtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sqldemo s=new sqldemo();
                        s.displayMedicines();
                        setVisible(false);
            }
        });

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sqldemo s=new sqldemo();
                    try {
                        s.insertMedicineData();
                        setVisible(false);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sqldemo s=new sqldemo();
                    try {
                        s.updateMedicineData();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sqldemo s=new sqldemo();
                    s.deleteMedicineData();
            }
        });


    }


    public void displayMedicines() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM medicine";
            ResultSet rs = stmt.executeQuery(sql);
    
            DefaultTableModel tableModel = new DefaultTableModel();
    
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Brand");
            tableModel.addColumn("Manufactured Date");
            tableModel.addColumn("Expiry Date");
            tableModel.addColumn("Quantity");
            tableModel.addColumn("Price");
    
            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("brand");
                row[3] = rs.getString("mfg");
                row[4] = rs.getString("exp");
                row[5] = rs.getInt("qty");
                row[6] = rs.getDouble("price");
                tableModel.addRow(row);
            }
    
            JTable table = new JTable(tableModel);
    
            JScrollPane scrollPane = new JScrollPane(table);
    
            JFrame frame = new JFrame("Medicine List");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

            table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
                public boolean isCellEditable(EventObject e) {
                    return false;
                }
            });
    
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error retrieving medicine data: " + ex.getMessage());
        }
    }
    
    
 
    public void generateInvoice(int medId, String customerName, int qtySold, double price) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/medical", "root", "");
            
            // Retrieve medicine details
            String sql = "SELECT * FROM medicine WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, medId);
            rs = pstmt.executeQuery();
            
            // Check if medicine exists and has enough quantity
            if (rs.next()) {
                int medQty = rs.getInt("qty");
                if (medQty < qtySold) {
                    System.out.println("Not enough quantity in stock.");
                    return;
                }
                
                // Calculate total price and update medicine quantity
                double totalPrice = qtySold * price;
                int newQty = medQty - qtySold;
                sql = "UPDATE medicine SET qty = ? WHERE id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, newQty);
                pstmt.setInt(2, medId);
                pstmt.executeUpdate();
                
                // Insert sale details into sales table
                sql = "INSERT INTO sales (customer_name, med_id, qty_sold, price, sale_date) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, customerName);
                pstmt.setInt(2, medId);
                pstmt.setInt(3, qtySold);
                pstmt.setDouble(4, price);
                pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                pstmt.executeUpdate();
                
                System.out.println("Sale successful. Total price: " + totalPrice);
            } else {
                System.out.println("Medicine not found.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error generating invoice: " + e.getMessage());
        } finally {
            // Close database resources
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    

  public void actionPerformed(ActionEvent ae){}

    public static void main(String[] args){

        sqldemo s=new sqldemo();
            s.MedicalDashboard();
    }
}
