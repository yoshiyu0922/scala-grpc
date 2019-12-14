## gRPC MEMO

#### gRPC Server起動

```sbtshell
$ sbt "runMain example.helloworld.HelloWorldServer"

[info] Loading settings for project global-plugins from idea.sbt ...
[info] Loading global plugins from /Users/yoshikawayuki/.sbt/1.0/plugins
[info] Loading settings for project grpc-scala-build from protoc.sbt ...
[info] Loading project definition from /Users/yoshikawayuki/workspace/grpc-scala/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to grpc-scala (in build file:/Users/yoshikawayuki/workspace/grpc-scala/)
[warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list
[info] Running example.helloworld.HelloWorldServer 
12 14, 2019 3:27:39 午後 example.helloworld.HelloWorldServer example$helloworld$HelloWorldServer$$start
情報: Server started, listening on 50051
``` 

#### gRPC Client実行

```sbtshell
$ sbt "runMain example.helloworld.HelloWorldClient"

[info] Loading settings for project global-plugins from idea.sbt ...
[info] Loading global plugins from /Users/yoshikawayuki/.sbt/1.0/plugins
[info] Loading settings for project grpc-scala-build from protoc.sbt ...
[info] Loading project definition from /Users/yoshikawayuki/workspace/grpc-scala/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to grpc-scala (in build file:/Users/yoshikawayuki/workspace/grpc-scala/)
[warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list
[info] Running example.helloworld.HelloWorldClient 
12 14, 2019 3:28:57 午後 example.helloworld.HelloWorldClient greet
情報: Will try to greet world ... <- Greeter.sayHelloが実行される
12 14, 2019 3:28:58 午後 example.helloworld.HelloWorldClient$$anonfun$greet$2 applyOrElse
警告: RPC failed: Status{code=UNIMPLEMENTED, description=Method not found: grpc.helloworld.Greeter/SayHello, cause=null}
[success] Total time: 3 s, completed 2019/12/14 15:28:58
```