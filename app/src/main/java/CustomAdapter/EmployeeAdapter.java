package CustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.Employee;
import astoundtechnology.recyclerview.R;

/**
 * Created by Astound Rushi on 13-07-2016.
 */
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>
{
    List<Employee> empList;
    public EmployeeAdapter(List<Employee> empList)
    {
        this.empList = empList;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView empName,empDesig,joinDate;
        public Date empJoinDate;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            empName=(TextView)itemView.findViewById(R.id.tvName);
            empDesig=(TextView)itemView.findViewById(R.id.tvDesignation);
            joinDate=(TextView)itemView.findViewById(R.id.tvJoiningDate);
            empJoinDate=new Date();
        }
    }

    @Override
    public int getItemCount()
    {
        return empList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Employee e=empList.get(position);

        holder.empName.setText(e.getEmployeeName());
        holder.empDesig.setText(e.getEmployeeDesignation());
        holder.empJoinDate=e.getJoiningDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.joinDate.setText(""+ dateFormat.format(holder.empJoinDate));
    }

}


