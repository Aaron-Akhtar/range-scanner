package uk.tsis.rangescan;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Scanner implements Runnable{

    public int gen(){
        return new Random().nextInt(255);
    }

    private String range;
    private int port;
    private int timeout;
    private Set<String> tried = new HashSet<>();

    /**
     * @param range - The ip range, example: 29.0.23.0 - will replace all 0's with random integers up to 255 to search for active hosts
     * @param port - The port to check for active addresses
     * @param timeout - The timeout until the software assumes its a inactive machine
     */
    public Scanner(String range, int port, int timeout) {
        this.range = range;
        this.port = port;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        while(true){
            String host = "";
            for (int x =0; x < range.length(); x++){

                if (x ==0){
                    if (range.charAt(x) == '0'){
                        host += gen();
                    }else{
                        host += range.charAt(x);
                    }
                }else {

                    if (range.charAt(x) == '0' && range.charAt(x - 1) == '.' && range.charAt(x - 1) == '.') {
                        host += gen();
                    } else {
                        host += range.charAt(x);
                    }
                }
            }
            if (!tried.contains(host)){
                try{
                    long p = System.currentTimeMillis();
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(host, port), timeout);
                    long f = System.currentTimeMillis() - p;
                    System.out.println("  ]Hit -> " + host + " |> " + InetAddress.getByName(host).getHostName());
                    Thread.sleep(30);
                }catch (Exception e){

                }
                tried.add(host);
            }
        }
    }

}
