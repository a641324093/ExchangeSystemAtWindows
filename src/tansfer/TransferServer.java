package tansfer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.FileUtils;
import utils.MyFileFilter;

public class TransferServer extends Thread {

    public static int PORT = 8821;
    private String filePath;
    public TransferServer(String filePath)
    {
        this.filePath=filePath;
    }
    void startServer() throws IOException {
        
         if (filePath == null) {
                return;
            }
        Socket s = null;
        ServerSocket ss = new ServerSocket(PORT);
        DataInputStream dis=null;
        DataInputStream fis=null;
        DataOutputStream ps=null;
        try {
            File fi = new File(filePath);
                System.out.println("文件大小:" + (int) fi.length());
                // public Socket accept() throws
            s = ss.accept();
            dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            dis.readByte();
            fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            ps = new DataOutputStream(s.getOutputStream());
//            ps.writeUTF(fi.getName());
//            ps.flush();
//            ps.writeLong((long) fi.length());
//            ps.flush();

            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];

            while (true) {
                int read = 0;
                if (fis != null) {
                    read = fis.read(buf);
                }

                if (read == -1) {
                    break;
                }
                ps.write(buf, 0, read);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
           if(fis!=null)
           {
             fis.close();
           }
           if(s!=null)
           {    
             s.close();
           }
        }
        System.out.println("文件传输完成!");
    }

    @Override
    public void run() {
        try {
            this.startServer();
        } catch (IOException ex) {
            Logger.getLogger(TransferServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
