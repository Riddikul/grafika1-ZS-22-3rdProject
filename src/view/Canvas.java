package view;

import model.*;
import render.DDALineRasterizer;
import render.Renderer3D;
import transforms.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Canvas extends JFrame {
    private double STEP = 0.05;

    private JPanel panel;
    private BufferedImage img;

    private Camera camera;
    private Renderer3D renderer3D;
    private Axis axis;
    private Mat4 model;
    private Mat4 projectionMat;
    private Mat4 OrthoRH = new Mat4OrthoRH(10, 10, 0.1, 200);

    private Mat4 PerspRH = new Mat4PerspRH(Math.PI / 4, 1, 0.1, 200);

    private int newX;
    private int newY;
    private int oldX;
    private int oldY;
    private int type;

    public Canvas(int width, int height) {
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        renderer3D = new Renderer3D(new DDALineRasterizer(img));
        axis = new Axis();
        projectionMat = PerspRH;
        model = new Mat4Identity().mul(new Mat4RotX(Math.PI / 180 * 45).mul(new Mat4RotY(Math.PI / 180 * 65)));
        camera = new Camera();
        camera = camera.withPosition(new Vec3D(-10,0,0));
        camera.withFirstPerson(false);
        camera.withRadius(5);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        setVisible(true);
        panel.setPreferredSize(new Dimension(width, height));
        add(panel, BorderLayout.CENTER);
        pack();

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                newX = e.getX();
                newY = e.getY();
            }
        });


        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 1) {
                    camera = camera.forward(STEP);
                } else {
                    camera = camera.backward(STEP);
                }
                render();
            }
        });


        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                oldX = newX;
                oldY = newY;
                newX = e.getX();
                newY = e.getY();

                double dx = newX - oldX;
                double dy = newY - oldY;

                if (SwingUtilities.isLeftMouseButton(e)) {
                    camera = camera.addAzimuth(Math.PI * dx / width);
                    camera = camera.addZenith(Math.PI * dy / width);
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    model = model.mul(new Mat4RotXYZ(-dx * Math.PI / 180, dy * Math.PI / 180, 0));
                }
                render();
            }
        });


        panel.requestFocusInWindow();


    panel.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_V)
        {
            if (projectionMat==OrthoRH)
                projectionMat = PerspRH;

            else
                projectionMat = OrthoRH;
        }

        if (e.getKeyCode() == KeyEvent.VK_C)
        {
            type =0;
            render();
        }

        if (e.getKeyCode() == KeyEvent.VK_P)
        {
            type =1;
            render();
        }

        if (e.getKeyCode() == KeyEvent.VK_W)
        { if(projectionMat==PerspRH)
            camera = camera.forward(STEP);
            else camera = camera.down(STEP);
          render();
        }

        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            if(projectionMat==PerspRH)
            camera = camera.backward(STEP);
                else camera = camera.up(STEP);
            render();
        }

        if (e.getKeyCode() == KeyEvent.VK_A)
        {camera = camera.right(STEP);
            render();
        }

        if (e.getKeyCode() == KeyEvent.VK_D)
        {camera = camera.left(STEP);
            render();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD1)
        { type=2;
            render();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD2)
        { type=3;
            render();
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD3)
        {   type=4;
            render();
        }

    }
});

    }



    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void render() {
        clear();
        renderer3D.setModel(model);
        renderer3D.setProjection(projectionMat);
        renderer3D.setView(camera.getViewMatrix());

        switch (type)
        {
            case 0:
                renderer3D.render(new Cube());
                renderer3D.render(axis);
                break;

            case 1:
                renderer3D.render(new Pyramid());
                renderer3D.render(axis);
                break;

            case 2:
                renderer3D.render(new Cube());
                renderer3D.render(axis);
                renderer3D.render(new Curves(Cubic.BEZIER));
                break;

            case 3:
                renderer3D.render(new Cube());
                renderer3D.render(axis);
                renderer3D.render(new Curves(Cubic.COONS));
                break;

            case 4:
                renderer3D.render(new Cube());
                renderer3D.render(axis);
                renderer3D.render(new Curves(Cubic.FERGUSON));
                break;
        }
        panel.repaint();
    }
    public void clear() {

        Graphics gr = img.getGraphics();
        gr.setColor(Color.black);
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }
    public void start() {
        clear();
        render();
        panel.repaint();
    }
}