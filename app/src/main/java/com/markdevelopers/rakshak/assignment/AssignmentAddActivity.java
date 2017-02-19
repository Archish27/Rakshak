package com.markdevelopers.rakshak.assignment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.BaseActivity;
import com.markdevelopers.rakshak.common.Config;
import com.markdevelopers.rakshak.common.RakshakApp;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;
import com.markdevelopers.rakshak.data.remote.models.AssignmentWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;
import com.markdevelopers.rakshak.ui.widgets.BaseButton;
import com.markdevelopers.rakshak.ui.widgets.BaseEditText;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Archish on 1/29/2017.
 */

public class AssignmentAddActivity extends BaseActivity implements AssignmentContract.AssignmentView {
    FloatingActionButton fabAdd;
    ImageView ivCloseButton;
    BaseEditText etDescription;
    AppCompatSpinner sCategory;
    ImageView ivImage;
    FrameLayout fImage;
    BaseButton bSubmit;
    final private int MY_PERMISSIONS_REQUEST_CAMERA = 123;
    private static final int ADDRESS_CAMERA_IMAGE = 1850;
    private static final int ADDRESS_GALLERY_IMAGE = 1851;
    String path = null;
    AssignmentAddPresenter assignmentAddPresenter;
    int did;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        Intent i = getIntent();
        did = i.getIntExtra("did", 0);
        NewsFeedRepository
                newsFeedRepository = ((RakshakApp) getApplication()).getComponent().newsFeedRepository();
        assignmentAddPresenter = new AssignmentAddPresenter(newsFeedRepository, this);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = validate();
                if (status) {
                    showProgressDialog();
                    if (path != null) {
                        File f = new File(path);
                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                        assignmentAddPresenter.onSendPost(new SharedPreferenceManager(getApplicationContext()).getAccessToken(), sCategory.getSelectedItem().toString(), etDescription.getText().toString(), reqFile, did);
                    } else {
                        assignmentAddPresenter.onSendPostNoImage(new SharedPreferenceManager(getApplicationContext()).getAccessToken(), sCategory.getSelectedItem().toString(), etDescription.getText().toString(), did);
                    }
                }
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });
        ivCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fImage.setVisibility(View.GONE);
                path = "";
            }
        });


    }

    private void showImageDialog() {
        Dialog dialog = new Dialog(AssignmentAddActivity.this);
        dialog.setContentView(R.layout.dialog_image);
        dialog.setTitle("Select Image");
        BaseTextView tvTakePhoto = (BaseTextView) dialog.findViewById(R.id.tvTakePhoto);
        BaseTextView tvGallery = (BaseTextView) dialog.findViewById(R.id.tvGallery);
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAddressPhotoWrapper();
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, ADDRESS_GALLERY_IMAGE);
            }
        });
        dialog.show();

    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sCategory = (AppCompatSpinner) findViewById(R.id.sCategory);
        etDescription = (BaseEditText) findViewById(R.id.etDescription);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fImage = (FrameLayout) findViewById(R.id.fImage);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivCloseButton = (ImageView) findViewById(R.id.ivCloseButton);
        bSubmit = (BaseButton) findViewById(R.id.bSubmit);
    }

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    private boolean validate() {
        if (sCategory.getSelectedItemId() == 0) {
            Toast.makeText(AssignmentAddActivity.this, "Please select category.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etDescription.getText().toString().isEmpty()) {
            etDescription.setError("Description cannot be empty");
            etDescription.setFocusable(true);
            return false;
        }
        return true;

    }

    private void takeAddressPhotoWrapper() {

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();

        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("ExternalStorage");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(AssignmentAddActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
                return;
            }
            ActivityCompat.requestPermissions(AssignmentAddActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                    MY_PERMISSIONS_REQUEST_CAMERA);
            return;
        }
        takeAddressPhoto();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case ADDRESS_CAMERA_IMAGE:
                    Toast.makeText(getApplicationContext(), "Image upload success", Toast.LENGTH_SHORT).show();
                    Uri cameraImageUri = Uri.fromFile(new File(path));
                    Picasso.with(getApplicationContext()).load(cameraImageUri).fit().into(ivImage);
                    fImage.setVisibility(View.VISIBLE);
                    break;
                case ADDRESS_GALLERY_IMAGE:
                    Uri selectedImageGallery = data.getData();
                    fImage.setVisibility(View.VISIBLE);
                    path = getRealPathFromURI(selectedImageGallery);
                    Picasso.with(getApplicationContext()).load(selectedImageGallery).fit().into(ivImage);
                    fImage.setVisibility(View.VISIBLE);

            }

        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AssignmentAddActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * Calls Camera action & Stores Image in SDCARD.
     */
    public void takeAddressPhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + Config.IMAGE_DIRECTORY_NAME);

        if (!folder.exists()) {
            folder.mkdir();
        }
        final Calendar c = Calendar.getInstance();
        String dateTime = c.get(Calendar.DAY_OF_MONTH) + "-"
                + ((c.get(Calendar.MONTH)) + 1) + "-"
                + c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.HOUR) + "-"
                + c.get(Calendar.MINUTE) + "-"
                + c.get(Calendar.SECOND);
        path = String.format(Environment.getExternalStorageDirectory() + "/" + Config.IMAGE_DIRECTORY_NAME + "/%s.png",
                dateTime);

        File photo = new File(path);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, ADDRESS_CAMERA_IMAGE);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onAssign(AssignmentWrapper assignmentWrapper) {

    }

    @Override
    public void onSuccess(UserResponse userResponse) {
        if (userResponse.getSuccess()) {
            dismissProgressDialog();
            finish();
        } else {
            dismissProgressDialog();
            Toast.makeText(AssignmentAddActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
