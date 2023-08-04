package task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CirclePointPos {
    public static void main(String[] args) {

        String circleFile = args[0];
        String pointsFile = args[1];

        try {
            // Читаем координаты и радиус из файла
            BufferedReader circleReader = new BufferedReader(new FileReader(circleFile));
            String circleLine = circleReader.readLine();
            String[] circleCoords = circleLine.split(" ");
            float circleX = Float.parseFloat(circleCoords[0]);
            float circleY = Float.parseFloat(circleCoords[1]);
            float circleRadius = Float.parseFloat(circleReader.readLine());
            circleReader.close();

            // Читаем координаты точек из файла
            BufferedReader pointsReader = new BufferedReader(new FileReader(pointsFile));
            String pointLine;
            while ((pointLine = pointsReader.readLine()) != null) {
                String[] pointCoords = pointLine.split(" ");
                float pointX = Float.parseFloat(pointCoords[0]);
                float pointY = Float.parseFloat(pointCoords[1]);
                int position = getPointPosition(circleX, circleY, circleRadius, pointX, pointY);
                System.out.print(position + "\n");
            }
            pointsReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPointPosition(float circleX, float circleY, float circleRadius, float pointX, float pointY) {
        float distance = (float) Math.sqrt(Math.pow(pointX - circleX, 2) + Math.pow(pointY - circleY, 2));
        if (distance < circleRadius) {
            return 1; // точка внутри круга
        } else if (distance > circleRadius) {
            return 2; // точка вне круга
        } else {
            return 0; // точка в круге
        }
    }
}
