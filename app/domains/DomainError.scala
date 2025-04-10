package domains

trait DomainError {
  // NOTE: 独自に作成したエラーがどこで発生したのかを追跡しやすくするために、例外と同様にスタックトレースを持たせるようにしています。
  private val stackTrace = {
    val traces = Thread.currentThread().getStackTrace
    traces.drop(traces.lastIndexWhere(t => t.getClassName == getClass.getName) + 1)
  }

  override def toString: String = {
    s"""${getClass.getName}
       |${stackTrace.map(s => s" at $s").mkString("\n")}
       |""".stripMargin
  }
}
