package domains

// NOTE: 処理をトランザクショナルな単位にまとめます。具体的にどのようにトランザクション制御するかはインフラレイヤの実装によります。
trait UnitOfWork
