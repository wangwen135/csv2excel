package com.wwh.excel.tools;

import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;

import com.wwh.excel.tools.swing.ImageFrame;
import com.wwh.excel.tools.swing.PopupDialog;
import com.wwh.excel.tools.worker.Csv2Xlsx;

public class Main {
    public static void main(String[] args) {

        try {
            boolean b = false;
            if (args.length == 0) {
                // 测试此环境是否支持显示器、键盘和鼠标
                if (GraphicsEnvironment.isHeadless()) {
                    System.out.println("无桌面环境，以无头模式运行！");
                } else {
                    launchFrame();
                    b = true;
                }
            } else if (args.length == 1) { // 参数化运行
                b = Csv2Xlsx.convert(args[0], null, null);
            } else if (args.length == 2) {
                b = Csv2Xlsx.convert(args[0], args[1], null);
            } else if (args.length == 3) {
                b = Csv2Xlsx.convert(args[0], args[1], args[2]);
            }

            if (!b) {
                printUseage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            printUseage();
        }

    }

    private static void printUseage() {
        System.out.println("");
        System.out.println("########################################################################");
        System.out.println("");
        System.out.println("命令行使用说明：");
        System.out.println("    参数1：输入的CSV文件");
        System.out.println("    参数2：输出的Excel文件，默认输出为原文件相同的目录下");
        System.out.println("    参数3：CSV文件编码，如：GBK，默认为UTF-8");
        System.out.println("如：");
        System.out.println("$ java -jar CSV转Excel工具.jar /opt/export/device.csv");
        System.out.println("$ java -jar CSV转Excel工具.jar /opt/export/device.csv /opt/download/device.xlsx");
        System.out.println("$ java -jar CSV转Excel工具.jar /opt/export/device.csv /opt/download/device.xlsx UTF-8");
        System.out.println("");

    }

    private static void launchFrame() {
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
                    printUseage();
                }
            }
        });
    }

}
