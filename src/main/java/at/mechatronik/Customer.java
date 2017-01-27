package at.mechatronik;

/**
 * Created by toriser on 19.01.17.
 */
public class Customer {

    private Long id;
    private String name1, name2, name3, address1, address2, address3, phone, email;

    public Customer(final Long id, final String name1, final String address1) {
        this.id = id;
        this.name1 = name1;
        this.address1 = address1;
    }

    public Customer(final Long id,
                    final String name1,
                    final String name2,
                    final String name3,
                    final String address1,
                    final String address2,
                    final String address3,
                    final String phone,
                    final String email) {
        this(id, name1, address1);
        setName2(name2);
        setName3(name3);
        setAddress2(address2);
        setAddress3(address3);
        setPhone(phone);
        setEmail(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
