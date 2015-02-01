package com.cubator.epitech.cubator;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import static android.view.WindowManager.*;


public class MainActivity extends Activity {
    protected CCGLSurfaceView _glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON, LayoutParams.FLAG_KEEP_SCREEN_ON);

        _glSurfaceView = new CCGLSurfaceView(this);

        setContentView(_glSurfaceView);
    }

    @Override
    public void onStart() {
        super.onStart();

        CCDirector.sharedDirector().attachInView(_glSurfaceView);

        CCDirector.sharedDirector().setDisplayFPS(true);

        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

        CCScene scene = GameLayer.scene();
        CCDirector.sharedDirector().runWithScene(scene);
    }

    @Override
    public void onPause() {
        super.onPause();
        CCDirector.sharedDirector().pause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        CCDirector.sharedDirector().resume();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        CCDirector.sharedDirector().end();
    }
}
