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