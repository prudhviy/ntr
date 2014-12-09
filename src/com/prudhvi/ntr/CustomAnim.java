package com.prudhvi.ntr;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CustomAnim extends Animation{
	private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;

	public CustomAnim(float fromDegrees, float toDegrees,
            float centerX, float centerY, float depthZ, boolean reverse){
		mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
	}

	@Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        // set time duration for animation to happen
		setDuration(400);
		// set true for the animation not to return to original state
        setFillAfter(false);
        setInterpolator(new AccelerateInterpolator());
    }
	
	public void applyTransformation(float interpolatedTime, Transformation t) {
		
		final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix;
        matrix = t.getMatrix();
        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        }
        else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
		
		// set the matrix to I
		//t.getMatrix().reset();
		
        // and translate randomly by max magnitude of 3px each way
		//t.getMatrix().postTranslate(interpolatedTime, interpolatedTime);
	}
}
