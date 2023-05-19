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