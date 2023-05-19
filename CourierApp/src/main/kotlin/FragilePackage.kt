import java.time.LocalDate

class FragilePackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    isFragile: Boolean,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE
) : Package(packageName, trackingNumber, sender, recipient, weight, price,  step), PackageDelivery {
    protected var isFragile = isFragile
    fun getIsFragile(): Boolean = isFragile
    fun setIsFragile(value: Boolean) {
        isFragile = value
    }

    override fun getEstimatedDeliveryTime(distance: Int): String{
        var averageSpeed: Int = 250
        return if(distance / averageSpeed == 0 || distance / averageSpeed == 1) "1 day" else "${distance / averageSpeed} days"
    }

    override fun deliverPackage() {
        step = DeliveryStatus.DELIVERED
        println("Package delivered on ${LocalDate.now().toString()}")
        println("Rate your fragile package delivery experience: ")
    }
}