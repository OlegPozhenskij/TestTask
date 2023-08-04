package task3;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class ReportGenerator {
    public static void main(String[] args) {

        String testsFile = args[0];
        String valuesFile = args[1];
        String reportFile = "./report.json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // Читаем tests.json
            JsonObject testsObject;
            try (FileReader reader = new FileReader(testsFile)) {
                testsObject = gson.fromJson(reader, JsonObject.class);
            }

            // Читаем values.json
            JsonObject valuesObject;
            try (FileReader reader = new FileReader(valuesFile)) {
                valuesObject = gson.fromJson(reader, JsonObject.class);
            }

            // обнавляем тестовые значения
            if (testsObject != null && valuesObject != null) {
                updateTestValues(testsObject, valuesObject);
            }

            // Записываем в report.json
            JsonObject reportObject = getReportObject(testsObject, valuesObject);

            try (FileWriter writer = new FileWriter(reportFile)) {
                gson.toJson(reportObject, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateTestValues(JsonObject testsObject, JsonObject valuesObject) {
        if (testsObject.has("tests")) {
            JsonArray testsArray = testsObject.getAsJsonArray("tests");
            JsonArray valsArray = valuesObject.getAsJsonArray("values");

            //проходим по самым верхним объектам
            for (JsonElement testElement : testsArray) {
                processJsonElement(testElement, valsArray);
            }
        }
    }

    //рекурсивно проходим по всем вложенным объектам
    private static void processJsonElement(JsonElement element, JsonArray valsArray) {
        //если объект
        if (element.isJsonObject()) {
            JsonObject jsonObject = element.getAsJsonObject();

            //обновили значение идём дальше
            updateTestValue(jsonObject, valsArray);

            for (String key : jsonObject.keySet()) {
                JsonElement nestedElement = jsonObject.get(key);
                processJsonElement(nestedElement, valsArray);
            }
        //если массив
        } else if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement nestedElement : jsonArray) {
                processJsonElement(nestedElement, valsArray);
            }
        }
    }


    //обновление одного объекта
    private static void updateTestValue(JsonObject testObject, JsonArray valuesObject) {

        if (testObject.has("id") && testObject.has("value")) {
            String id = testObject.get("id").getAsString();

            for (JsonElement valElement : valuesObject) {

                if (valElement.isJsonObject() && valElement.getAsJsonObject().has("id")) {
                    if (valElement.getAsJsonObject().has("value")) {
                        if (Objects.equals(id, valElement.getAsJsonObject().get("id").getAsString())) {
                            String value = valElement.getAsJsonObject().get("value").getAsString();
                            testObject.addProperty("value", value);
                        }
                    }
                }
            }

        }
    }



    //выводим получившийся json
    private static JsonObject getReportObject(JsonObject testsObject, JsonObject valuesObject) {
        if (testsObject.has("tests")) {
            JsonArray testsArray = testsObject.getAsJsonArray("tests");
            JsonArray valuesArray = valuesObject.getAsJsonArray("values");
            for (JsonElement testElement : testsArray) {

                if (testElement.isJsonObject()) {
                    JsonObject testObject = testElement.getAsJsonObject();

                    if (testObject.has("id") && testObject.has("value")) {
                        return testsObject;
                    }
                }
            }
        }
        return null;
    }
}