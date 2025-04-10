package domains

object EntityMetaDataCreatorImpl extends EntityMetaDataCreator {
  override def create = EntityMetaDataImpl(0)
}

type Version = Int

case class EntityMetaDataImpl(
                               version: Version
                             ) extends EntityMetaData