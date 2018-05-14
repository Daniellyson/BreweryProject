package view;

import controller.ApplicationController;
import exception.GetCustomerException;
import exception.InvalidFormatException;
import exception.NullException;
import listener.WindowListener;
import model.City;
import model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class ListingCustomer extends JFrame {
    private ApplicationController controller;

    private Container container;

    private ArrayList<Customer> customerListing;
    private ArrayList<City> cities;

    private static boolean gotIn = false;

    public ListingCustomer(ApplicationController controller) {

        super("DJ Brewery (Customer Listing)");

        if(!gotIn) {
            this.controller = controller;
            setUpMainWindow();
            setUpTable();
            setVisible(true);
        }
    }

    public void setUpMainWindow() {

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());

    }

    public void setUpTable() {
        customerListing = new ArrayList<Customer>();

        try {
            customerListing = controller.getAllCustomers();

        } catch (GetCustomerException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (NullException e) {
            e.printStackTrace();
        }

        AllCustomersModel allCustomersModel = new AllCustomersModel(customerListing);
        JTable customerTable = new JTable(allCustomersModel);
        int maxColumn = customerTable.getColumnCount();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i = 0; i < maxColumn; i++) {
            customerTable.getColumnModel().getColumn(i).setPreferredWidth(100);
            customerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        customerTable.getColumnModel().getColumn(12).setPreferredWidth(250);
        customerTable.getColumnModel().getColumn(maxColumn - 1).setPreferredWidth(350);



        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane customerScrollPane = new JScrollPane(customerTable);
        container.add(customerScrollPane, BorderLayout.CENTER);

        gotIn = true;

        this.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                gotIn = false;
            }
        });
    }
}
