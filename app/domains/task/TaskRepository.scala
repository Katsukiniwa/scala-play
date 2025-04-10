package domains.task

import domains.*

import scala.util.Try
import scala.concurrent.{ExecutionContext, Future}

/*
 * NOTE: リポジトリはドメインレイヤからは呼び出さず、アプリケーションサービスからのみ呼び出すようにします。
 * リポジトリの責務はエンティティの永続化ですが、永続化は副作用となるため、
 * エンティティなどから呼び出すとエンティティが副作用を起こすことになってしまいます。
 * リポジトリの呼び出しをアプリケーションサービスに限定することで、副作用を局所化できます。
 */

/*
 * NOTE: リポジトリは自集約のエンティティだけを永続化します。他集約のエンティティを扱ってはいけません。
 * 自集約と他集約のエンティティを合成（JOIN）して扱う必要が有る場合は、
 * CQRSパターンのクエリモデルを使用します。
 */

trait TaskRepository {
  def list: Future[Seq[Task]]

  def save(task: Task): Future[Task]

  //  def delete(task: Task): Try[Task]
}