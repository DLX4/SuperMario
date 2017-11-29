package com.suramire.androidgame25;

import android.graphics.Bitmap;

import com.suramire.androidgame25.util.L;

/**
 * Created by Suramire on 2017/11/29.
 */

public class Mushroom extends Sprite {
    private boolean isJumping;
    private int speedY;

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public Site getDirection() {
        return direction;
    }

    public void setDirection(Site direction) {
        this.direction = direction;
    }

    //道具移动的方向
    private Site direction;

    public Mushroom(Bitmap bitmap) {
        super(bitmap);
    }


    @Override
    public void logic() {
        if(isVisiable()){
            switch (direction){
                //道具不动
                case 上中:{

                }break;
                //道具往左移动
                case 上左:{
                    move(-2,0);
                }break;
                //道具往右移动
                case 上右:{
                    move(2,0);
                }break;
            }
            if(isJumping()){
                move(0,speedY++);
            }
        }
    }

    @Override
    protected void outOfBounds() {
        //在超出左边界 以及掉入坑里的是否表示为不可见
        if(getX()<-getWidth() || getY()>400){
            setVisiable(false);
        }
    }


    /**
     * 碰撞检测
     * @param tiledLayer
     * @param site
     * @return
     */
    public boolean siteCollisionWith(TiledLayer tiledLayer,Site site){
        int siteX = 0;
        int siteY = 0;
        switch (site){
            case 上左:{
                siteX = getX() + getWidth() / 4;
                siteY = getY();
            }break;
            case 上中:{
                siteX = getX() + getWidth() / 2;
                siteY = getY();
            }break;
            case 上右:{
                siteX = getX() + 3 * getWidth() / 4;
                siteY = getY();
            }break;

            case 下左:{
                siteX = getX() + getWidth() / 4;
                siteY = getY() + getHeight();
            }break;
            case 下中:{
                siteX = getX() + getWidth() / 2;
                siteY = getY() + getHeight();
            }break;
            case 下右:{
                siteX = getX() + 3 * getWidth() / 4;
                siteY = getY() + getHeight();
            }break;

            case 左上:{
                siteX = getX();
                siteY = getY() + getHeight() / 4;
            }break;
            case 左中:{
                siteX = getX();
                siteY = getY() + getHeight() / 2;
            }break;
            case 左下:{
                siteX = getX();
                siteY = getY() + 3 * getHeight() / 4;
            }break;

            case 右上:{
                siteX = getX() + getWidth();
                siteY = getY() + getHeight() / 4;
            }break;
            case 右中:{
                siteX = getX() + getWidth();
                siteY = getY() + getHeight() / 2;
            }break;
            case 右下:{
                siteX = getX() + getWidth();
                siteY = getY() + 3 * getHeight() / 4;
            }break;
        }
        //在地图上的坐标
        int mapX = siteX - tiledLayer.getX();
        int mapY = siteY - tiledLayer.getY();
        //在地图上的对应行列
        int col = mapX / tiledLayer.getWidth();
        int row = mapY / tiledLayer.getHeight();
//        L.e("col:"+col+" row:"+row);
        //超出边界
        if(col>tiledLayer.getCols()-1|| row>tiledLayer.getRows()-1){
            return true;
        }
        //存在障碍物
        if(col>=0&&row>=0){
            if(tiledLayer.getTiledCell(col,row)!=0){
                return true;
            }
        }

        return false;
    }

    public boolean siteCollisionWith(Sprite sprite,Site site){
        int sy = sprite.getY();
        int sx = sprite.getX();
        int sh = sprite.getHeight();
        int sw = sprite.getWidth();
        int w = getWidth();
        int h = getHeight();
        int x = getX();
        int y = getY();

        switch (site){
            case 下中:{

                if(collisionWith(sprite)
                        && sy > y
                        && h >=sy-y
                        && x+w/4>=sx
                        && x+w/4 <=sx+sw
                        ){
//                    Log.e("MyView2", "y:" + y+" h:"+h + " sy:"+sy +" sh:"+sh +" x:"+x+" sx:"+sx +" w:"+w+ " sw:"+sw);
                    return true;
                }
            }break;
            case 上中:{

                if(collisionWith(sprite)
                        && sy + sh >= y //砖块高于玛丽最多一行高度
                        && x + w /4>= sx//玛丽右3/4宽度可以顶砖块
                        && x + w /4<= sx + sw//玛丽左3/4宽度可以顶砖块
                        ){
//                    Log.e("MyView2", "y:" + y+" h:"+h + " sy:"+sy +" sh:"+sh +" x:"+x+" sx:"+sx +" w:"+w+ " sw:"+sw);
                    return true;
                }
            }break;


//

            case 右中:{
                if(collisionWith(sprite)
                        &&x+w==sx
                        &&sy - y <h//只和同一行砖块左右碰撞
                        ){
                    return true;
                }
            }break;
            case 左中:{
                if(collisionWith(sprite)
                        &&sx+sw==x
//                        &&y-sy<sh
                        &&sy - y <h//只和同一行砖块左右碰撞
                        ){
                    return true;
                }
            }break;
//

        }

        return false;
    }
}