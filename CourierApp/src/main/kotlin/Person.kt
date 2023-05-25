abstract class Person(
    private var name: String,
    private var surname: String,
    private var age: Int,
    private var gender: String,
    private var phone: String,
    private var email: String,
    private var address: String
) {
    fun getName(): String = name
    fun getSurname(): String = surname
    fun getAge(): Int = age
    fun getGender(): String = gender
    fun getPhone(): String = phone
    fun setPhone(phone: String) {
        this.phone = phone
    }
    fun getEmail(): String = email

    fun getAddress(): String = address
    fun setAddress(address: String) {
        this.address = address
    }
}