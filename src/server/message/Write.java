package server.message;

import java.net.Socket;

public class Write extends Message {

    int file;
    int data;

    public Write(int sender, int file, int data) {
        super(sender);
        this.file = file;
        this.data = data;
    }

    public int getFile() {
        return file;
    }

    public int getData() {
        return data;
    }

}
