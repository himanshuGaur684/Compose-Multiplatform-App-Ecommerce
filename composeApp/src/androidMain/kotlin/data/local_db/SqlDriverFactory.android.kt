package data.local_db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app_db.AppDatabase

actual class SqlDriverFactory actual constructor(context: Any?) {

    lateinit var context: Context

    init {
        this.context = context as Context

    }

    actual fun createSqlDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = this.context,
            name = "app.db"
        )
    }

}