package nnk.com.cwp11;

/**
 * Created by Achilles on 8/3/2018.
 */
public class ContactModel
{
    private String name,phone_number;
    public ContactModel()
    {

    }
    public ContactModel(String name,String phone_number)
    {
        this.name=name;
        this.phone_number=phone_number;
    }
    //getter methods

    public String getName()
    {
        return name;
    }
    public String getPhone_number()
    {
        return phone_number;
    }
}
