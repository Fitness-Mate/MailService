package fitmate;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class ServiceConst {
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static int VERIFYING_REQUEST_OUTDATED_MINUTES = 5;
    public static int VERIFIED_MAIL_OUTDATED_MINUTES = 25;
}
