# attackRmi
本项目采取了直接使用socket发包的方式攻击rmi。下面的版本指的是openjdk的版本

都是通过反序列化进行攻击，所以下面的6中攻击方式都需要本地classpath包含gadget
## AttackRegistryByBindAndAnnotationInvocationHandler
这个其实就是ysoserial的RMIRegistryExploit。

条件：
- < jdk8u121

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/75f31e0bd829/
## AttackRegistryByDGC
条件：
- < jdk8u121

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/75f31e0bd829/
## AttackRegistryByLookup
条件：
- < jdk8u121

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/75f31e0bd829/
## AttackRegistryByLookupAndUnicastRef
条件：
- < jdk8u232

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/523d48606333/
## AttackRegistryByLookupAndUnicastRefRemoteObject
条件：
- < jdk8u242

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/033462472c28

## AttackServerByNonPrimitiveParameter
条件：
- < jdk8u242 
  - 除primitive tyep以外的类型可被利用
- \>= jdk8u242
  - 除primitive type和String以外的类型可被利用

https://hg.openjdk.java.net/jdk8u/jdk8u/jdk/rev/033462472c28


