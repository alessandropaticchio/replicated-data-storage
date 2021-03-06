package server;

import org.json.simple.parser.ParseException;
import server.buffer.Buffer;
import server.logic.LogicHandler;
import server.multicast.MulticastHandler;
import server.queue.InputQueue;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

public class Server {

    private final MulticastHandler multicast;
    private final LogicHandler logic;
    private ThreadedClientServer tcs;
    private Buffer buffer;
    private InputQueue queue;
    private final String groupAddr = "225.4.5.6";
    private final int port = 44500;
    private final int ID;


    public Server() throws UnknownHostException {
        this.ID = new Random().nextInt(65000);
        this.multicast = new MulticastHandler(groupAddr, port, ID, this);
        this.logic = new LogicHandler(this);
        this.buffer = new Buffer(this);
        this.queue = new InputQueue(this);
    }

    public void start() throws IOException, ParseException {
        // Run multicast handler
        new Thread(multicast).start();
        this.logic.fetchData();
    }

    public static void main(String[] args) throws IOException, ParseException {
        Server server = new Server();
        server.start();

        System.out.println("Replicated Storage Service is on.\nThe IP of this server is: " + GetIP.getIP().toString());

        server.goTcs(2004);
    }

    public void goTcs(int port) {
        this.tcs = new ThreadedClientServer(port, this);
        tcs.run();
    }

    public MulticastHandler getMulticast() {
        return multicast;
    }

    public LogicHandler getLogic() {
        return logic;
    }

    public Buffer getBuffer() { return buffer; }

    public InputQueue getQueue() { return queue; }

    public String getGroupAddr() { return groupAddr; }

    public int getPort() { return port; }

    public int getID() { return ID; }

    public ThreadedClientServer getTcs() {
        return tcs;
    }


}
