package models

import play.api.libs.json.*

case class Person(name: String, age: Int)

object Person {
  def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))

  def tupled: ((String, Int)) => Person = this.apply.tupled

  implicit val personFormat: OFormat[Person] = Json.format[Person]
}