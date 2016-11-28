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
            String text="";
            String i;
            while ((i = bfr.readLine()) != null) {
                text = text + i + "\r\n";
            }
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: NastayServer\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + text.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + text;
            os.write(result.getBytes());
            os.flush();
        }else{
            os.write("<html><h2>404</h2></html>".getBytes());
            os.flush();
        }
    }
}