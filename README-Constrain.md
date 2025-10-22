databaseChangeLog:
- changeSet:
  id: et-text-fk-cascade
  author: you
  changes:
  # 1) Alte FK löschen (Namen anpassen!)
  - dropForeignKeyConstraint:
  baseTableName: VERARBEITUNGSERGEBNISTEXT
  constraintName: FK_VERARBEITUNGSERGEBNISTEXT_EINGEHENDERTRANSFER
  # 2) FK mit ON DELETE CASCADE neu anlegen
  - addForeignKeyConstraint:
  baseTableName: VERARBEITUNGSERGEBNISTEXT
  baseColumnNames: EINGEHENDERTRANSFER_ID
  referencedTableName: EINGEHENDERTRANSFER
  referencedColumnNames: ID
  constraintName: FK_VERARB_TEXT_ET
  onDelete: CASCADE
  # 3) (empfohlen) Index auf FK
  - createIndex:
  tableName: VERARBEITUNGSERGEBNISTEXT
  indexName: IX_VERARB_TEXT_ET
  columns:
  - column: { name: EINGEHENDERTRANSFER_ID }
  rollback:
  - dropForeignKeyConstraint:
  baseTableName: VERARBEITUNGSERGEBNISTEXT
  constraintName: FK_VERARB_TEXT_ET
  - addForeignKeyConstraint:
  baseTableName: VERARBEITUNGSERGEBNISTEXT
  baseColumnNames: EINGEHENDERTRANSFER_ID
  referencedTableName: EINGEHENDERTRANSFER
  referencedColumnNames: ID
  constraintName: FK_VERARBEITUNGSERGEBNISTEXT_EINGEHENDERTRANSFER
