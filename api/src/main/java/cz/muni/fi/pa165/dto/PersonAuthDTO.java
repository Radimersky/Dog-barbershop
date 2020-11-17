package cz.muni.fi.pa165.dto;

import java.util.Objects;

public class PersonAuthDTO {

    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonAuthDTO)) return false;
        PersonAuthDTO that = (PersonAuthDTO) o;
        return Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhone(), getPassword());
    }
}
