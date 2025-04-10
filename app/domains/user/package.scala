package domains

package object user {
  case class UserId(value: String) extends AnyVal with EntityId

  case class UserName(value: String) extends AnyVal with Value[String]

  object UserId {
    def newId(implicit idGen: EntityIdGenerator): UserId = UserId(idGen.genId())
  }
}