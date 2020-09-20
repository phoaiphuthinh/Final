package hcmus.phpthinh.afinal;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.FileOutputStream;

public class DialogUpdate extends DialogFragment {

    EditText editHeight, editWeight;
    ImageButton buttonYes, buttonNo;

    public static DialogUpdate newInstance(String data){
        DialogUpdate dialog = new DialogUpdate();
        Bundle args = new Bundle();
        args.putString("data", data);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_update, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editHeight = (EditText)view.findViewById(R.id.editUpdateHeight);
        editWeight = (EditText)view.findViewById(R.id.editUpdateWeight);
        buttonNo = (ImageButton)view.findViewById(R.id.buttonNo);
        buttonYes = (ImageButton)view.findViewById(R.id.buttonYes);

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String height = editHeight.getText().toString();
        String weight = editWeight.getText().toString();
        if (height.isEmpty() || weight.isEmpty())
            Toast.makeText(getContext(), "Please fill both", Toast.LENGTH_SHORT).show();
        else {
            
            dismiss();
        }
    }
}
