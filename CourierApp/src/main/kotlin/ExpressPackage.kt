import java.time.LocalDate

class ExpressPackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE
) : RegularPackage(packageName, trackingNumber, sender, recipient, weight, price, step), PackageDelivery {
    private var deliveryTime: String? = null
    fun getDeliveryTime(): String? = deliveryTime
    fun setDeliveryTime(value: String) {
        deliveryTime = value
    }

    override fun getEstimatedDeliveryTime(distance: Int): String{
        var averageSpeed: Int = 250
        return if(distance / averageSpeed == 0 || distance / averageSpeed == 1) "1 day" else "${distance / averageSpeed} days"
    }

    override fun deliverPackage() {
        deliveryTime = LocalDate.now().toString()
        step = DeliveryStatus.DELIVERED
        println("Package delivered on $deliveryTime")
        println("Rate your express delivery experience: ")
    }
}