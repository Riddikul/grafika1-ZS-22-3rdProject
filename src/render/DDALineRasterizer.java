package render;

import java.awt.image.BufferedImage;

public class DDALineRasterizer {
    private BufferedImage img;
    public DDALineRasterizer (BufferedImage img)
    {
        this.img = img;
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }

    public void rasterize(int x1, int y1, int x2, int y2, int color) {
  /* Pro vykreslení úsečky je použit Bresenhamův algoritmus.
   Tento algoritmus vychází z DDA algoritmu, narozdíl od něho se ale tento pohybuje jen v celých číslech.
   Funguje na principu hledání nejblížších bodů, které leží k úsečce.
 */
        if (x2 > 0 && x2 < img.getWidth() && y2 > 0 && y2 < img.getHeight())
        {
            if ((x1 == x2) && (y1 == y2)) {
            img.setRGB(Math.round(x1), Math.round(y1), color);

        } else {
            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int diff = dx - dy;

            int shift_x, shift_y;

            if (x1 < x2) shift_x = 1;
            else shift_x = -1;
            if (y1 < y2) shift_y = 1;
            else shift_y = -1;

            while ((x1 != x2) || (y1 != y2)) {

                int p = 2 * diff;

                if (p > -dy) {
                    diff = diff - dy;
                    x1 = x1 + shift_x;
                }
                if (p < dx) {
                    diff = diff + dx;
                    y1 = y1 + shift_y;
                }
                img.setRGB(Math.round(x1), Math.round(y1),color);
                if (x1 > 0 && x1 < img.getWidth() && y1 > 0 && y1 < img.getHeight()) {
                    img.setRGB(Math.round(x1), Math.round(y1), color);
            }

        }
    }}


    }}