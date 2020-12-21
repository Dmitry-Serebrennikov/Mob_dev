package com.example.colortilescnv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;


public class TilesView extends View {

    boolean flag = false;
    int colors[] = {Color.BLUE, Color.GRAY};
    int width, height;
    int col = 2, row = 2, outline = 5;
    Tile[][] tiles = new Tile[col][row];
    int tileHeight, tileWidth;

    public TilesView(Context context) {
        super(context);
    }


    public TilesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();

        int t_width = width / col;
        int t_height = height / row;

        Random random = new Random();

        Paint p = new Paint();
        Rect rect = new Rect();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!flag){
                    int left = j * t_width;
                    int top = i * t_height;
                    int right = (j + 1) * t_width;
                    int bottom = (i + 1) * t_height;
                    int pos = random.nextInt(colors.length);

                    rect.set(left + outline, top + outline, right - outline, bottom - outline);

                    p.setColor(colors[pos]);
                    canvas.drawRect(rect, p);

                    tiles[i][j] = new Tile(left, right, top, bottom, colors[pos]);
                }
                else {
                    rect.set(tiles[i][j].left + outline, tiles[i][j].top + outline, tiles[i][j].right - outline,  tiles[i][j].bottom - outline);
                    p.setColor(tiles[i][j].color);
                    canvas.drawRect(rect, p);
                }
            }
        }
        if (!flag)
            flag = true;
        super.onDraw(canvas);
    }
    public boolean checkColors(Tile[][] tiles, int col, int row){
        int color = tiles[0][0].color;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (color != tiles[i][j].color)
                    return false;
            }
        }
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (tiles[i][j].left < x && tiles[i][j].right > x && tiles[i][j].top < y && tiles[i][j].bottom > y) {
                        for (int k = 0; k < row; k++) {
                            for (int l = 0; l < col; l++) {
                                if (k == i || l == j)
                                {
                                    tiles[k][l].changeColor();
                                }
                            }
                        }
                        break;
                    }
                }
            }
            invalidate();
        }
        boolean win = checkColors(tiles, col, row);
        if (win)
        {
            Toast toast = Toast.makeText(getContext(), "You won!", Toast.LENGTH_SHORT); toast.show();
            return true;
        }
        return true;

    }
}
class Tile {
    int top, right, bottom, left;
    int color;

    Tile(int left, int right, int top, int bottom, int color){
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.color = color;
    }

    void changeColor(){
        if (this.color == Color.BLUE){
            this.color = Color.GRAY;
        }
        else
            this.color = Color.BLUE;
    }
}

