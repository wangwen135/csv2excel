package com.wwh.excel.tools.swing;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

public class PopupDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private ImageIcon icon;

    private Frame owner;

    private Timer timer;

    private void initTimer(int delayTime) {
        // 定时消失
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        };
        timer = new Timer(delayTime, taskPerformer);
        timer.setRepeats(false);
    }

    /**
     * Create the dialog.
     */
    public PopupDialog(Frame owner, ImageIcon icon2, int delayTime) {
        super(owner);
        this.icon = icon2;
        this.owner = owner;

        initTimer(delayTime);

        JLabel imageLabel = new JLabel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                icon.paintIcon(this, g, 0, 0);
            }
        };

        this.add(imageLabel);

        this.setUndecorated(true); // 关键语句1 不启用窗体装饰

        this.setSize(icon.getIconWidth(), icon.getIconHeight()); // 设置窗口大小

        // 这个需要包的支持
        // AWTUtilities.setWindowOpaque(this, false);

        // JDK 1.7 版本以上，使用 this.setBackground(new Color(0, 0, 0, 0));
        this.setBackground(new Color(0, 0, 0, 0)); // 关键句2

        this.setLocationRelativeTo(owner);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                PopupDialog.this.setVisible(false);

            }
        });
    }

    /**
     * 先显示再延期隐藏
     */
    public void showDelayHide() {
        this.setLocationRelativeTo(owner);
        setVisible(true);
        timer.start();
    }

    /**
     * 先显示再延期隐藏
     * 
     * @param x 窗体中心点
     * @param y 窗口中心点
     */
    public void showDelayHide(int x, int y) {
        int x2 = x - getWidth() / 2;
        int y2 = y - getHeight() / 2;
        this.setLocation(x2, y2);
        setVisible(true);
        timer.start();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (!b) {
            timer.stop();
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ImageIcon icon = new ImageIcon(PopupDialog.class.getResource("/images/success.png"));
            PopupDialog dialog = new PopupDialog(null, icon, 5000);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            // dialog.setVisible(true);
            dialog.showDelayHide(100, 200);

            Thread.sleep(6000);
            dialog.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
