package tansfer;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

public class TransferClient extends Thread{
    private TransferClientSocket cs = null;
    private String ip = "127.0.0.1";
    private int port =TransferServer.PORT;
    private String sendMessage = "Windwos";
    //保存的路径不包括文件名
    private String des_path;
    
    public TransferClient(String des_path) {
        this.des_path=des_path;
    }
    
    public void startClient()
    {
        try {
            if (createConnection()) {
                sendMessage();
                getMessage();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private boolean createConnection() {
        cs = new TransferClientSocket(ip, port);
        try {
            cs.CreateConnection();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private void sendMessage() {
        if (cs == null)
            return;
        try {
            cs.sendMessage(sendMessage);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "传送数据错误！\n");
        }
    }

    private void getMessage() {
        if (cs == null)
            return;
        DataInputStream inputStream = null;
        try {
            inputStream = cs.getMessageStream();
        } catch (Exception e) {
            System.out.print("获得数据错误!\n");
            return;
        }

        try {
            String savePath = des_path;
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            int passedlen = 0;
            long len=0;
            //JAVASE7以后的新特效try with source
            try (//            String file_name=inputStream.readUTF();
//             System.out.println();
//            inputStream.readUTF();
                    DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savePath)))
//            len = inputStream.readLong();
            ) {
                while (true) {
                    int read = 0;
                    if (inputStream != null) {
                        read = inputStream.read(buf);
                    }
                    passedlen += read;
                    if (read == -1) {
                        break;
                    }
//                System.out.println("文件接收了" +  (passedlen * 100/ len) + "%\n");
                    fileOut.write(buf, 0, read);
                }
//            System.out.println("传送完成！" + savePath + "\n");
            }
//            System.out.println("传送完成！" + savePath + "\n");
        } catch (Exception e) {
//            System.out.println("传送失败！·" + "\n");
        }
    }
    
    @Override
    public void run()
    {
        this.startClient();
    }
}