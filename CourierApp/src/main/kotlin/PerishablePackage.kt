import java.time.LocalDate
import kotlin.random.Random

class PerishablePackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    deliveryMethod: DeliveryMethod,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE,
    private var shelfLife: Int = Random.nextInt(10, 365),
    private var productionDate: LocalDate = LocalDate.now(),
    private var isPerishable: Boolean = true
) : Package(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod, step), PackageDelivery {

    fun getShelfLife(): Int = shelfLife

    fun getProductionDate(): LocalDate = productionDate

    fun getIsPerishable(): Boolean = isPerishable
    fun setIsPerishable(isPerishable: Boolean) {
        this.isPerishable = isPerishable
    }


    override fun deliverPackage() {
        step = DeliveryStatus.DELIVERED
        println("Package delivered on ${LocalDate.now()}")
        println("Rate your regular delivery experience: ")
    }
}