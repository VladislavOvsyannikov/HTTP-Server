import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ServerProcessor {

        private Socket s;
        private InputStream is;
        private OutputStream os;
        private String index;

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
            System.out.println(s);
            index = s;
            while (s.trim().length() != 0){
//                System.out.println(s);
                s = br.readLine();
            }
        }

    private void writeOutputStream() throws IOException {
        if (Objects.equals(index, "GET /index.html HTTP/1.1")) {
            String s = "<html><head><title> Test </title></head> <body><H1 align=center>Hello World!</H1></body></html>";
            os.write(s.getBytes());
            os.flush();
        }else{
            String s = "<html><h2>404</h2></html>";
            os.write(s.getBytes());
            os.flush();
        }
    }
}