package object domains {

  // NOTE: AnyValにミックスインできるように、Anyを継承します
  trait Value[T] extends Any {
    def value: T
  }

  case class DomainResult[+ENTITY <: Entity[? <: EntityId], +EVENT <: DomainEvent](entity: ENTITY, event: EVENT)
}