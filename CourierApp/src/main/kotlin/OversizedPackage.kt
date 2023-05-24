import java.time.LocalDate
import kotlin.random.Random
class OversizedPackage(
    packageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE,
    private var length: Float,
    private var width: Float,
    private var height: Float
) : Package(packageName, trackingNumber, sender, recipient, weight, price, step), PackageDelivery {

    val rangeStart = 0.1f
    val rangeStop = 101f
    init {
        length = String.format("%.2f", Random.nextFloat() * (rangeStop - rangeStart) + rangeStart).toFloat()
        width =  String.format("%.2f", Random.nextFloat() * (rangeStop - rangeStart) + rangeStart).toFloat()
        height = String.format("%.2f", Random.nextFloat() * (rangeStop - rangeStart) + rangeStart).toFloat()
    }
    fun getPackageLength(): Float = length
    fun setPackageLength(length: Float) {
        this.length = length
    }

    fun getWidth(): Float = width
    fun setWidth(width: Float) {
        this.width = width
    }

    fun getHeight(): Float = height
    fun setHeight(height: Float) {
        this.height = height
    }

    fun getVolume(): Float = String.format("%.2f", length * width * height).toFloat()

    fun getLength(): Float = length
    fun setLength(length: Float) {
        this.length = length
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