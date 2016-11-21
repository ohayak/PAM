package eirb.ohayak.pam.androidapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity {

    private Button addBook ;
    private Button searchBook ;
    private TextView titleAdd ;
    private TextView isbnAdd ;
    private TextView titleSearch ;
    private ListView listView ;

    private BookDB db;

    // UI references.
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        addBook = (Button) findViewById(R.id.btn_add_book);
        searchBook = (Button) findViewById(R.id.btn_search_book);
        titleAdd = (TextView) findViewById(R.id.book_title);
        isbnAdd = (TextView) findViewById(R.id.book_isbn);
        titleSearch = (TextView) findViewById(R.id.search_book_title);
        listView = (ListView) findViewById(R.id.listView);
        db = new BookDB(getApplicationContext());

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });

        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBook();
            }
        });
        db.open();
    }

    private void searchBook() {
        String title = titleSearch.getText().toString();
        List<Book> result = db.getBookByTitle(title);
        ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(MainActivity.this, R.layout.activity_main, result);
        listView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(),
                "Books found: "+result.size(), Toast.LENGTH_SHORT).show();
    }

    private void addBook() {
        String title = titleAdd.getText().toString();
        String isbn = isbnAdd.getText().toString();
        Book book = new Book(title, isbn);
        db.insertBook(book);
        Toast.makeText(getApplicationContext(),
                "Book added", Toast.LENGTH_SHORT).show();
    }


}

