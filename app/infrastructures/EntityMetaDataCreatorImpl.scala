package infrastructures

import domains.{EntityMetaData, EntityMetaDataCreator}

object EntityMetaDataCreatorImpl extends EntityMetaDataCreator {
  override def create = EntityMetaDataImpl(0)
}

case class EntityMetaDataImpl(
                               version: Version
                             ) extends EntityMetaData