/**
 * 
 */
package sango.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Rick
 *
 */
public class Utils {
	private static ZoneId Taipei = ZoneId.of("Asiz/Tapiei");

	public static Timestamp getTWTimestamp() {
		return Timestamp.valueOf(LocalDateTime.now(Taipei));
	}

	public static Timestamp getTWTimestamp(Timestamp localTime) {
		LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(localTime.getTime()), Taipei);
		return Timestamp.valueOf(triggerTime);
	}
}
