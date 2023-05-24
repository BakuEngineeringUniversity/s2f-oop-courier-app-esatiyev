import java.time.LocalDate
import kotlin.random.Random

class PerishablePackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE,
    private var shelfLife: Int = Random.nextInt(10, 365),
    private var productionDate: LocalDate = LocalDate.now(),
    private var isPerishable: Boolean = true
) : Package(packageName, trackingNumber, sender, recipient, weight, price, step), PackageDelivery {

    fun getShelfLife(): Int = shelfLife

    fun getProductionDate(): LocalDate = productionDate

    fun getIsPerishable(): Boolean = isPerishable
    fun setIsPerishable(isPerishable: Boolean) {
        this.isPerishable = isPerishable
    }

    override fun getEstimatedDeliveryTime(distance: Int): String{
        var averageSpeed: Int = 100

        return if(distance / averageSpeed == 0 || distance / averageSpeed == 1) "1 day" else "${distance / averageSpeed} days"
    }

    override fun deliverPackage() {
        step = DeliveryStatus.DELIVERED
        println("Package delivered on ${LocalDate.now()}")
        println("Rate your regular delivery experience: ")
    }
}