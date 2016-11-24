import java.io.*;
import java.net.Socket;

public class ServerProcessor {

        private Socket s;
        private InputStream is;
        private OutputStream os;
        private String fileName;

        public ServerProcessor(Socket s) throws IOException {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void start() throws IOException {
            readInputStream();
            writeOutputStream();
            s.close();
        }

        private void readInputStream() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();
            String[] s1 = s.split(" ");
            String s2 = s1[1];
            if (s2.length()>0) {
                fileName = s2.substring(1, s2.length());
            }else fileName = "";
        }

    private void writeOutputStream() throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line;
            while ((line = bfr.readLine()) != null) {
                os.write(line.getBytes());
            }
            os.flush();
        }else{
            os.write("<html><h2>404</h2></html>".getBytes());
            os.flush();
        }
    }
}