package example.helloworld

import java.util.logging.Logger

import grpc.helloworld.helloworld.{GreeterGrpc, HelloReply, HelloRequest}
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.{ExecutionContext, Future}

object HelloWorldServer {
  private val port = 50051
  private val logger = Logger.getLogger(classOf[HelloWorldServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new HelloWorldServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }
}

class HelloWorldServer(executionContext: ExecutionContext) { self =>
  private[this] var serverOpt: Option[Server] = None

  private def start(): Unit = {
    val server = ServerBuilder.forPort(HelloWorldServer.port).build.start
    // addService: Serviceクラスをbindする。対象のリクエストを呼ばれると必ず実行される
//    val server = ServerBuilder.forPort(HelloWorldServer.port).addService(GreeterGrpc.bindService(new GreeterImpl, executionContext)).build.start
    serverOpt = Some(server)
    HelloWorldServer.logger.info("Server started, listening on " + HelloWorldServer.port)
    // シャットダウンした時のイベント
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private def stop(): Unit = serverOpt.foreach(_.shutdown())

  private def blockUntilShutdown(): Unit = serverOpt.foreach(_.awaitTermination())

  // bindするServiceクラス
  private class GreeterImpl extends GreeterGrpc.Greeter {
    override def sayHello(req: HelloRequest) = {
      val reply = HelloReply(message = "Hello " + req.name)
      Future.successful(reply)
    }
  }
}
