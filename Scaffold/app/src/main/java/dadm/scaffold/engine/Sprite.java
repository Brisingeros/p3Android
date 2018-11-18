package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public abstract class Sprite extends GameObject {

    protected double positionX;
    protected double positionY;
    protected double rotation;

    protected double pixelFactor;

    private final Bitmap bitmap;
    protected final int imageHeight;
    protected final int imageWidth;


    protected float[] collider;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.imageHeight = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.imageWidth = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);

        this.collider = new float[]{this.imageWidth*0.35f, this.imageHeight*0.45f};

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - imageWidth
                || positionY < - imageHeight) {
            return;
        }
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + imageWidth/2), (float) (positionY + imageHeight/2));
        canvas.drawBitmap(bitmap, matrix, null);
    }

    public boolean collisionAABB(Sprite spr){

        double posX = spr.getPositionX();
        double posY = spr.getPositionY();
        float[] col = spr.getCollider();

        double distance = Math.sqrt(Math.pow(this.positionX - posX, 2) + Math.pow(this.positionY - posY, 2));

        if (distance < 100)
            return false;

        //X axis collision
        boolean colAbs = (this.positionX + this.collider[0] > posX - col[0]) && (this.positionX - this.collider[0] < posX + col[0]);

        if (!colAbs)
            return false;

        //Y axis collision
        colAbs = (this.positionY + this.collider[1] > posY - col[1]) && (this.positionY - this.collider[1] < posY + col[1]);

        return colAbs;
    }

    public abstract void onCollision(GameEngine gameEngine);

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public float[] getCollider() {
        return collider;
    }
}
