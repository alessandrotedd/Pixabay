package app.alessandrotedesco.pixabay.database.converter

import androidx.room.TypeConverter
import app.alessandrotedesco.pixabay.database.model.Example

class ExampleConverter { // TODO example
    @TypeConverter
    fun toString(something: Example): String {
        return "example"
    }

    @TypeConverter
    fun fromString(value: String): Example {
        return Example("example")
    }
}