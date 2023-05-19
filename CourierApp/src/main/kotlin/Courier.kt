class Courier(
    private var name: String,
    private var pricePerKg: Float,
    )  {

    var phone: String = ""
    var email: String = ""

    fun getCourierName(): String = name
    fun setCourierName(value: String) {
        name = value
    }

    fun getPricePerKg(): Float = pricePerKg
    fun setPricePerKg(value: Float) {
        if(value < 0) pricePerKg = 10f
        else pricePerKg = value
    }


    var packages = ArrayList<Package>()

    fun addPackage(packet: Package) {
        packages.add(packet)
    }

    fun removePackage(packet: Package) {
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
        for (row in packages){
            if(row.getTrackingNumber() == trackingNumber) {
                return String.format("%.2f",pricePerKg * row.getWeight()).toFloat()
            }
        }
        return "Invalid tracking number!!!"
    }
}