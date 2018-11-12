
package Player;

import Model.CPiece;
import Model.GPos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Nam Lee
 */
public class JGoban extends JPanel {

    static final int TOP = 10;
    static final int LEFT = 10;
    static final int BOARDSIZE = 19; // kich co bàn cờ
    static final int PIECESIZE = 26;
    static final int MAXPIECENUM = BOARDSIZE * BOARDSIZE; //bàn cờ
    static final int GC_OK = 0;
    static final int GC_ILLEGAL = 1;
    static final int GC_CANNOT = 2;
    static final int GC_FILLED = 3;
    static final int GC_WIN = 4;
    public boolean Sakiyomi;
    public boolean Kinjite;
    public CPiece Pieces[][];// Dữ liệu bàn cờ
    public int Area[][];    // vùng
    static final int AREASIZE = 2;
    public int numPiece;
    static final int SPACE = (PIECESIZE - 20) / 2;

    Graphics bufferGraphics;
    Image offscreen;
    Dimension dim;

    Image blackImage;
    Image whiteImage;

    public JGoban() {

        resize(310, 310);
// Khởi tạo chứa dữ liệu bàn cờ
        Pieces = new CPiece[BOARDSIZE][BOARDSIZE];
// khởi tạo dữ liệu bàn cờ 
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Pieces[i][j] = new CPiece();
            }
        }
        Area = new int[BOARDSIZE][BOARDSIZE];

        try {
            URL url = this.getClass().getResource("/image/X.png");
            blackImage = ImageIO.read(url);
            url = this.getClass().getResource("/image/O.png");
            whiteImage = ImageIO.read(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Initialize() {
// khởi động bàn cờ trống
        for (int x = 0; x < BOARDSIZE; x++) {
            for (int y = 0; y < BOARDSIZE; y++) {
                Pieces[x][y].State = CPiece.EMPTY;
                Area[x][y] = 0;
            }
        }
        numPiece = 0;
    }

    public void init(int width, int height) {
//        tạo 1 khung với chiều dài chiều rộng vào offscreen
        offscreen = createImage(width, height);
//        thiết lập 1 Graphics từ offscreen
        bufferGraphics = offscreen.getGraphics();
    }

    public void Put(int color, int x, int y) {

        if (Pieces[x][y].State == CPiece.EMPTY) {
            Pieces[x][y].State = color;
            numPiece++;

            int x1, x2, y1, y2;
            x1 = (x - AREASIZE < 0) ? 0 : x - AREASIZE;
            x2 = (x + AREASIZE >= BOARDSIZE) ? BOARDSIZE - 1 : x + AREASIZE;
            y1 = (y - AREASIZE < 0) ? 0 : y - AREASIZE;
            y2 = (y + AREASIZE >= BOARDSIZE) ? BOARDSIZE - 1 : y + AREASIZE;
            for (; x1 <= x2; x1++) {
                for (y = y1; y <= y2; y++) {
                    Area[x1][y]++;
                }
            }

        }

    }

    public void Remove(int x, int y) {

        if (Pieces[x][y].State != CPiece.EMPTY) {

            Pieces[x][y].State = CPiece.EMPTY;
            numPiece--;

            int x1, x2, y1, y2;
            x1 = (x - AREASIZE < 0) ? 0 : x - AREASIZE;
            x2 = (x + AREASIZE >= BOARDSIZE) ? BOARDSIZE - 1 : x + AREASIZE;
            y1 = (y - AREASIZE < 0) ? 0 : y - AREASIZE;
            y2 = (y + AREASIZE >= BOARDSIZE) ? BOARDSIZE - 1 : y + AREASIZE;
            for (; x1 <= x2; x1++) {
                for (y = y1; y <= y2; y++) {
                    Area[x1][y]--;
                }
            }

        }

    }

    public int Check(int color, int x, int y) {

        int ret;

        if (Pieces[x][y].State != CPiece.EMPTY) {
            return GC_CANNOT;
        }

        Put(color, x, y);

        Draw();

        if (numPiece == MAXPIECENUM) {
            return GC_FILLED;
        }

        return GC_OK;
    }

    public boolean GetPos(int x, int y, GPos pos) {
// lấy vị trí 
        if (x <= LEFT 
                || x >= LEFT + (PIECESIZE * (BOARDSIZE - 1))
                || (x - LEFT) % PIECESIZE <= 3
                || (y - TOP) % PIECESIZE >= PIECESIZE - 3) {
            return false;
        }
        if (y <= TOP 
                || y >= TOP + (PIECESIZE * (BOARDSIZE - 1))
                || (y - TOP) % PIECESIZE <= 3
                || (y - TOP) % PIECESIZE >= PIECESIZE - 3) {
            return false;
        }
        pos.x = (x - (LEFT)) / PIECESIZE;
        pos.y = (y - (TOP)) / PIECESIZE;
        return true;
    }

    public void Draw() {

        this.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x, y;

        bufferGraphics.setColor(Color.white);
        bufferGraphics.clearRect(0, 0, offscreen.getWidth(this), offscreen.getHeight(this));
        bufferGraphics.setColor(Color.black);
        for (x = 0; x < BOARDSIZE; x++) {
            bufferGraphics.drawLine(x * PIECESIZE + LEFT, TOP, x * PIECESIZE + LEFT, TOP + (BOARDSIZE - 1) * PIECESIZE);
        }
        for (y = 0; y < BOARDSIZE; y++) {
            bufferGraphics.drawLine(LEFT, y * PIECESIZE + TOP, LEFT + (BOARDSIZE - 1) * PIECESIZE, y * PIECESIZE + TOP);
        }

        if (blackImage == null || whiteImage == null) {
            for (x = 0; x < BOARDSIZE; x++) {
                for (y = 0; y < BOARDSIZE; y++) {
                    switch (Pieces[x][y].State) {
                        case CPiece.BLACK:
                            bufferGraphics.setColor(Color.red);

                            bufferGraphics.drawLine(x * PIECESIZE + 2, y * PIECESIZE + 2, (x + 1) * PIECESIZE - 2, (y + 1) * PIECESIZE - 2);
                            bufferGraphics.drawLine(x * PIECESIZE + 2, (y + 1) * PIECESIZE - 2, (x + 1) * PIECESIZE - 2, y * PIECESIZE + 2);

                            break;
                        case CPiece.WHITE:
                            bufferGraphics.setColor(Color.blue);
                            bufferGraphics.drawOval(x * PIECESIZE, y * PIECESIZE, PIECESIZE - 2, PIECESIZE - 2);
                            break;
                    }
                }
            }
        } else {
            for (x = 0; x < BOARDSIZE; x++) {
                for (y = 0; y < BOARDSIZE; y++) {
                    switch (Pieces[x][y].State) {
                        case CPiece.WHITE:
                            bufferGraphics.drawImage(whiteImage, x * PIECESIZE + LEFT + SPACE, y * PIECESIZE + TOP + SPACE, this);
                            break;
                        case CPiece.BLACK:
                            bufferGraphics.drawImage(blackImage, x * PIECESIZE + LEFT + SPACE, y * PIECESIZE + TOP + SPACE, this);
                            break;
                    }
                }
            }

        }

        g.drawImage(offscreen, 0, 0, this);
    }
}
