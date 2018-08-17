package nnk.com.cwp11;

/**
 * Created by Achilles on 8/7/2018.
 */
public class Reminder
{
    public int ID;
    public String rem_name,rem_phone,rem_birthday,rem_message;
    public Reminder(int ID,String rem_name,String rem_phone,String rem_message,String rem_birthday)
    {
        this.ID=ID;
        this.rem_name=rem_name;
        this.rem_phone=rem_phone;
        this.rem_birthday=rem_birthday;
        this.rem_message=rem_message;
    }
    public Reminder(String rem_name,String rem_phone,String rem_message,String rem_birthday)
    {
        this.rem_name=rem_name;
        this.rem_phone=rem_phone;
        this.rem_birthday=rem_birthday;
        this.rem_message=rem_message;
    }

    public int getID()
    {
        return ID;
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
