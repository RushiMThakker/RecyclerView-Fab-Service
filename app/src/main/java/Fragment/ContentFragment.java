package Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import astoundtechnology.recyclerview.R;

/**
 * Created by Astound Rushi on 15-07-2016.
 */
public class ContentFragment extends Fragment
{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapater;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private List<Employee> employeeList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.content_fragment, container, false);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        recyclerView = (RecyclerView) view.findViewById(R.id.employeeName);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapater = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(recyclerViewAdapater);

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                refreshData();
            }
        });

        return view;
    }

    private void prepareEmployeeList(JSONArray empList)
    {
        employeeList.clear();
        for (int i = 0; i < empList.length(); i++)
        {
            try
            {
                JSONObject emp = empList.getJSONObject(i);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date joinDate = dateFormat.parse(emp.getString("Employee_Date"));
                Employee current_emp =
                        new Employee(emp.getString("Employee_Name"), emp.getString("Employee_Designation"),
                                joinDate);
                employeeList.add(current_emp);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void refreshData()
    {
        WebCall webCall = new WebCall();
        webCall.execute();
    }

    private class WebCall extends AsyncTask
    {
        private ProgressDialog mProgressDialog;

        @Override
        protected Object doInBackground(Object[] params)
        {
            HttpCalls callService = new HttpCalls();
            String jsonString = callService.getJSON();
            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("Employee");
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
            mProgressDialog = new ProgressDialog(getActivity());
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
