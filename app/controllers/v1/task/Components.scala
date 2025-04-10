package controllers.v1.task

import usecases.task.TaskUseCase
import domains.task.*
import domains.user.*
import infrastructures.repository.{SlickUserRepository, SlickTaskRepository}

object Components {

  val TaskService: TaskUseCase = new TaskUseCase with InfrastructureAware {
    override val taskRepository     = SlickTaskRepository
//    override val taskEventPublisher = new TaskEventPublisher with TaskEventPublisherOnKafka
    override val userRepository     = new UserRepository with SlickUserRepository 
  }

  val TaskViewQueryProcessor = new TaskViewQueryProcessor with TaskViewQueryProcessorOnES
}