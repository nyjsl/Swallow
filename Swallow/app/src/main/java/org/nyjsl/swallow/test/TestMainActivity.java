package org.nyjsl.swallow.test;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.nyjsl.swallow.R;
import org.nyjsl.swallow.Swallow;
import org.nyjsl.swallow.presenter.TestPresenter;
import org.nyjsl.swallow.ui.BaseActivity;
import org.nyjsl.swallow.utils.NetWorkUtil;
import org.nyjsl.swallow.views.TestMainView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit.Callback;


public class TestMainActivity extends BaseActivity implements TestMainView,EasyPermissions.PermissionCallbacks {

    private static final String TAG = "TestMainActivity";

    @Bind(R.id.test_tv) TextView  test_tv;
    @Bind(R.id.tv_test) TextView  tv_test;
    @Bind(R.id.show)  Button show;
    @Bind(R.id.change) Button  change;
    @Bind(R.id.duang) Button  duang;
    @Bind(R.id.camera) Button  camera;
    @Bind(R.id.location) Button  location;
    @Bind(R.id.lv) PullToRefreshListView ptr;
    @Bind(R.id.avatar) ImageView avatar;
    @Bind(R.id.circle) View circle;

    private TestPresenter presenter;

    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test_main;
    }


    @Override
    protected void setListeners() {}

    @Override
    protected void init() {

        presenter = new TestPresenter();
        bindPresenter(presenter);
        initCircle();
        initPtrFrame();
        getRunningProcesses();

    }

    private void initCircle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circle.setVisibility(View.VISIBLE);
            circle.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setOval(0, 0, view.getWidth(), view.getHeight());
                    }
                }
            });
            circle.setClipToOutline(true);
        }else{
            circle.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.circle)
    void clickCircle(){
        jump(TestViewDragHelperActivity.class);
    }

    @Override
    public void onConnect(NetWorkUtil.NetType type) {
        showToast(type.name());
    }

    @Override
    public void onDisConnect() {
        showToast("断网咯");
    }

    @OnClick(R.id.change)
    void change(){
        Swallow.Configuration.MATERIAL_DIALOG = !Swallow.Configuration.MATERIAL_DIALOG;
    }
    @OnClick(R.id.duang)
    void duang(){
        replaceFragment(R.id.container, TestFragment.newInstance());
    }

    @OnClick(R.id.camera)
    @AfterPermissionGranted(RC_CAMERA_PERM)
    void camera(){
        if(EasyPermissions.hasPermissions(mContext, android.Manifest.permission.CAMERA)){
            showToast("Camera Camera");
        }else{
            EasyPermissions.requestPermissions(this,getString(R.string.rationale_camera),RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @OnClick(R.id.location)
    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    void location(){
        String[] perms = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS };
        if (EasyPermissions.hasPermissions(this, perms)) {
            showToast("Location Contacts");
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location_contacts),RC_LOCATION_CONTACTS_PERM, perms);
        }
    }

    @Override
    public void getUser(GitHubService service, String user, Callback<User> callback) {
        presenter.requestUser(service, user, callback);
    }

    @Override
    public void bindUser(User user) {
        presenter.bindUserView(user, tv_test, avatar);
    }

    @Override
    public void getRunningProcesses() {
        List<ActivityManager.RunningServiceInfo> runningServices = getRunningServiceInfos();
        presenter.bind(ptr, runningServices);
    }

    private List<ActivityManager.RunningServiceInfo> getRunningServiceInfos() {
        ActivityManager systemService = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        return systemService.getRunningServices(2000);
    }

    @Override
    public void initPtrFrame() {

        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                List<ActivityManager.RunningServiceInfo> runningServices = getRunningServiceInfos();
                presenter.refresh(ptr, runningServices);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                List<ActivityManager.RunningServiceInfo> runningServices = getRunningServiceInfos();
                presenter.loadMore(ptr,runningServices);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + perms.size());
    }

    @Override
    public void onPermissionsDenied(List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + perms.size());
    }
}
