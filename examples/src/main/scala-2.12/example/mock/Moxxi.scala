package example.mock

import example.model.{Item, Person}
import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.lang.scala.json.JsonArray
import io.vertx.scala.ext.web.RoutingContext

import scala.collection.mutable

/**
  * Created by Sascha on 25.04.2017.
  * This is just for data mocking. It's just for testing and hacked together.
  */
class Moxxi {
  type RCHandler = (RoutingContext) => Unit

  lazy val routes:List[(HttpMethod, String, RCHandler)] = List(
    (HttpMethod.GET, "/persons", getPersons),
    (HttpMethod.GET, "/person/:name", getPerson),
    (HttpMethod.POST, "/person", postPerson),
    (HttpMethod.PUT, "/person", postPerson), // changing is the same as adding
    (HttpMethod.DELETE, "/person/:name", deletePerson),
    /********************************************************************************************************/
    (HttpMethod.GET, "/items", getItems),
    (HttpMethod.GET, "/item/:title", getItem),
    (HttpMethod.POST, "/item", postItem),
    (HttpMethod.PUT, "/item", postItem),  // changing is the same as adding
    (HttpMethod.DELETE, "/item/:title", deleteItem)
  )

  val getPersons:RCHandler = (rc:RoutingContext) => {
    rc.response().end(MockPersons.get.encode())
  }

  val getPerson:RCHandler = (rc:RoutingContext) => {
    val name = rc.request().getParam("name")
    rc.response().end(MockPersons.get(name.getOrElse("")).encode())
  }

  val postPerson:RCHandler = (rc:RoutingContext) => {
    rc.request().bodyHandler(buff => {
      val jp = buff.toJsonObject
      MockPersons.add(jp)
      rc.response().end(jp.encode())
    })
  }

  val deletePerson:RCHandler = (rc:RoutingContext) => {
    val title = rc.request().getParam("name")
    rc.response().end(MockPersons.delete(title.getOrElse("")).encode())
  }

  /********************************************************************************************************/

  val getItems:RCHandler = (rc:RoutingContext) => {
    rc.response().end(MockItems.get.encode())
  }

  val getItem:RCHandler = (rc:RoutingContext) => {
    val title = rc.request().getParam("title")
    rc.response().end(MockItems.get(title.getOrElse("")).encode())
  }

  val postItem:RCHandler = (rc:RoutingContext) => {
    rc.request().bodyHandler(buff => {
      val jp = buff.toJsonObject
      MockItems.add(jp)
      rc.response().end(jp.encode())
    })
  }

  val deleteItem:RCHandler = (rc:RoutingContext) => {
    val title = rc.request().getParam("title")
    rc.response().end(MockItems.delete(title.getOrElse("")).encode())
  }
}

object MockPersons {
  import scala.collection.JavaConverters._
  import MockItems.Implicits._
  import Implicits._

  private var persons:mutable.Map[String, Person] = mutable.Map[String, Person]()

  this.add(new Person("Lisa", 100, Array(), Array()))

  def get:JsonArray = persons.toList.foldLeft(new JsonArray())((prev, elem) => {
      prev.add(Implicits.person2json(elem._2))
  })

  def get(name:String):JsonObject = if(persons.contains(name)) persons(name) else new JsonObject()

  def add(person:JsonObject) = {
    val per = json2person(person)
    persons = persons + (per.name -> per)
  }

  def delete(name:String): JsonObject = if(persons.contains(name)) {
    val person = persons(name)
    persons = persons - person.name

    person
  } else new JsonObject()

  object Implicits {
    implicit def person2json(p:Person):JsonObject = {
      new JsonObject().put("name", p.name).put("knows", p.knows.foldLeft(new JsonArray())((prev, elem) => prev.add(elem.name))).put("items", p.items.foldLeft(new JsonArray())((prev, elem) => prev.add(MockItems.Implicits.item2json(elem))))
    }

    implicit  def json2person(j:JsonObject) = new Person(j.getString("name"), j.getInteger("age"),j.getJsonArray("knows").getList.asScala.map(f => persons(f.asInstanceOf[String])).toArray, j.getJsonArray("items").getList.asScala.map(f => json2item(MockItems.get(f.asInstanceOf[String]))).toArray)
  }
}

object MockItems {
  import Implicits._

  private var items: mutable.Map[String, Item] = mutable.Map[String, Item]()

  this.add(new Item("Peters TV", 1))
  this.add(new Item("Lisas car", 1))

  def add(item:JsonObject) = {
    val it = json2item(item)
    items = items + (it.title -> it)
  }

  def delete(title:String): JsonObject = if(items.contains(title)) {
    val item = items(title)
    items = items - item.title

    item
  } else new JsonObject()

  def get(title: String): JsonObject = if(items.contains(title)) items(title) else new JsonObject()

  def get:JsonArray = items.toList.foldLeft(new JsonArray())((prev, elem) => {
    prev.add(Implicits.item2json(elem._2))
  })

  object Implicits {
    implicit def item2json(i: Item): JsonObject = {
      new JsonObject().put("title", i.title).put("value", i.value)
    }

    implicit def json2item(j: JsonObject) = new Item(j.getString("title"), j.getFloat("value"))
  }
}
