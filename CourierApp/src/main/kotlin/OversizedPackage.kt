
import java.time.LocalDate
import kotlin.random.Random

class OversizedPackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    deliveryMethod: DeliveryMethod,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE,
    private var length: Int = Random.nextInt(80,500),
    private var width: Int = kotlin.random.Random.nextInt(80,500),
    private var height: Int = Random.nextInt(80,500)
) : Package(packageName, trackingNumber, sender, recipient, weight, price, deliveryMethod, step), PackageDelivery {

    fun getPackageLength(): Int = length
    fun setPackageLength(length: Int) {
        this.length = length
    }

    fun getWidth(): Int = width
    fun setWidth(width: Int) {
        this.width = width
    }

    fun getHeight(): Int = height
    fun setHeight(height: Int) {
        this.height = height
    }

    fun getVolume(): Int = length * width * height

    fun getLength(): Int = length
    fun setLength(length: Int) {
        this.length = length
    }

//    override fun getEstimatedDeliveryTime(trackingNumber: String): String{
//        val averageSpeed: Int = 100
////        for (row in )
//        return if(distance / averageSpeed == 0 || distance / averageSpeed == 1) "1 day" else "${distance / averageSpeed} days"
//    }

    override fun deliverPackage() {
        step = DeliveryStatus.DELIVERED
        println("Package delivered on ${LocalDate.now().toString()}")
        println("Rate your regular delivery experience: ")
    }
}