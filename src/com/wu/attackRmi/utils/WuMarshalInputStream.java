package com.wu.attackRmi.utils;

import com.wu.attackRmi.MockInterface;
import sun.rmi.server.MarshalInputStream;
import sun.rmi.server.UnicastRef;
import sun.rmi.transport.LiveRef;
import sun.rmi.transport.tcp.TCPEndpoint;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.server.ObjID;
import java.rmi.server.RemoteObjectInvocationHandler;
import java.util.Random;

public class WuMarshalInputStream extends MarshalInputStream {
    WuMarshalInputStream(InputStream in) throws IOException {
        super(in);
    }

    protected Class<?> resolveProxyClass(String[] interfaces){
        Class clazz;
        try{
            clazz = Class.forName(interfaces[0]);
        }catch (ClassNotFoundException e){
            ObjID id = new ObjID(new Random().nextInt()); // RMI registry
            TCPEndpoint te = new TCPEndpoint("127.0.0.1", 2333);
            UnicastRef refObject = new UnicastRef(new LiveRef(id, te, false));
            RemoteObjectInvocationHandler myInvocationHandler = new RemoteObjectInvocationHandler(refObject);

            MockInterface proxy = (MockInterface) Proxy.newProxyInstance(MockInterface.class.getClassLoader(), new Class[] { MockInterface.class, Remote.class }, myInvocationHandler);
            clazz = proxy.getClass();
            return clazz;
        }
        try {
            return super.resolveProxyClass(interfaces);
        }catch (Exception ee){
            ee.printStackTrace();
        }
        return clazz;
    }
}
