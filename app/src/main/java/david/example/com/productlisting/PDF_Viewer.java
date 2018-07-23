package david.example.com.productlisting;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDF_Viewer extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf__viewer);
        pdfView = (PDFView)findViewById(R.id.pdfViewer);
        String pdfFilePath = this.getIntent().getStringExtra("filepath");
        Uri fileUri = Uri.parse(pdfFilePath);
        pdfView.fromUri(fileUri).load();


    }
}
