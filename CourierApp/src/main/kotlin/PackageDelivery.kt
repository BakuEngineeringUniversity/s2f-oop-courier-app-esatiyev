interface PackageDelivery {
    fun getEstimatedDeliveryTime(distance: Int): String
    fun deliverPackage()
}