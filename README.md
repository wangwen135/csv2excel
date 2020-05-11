### CSV文件转Excel

将csv文件拖拽到可爱的初音MM上即会在相同的目录生成Excle文件

#### 双击执行 
可执行jar包，双击即可显示图形界面

**自定义窗口**  
图片要是透明背景的  
- images/frame.png -- 主窗口  
- images/icon.png  -- 任务栏小图标  
- images/success.png -- 弹出提示框   


#### 命令行执行

**命令行使用说明：**  
> 参数1：输入的CSV文件  
> 参数2：输出的Excel文件，默认输出为原文件相同的目录下  
> 参数3：CSV文件编码，如：GBK，默认为UTF-8  

如：
```
$ java -jar CSV转Excel工具.jar /opt/export/device.csv
$ java -jar CSV转Excel工具.jar /opt/export/device.csv /opt/download/device.xlsx
$ java -jar CSV转Excel工具.jar /opt/export/device.csv /opt/download/device.xlsx UTF-8
```

