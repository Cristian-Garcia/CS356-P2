package app;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public abstract class MiniTwitterForm extends JFrame implements ActionListener {

    protected void styleButton(JButton b, int posX, int posY, int width, int height) {
        b.setBounds(posX, posY, width, height);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.CENTER);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setBackground(new Color(98, 190, 253));
        b.setForeground(Color.WHITE);
        b.setBorderPainted(true);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
        addButtonMouseListener(b);
        b.addActionListener(this);
    }
    
    private void addButtonMouseListener(JButton b){
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(79, 184, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(98, 190, 253));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(251, 150, 82));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(251, 150, 82));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setBackground(b, new Color(98, 190, 253));
            }

            void setBackground(JButton b, Color c) {
                if (b.isEnabled()) {
                    b.setBackground(c);
                }
            }
        });
    }
    
    protected void stylePanel(JPanel p, int posX, int posY, int width, int height) {
        p.setBounds(posX, posY, width, height);
        p.setLayout(null);
        p.setBackground(Color.LIGHT_GRAY);
        p.setOpaque(true);
        p.setBorder(BorderFactory.createBevelBorder(1));
        add(p);
    }

    protected void styleTree(JTree t, int posX, int posY, int width, int height) {
        t.setBounds(posX, posY, width, height);
        t.setLayout(null);
        t.setBackground(Color.WHITE);
        t.setOpaque(true);
        t.setBorder(BorderFactory.createLineBorder(new Color(98, 190, 253)));
    }

    protected void styleTitleLabel(JLabel l, int posX, int posY, int width, int height) {
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setFont(new Font("SANS_SERIF", Font.BOLD, 16));
        l.setBounds(posX,posY,width,height);
    }
  
    public void errorMessage(String messageTitle, String messageText){
        JOptionPane.showMessageDialog(this, messageText, messageTitle, JOptionPane.ERROR_MESSAGE);
    }   
}
