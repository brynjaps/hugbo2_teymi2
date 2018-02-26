package is.hi.gordon;

import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

/**
 * Created by brynj on 26/02/2018.
 */

public class UUIDConverter {
    @TypeConverter
    public String toString(UUID model) {
        return model == null ? null : model.toString();
    }

    @TypeConverter
    public UUID toUUID(String data) {
        return data == null ? null : UUID.fromString(data);
    }
}
