package cluster.simple

import cluster.commons.Add

object ClusterApp extends App {

  //initiate frontend node
  Frontend.initiate()

  //initiate three nodes from backend
  Backend.initiate(2552)

  Backend.initiate(2560)

  Backend.initiate(2561)

  Thread.sleep(10000)

  Frontend.getFrontend ! Add(2, 4)

}

/*
[INFO] [06/17/2018 15:22:26.622] [main] [akka.remote.Remoting] Starting remoting
[INFO] [06/17/2018 15:22:27.496] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://ClusterSystem@127.0.0.1:2551]
[INFO] [06/17/2018 15:22:27.521] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Starting up...
[INFO] [06/17/2018 15:22:27.681] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Registered cluster JMX MBean [akka:type=Cluster]
[INFO] [06/17/2018 15:22:27.682] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Started up successfully
[WARN] [06/17/2018 15:22:27.750] [ClusterSystem-akka.actor.default-dispatcher-4] [akka.tcp://ClusterSystem@127.0.0.1:2551/system/cluster/core/daemon/downingProvider] Don't use auto-down feature of Akka Cluster in production. See 'Auto-downing (DO NOT USE)' section of Akka Cluster documentation.
[INFO] [06/17/2018 15:22:27.790] [ClusterSystem-akka.actor.default-dispatcher-3] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Node [akka.tcp://ClusterSystem@127.0.0.1:2551] is JOINING, roles [frontend, dc-default]
[INFO] [06/17/2018 15:22:27.814] [main] [akka.remote.Remoting] Starting remoting
[INFO] [06/17/2018 15:22:27.850] [ClusterSystem-akka.actor.default-dispatcher-3] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Leader is moving node [akka.tcp://ClusterSystem@127.0.0.1:2551] to [Up]
[INFO] [06/17/2018 15:22:27.855] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://ClusterSystem@127.0.0.1:55775]
[INFO] [06/17/2018 15:22:27.859] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55775] - Starting up...
[WARN] [06/17/2018 15:22:27.887] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Could not register Cluster JMX MBean with name=akka:type=Cluster as it is already registered. If you are running multiple clusters in the same JVM, set 'akka.cluster.jmx.multi-mbeans-in-same-jvm = on' in config
[INFO] [06/17/2018 15:22:27.891] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55775] - Started up successfully
[WARN] [06/17/2018 15:22:27.927] [ClusterSystem-akka.actor.default-dispatcher-19] [akka.tcp://ClusterSystem@127.0.0.1:55775/system/cluster/core/daemon/downingProvider] Don't use auto-down feature of Akka Cluster in production. See 'Auto-downing (DO NOT USE)' section of Akka Cluster documentation.
[INFO] [06/17/2018 15:22:27.948] [main] [akka.remote.Remoting] Starting remoting
[INFO] [06/17/2018 15:22:27.987] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://ClusterSystem@127.0.0.1:55788]
[INFO] [06/17/2018 15:22:27.989] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55788] - Starting up...
[WARN] [06/17/2018 15:22:28.004] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Could not register Cluster JMX MBean with name=akka:type=Cluster as it is already registered. If you are running multiple clusters in the same JVM, set 'akka.cluster.jmx.multi-mbeans-in-same-jvm = on' in config
[INFO] [06/17/2018 15:22:28.005] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55788] - Started up successfully
[WARN] [06/17/2018 15:22:28.018] [ClusterSystem-akka.actor.default-dispatcher-5] [akka.tcp://ClusterSystem@127.0.0.1:55788/system/cluster/core/daemon/downingProvider] Don't use auto-down feature of Akka Cluster in production. See 'Auto-downing (DO NOT USE)' section of Akka Cluster documentation.
[INFO] [06/17/2018 15:22:28.062] [main] [akka.remote.Remoting] Starting remoting
[INFO] [06/17/2018 15:22:28.097] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://ClusterSystem@127.0.0.1:55803]
[INFO] [06/17/2018 15:22:28.101] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55803] - Starting up...
[WARN] [06/17/2018 15:22:28.111] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Could not register Cluster JMX MBean with name=akka:type=Cluster as it is already registered. If you are running multiple clusters in the same JVM, set 'akka.cluster.jmx.multi-mbeans-in-same-jvm = on' in config
[INFO] [06/17/2018 15:22:28.111] [main] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55803] - Started up successfully
[WARN] [06/17/2018 15:22:28.135] [ClusterSystem-akka.actor.default-dispatcher-6] [akka.tcp://ClusterSystem@127.0.0.1:55803/system/cluster/core/daemon/downingProvider] Don't use auto-down feature of Akka Cluster in production. See 'Auto-downing (DO NOT USE)' section of Akka Cluster documentation.
[INFO] [06/17/2018 15:22:28.338] [ClusterSystem-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Received InitJoin message from [Actor[akka.tcp://ClusterSystem@127.0.0.1:55788/system/cluster/core/daemon/joinSeedNodeProcess-1#-251609160]] to [akka.tcp://ClusterSystem@127.0.0.1:2551]
[INFO] [06/17/2018 15:22:28.339] [ClusterSystem-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Sending InitJoinAck message from node [akka.tcp://ClusterSystem@127.0.0.1:2551] to [Actor[akka.tcp://ClusterSystem@127.0.0.1:55788/system/cluster/core/daemon/joinSeedNodeProcess-1#-251609160]]
[INFO] [06/17/2018 15:22:28.339] [ClusterSystem-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Received InitJoin message from [Actor[akka.tcp://ClusterSystem@127.0.0.1:55775/system/cluster/core/daemon/joinSeedNodeProcess-1#-1739602070]] to [akka.tcp://ClusterSystem@127.0.0.1:2551]
[INFO] [06/17/2018 15:22:28.340] [ClusterSystem-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Sending InitJoinAck message from node [akka.tcp://ClusterSystem@127.0.0.1:2551] to [Actor[akka.tcp://ClusterSystem@127.0.0.1:55775/system/cluster/core/daemon/joinSeedNodeProcess-1#-1739602070]]
[INFO] [06/17/2018 15:22:28.340] [ClusterSystem-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Received InitJoin message from [Actor[akka.tcp://ClusterSystem@127.0.0.1:55803/system/cluster/core/daemon/joinSeedNodeProcess-1#331829173]] to [akka.tcp://ClusterSystem@127.0.0.1:2551]
[INFO] [06/17/2018 15:22:28.341] [ClusterSystem-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Sending InitJoinAck message from node [akka.tcp://ClusterSystem@127.0.0.1:2551] to [Actor[akka.tcp://ClusterSystem@127.0.0.1:55803/system/cluster/core/daemon/joinSeedNodeProcess-1#331829173]]
[INFO] [06/17/2018 15:22:28.393] [ClusterSystem-akka.actor.default-dispatcher-2] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Node [akka.tcp://ClusterSystem@127.0.0.1:55788] is JOINING, roles [backend, dc-default]
[INFO] [06/17/2018 15:22:28.393] [ClusterSystem-akka.actor.default-dispatcher-2] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Node [akka.tcp://ClusterSystem@127.0.0.1:55775] is JOINING, roles [backend, dc-default]
[INFO] [06/17/2018 15:22:28.394] [ClusterSystem-akka.actor.default-dispatcher-2] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Node [akka.tcp://ClusterSystem@127.0.0.1:55803] is JOINING, roles [backend, dc-default]
[INFO] [06/17/2018 15:22:28.519] [ClusterSystem-akka.actor.default-dispatcher-20] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55788] - Welcome from [akka.tcp://ClusterSystem@127.0.0.1:2551]
[INFO] [06/17/2018 15:22:28.519] [ClusterSystem-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55775] - Welcome from [akka.tcp://ClusterSystem@127.0.0.1:2551]
[INFO] [06/17/2018 15:22:28.529] [ClusterSystem-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:55803] - Welcome from [akka.tcp://ClusterSystem@127.0.0.1:2551]
[WARN] [SECURITY][06/17/2018 15:22:28.535] [ClusterSystem-akka.remote.default-remote-dispatcher-10] [akka.serialization.Serialization(akka://ClusterSystem)] Using the default Java serializer for class [cluster.commons.BackendRegistration$] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
[WARN] [SECURITY][06/17/2018 15:22:28.537] [ClusterSystem-akka.remote.default-remote-dispatcher-9] [akka.serialization.Serialization(akka://ClusterSystem)] Using the default Java serializer for class [cluster.commons.BackendRegistration$] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
[WARN] [SECURITY][06/17/2018 15:22:28.540] [ClusterSystem-akka.remote.default-remote-dispatcher-8] [akka.serialization.Serialization(akka://ClusterSystem)] Using the default Java serializer for class [cluster.commons.BackendRegistration$] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
[INFO] [06/17/2018 15:22:28.744] [ClusterSystem-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Leader is moving node [akka.tcp://ClusterSystem@127.0.0.1:55775] to [Up]
[INFO] [06/17/2018 15:22:28.744] [ClusterSystem-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Leader is moving node [akka.tcp://ClusterSystem@127.0.0.1:55788] to [Up]
[INFO] [06/17/2018 15:22:28.744] [ClusterSystem-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://ClusterSystem)] Cluster Node [akka.tcp://ClusterSystem@127.0.0.1:2551] - Leader is moving node [akka.tcp://ClusterSystem@127.0.0.1:55803] to [Up]
Frontend: I'll forward add operation to backend node to handle it.
[WARN] [SECURITY][06/17/2018 15:22:38.126] [ClusterSystem-akka.remote.default-remote-dispatcher-14] [akka.serialization.Serialization(akka://ClusterSystem)] Using the default Java serializer for class [cluster.commons.Add] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
I'm a backend with path: Actor[akka://ClusterSystem/user/Backend#-814562267] and I received add operation.
 */