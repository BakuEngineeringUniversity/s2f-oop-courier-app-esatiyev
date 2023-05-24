import java.time.LocalDate
class HazardousPackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE,
    private var isAllow: Boolean = true
) : Package(packageName, trackingNumber, sender, recipient, weight, price, step), PackageDelivery {
    fun getIsAllow(): Boolean = isAllow
    fun setIsAllow(isAllow: Boolean) {
        this.isAllow = isAllow
    }

    override fun getEstimatedDeliveryTime(distance: Int): String{
        var averageSpeed: Int = 100

        return if(distance / averageSpeed == 0 || distance / averageSpeed == 1) "1 day" else "${distance / averageSpeed} days"
    }

    override fun deliverPackage() {
        step = DeliveryStatus.DELIVERED
        println("Package delivered on ${LocalDate.now().toString()}")
        println("Rate your regular delivery experience: ")
    }
}