package data.local_db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app_db.AppDatabase

actual class SqlDriverFactory actual constructor(context: Any?) {
    actual fun createSqlDriver(): SqlDriver {
        return NativeSqliteDriver(schema = AppDatabase.Schema, name = "app.db")
    }

}