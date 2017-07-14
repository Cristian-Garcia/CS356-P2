package app;

import tree.*;
import user.User;
import user.UserElement;
import visitor.*;
import java.awt.*;
import javax.swing.*;

import group.Group;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends MiniTwitterForm implements ActionListener{

    private static AdminPanel INSTANCE;  
    
    private UserElement rootGroup;
    private UserElementTreeModel treeModel;
    private JTree tree;
    private JScrollPane treeScrollPane;
    private JTextField userID,
    				   groupID;
    private JPanel analysisPanel,
    			   treeViewPanel,
    			   userGroupManagementPanel;
    private JLabel userGroupManagementTitle,
	   			   groupIDLabel,
	   			   userIDLabel,
	   			   treeViewTitle,
	   			   analysisTitle,
	   			   formTitle;
    private JButton addGroup,
    				addUser,
    				openUserView,
    				showPositivePercentage,
    				showMessagesTotal,
    				showGroupTotal,
    				showUserTotal;
  
    private AdminPanel(){
        System.out.println("Admin Panel Rendered");
    }
    
    public static AdminPanel getInstance(){
        if (INSTANCE == null){
            synchronized(AdminPanel.class){
                if (INSTANCE == null){
                    INSTANCE = new AdminPanel();
                }
            }
        }
        return INSTANCE;
    }
 
    public void init(){
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("CS356 - Mini-Twitter");
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        formTitle = new JLabel("Mini-Twitter");
        formTitle.setHorizontalAlignment(SwingConstants.CENTER);
        formTitle.setBounds(280,15,250,50);
        formTitle.setForeground(new Color(98, 190, 253));
        formTitle.setFont(new Font("SANS_SERIF", Font.BOLD + Font.ITALIC, 36));
        add(formTitle);
        
        // visitor panel
        analysisPanel = new JPanel();
        stylePanel(analysisPanel, 230, 230, 350, 130);
        
        showUserTotal = new JButton("Show User Total");
        styleButton(showUserTotal, 10, 25, 160, 35);
        analysisPanel.add(showUserTotal);
        
        showGroupTotal = new JButton("Show Group Total");
        styleButton(showGroupTotal, 180, 25, 160, 35);
        analysisPanel.add(showGroupTotal);
        
        showMessagesTotal = new JButton("Show Messages Total");
        styleButton(showMessagesTotal, 10, 75, 160, 35);
        analysisPanel.add(showMessagesTotal);
        
        showPositivePercentage = new JButton("Show Positive Percentage");
        styleButton(showPositivePercentage, 180, 75, 160, 35);
        analysisPanel.add(showPositivePercentage);
    
        
        // tree view panel
        treeViewPanel = new JPanel();
        stylePanel(treeViewPanel, 10, 10, 210, 351);
        
        rootGroup = new Group(treeModel, "Root");
        treeModel = new UserElementTreeModel(rootGroup);
        tree = new JTree(treeModel);
        tree.setCellRenderer(new UserElementTreeCellRenderer());
        
        styleTree(tree, 0, 0, 190, 255);

        treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setBounds(10, 40, 190, 255);
        treeViewPanel.add(treeScrollPane);
        
        openUserView = new JButton("Open User View");
        styleButton(openUserView, 10, 305, 190, 35);
        treeViewPanel.add(openUserView);
        
        treeViewTitle = new JLabel("Users");
        styleTitleLabel(treeViewTitle, 0, 5, 210, 30);
        treeViewPanel.add(treeViewTitle);
        
        add(analysisPanel);
        
       
        // user/group nav panel
        userGroupManagementPanel = new JPanel();
        stylePanel(userGroupManagementPanel, 230, 90, 350, 120);
        
        addUser = new JButton("Add User");
        styleButton(addUser, 180, 20, 160, 35);
        userGroupManagementPanel.add(addUser);
        
        addGroup = new JButton("Add Group");
        styleButton(addGroup, 180, 70, 160, 35);
        userGroupManagementPanel.add(addGroup);
        
        userID = new JTextField();
        userID.setBounds(10, 20, 160, 35);
        userGroupManagementPanel.add(userID);
        
        groupID = new JTextField();
        groupID.setBounds(10, 70, 160, 35);
        userGroupManagementPanel.add(groupID);
        
        userIDLabel = new JLabel("User ID:");
        userIDLabel.setBounds(10, 36, 160, 15);
        userGroupManagementPanel.add(userIDLabel);
        
        groupIDLabel = new JLabel("Group ID:");
        groupIDLabel.setBounds(10, 91, 160, 15);
        userGroupManagementPanel.add(groupIDLabel);
       
        setVisible(true);
        System.out.println("Admin Panel Generated");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addUser) {
           addUser();
        } 
        else if (ae.getSource() == addGroup){
            addGroup();
        }
        else if (ae.getSource() == openUserView){
            openUserView();
        }
        else if (ae.getSource() == showUserTotal){
            showUserTotal();
        }
        else if (ae.getSource() == showGroupTotal){
            showGroupTotal();
        }
        else if (ae.getSource() == showMessagesTotal){
            showMessagesTotal();
        }
        else if (ae.getSource() == showPositivePercentage){
            showPositivePercentage();
        }
    }
    
    private void addUser(){
        String id = userID.getText();
        if (!id.equals("")){
            UserElement parent = getSelectedUserElement();
            if (treeModel.findUserByID(rootGroup, id) == null){
                treeModel.addUserElement(parent, new User(treeModel, id));
                userID.setText("");
            }
            else {
                errorMessage("User Already Exists",  "Error: That username is taken.");
            }
        }
    }
    
    private void addGroup(){
        String id = groupID.getText();
        if (!id.equals("")){
            UserElement parent = getSelectedUserElement();
            if (treeModel.findGroupByID(rootGroup, id) == null){
                treeModel.addUserElement(parent, new Group(treeModel, id));
                groupID.setText("");
            }
            else {
                errorMessage("Group Already Exists",  "Error: That Group name is taken.");
            }            
        }
    }
    
    private void openUserView() {
        UserElement elem = getSelectedUserElement();
        elem.openUserView();
    }
    
    private void showUserTotal(){
        int result;
        UserElement start = getSelectedUserElement();
        UserTotalVisitor vis = new UserTotalVisitor();
        start.accept(vis);
        result = vis.total;
        JOptionPane.showMessageDialog(this, "Total count of Users: " + result, "Total Messages", JOptionPane.PLAIN_MESSAGE);
        System.out.println("Total users: " + result);
    }

    private void showGroupTotal(){
        int result;
        UserElement start = getSelectedUserElement();
        GroupTotalVisitor vis = new GroupTotalVisitor();
        start.accept(vis);
        result = vis.total;
        JOptionPane.showMessageDialog(this, "Total count of Groups: " + result, "Total Groups", JOptionPane.PLAIN_MESSAGE);
        System.out.println("Total groups: " + result);
    }
    
    private void showMessagesTotal(){
        int result;
        UserElement start = getSelectedUserElement();
        MessagesTotalVisitor vis = new MessagesTotalVisitor();
        start.accept(vis);
        result = vis.total;
        JOptionPane.showMessageDialog(this, "Total count of Tweets: " + result, "Total Messages", JOptionPane.PLAIN_MESSAGE);
        System.out.println("Total messages: " + result);
    }
    
    private void showPositivePercentage(){
        double result;
        UserElement start = getSelectedUserElement();
        PositiveMessagesTotalVisitor posTotalVis = new PositiveMessagesTotalVisitor();
        MessagesTotalVisitor messagesTotalVis = new MessagesTotalVisitor();
        start.accept(posTotalVis);
        start.accept(messagesTotalVis);
        
        result = (double)posTotalVis.total / (double)messagesTotalVis.total * 100.0;
        
        JOptionPane.showMessageDialog(this, "Percentage of Tweets containing positive messages: " + result + "%", "Positive Percentage", JOptionPane.PLAIN_MESSAGE);
        System.out.println("Positive percentage: " + result + "%");
    }

    private UserElement getSelectedUserElement(){
        UserElement result = ((UserElement)tree.getLastSelectedPathComponent());
        if (result == null){
            result = rootGroup;
        }
        return result;
    }
}
