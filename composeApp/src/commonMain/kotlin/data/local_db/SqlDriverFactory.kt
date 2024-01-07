package data.local_db

import app.cash.sqldelight.db.SqlDriver

expect class SqlDriverFactory(context: Any?) {

    fun createSqlDriver(): SqlDriver

}