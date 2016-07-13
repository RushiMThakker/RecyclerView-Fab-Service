package astoundtechnology.recyclerview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import CustomAdapter.EmployeeAdapter;
import Model.Employee;
import Service.HttpCalls;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapater;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private List<Employee> employeeList=new ArrayList<Employee>();
    private FloatingActionButton fab;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.employeeName);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapater = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(recyclerViewAdapater);

        fab=(FloatingActionButton)findViewById(R.id.floatingActionButton);
    }

    private void prepareEmployeeList(JSONArray empList)
    {
        employeeList.clear();

        for(int i=0;i<empList.length();i++)
        {
            try
            {
                JSONObject emp=empList.getJSONObject(i);

                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

                Date joinDate=dateFormat.parse(emp.getString("Employee_Date"));

                Employee current_emp=
                        new Employee(emp.getString("Employee_Name"),emp.getString("Employee_Designation"),
                                joinDate);
                employeeList.add(current_emp);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void refreshData(View v)
    {
        WebCall webCall=new WebCall();
        webCall.execute();

    }

    private class WebCall extends AsyncTask
    {
        private ProgressDialog mProgressDialog;

        @Override
        protected Object doInBackground(Object[] params)
        {
            HttpCalls callService=new HttpCalls();
            String jsonString=callService.getJSON();
            try
            {
                jsonObject=new JSONObject(jsonString);
                jsonArray=jsonObject.getJSONArray("Employee");
                prepareEmployeeList(jsonArray);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute()
        {
            mProgressDialog=new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Refreshing Data");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();

        }

        @Override
        protected void onPostExecute(Object o)
        {
            mProgressDialog.dismiss();
            recyclerViewAdapater.notifyDataSetChanged();
        }
    }
}

