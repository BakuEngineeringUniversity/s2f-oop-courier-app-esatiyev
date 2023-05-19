import java.lang.Package

abstract class Person(
    private var name: String,
    private var surname: String,
    private var age: Int,
    private var gender: String,
    private var phone: String,
    private var email: String
) {
    fun getName(): String = name

    fun getSurname(): String = surname
    fun getEmail(): String = email
}

class User(
    name: String,
    surname: String,
    age: Int,
    gender: String,
    phone: String,
    email: String,
    private var password: String
) : Person(name, surname, age, gender, phone, email) {
    fun getPassword(): String = password
    fun setPassword(value: String) {
        this.password = value
    }

    var packages = ArrayList<Package>()

    fun addPackage(packet: Package) {
        packages.add(packet)
    }

    fun removePackage(packet: Package) {
        packages.remove(packet)
    }
}