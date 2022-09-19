package com.example.androidasm;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomStudentList extends BaseAdapter {
    private Activity context;
    ArrayList<Student> students;
    private PopupWindow pwindo;
    SQLiteDatabaseHandler db;
    BaseAdapter ba;

    public CustomStudentList(Activity context, ArrayList students,SQLiteDatabaseHandler db) {
        this.context = context;
        this.students=students;
        this.db=db;
    }

    public static class ViewHolder
    {
        TextView textViewId;
        TextView textViewName;
        TextView textViewEmail;
        TextView textViewTel;
        Button editButton;
        Button deleteButton;
    }
    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            row = inflater.inflate(R.layout.row_item, null, true);

            vh.textViewId = (TextView) row.findViewById(R.id.textViewId);
            vh.textViewName = (TextView) row.findViewById(R.id.textViewName);
            vh.textViewEmail = (TextView) row.findViewById(R.id.textViewEmail);
            vh.textViewTel = (TextView) row.findViewById(R.id.textViewTel);
            vh.editButton = (Button) row.findViewById(R.id.edit);
            vh.deleteButton = (Button) row.findViewById(R.id.delete);

            // store the holder with the view.
            row.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textViewName.setText(students.get(position).getName());
        vh.textViewEmail.setText(students.get(position).getEmail());
        vh.textViewTel.setText(students.get(position).getTel());
        vh.textViewId.setText("" + students.get(position).getId());
        final int positionPopup = position;
        vh.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Save: ", "" + positionPopup);
                editPopup(positionPopup);
            }
        });
        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Last Index", "" + positionPopup);
                //     Integer index = (Integer) view.getTag();
                db.delete(students.get(positionPopup));

                //      countries.remove(index.intValue());
                students = (ArrayList) db.getAllStudent();
                Log.d("Student size", "" + students.size());
                notifyDataSetChanged();
            }
        });
        return  row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {
        return students.size();
    }

    public void editPopup(final int positionPopup)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.edit_popup,
                (ViewGroup) context.findViewById(R.id.popup_element));
        pwindo = new PopupWindow(layout, 600, 670, true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

        final EditText nameStudent = (EditText) layout.findViewById(R.id.editTextName);
        final EditText emailStudent = (EditText) layout.findViewById(R.id.editTextEmail);
        final EditText telStudent = (EditText) layout.findViewById(R.id.editTextTel);
        nameStudent.setText(students.get(positionPopup).getName());
        emailStudent.setText(students.get(positionPopup).getEmail());
        telStudent.setText(students.get(positionPopup).getTel());
        Log.d("Name: ", "" + students.get(positionPopup));
        Button save = (Button) layout.findViewById(R.id.save_popup);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = nameStudent.getText().toString();
                String emailStr = emailStudent.getText().toString();
                String telStr = telStudent.getText().toString();
                Student student = students.get(positionPopup);
                student.setName(nameStr);
                student.setEmail(emailStr);
                student.setTel(telStr);
                db.update(student);
                students = (ArrayList) db.getAllStudent();
                notifyDataSetChanged();
                for (Student student1 : students) {
                    String log = "Id: " + student1.getId() + " ,Name: " + student1.getName() + " ,Email: " + student1.getEmail() + " ,Telephone: " + student1.getTel();
                    // Writing Countries to log
                    Log.d("Name: ", log);
                }
                pwindo.dismiss();
            }
        });
    }
}
