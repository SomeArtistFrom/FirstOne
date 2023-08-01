package jm.task.core.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
       // return "User с именем - " + getName()  + " добавлен в базу данных";
        return "В базе есть : " + getName() ;
    }

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//
//        result = prime * result + ((age == null) ? 0 : age.hashCode());
//        result = prime * result + ((name == null) ? 0 : name.hashCode());
//        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
//
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//
//        if (obj == null) {
//            return false;
//        }
//
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//
//        User other = (User) obj;
//
//        if (age != other.getAge()) {
//            return false;
//        }
//
//        if (name == null) {
//            if (other.getName() != null) {
//                return false;
//            }
//        } else if (!name.equals(other.getName())) {
//            return false;
//        }
//
//        if (lastName == null) {
//            if (other.getLastName() != null) {
//                return false;
//            }
//        } else if (!lastName.equals(other.getLastName())) {
//            return false;
//        }
//
//        return true;
//    }
}
