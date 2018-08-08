package nnk.com.cwp11;

/**
 * Created by Achilles on 8/7/2018.
 */
public class Reminder
{
    public String rem_name,rem_phone,rem_birthday,rem_message;
    public Reminder(String rem_name,String rem_phone,String rem_birthday,String rem_message)
    {
        this.rem_name=rem_name;
        this.rem_phone=rem_phone;
        this.rem_birthday=rem_birthday;
        this.rem_message=rem_message;
    }

    public String getRem_name()
    {
        return rem_name;
    }
    public String getRem_phone()
    {
        return rem_phone;
    }
    public String getRem_birthday()
    {
        return rem_birthday;
    }
    public String getRem_message()
    {
        return rem_message;
    }
}
