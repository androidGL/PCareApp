package com.example.firstapplication.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

import androidx.core.app.ActivityCompat;

import com.example.firstapplication.base.BasePresenter;
import com.example.firstapplication.base.IView;
import com.example.firstapplication.contract.VerifyFaceContarct;
import com.example.firstapplication.entity.UserInfo;
import com.example.firstapplication.model.VerifyFaceModel;
import com.example.firstapplication.net.Api;
import com.example.firstapplication.utils.PermissionHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.ResponseBody;

/**
 * @Author: gl
 * @CreateDate: 2019/10/30
 * @Description:
 */
public class VerifyFacePresenter extends BasePresenter<VerifyFaceContarct.View> implements VerifyFaceContarct.Presenter {

    private Activity activity;

    private HandlerThread mCameraThread;
    private Handler mCameraHandler;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCaptureRequestBuilder;//实时图像的输出目标
    private CaptureRequest mCaptureRequest;
    private CameraCaptureSession mCameraCaptureSession;//不断捕获的图像
    private SurfaceTexture mSurfaceTexture;//从activity界面获取
    private Size mPreviewSize;
    private ImageReader mImageReader;
    private String mCameraId;

    private Handler timerHandler = new Handler();//执行循环定时的任务
    private Runnable timerRunnable;
    private VerifyFaceModel mModel;
    private String imgBase64;
    private SurfaceTextureListener mSurfaceTextureListener = new SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            //当SurefaceTexture可用的时候，设置相机参数并打开相机
            setupCamera(width, height);
            CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
            try {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                manager.openCamera(mCameraId, mStateCallback, mCameraHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    //监听摄像头的状态
    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        //摄像头打开，可以创建会话，开始预览
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            startPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;
        }
    };

    //捕获每帧图片
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener
            = new ImageReader.OnImageAvailableListener() {

        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireLatestImage();
            //我们可以将这帧数据转成字节数组，类似于Camera1的PreviewCallback回调的预览帧数据
            if (image == null) {
                return;
            }
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            //ImageFormat.JPEG格式直接转化为Bitmap格式。
            Bitmap temp = BitmapFactory.decodeByteArray(data, 0, data.length);
//因为摄像机数据默认是横的，所以需要旋转90度。
//            Bitmap newBitmap = BitmapUtil.rotateBitmap(temp, 90);
            encodeImage(temp);
            imgBase64 = Base64.encodeToString(data,Base64.NO_WRAP);
            image.close();
        }

    };
    public VerifyFacePresenter(VerifyFaceContarct.View view) {
        super(view);
        mModel = new VerifyFaceModel();
        activity = (Activity) getView();
    }

    @Override
    public void setUserInfo(UserInfo info) {

    }

    //设置相机
    private void setupCamera(int width, int height) {
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        try {
            //遍历所有的摄像头
            for(String cameraId : manager.getCameraIdList()){
                //获取到每个相机的参数对象，包含前后摄像头，分辨率等
                CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
                //摄像头的方向
                Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                //此处默认打开前置置摄像头
                if (facing != null && facing != CameraCharacteristics.LENS_FACING_FRONT)
                    continue;
                //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                assert map != null;
                //设置保存图像的监听
                Size largest = Collections.max(
                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                        new CompareSizesByArea());
                mImageReader = ImageReader.newInstance(width, height,
                        ImageFormat.JPEG, 1);
                mImageReader.setOnImageAvailableListener(
                        mOnImageAvailableListener, mCameraHandler);
                //根据TextureView的尺寸设置预览尺寸
                mPreviewSize = getOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height);
                mCameraId = cameraId;
                break;
            }
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    @Override
    public void openCamera(TextureView textureView) {
        //TODO 打开相机的一些操作
        PermissionHelper.requestCameraPermission(activity, true);
        mCameraThread = new HandlerThread("CameraThread");
        mCameraThread.start();
        mCameraHandler = new Handler(mCameraThread.getLooper());
        if(null != textureView&&!textureView.isAvailable()){
            textureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }else {
            startPreview();
        }
//        //人脸识别验证
//        verifyFaceRequest(Api.name);
    }

    public void startPreview() {
        mSurfaceTexture = getView().getSurfaceTexture();
        mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        Surface previewSurface = new Surface(mSurfaceTexture);
        Surface imageSurface = mImageReader.getSurface();
        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //这里是指实时图像数据的输出目标，以后录制视频、直播等都需要在这里添加对应的Target
            mCaptureRequestBuilder.addTarget(previewSurface);
            mCaptureRequestBuilder.addTarget(imageSurface);
            //创建捕获请求，在需要预览、拍照、再次预览的时候都需要通过创建请求来完成
            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface, imageSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        mCaptureRequest = mCaptureRequestBuilder.build();
                        mCameraCaptureSession = session;
                        //不断捕获图像，显示预览图像
                        mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, mCameraHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }
    private Size getOptimalSize(Size[] sizeMap, int width, int height) {
        List<Size> sizeList = new ArrayList<>();
        for (Size option : sizeMap) {
            if (width > height) {
                if (option.getWidth() > width && option.getHeight() > height) {
                    sizeList.add(option);
                }
            } else {
                if (option.getWidth() > height && option.getHeight() > width) {
                    sizeList.add(option);
                }
            }
        }
        if (sizeList.size() > 0) {
            return Collections.min(sizeList, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
                }
            });
        }
        return sizeMap[0];
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @Override
    public void closeCamera() {
        if (mCameraCaptureSession != null) {
            mCameraCaptureSession.close();
            mCameraCaptureSession = null;
        }
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
        if (null != mImageReader) {
            mImageReader.close();
            mImageReader = null;
        }
        if(null != timerHandler){
            timerHandler.removeCallbacks(timerRunnable);
            timerHandler = null;
            timerRunnable = null;
        }
    }

    @Override
    public void verifyFaceRequest(String faceId) {

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(imgBase64)) {
                    timerHandler.postDelayed(this,2000);
                    return;
                }

                Disposable disposable = mModel.compareFace(faceId, imgBase64,new DisposableSingleObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody value) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(value.string());
                            getView().showToast("Response:"+jsonObject.toString());
                            if(1==jsonObject.optInt("status")){
                                timerHandler.removeCallbacks(timerRunnable);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showToast(e.getMessage());
                    }
                });
                addDisposable(disposable);
                timerHandler.postDelayed(this,2000);
            }
        };
        timerHandler.postDelayed(timerRunnable,2000);
    }

    @Override
    public void verifyFaceResult() {

    }
    @Override
    public void detectFaceRequest(String faceId) {

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(imgBase64)) {
                    timerHandler.postDelayed(this,2000);
                    return;
                }
                Disposable disposable = mModel.detectFace(imgBase64,new DisposableSingleObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody value) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(value.string());
                            getView().showToast("Response:"+jsonObject.toString());
                            if(1==jsonObject.optInt("status")){
                                timerHandler.removeCallbacks(timerRunnable);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showToast(e.getMessage());
                    }
                });
                addDisposable(disposable);
                timerHandler.postDelayed(this,2000);
            }
        };
        timerHandler.postDelayed(timerRunnable,2000);

    }
}
