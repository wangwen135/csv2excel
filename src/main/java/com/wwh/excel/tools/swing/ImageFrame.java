package com.wwh.excel.tools.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.wwh.excel.tools.worker.Csv2Xlsx;

public class ImageFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private ImageIcon icon;
    private Point origin = new Point();; // 用于移动窗体

    // 小弹出框
    private PopupDialog popupDialog;

    public ImageFrame(ImageIcon icon2) {
        this.icon = icon2;
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

        // 设置透明度
        this.setOpacity(0.9f);

        this.setLocationRelativeTo(null); // 设置窗口居中
        // setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 鼠标事件监听
        // 由于取消了默认的窗体结构，所以我们要手动设置一下移动窗体的方法
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();
            }

            // 窗体上单击鼠标右键关闭程序
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.exit(0);
                } else {
                    if (popupDialog != null) {
                        popupDialog.showDelayHide(e.getXOnScreen(), e.getYOnScreen());
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
        drag(imageLabel);

        // 设置窗口图标
        Image img2 = Toolkit.getDefaultToolkit().getImage(ImageFrame.class.getResource("/images/icon.png"));
        setIconImage(img2);
    }

    // 定义的拖拽方法
    public void drag(Component c) {
        // c 表示要接受拖拽的控件
        new DropTarget(c, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde)// 重写适配器的drop方法
            {
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))// 如果拖入的文件格式受支持
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);// 接收拖拽来的数据

                        @SuppressWarnings("unchecked")
                        List<File> list = (List<File>) (dtde.getTransferable()
                                .getTransferData(DataFlavor.javaFileListFlavor));

                        if (list.size() > 1) {
                            JOptionPane.showMessageDialog(null, "一次拖一个文件，靓仔");
                            return;
                        }

                        Csv2Xlsx.convert(list.get(0));

                        Point losPoint = ImageFrame.this.getLocationOnScreen();
                        popupDialog.showDelayHide(losPoint.x + dtde.getLocation().x, losPoint.y + dtde.getLocation().y);

                        dtde.dropComplete(true);// 指示拖拽操作已完成

                    } else {
                        dtde.rejectDrop();// 否则拒绝拖拽来的数据
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "出错了，靓仔\n" + e.getMessage());
                }
            }
        });
    }

    public PopupDialog getPopupDialog() {
        return popupDialog;
    }

    public void setPopupDialog(PopupDialog popupDialog) {
        this.popupDialog = popupDialog;
    }

}
