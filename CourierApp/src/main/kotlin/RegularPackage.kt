import java.time.LocalDate

open class RegularPackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    deliveryMethod: String,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE
) : Package(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod, step), PackageDelivery{

    override fun getEstimatedDeliveryTime(distance: Int): String{
        var averageSpeed: Int = 150

        return if(distance / averageSpeed == 0 || distance / averageSpeed == 1) "1 day" else "${distance / averageSpeed} days"
    }

    override fun deliverPackage() {
        step = DeliveryStatus.DELIVERED
        println("Package delivered on ${LocalDate.now().toString()}")
        println("Rate your regular delivery experience: ")
    }
}