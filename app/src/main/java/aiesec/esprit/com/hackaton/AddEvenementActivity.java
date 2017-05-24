package aiesec.esprit.com.hackaton;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import aiesec.esprit.com.hackaton.Entity.Evenement;
import aiesec.esprit.com.hackaton.utils.City;

public class AddEvenementActivity extends AppCompatActivity {

    EditText eventTitle, eventAddress, eventDescription;
    TextView eventStartDate, eventEndDate, foodValue, clothesValue;
    RelativeLayout imagePicker;
    SeekBar foodRange, clotherRange;
    Spinner gouverneratSpinner;
    private String foodObjectif = "";
    private String clothesObjectif = "";
    AppCompatButton addEventButton;
    String picturePath = "";
    private static int RESULT_LOAD_IMAGE = 1;


    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    ImageView imageView;


    Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evenement);
        getSupportActionBar().setTitle("");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        City city = new City();
        foodValue = (TextView) findViewById(R.id.food_value);
        clothesValue = (TextView) findViewById(R.id.clothes_value);


        ArrayList<String> countries = new ArrayList<>(city.Tunisia.keySet());

        eventTitle = (EditText) findViewById(R.id.evenement_title);
        eventAddress = (EditText) findViewById(R.id.evenement_address);
        eventDescription = (EditText) findViewById(R.id.event_description);


        eventStartDate = (TextView) findViewById(R.id.evenement_start_date);
        eventEndDate = (TextView) findViewById(R.id.evenement_end_date);

        imagePicker = (RelativeLayout) findViewById(R.id.image_picker);
        foodRange = (SeekBar) findViewById(R.id.food_range);
        clotherRange = (SeekBar) findViewById(R.id.clother_range);
        gouverneratSpinner = (Spinner) findViewById(R.id.gouvernerat_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gouverneratSpinner.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(eventStartDate);
            }

        };

        final DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(eventEndDate);
            }

        };


        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(AddEvenementActivity.this, startDateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEvenementActivity.this, endDateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        clotherRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                clothesObjectif = String.valueOf(i);
                clothesValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        foodRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                foodObjectif = String.valueOf(i);
                foodValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        addEventButton = (AppCompatButton) findViewById(R.id.btn_add_event);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(AddEvenementActivity.this);
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("waiting for upload");
                progressDialog.show();
                final String keyGnerated = mDatabase.push().getKey();
                storageReference = storageReference.child(keyGnerated + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");


                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = storageReference.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Evenement evenement = new Evenement();
                        evenement.setId(keyGnerated);
                        evenement.setAdresse(eventAddress.getText().toString());
                        evenement.setCurrentArgent("0");
                        evenement.setCurrentNourriture("0");
                        evenement.setCurrentVetement("0");
                        evenement.setObjectifArgent("0");

                        evenement.setObjectifNourriture(foodObjectif);
                        evenement.setObjectiVetement(clothesObjectif);
                        evenement.setDescription(eventDescription.getText().toString());
                        evenement.setGouvernerat(gouverneratSpinner.getSelectedItem().toString());
                        evenement.setDateDebut(eventStartDate.getText().toString());
                        evenement.setDateFin(eventEndDate.getText().toString());
                        evenement.setImageUrl(downloadUrl.toString());
                        evenement.setUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        evenement.setTitle(eventTitle.getText().toString());

                        mDatabase.child("Evenement").child(keyGnerated).setValue(evenement);
                        progressDialog.dismiss();
                        EvenementDetailActivity.evenementid = keyGnerated;
                        startActivity(new Intent(AddEvenementActivity.this, EvenementDetailActivity.class));


                    }
                });


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageView = (ImageView) findViewById(R.id.event_image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }

    private void updateLabel(TextView textView) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView.setText(sdf.format(myCalendar.getTime()));
    }

}
