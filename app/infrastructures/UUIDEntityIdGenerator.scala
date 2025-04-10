package infrastructures

import java.util.UUID
import domains.EntityIdGenerator

object UUIDEntityIdGenerator extends EntityIdGenerator {
  override def genId() = UUID.randomUUID().toString
}