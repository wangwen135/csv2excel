package com.wwh.excel.tools;

import java.awt.EventQueue;

import javax.swing.ImageIcon;

import com.wwh.excel.tools.swing.ImageFrame;
import com.wwh.excel.tools.swing.PopupDialog;

public class Main {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // 窗口
                    ImageIcon icon = new ImageIcon(Main.class.getResource("/images/frame.png"));
                    ImageFrame frame = new ImageFrame(icon);

                    // 弹出框
                    ImageIcon icon2 = new ImageIcon(Main.class.getResource("/images/success.png"));
                    PopupDialog pd = new PopupDialog(frame, icon2, 1000);
                    frame.setPopupDialog(pd);

                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
