package example.helloworld

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

import grpc.helloworld.helloworld.GreeterGrpc.GreeterBlockingStub
import grpc.helloworld.helloworld.{GreeterGrpc, HelloRequest}
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}

import scala.util.Try

object HelloWorldClient {
  def apply(host: String, port: Int): HelloWorldClient = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = GreeterGrpc.blockingStub(channel)
    new HelloWorldClient(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    val client = HelloWorldClient("localhost", 50051)
    Try {
      val user = args.headOption.getOrElse("world")
      client.greet(user)
    } recover {
      case _: Throwable => client.shutdown()
    }
  }
}

class HelloWorldClient private (
  private val channel: ManagedChannel,
  private val blockingStub: GreeterBlockingStub
) {
  private[this] val logger = Logger.getLogger(classOf[HelloWorldClient].getName)

  def shutdown(): Unit = channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)

  /** HelloWorldClientServerにリクエスト */
  def greet(name: String): Unit = {
    logger.info("Will try to greet " + name + " ...")
    Try {
      val request = HelloRequest(name = name)
      val response = blockingStub.sayHello(request)
      logger.info("Greeting: " + response.message)
    } recover {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
    }
  }
}
