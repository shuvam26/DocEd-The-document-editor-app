package com.example.laptop.doced;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText inputText,outputText,selText;
    boolean showToolbar = true;
    boolean isbold = true;
    boolean isitalic = true;
    boolean isunderline = true;
    boolean isstrike = true;
    boolean nightmode = true;
    boolean fontstyle = true;
    private String filepath = "DocEd";
    File myInternalFile;
    String myData = "";
    // Unique request code.
    private static final int WRITE_REQUEST_CODE = 43;
    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "Picker";
    NavigationView navigationView;
    Toolbar toolbar,toolbarbottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Window.SetSoftInputMode(SoftInput.AdjustPan);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        selText = (EditText) findViewById(R.id.Editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toolbar toolbarbottom = (Toolbar) findViewById(R.id.toolbarbottom);
        toolbarbottom.inflateMenu(R.menu.bottom_nav_items);
        toolbarbottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem arg0) {

                if (arg0.getItemId() == R.id.bold) {
                    if (isbold == true) {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable s = selText.getText();
                        s.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startSelection, endSelection, 0);
                        isbold = false;
                    } else {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable str = selText.getText();
                        StyleSpan[] ss = str.getSpans(startSelection, endSelection, StyleSpan.class);

                        for (int i = 0; i < ss.length; i++) {
                            if (ss[i].getStyle() == android.graphics.Typeface.BOLD) {
                                str.removeSpan(ss[i]);
                            }
                        }
                        selText.setText(str);
                        selText.setSelection(startSelection, endSelection);
                        isbold = true;
                    }

                } else if (arg0.getItemId() == R.id.italic) {       //italic
                    if (isitalic == true) {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable s = selText.getText();
                        s.setSpan(new StyleSpan(Typeface.ITALIC), startSelection, endSelection, 0);
                        isitalic = false;
                    } else {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable str = selText.getText();
                        StyleSpan[] ss = str.getSpans(startSelection, endSelection, StyleSpan.class);

                        for (int i = 0; i < ss.length; i++) {
                            if (ss[i].getStyle() == Typeface.ITALIC) {
                                str.removeSpan(ss[i]);
                            }
                        }
                        selText.setText(str);
                        selText.setSelection(startSelection, endSelection);
                        isitalic = true;
                    }

                } else if (arg0.getItemId() == R.id.underline) {        //underline
                    if (isunderline == true) {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable s = selText.getText();
                        s.setSpan(new UnderlineSpan(), startSelection, endSelection, 0);
                        isunderline = false;
                    } else {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable str = selText.getText();
                        UnderlineSpan[] ulSpan = str.getSpans(startSelection, endSelection, UnderlineSpan.class);
                        for (int i = 0; i < ulSpan.length; i++) {
                            str.removeSpan(ulSpan[i]);
                        }
                        selText.setText(str);
                        selText.setSelection(startSelection, endSelection);
                        isunderline = true;
                    }
                } else if (arg0.getItemId() == R.id.strikethrough) {
                    if (isstrike == true) {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable s = selText.getText();
                        s.setSpan(new StrikethroughSpan(), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        isstrike = false;
                    } else {
                        selText = (EditText) findViewById(R.id.Editor);
                        int startSelection = selText.getSelectionStart();
                        int endSelection = selText.getSelectionEnd();
                        Spannable str = selText.getText();
                        StrikethroughSpan[] ulSpan = str.getSpans(startSelection, endSelection, StrikethroughSpan.class);
                        for (int i = 0; i < ulSpan.length; i++) {
                            str.removeSpan(ulSpan[i]);
                        }
                        selText.setText(str);
                        selText.setSelection(startSelection, endSelection);
                        isstrike = true;
                    }
                } else if (arg0.getItemId() == R.id.font) {

                    selText = (EditText) findViewById(R.id.Editor);
                    int startSelection = selText.getSelectionStart();
                    int endSelection = selText.getSelectionEnd();
                    Spannable s = selText.getText();
                    View menuItemView = findViewById(R.id.font);// SAME ID AS MENU ID
                    showMenu(menuItemView,startSelection,endSelection);



                }else if(arg0.getItemId() == R.id.fontcolor){
                    selText = (EditText) findViewById(R.id.Editor);
                    final int startSelection = selText.getSelectionStart();
                    final int endSelection = selText.getSelectionEnd();
                    final Spannable s = new SpannableString(selText.getText());
                    ColorPickerDialogBuilder
                            .with(MainActivity.this)
                            .setTitle("Choose Color")
                            .initialColor(Color.WHITE)
                            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                            .density(12)
                            .setOnColorSelectedListener(new OnColorSelectedListener() {
                                @Override
                                public void onColorSelected(int selectedColor) {
                                    //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                                }
                            })
                            .setPositiveButton("Ok", new ColorPickerClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                                    s.setSpan(new ForegroundColorSpan(selectedColor), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    selText.setText(s);

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .build()
                            .show();

                }else if(arg0.getItemId() == R.id.highlight){
                    selText = (EditText) findViewById(R.id.Editor);
                    final int startSelection = selText.getSelectionStart();
                    final int endSelection = selText.getSelectionEnd();
                    final Spannable s = new SpannableString(selText.getText());
                    ColorPickerDialogBuilder
                            .with(MainActivity.this)
                            .setTitle("Choose Color")
                            .initialColor(Color.WHITE)
                            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                            .density(12)
                            .setOnColorSelectedListener(new OnColorSelectedListener() {
                                @Override
                                public void onColorSelected(int selectedColor) {
                                    //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                                }
                            })
                            .setPositiveButton("Ok", new ColorPickerClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                                    s.setSpan(new BackgroundColorSpan(selectedColor), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    selText.setText(s);

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .build()
                            .show();
                }else if(arg0.getItemId() == R.id.fontsize){
                    selText = (EditText) findViewById(R.id.Editor);
                    int startSelection = selText.getSelectionStart();
                    int endSelection = selText.getSelectionEnd();
                    Spannable s = selText.getText();
                    View menuItemView = findViewById(R.id.fontsize);// SAME ID AS MENU ID
                    showMenu2(menuItemView,startSelection,endSelection);
                }

                return false;
            }
        });

        toolbarbottom.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //drawer.setBackgroundResource(R.drawable.side_nav_bar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setBackgroundResource(R.drawable.side_nav_bar);

        View header=navigationView.getHeaderView(0);
        LinearLayout sideNavLayout = (LinearLayout)header.findViewById(R.id.navhead);
        sideNavLayout.setBackgroundResource(R.drawable.side_nav_bar);
        toolbar.setBackgroundResource(R.drawable.side_nav_bar);
        toolbarbottom.setBackgroundResource(R.drawable.side_nav_bar);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //Toolbar bottomBar = (Toolbar) findViewById(R.id.toolbarbottom);
        //Menu bottomMenu = bottomBar.getMenu();
        //getMenuInflater().inflate(R.menu.bottom_nav_items, bottomMenu);
       // MenuItem searchItem = menu.findItem(R.id.searchbutton);
        //SearchView searchView =
          //      (SearchView) MenuItemCompat.getActionView(searchItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.textformatbutton) {
            Toolbar toolbarbottom = (Toolbar) findViewById(R.id.toolbarbottom);
            if(showToolbar == true) {
                toolbarbottom.setVisibility(View.VISIBLE);
                showToolbar = false;
            }else{
                toolbarbottom.setVisibility(View.GONE);
                showToolbar = true;
            }
        }else if(id == R.id.searchbutton){
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.searchbutton)
                    .getActionView();
            if (null != searchView) {
                searchView.setSearchableInfo(searchManager
                        .getSearchableInfo(getComponentName()));
                searchView.setIconifiedByDefault(false);
            }

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    // This is your adapter that will be filtered
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    // **Here you can get the value "query" which is entered in the search box.**
                    EditText search = (EditText)findViewById(R.id.Editor);
                    String string = search.getText().toString();
                    if(string.contains(query)){
                        int position = string.indexOf("query");              // where C is your character to be searched
                        selText = (EditText) findViewById(R.id.Editor);
                        final Spannable s = new SpannableString(selText.getText());
                        s.setSpan(new BackgroundColorSpan(Color.YELLOW), position, position + query.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                    }

                    return true;

                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

        }else if(id == R.id.nightmode){
            EditText night = (EditText) findViewById(R.id.Editor);
            if(nightmode == true) {
                navigationView = (NavigationView) findViewById(R.id.nav_view);
                View header=navigationView.getHeaderView(0);
                LinearLayout sideNavLayout = (LinearLayout)header.findViewById(R.id.navhead);
                sideNavLayout.setBackgroundColor(Color.BLACK);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setBackgroundColor(Color.BLACK);
                toolbarbottom = (Toolbar) findViewById(R.id.toolbarbottom);
                toolbarbottom.setBackgroundColor(Color.BLACK);
                night.setBackgroundColor(Color.BLACK);
                night.setTextColor(Color.WHITE);
                nightmode = false;
            }else{
                night.setBackgroundResource(R.drawable.rectangle_background);
                night.setTextColor(Color.BLACK);
                navigationView = (NavigationView) findViewById(R.id.nav_view);
                View header=navigationView.getHeaderView(0);
                LinearLayout sideNavLayout = (LinearLayout)header.findViewById(R.id.navhead);
                sideNavLayout.setBackgroundResource(R.drawable.side_nav_bar);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setBackgroundResource(R.drawable.side_nav_bar);
                toolbarbottom = (Toolbar) findViewById(R.id.toolbarbottom);
                toolbarbottom.setBackgroundResource(R.drawable.side_nav_bar);
                nightmode = true;
            }
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new) {
            outputText = (EditText) findViewById(R.id.Editor);
            outputText.setText("");
        } else if (id == R.id.nav_open) {

            performFileSearch();

        } else if (id == R.id.nav_save) {

            createFile("text/plain","");

        }  else if (id == R.id.nav_close) {

            EditText close = (EditText)findViewById(R.id.Editor);
            if(close.getText().toString()!=null){
                final AlertDialog.Builder alertdg = new AlertDialog.Builder(this);
                alertdg.setTitle("Save Changes ?");
                alertdg.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performFileSearch();
                    }
                });

                alertdg.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertdg.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exit(1);
                    }
                });
                alertdg.show();
            }






        } else if(id == R.id.nav_theme){



        }else if(id == R.id.red){
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header=navigationView.getHeaderView(0);
            LinearLayout sideNavLayout = (LinearLayout)header.findViewById(R.id.navhead);
            sideNavLayout.setBackgroundResource(R.drawable.side_nav_bar_red);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.side_nav_bar_red);
            toolbarbottom = (Toolbar) findViewById(R.id.toolbarbottom);
            toolbarbottom.setBackgroundResource(R.drawable.side_nav_bar_red);
        }else if(id == R.id.blue){
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header=navigationView.getHeaderView(0);
            LinearLayout sideNavLayout = (LinearLayout)header.findViewById(R.id.navhead);
            sideNavLayout.setBackgroundResource(R.drawable.side_nav_bar);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.side_nav_bar);
            toolbarbottom = (Toolbar) findViewById(R.id.toolbarbottom);
            toolbarbottom.setBackgroundResource(R.drawable.side_nav_bar);
        }else if(id == R.id.green){
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header=navigationView.getHeaderView(0);
            LinearLayout sideNavLayout = (LinearLayout)header.findViewById(R.id.navhead);
            sideNavLayout.setBackgroundResource(R.drawable.side_nav_bar_green);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.side_nav_bar_green);
            toolbarbottom = (Toolbar) findViewById(R.id.toolbarbottom);
            toolbarbottom.setBackgroundResource(R.drawable.side_nav_bar_green);
        }else if(id == R.id.nav_share){
            EditText msg = (EditText)findViewById(R.id.Editor);
            String message = msg.getText().toString();
            if(message!=null) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, message);
                share.setType("text/*");
                startActivity(Intent.createChooser(share, "Share"));
            }else{
                Toast.makeText(MainActivity.this, "Cannot send empty body !", Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


        /**
         * Fires an intent to spin up the "file chooser" UI and select an image.
         */

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("text/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    // Here are some examples of how you might call this method.
// The first parameter is the MIME type, and the second parameter is the name
// of the file you are creating:
//
// createFile("text/plain", "foobar.txt");
// createFile("image/png", "mypicture.png");



    private void createFile(String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Create a file with the requested MIME type.
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());
                try {
                    String readtext = readTextFromUri(uri);
                    //Intent j = new Intent(FileChooser.this,MainActivity.class);
                    //j.putExtra("Readtext",readtext);
                    //startActivity(j);
                    outputText = (EditText) findViewById(R.id.Editor);
                    outputText.setText("");
                    outputText.setText(Html.fromHtml(readtext));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        else if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());
                writeTextFromUri(uri);
            }
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        //fileInputStream.close();
        inputStream.close();
        //parcelFileDescriptor.close();
        return stringBuilder.toString();
    }

    private void writeTextFromUri(Uri uri){
        try{
            ParcelFileDescriptor pFileDescriptor = this.getContentResolver().
                    openFileDescriptor(uri, "w");

            FileOutputStream fileOutputStream =
                    new FileOutputStream(pFileDescriptor.getFileDescriptor());

            outputText = (EditText) findViewById(R.id.Editor);
            String textContent = Html.toHtml(outputText.getText());
            fileOutputStream.write(textContent.getBytes());
            fileOutputStream.close();
            pFileDescriptor.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showMenu(View v, final int startSelection, final int endSelection) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.font_style, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sans1:
                            //selText = (EditText) findViewById(R.id.Editor);
                            //int startSelection = selText.getSelectionStart();
                            //int endSelection = selText.getSelectionEnd();
                            int start = startSelection;
                            int end = endSelection;
                            //Typeface typeface = Typeface.createFromAsset(getAssets(),"font/Pacifico.ttf");
                            TypefaceSpan robotoTFS = new TypefaceSpan("sans-serif");
                            Spannable s1 = selText.getText();
                            s1.setSpan(robotoTFS, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            return true;
                    case R.id.sans2:
                        start = startSelection;
                        end = endSelection;
                        robotoTFS = new TypefaceSpan("sans-serif-light");
                        s1 = selText.getText();
                        s1.setSpan(robotoTFS, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        return true;
                    case R.id.sans3:
                        start = startSelection;
                        end = endSelection;
                        robotoTFS = new TypefaceSpan("sans-serif-thin");
                        s1 = selText.getText();
                        s1.setSpan(robotoTFS, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        return true;
                    case R.id.sans4:
                        start = startSelection;
                        end = endSelection;
                        robotoTFS = new TypefaceSpan("sans-serif-condensed");
                        s1 = selText.getText();
                        s1.setSpan(robotoTFS, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        return true;
                    case R.id.mono:
                        start = startSelection;
                        end = endSelection;
                        robotoTFS = new TypefaceSpan("monospace");
                        s1 = selText.getText();
                        s1.setSpan(robotoTFS, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }


    /*public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        //popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.font_style);
        popup.show();
    }*/

    /*@Override
    public void onWindowFocusChanged (boolean hasWindowFocus) {
        boolean hadSelection = this.hasSelection();
        int start=0, end=0;
        if(hadSelection) {
            start = getSelectionStart();
            end = getSelectionEnd();
        }
        super.onWindowFocusChanged(hasWindowFocus);
        if(hadSelection) {
            setSelection(start, end);
        }
    }*/


    private void showMenu2(View v, final int startSelection, final int endSelection) {
        PopupMenu popup = new PopupMenu(this, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.font_size, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.a:
                        //int start = startSelection;
                        //int end = endSelection;
                        Spannable s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(24), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.b:
                        //start = startSelection;
                        //end = endSelection;
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(34), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.c:
                        //int start = startSelection;
                        //int end = endSelection;
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(44), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.d:
                        //int start = startSelection;
                        //int end = endSelection;
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(54), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.e:
                        //int start = startSelection;
                        //int end = endSelection;
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(64), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.f:
                        //int start = startSelection;
                        //int end = endSelection;
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(74), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.g:
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(84), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.h:
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(94), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.i:
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(104), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    case R.id.j:
                        s = new SpannableString(selText.getText());
                        s.setSpan(new AbsoluteSizeSpan(114), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        selText.setText(s);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }










}
