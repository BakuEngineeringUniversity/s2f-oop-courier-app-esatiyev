class User(
    name: String,
    surname: String,
    age: Int,
    gender: String,
    phone: String,
    email: String,
    address: String,
    private var personalNo: String,
    private var password: String
) : Person(name, surname, age, gender, phone, email, address) {
    fun getPassword(): String = password
    fun setPassword(password: String) {
        this.password = password
    }

    fun getPersonalNo(): String = personalNo
    fun setPersonalNo(personalNo: String) {
        this.personalNo = personalNo
    }

    var packages = ArrayList<Package>()

    fun addPackage(packet: Package) {
        packages.add(packet)
    }

    fun removePackage(packet: Package) {
        packages.remove(packet)
    }
}