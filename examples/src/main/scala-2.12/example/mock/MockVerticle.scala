package example.mock

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.Router

/**
  * Created by Sascha on 25.04.2017.
  */
class MockVerticle extends ScalaVerticle {
  override def start(): Unit = {
    val router = Router.router(vertx)

    new Moxxi().routes.foreach(f => router.route(f._1, f._2).handler((rc) => f._3(rc)))

    vertx.createHttpServer().requestHandler(router.accept _).listen(3000)
  }
}

object MockVerticle {
  def main(args: Array[String]): Unit = {
    Vertx.vertx.deployVerticle(ScalaVerticle.nameForVerticle[MockVerticle])
  }
}
