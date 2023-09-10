package render;

import model.Part;
import model.Solid;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;
import java.util.ArrayList;
import java.util.List;

public class Renderer3D {

    private Mat4 model, view, projection;
    private DDALineRasterizer ddaLineRasterizer;
    private int color;
    private List<Integer> indexBuffer;
    private List<Point3D> vertexBuffer;


    public Renderer3D(DDALineRasterizer ddaLineRasterizer) {
        this.ddaLineRasterizer = ddaLineRasterizer;
        indexBuffer = new ArrayList<>();
        vertexBuffer = new ArrayList<>();
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void render(Solid solid) {

        indexBuffer = solid.getIndices();

        if (!solid.getTransform()) {
            model = new Mat4Identity();

        }
        Mat4 mat4 = (model).mul(view).mul(projection);

        for (Point3D point : solid.getVertices()) {
            vertexBuffer.add(point.mul(mat4));
        }

        for (Part part : solid.getParts()) {
            color=part.getColor();
                for (int i = part.getStart(); i < part.getCount(); i += 2) {
                    line(vertexBuffer.get(indexBuffer.get(i)), vertexBuffer.get(indexBuffer.get(i + 1)));
            }
        }

        vertexBuffer = new ArrayList<>();
        indexBuffer = new ArrayList<>();
    }

    private void line(Point3D a, Point3D b) {
        Point3D pointA = a;
        Point3D pointB = b;

        if (clip(pointA) || clip(pointB)) {

            return;
        }



        Vec3D vectorA = new Vec3D();
        Vec3D vectorB = new Vec3D();

        if (pointA.dehomog().isPresent() || pointB.dehomog().isPresent()) {
            vectorA = pointA.dehomog().get();
            vectorB = pointB.dehomog().get();
        }
        int x1 = (int) ((vectorA.getX() + 1) * (ddaLineRasterizer.getWidth()) / 2);
        int y1 = (int) ((1 - vectorA.getY()) * (ddaLineRasterizer.getHeight()) / 2);
        int x2 = (int) ((vectorB.getX() + 1) * (ddaLineRasterizer.getWidth()) / 2);
        int y2 = (int) ((1 - vectorB.getY()) * (ddaLineRasterizer.getHeight()) / 2);
        ddaLineRasterizer.rasterize(x1,y1,x2,y2,color);
    }

    public boolean clip(Point3D a) {
        return (-a.getW() >= a.getX() ||
                a.getX() >= a.getW()||
                -a.getW() >= a.getY() ||
                a.getY() >= a.getW() ||
                0 >= a.getZ() ||
                a.getZ() >= a.getW());
    }

}