abstract class Person(
    private var name: String,
    private var surname: String,
    private var age: Int,
    private var gender: String,
    private var phone: String,
    private var email: String
) {

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
}

