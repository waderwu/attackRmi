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