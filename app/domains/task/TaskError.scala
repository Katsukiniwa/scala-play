package domains.task

import domains.*

sealed trait TaskError extends DomainError {
  val task: Task
}

case class TaskAlreadyOpened(task: Task) extends TaskError

case class TaskAlreadyClosed(task: Task) extends TaskError