package com.example.android.io2014;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class Add_garage extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {
    private EditText inputName, inputcontact,input_service,input_description,input_location,addressz;
    private TextInputLayout inputLayoutName, inputLayoutContact,input_layout_service,input_layout_description,input_layout_location;
    private Button btnSignUp;
    Spinner spinner;
    CircleImageView img;
    CheckBox checked;
    String item;
    String imageEncoded;
    String l="0.329202",lg="32.570987";

    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "mymechanics";

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garage);



        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        checked=(CheckBox)findViewById(R.id.checked);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutContact = (TextInputLayout) findViewById(R.id.input_layout_contact);

        input_layout_service=(TextInputLayout)findViewById(R.id.input_layout_service);
        input_layout_description=(TextInputLayout)findViewById(R.id.input_layout_description);
        input_layout_location=(TextInputLayout)findViewById(R.id.input_layout_location);

        inputName = (EditText) findViewById(R.id.input_name);
        inputcontact = (EditText) findViewById(R.id.input_contact);

        btnSignUp = (Button) findViewById(R.id.btn_signup);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputcontact.addTextChangedListener(new MyTextWatcher(inputcontact));

        input_service=(EditText)findViewById(R.id.input_service);
        input_service.addTextChangedListener(new MyTextWatcher(input_service));

        input_description=(EditText)findViewById(R.id.input_description);
        input_description.addTextChangedListener(new MyTextWatcher(input_description));

        input_location=(EditText)findViewById(R.id.input_location);
        input_location.addTextChangedListener(new MyTextWatcher(input_location));

        img=(CircleImageView)findViewById(R.id.garageimage);

        addressz=(EditText)findViewById(R.id.address);
        Firebase.setAndroidContext(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        // Spinner Drop down elements
        List<String> categories = new ArrayList <String>();
        categories.add("Select Specification");
        categories.add("Toyota");
        categories.add("FORD");
        categories.add("Mercedes");
        categories.add("BMW");
        categories.add("SUUBARU");
        categories.add("LAND ROVER");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });


    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateContact()) {
            return;
        }
        if (!validateService()) {
            return;
        }
        if (!validateDescription()) {
            return;
        }
        if (!validateLocation()) {
            return;
        }


        Firebase fire = new Firebase(Config.FIREBASE_URL);

        String gname = inputName.getText().toString().trim();
        String gspecification = item;
        String gservice = input_service.getText().toString().trim();
        String gdescription = input_description.getText().toString().trim();
        String gcontact = inputcontact.getText().toString().trim();
        String glocation = input_location.getText().toString().trim();
        String glogitude=l;
        String glatitude = lg;
        String gaddress= addressz.getText().toString().trim();

        Model model = new Model();
        model.setGaragename(gname);
        model.setSpecification(gspecification);
        model.setService(gservice);
        model.setDescription(gdescription);
        model.setContact(gcontact);
         model.setLocation(glocation);

        model.setLogitude(glogitude);
        model.setLatitude(glatitude);
        model.setAddress(gaddress);

        model.setThumbnail(imageEncoded);

       boolean check = checked.isChecked();
//        if (check == true) {
////            model.setLatitude(latitude.toString());
////            model.setLogitude(logitude.toString());
////            model.setAddress(exactlocation);
//        } else {
//            model.setLocation(glocation);
////            location2.setVisibility(View.VISIBLE);
//
//        }
        fire.child("Data").push().setValue(model);

    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateContact() {
        String contact = inputcontact.getText().toString().trim();

        if (contact.isEmpty() ) {
            inputLayoutContact.setError(getString(R.string.err_msg_email));
            requestFocus(inputcontact);
            return false;
        } else {
            inputLayoutContact.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateService() {
        if (input_service.getText().toString().trim().isEmpty()) {
            input_layout_service.setError("Enter service");
            requestFocus(input_service);
            return false;
        } else {
            input_layout_service.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateDescription() {
        if (input_description.getText().toString().trim().isEmpty()) {
            input_layout_description.setError("Enter Description");
            requestFocus(input_description);
            return false;
        } else {
            input_layout_description.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateLocation() {
        boolean check = checked.isChecked();
        if (check==true){

        }else {
            if (input_location.getText().toString().trim().isEmpty()) {
                input_layout_location.setError("Enter location");
                requestFocus(input_location);
                return false;
            } else {
                input_layout_location.setErrorEnabled(false);
            }
        }


        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
         item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_service:
                    validateService();
                    break;
                case R.id.input_contact:
                    validateContact();
                    break;
                case R.id.input_description:
                    validateDescription();
                    break;
                case R.id.input_location:
                    validateLocation();
                    break;

            }
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public void takeImageFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(mphoto);
            encodeBitmapAndSaveToFirebase(mphoto);
        } else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            // Get the url from data
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // Get the path from the Uri
                String path = getPathFromURI(selectedImageUri);
                Log.i(TAG, "Image Path : " + path);
                // Set the image in ImageView
                img.setImageURI(selectedImageUri);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //img.setImageBitmap(bitmap);
                encodeBitmapAndSaveToFirebase(bitmap);
            }

        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Add_garage.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Add_garage.this);
                if (items[item].equals("Take Photo")) {
                    //userChoosenTask="Take Photo";
                    if (result)
                        takeImageFromCamera();
                } else if (items[item].equals("Choose from Gallery")) {
                    //userChoosenTask="Choose from Library";
                    if (result)
                        openGallery();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }


    @Override
    protected void onResume() {
        super.onResume();
//
    }
}
