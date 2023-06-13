abstract class Package(
    paackageName: String,
    trackingNumber: String,
    sender: String,
    recipient: String,
    weight: Float,
    price: Float,
    var deliveryMethod: DeliveryMethod,
    var step: DeliveryStatus = DeliveryStatus.IN_OVERSEAS_WAREHOUSE
) : PackageDelivery  {
    private var packageName: String? = paackageName
    fun getPackageName() = packageName
    fun setPackageName(value: String) {
        packageName = value
    }

    private var trackingNumber: String? = trackingNumber
    fun getTrackingNumber(): String? = trackingNumber
    fun setTrackingNumber(value: String) {
        trackingNumber = value
    }

    private var sender: String = sender
    fun getSender() = sender
    fun setSender(value: String) {
        sender = value
    }

    private var recipient: String = recipient
    fun getRecipient(): String = recipient
    fun setRecipient(value: String) {
        recipient = value
    }

    private var weight: Float = weight
    fun getWeight(): Float = weight
    fun setWeight(value: Float) {
        weight = if(value <= 0) 1f
        else value
    }

    private var price: Float = price
    fun  getPrice(): Float = price
    fun setPrice(value: Float) {
        price = if(value <= 0) 0f
        else value
    }

    fun updatePackageStep(value: Int){
        val statusValues = DeliveryStatus.values()
        if (value < statusValues.size) {
            this.step = statusValues.elementAt(value)
        }
        else {
            println("This package is already delivered.")
        }

    }
    fun trackPackage() : Unit = println("Package with tracking number $trackingNumber is currently $step.")

    override fun getEstimatedDeliveryTime(distance: Int): String = "" // it will be overrided

    override fun deliverPackage() {} // it will be overrided
}