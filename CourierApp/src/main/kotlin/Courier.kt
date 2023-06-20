class Courier(
    private var name: String,
    private var pricePerKg: Float,
    private var password: String
    ) {

    var phone: String = ""
    var email: String = ""

    fun getPassword(): String = password
    fun setPassword(value: String) {
        password = value
    }
    fun getCourierName(): String = name
    fun setCourierName(value: String) {
        name = value
    }

    fun getPricePerKg(): Float = pricePerKg
    fun setPricePerKg(value: Float) {
        if(value < 0) pricePerKg = 10f
        else pricePerKg = value
    }


    var users = ArrayList<User>()
    var packages = ArrayList<Package>()

    fun createUser(user: User) {
        users.add(User(user.getName(), user.getSurname(), user.getAge(), user.getGender(),
                       user.getPhone(), user.getEmail(), user.getAddress(), user.getPersonalNo(), user.getPassword()))
    }
    fun deleteUser(user: User) {
        for (row in users) {
            if (row.getEmail() == user.getEmail())
                users.remove(user)
        }
    }

    fun addPackage(packet: Package, user: User) {
        // User's packages seperately
        for (row in users){
            if(row.getEmail() == user.getEmail()) {
                row.packages.add(packet)
            }
        }
        // All packages in courier
        packages.add(packet)
    }

    fun removePackage(packet: Package, user: User) {
        // User's packages
        for (row in users) {
            if (row.getEmail() == user.getEmail()) {
                row.packages.remove(packet)
            }
        }

        // All packages in courier
        packages.remove(packet)
    }


    fun getTotalRevenue(): Float {
        var totalRevenue: Float = 0f
        for (packet in packages) {
            totalRevenue += packet.getWeight() * pricePerKg
        }
        return String.format("%.2f", totalRevenue).toFloat()
    }

    fun calculateDeliveryCost(trackingNumber: String): Any {
        for (row in packages) {
            if(row.getTrackingNumber() == trackingNumber) {
                return String.format("%.2f", pricePerKg * row.getWeight()).toFloat()
            }
        }

        return "Invalid tracking number!!!"
    }
}