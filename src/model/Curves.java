package model;

import transforms.Cubic;
import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;

public class Curves extends Solid {

    public Curves(Mat4 baseMat) {

        vertexBuffer = new ArrayList<>();

        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(1, -1, -1);
        Point3D p3 = new Point3D(1, -1, 0);
        Point3D p4 = new Point3D(1, -1, 1);


        Cubic cubic = new Cubic(baseMat, p1, p2, p3, p4);

        indexBuffer = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            vertexBuffer.add(new Point3D(cubic.compute((double) i /10)));
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }

        parts = new ArrayList<>();
        parts.add(new Part( 0, 10,0xffffff));
    }
}