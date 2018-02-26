package michal.jablonski.exampleapplication.rest.models.dailyMotionModel;

public class DailyMotionModelUtil {

    public static String getFieldsQuery(DailyMotionModel.Fields... fields) {
        StringBuilder output = new StringBuilder();
        for (DailyMotionModel.Fields field : fields) {
            output.append(field.toString());
            output.append(",");
        }

        String outString = output.toString();
        int lastIndexOfComma = output.lastIndexOf(",");
        if (lastIndexOfComma == outString.length() - 1) {
            return outString.substring(0, lastIndexOfComma);
        }
        return outString;
    }

    public static String getFieldsQuery(DailyMotionModel.Fields field) {
        return getFieldsQuery(field);
    }
}
